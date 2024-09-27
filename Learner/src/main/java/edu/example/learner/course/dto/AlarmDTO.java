package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Alarm;
import edu.example.learner.course.entity.Priority;
import edu.example.learner.course.entity.TemporaryMember;
import edu.example.learner.entity.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlarmDTO {

    private Long alarmId;

    private Long memberId;

    private String alarmContent;

    private String alarmTitle;

    private String alarmType;

    private LocalDateTime createdAt; // 생성 시간

    private String priority;

    private boolean alarmStatus;

    public Alarm toEntity() {
        return Alarm.builder()
                .alarmId(alarmId)
                .alarmContent(alarmContent)
                .alarmTitle(alarmTitle)
                .alarmStatus(alarmStatus)
                .priority(Priority.valueOf(priority))
                .createdAt(createdAt)
                .temporaryMember(TemporaryMember.builder().id(memberId)
                        .build())
                .build();
    }
    public AlarmDTO(Alarm alarm) {
        this.alarmId = alarm.getAlarmId();
        this.memberId = alarm.getTemporaryMember().getId();
        this.alarmContent = alarm.getAlarmContent();
        this.alarmTitle = alarm.getAlarmTitle();
        this.priority = alarm.getPriority().name();
        this.alarmStatus = alarm.isAlarmStatus();
        this.createdAt = alarm.getCreatedAt();
    }

}

