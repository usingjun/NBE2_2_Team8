package edu.example.learner.service;

import edu.example.learner.dto.InquiryDTO;
import edu.example.learner.entity.Inquiry;
import edu.example.learner.entity.InquiryStatus;
import edu.example.learner.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;

    @Override
    public InquiryDTO read(Long inquiryId) {
        return new InquiryDTO(inquiryRepository.findById(inquiryId).orElseThrow());
    }

    @Override
    public List<InquiryDTO> readAll() {
        return inquiryRepository.findAll().stream().map(InquiryDTO::new).collect(Collectors.toList());
    }

    @Override
    public InquiryDTO register(InquiryDTO inquiryDTO) {
        try {
            Inquiry savedInquiry = inquiryRepository.save(inquiryDTO.toEntity());
            return new InquiryDTO(savedInquiry);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public InquiryDTO update(InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryDTO.getInquiryId()).orElseThrow();
        try {
            inquiry.changeInquiryTitle(inquiryDTO.getInquiryTitle());
            inquiry.changeInquiryContent(inquiryDTO.getInquiryContent());
            inquiry.changeInquiryStatus(InquiryStatus.valueOf(inquiryDTO.getInquiryStatus()));
            return new InquiryDTO(inquiry);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long inquiryId) {
        inquiryRepository.findById(inquiryId).orElseThrow();
        try {
            inquiryRepository.deleteById(inquiryId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
