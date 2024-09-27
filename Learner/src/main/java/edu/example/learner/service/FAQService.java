package edu.example.learner.service;

import edu.example.learner.dto.FAQDTO;
import edu.example.learner.entity.FAQCategory;

import java.util.List;

public interface FAQService {
    FAQDTO read(Long faqId);

    List<FAQDTO> readAll();

    List<FAQDTO> readByCategory(String faqCategory);

    FAQDTO register(FAQDTO faqDTO);

    FAQDTO update(FAQDTO faqDTO);

    void delete(Long faqId);
}
