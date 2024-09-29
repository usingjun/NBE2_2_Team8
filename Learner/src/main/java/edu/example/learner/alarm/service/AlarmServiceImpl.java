package edu.example.learner.alarm.service;

import edu.example.learner.alarm.dto.AlarmDTO;
import edu.example.learner.alarm.repository.AlarmRepository;
import edu.example.learner.course.service.TemporaryMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;

    private final TemporaryMemberRepository temporaryMemberRepository;

    @Override
    public AlarmDTO findByAlarmTitle(String alarmTitle) {
        return null;
    }

    @Override
    public AlarmDTO read(Long alarmId) {
        return null;
    }

    @Override
    public List<AlarmDTO> findAllAlarms() {
        return List.of();
    }

    @Override
    public AlarmDTO add(AlarmDTO alarmDTO) {
        return null;
    }

    @Override
    public AlarmDTO update(AlarmDTO alarmDTO) {
        return null;
    }

    @Override
    public AlarmDTO delete(Long alarmId) {
        return null;
    }
}
