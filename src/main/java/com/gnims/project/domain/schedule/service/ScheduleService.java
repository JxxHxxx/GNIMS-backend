package com.gnims.project.domain.schedule.service;

import com.gnims.project.domain.event.entity.Event;
import com.gnims.project.domain.event.repository.EventRepository;
import com.gnims.project.domain.schedule.dto.*;
import com.gnims.project.domain.schedule.entity.Schedule;
import com.gnims.project.domain.schedule.repository.ScheduleRepository;
import com.gnims.project.domain.user.entity.User;
import com.gnims.project.domain.user.repository.UserRepository;
import com.gnims.project.util.embedded.Appointment;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public void makeSchedule(ScheduleForm form) {
        //이벤트 엔티티 생성 및 저장
        Appointment appointment = new Appointment(form);
        Event event = new Event(appointment, form);
        Event saveEvent = eventRepository.save(event);

        //초대된 사용자 목록
        List<User> users = userRepository.findAllById(form.getParticipantsId());

        //User 들이 팔로우 인지 확인할 필요성 있음 (개선 버전에서 추가)

        //스케쥴 엔티티 생성 및 저장
        List<Schedule> schedules = users.stream().map(user -> new Schedule(user, saveEvent))
                .collect(Collectors.toList());

        //스케줄 생성자는 isAccepted = true
        Long eventMakerId = event.getCreateBy();
        Schedule eventMakerSchedule = schedules.stream().filter(s -> s.receiveUserId().equals(eventMakerId))
                .findFirst().get();
        eventMakerSchedule.acceptSchedule();

        scheduleRepository.saveAll(schedules);
    }

    public List<ReadPastAllResponse> readAllSchedule(Long userId) {
        List<Schedule> schedules = scheduleRepository.findAllByUser_IdAndIsAcceptedIs(userId, true);

        List<Schedule> liveSchedules = filterDeletedSchedules(schedules);

        return liveSchedules.stream().map(s -> new ReadPastAllResponse(
                        s.getEvent().getId(),
                        s.getEvent().receiveDate(),
                        s.getEvent().receiveTime(),
                        s.getEvent().getCardColor(),
                        s.getEvent().getSubject(),
                        s.findInvitees()
                )).collect(Collectors.toList());
    }

    public ReadOneResponse readOneSchedule(Long eventId) {
        List<Schedule> schedules = scheduleRepository.findAllByEvent_IdAndIsAcceptedIs(eventId, true);

        Event event = schedules.get(0).getEvent();

        checkIsDeleted(event);

        List<ReadOneUserDto> invitees = schedules.stream()
                .filter(s -> s.getIsAccepted().equals(true))
                .map(s -> new ReadOneUserDto(s.getUser().getUsername()))
                .collect(Collectors.toList());

        return new ReadOneResponse(
                event.getId(),
                event.receiveDate(),
                event.receiveTime(),
                event.getCardColor(),
                event.getSubject(),
                event.getContent(),
                invitees);
    }

    public List<ReadPastAllResponse> readPendingSchedule(Long userId) {
        List<Schedule> schedules = scheduleRepository.findAllByUser_IdAndIsAcceptedIs(userId, false);

        List<Schedule> liveSchedules = filterDeletedSchedules(schedules);

        return liveSchedules.stream().map(s -> new ReadPastAllResponse(
                s.getEvent().getId(),
                s.getEvent().receiveDate(),
                s.getEvent().receiveTime(),
                s.getEvent().getCardColor(),
                s.getEvent().getSubject(),
                s.findInvitees())).collect(Collectors.toList());
    }

    public List<ReadPastAllResponse> readPastSchedule(Long userId) {
        List<Schedule> schedules = scheduleRepository.findAllByUser_IdAndIsAcceptedIs(userId, true);
        List<Schedule> pastSchedules = schedules.stream().filter(s -> s.getEvent().getIsDeleted().equals(false))
                .filter(s -> ChronoUnit.DAYS.between(LocalDate.now(), s.getEvent().receiveDate()) < 0)
                .collect(Collectors.toList());

        return pastSchedules.stream().map(s -> new ReadPastAllResponse(
                s.getEvent().getId(),
                s.getEvent().receiveDate(),
                s.getEvent().receiveTime(),
                s.getEvent().getCardColor(),
                s.getEvent().getSubject(),
                s.findInvitees())).collect(Collectors.toList());
    }

    @Transactional
    public void acceptSchedule(Long userId, Long eventId) {
        Schedule schedule = scheduleRepository.findByUser_IdAndEvent_Id(userId, eventId).get();
        Event event = schedule.getEvent();

        checkIsDeleted(event);

        schedule.acceptSchedule();
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void softDeleteSchedule(Long userId, Long eventId) {
        Event event = eventRepository.findByCreateByAndId(userId, eventId).orElseThrow(
                () -> new SecurityException("삭제 권한이 없습니다."));

        checkIsDeleted(event);

        event.removeEvent();
        eventRepository.save(event);
    }

    @Transactional
    public void updateSchedule(Long userId, UpdateForm updateForm, Long eventId) {
        Event event = eventRepository.findByCreateByAndId(userId, eventId).orElseThrow(
                () -> new SecurityException("수정 권한이 없습니다."));

        checkIsDeleted(event);

        event.updateEvent(updateForm);
        eventRepository.save(event);
    }

    private static void checkIsDeleted(Event event) {
        if (event.getIsDeleted().equals(true)) {
            throw new IllegalArgumentException("이미 삭제된 일정입니다.");
        }
    }

    private static List<Schedule> filterDeletedSchedules(List<Schedule> schedules) {
        List<Schedule> liveSchedules = schedules.stream()
                .filter(s -> s.getEvent().getIsDeleted().equals(false))
                .collect(Collectors.toList());
        return liveSchedules;
    }
}
