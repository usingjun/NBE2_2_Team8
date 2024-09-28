package edu.example.learner.course.service;


import edu.example.learner.course.dto.AlarmDTO;

import java.util.List;

public interface AlarmService {
    public AlarmDTO findByAlarmTitle(String alarmTitle);
    public AlarmDTO read(Long alarmId);
    public List<AlarmDTO> findAllAlarms();
    public AlarmDTO add(AlarmDTO alarmDTO);
    public AlarmDTO update(AlarmDTO alarmDTO);
    public AlarmDTO delete(Long alarmId);
}
