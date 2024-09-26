package edu.example.learner.service;

import edu.example.learner.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {
    AnswerDTO read(Long answerId);

    List<AnswerDTO> readAll();

    AnswerDTO register(AnswerDTO answerDTO);

    AnswerDTO update(AnswerDTO answerDTO);

    void delete(Long answerId);
}
