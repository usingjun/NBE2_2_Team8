package edu.example.learner.alarm.service;


import edu.example.learner.alarm.dto.AlarmDTO;

import java.util.List;

public interface AlarmService {
    public AlarmDTO findByAlarmTitle(String alarmTitle);
    public AlarmDTO read(Long alarmId);
    public List<AlarmDTO> findAllAlarms();
    public AlarmDTO add(AlarmDTO alarmDTO);
    public AlarmDTO update(AlarmDTO alarmDTO);
    public AlarmDTO delete(Long alarmId);
}
