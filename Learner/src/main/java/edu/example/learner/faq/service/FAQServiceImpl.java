package edu.example.learner.faq.service;

import edu.example.learner.faq.dto.FAQDTO;
import edu.example.learner.faq.entity.FAQ;
import edu.example.learner.faq.repository.FAQRepository;
import edu.example.learner.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class FAQServiceImpl implements FAQService {
    private final FAQRepository faqRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public FAQDTO read(Long faqId) {
        return new FAQDTO(faqRepository.findById(faqId).orElseThrow());
    }

    @Override
    public List<FAQDTO> readByCategory(String faqCategory) {
        return faqRepository.findByFaqCategory(faqCategory).stream().map(FAQDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<FAQDTO> readAll() {
        return faqRepository.findAll().stream().map(FAQDTO::new).collect(Collectors.toList());
    }

    @Override
    public FAQDTO register(FAQDTO faqDTO) {
        try {
            FAQ savedFAQ = faqRepository.save(faqDTO.toEntity());
            return new FAQDTO(savedFAQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public FAQDTO update(FAQDTO faqDTO) {
        FAQ faq = faqRepository.findById(faqDTO.getFaqId()).orElseThrow();
        try {
            faq.changeFaqTitle(faqDTO.getFaqTitle());
            faq.changeFaqContent(faqDTO.getFaqContent());
            faq.changeFaqCategory(faqDTO.getFaqCategory());
            return new FAQDTO(faq);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long faqId) {
        faqRepository.findById(faqId).orElseThrow();
        try {
            faqRepository.deleteById(faqId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
