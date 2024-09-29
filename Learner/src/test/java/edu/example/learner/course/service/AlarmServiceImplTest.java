package edu.example.learner.course.service;

import edu.example.learner.alarm.entity.Alarm;
import edu.example.learner.alarm.entity.AlarmType;
import edu.example.learner.alarm.service.AlarmService;
import edu.example.learner.course.entity.Priority;
import edu.example.learner.course.entity.TemporaryMember;
import edu.example.learner.alarm.repository.AlarmRepository;
import edu.example.learner.entity.Member;
import edu.example.learner.entity.Role;
import edu.example.learner.repository.MemberRepository;
import edu.example.learner.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Log4j2
@Transactional
@Rollback(false)
class AlarmServiceImplTest {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private TemporaryMemberService temporaryMemberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AlarmRepository alarmRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder().nickname("test")
                .role(Role.USER).email("test").build();
        memberRepository.save(member);
        Alarm alarm = Alarm.builder().alarmId(1L)
                .alarmTitle("test")
                .priority(Priority.LOW)
                .member(member)
                .alarmStatus(false)
                .alarmType(AlarmType.INQUIRY)
                .build();
        alarm = alarmRepository.save(alarm);

    }
    @Test
    void add(){

    }
    @Test
    void read(){
        Alarm alarm = alarmRepository.findById(1L).orElseThrow();
        System.out.println(alarm);
    }

}