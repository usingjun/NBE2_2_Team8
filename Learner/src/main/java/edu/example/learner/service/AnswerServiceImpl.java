package edu.example.learner.service;

import edu.example.learner.dto.AnswerDTO;
import edu.example.learner.entity.Answer;
import edu.example.learner.entity.InquiryStatus;
import edu.example.learner.repository.AnswerRepository;
import edu.example.learner.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public AnswerDTO read(Long answerId) {
        return new AnswerDTO(answerRepository.findById(answerId).orElseThrow());
    }

    @Override
    public List<AnswerDTO> readAll() {
        return answerRepository.findAll().stream().map(AnswerDTO::new).collect(Collectors.toList());
    }

    @Override
    public AnswerDTO register(AnswerDTO answerDTO) {
        try {
            Answer savedAnswer = answerRepository.save(answerDTO.toEntity());
            return new AnswerDTO(savedAnswer);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public AnswerDTO update(AnswerDTO answerDTO) {
        Answer answer = answerRepository.findById(answerDTO.getAnswerId()).orElseThrow();
        try {
            answer.changeAnswerContent(answerDTO.getAnswerContent());
            return new AnswerDTO(answer);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long answerId) {
        answerRepository.findById(answerId).orElseThrow();
        try {
            answerRepository.deleteById(answerId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
