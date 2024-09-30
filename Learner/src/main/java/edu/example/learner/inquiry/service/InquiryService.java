package edu.example.learner.inquiry.service;

import edu.example.learner.inquiry.dto.InquiryDTO;

import java.util.List;

public interface InquiryService {
    InquiryDTO read(Long inquiryId);

    List<InquiryDTO> readAll();

    InquiryDTO register(InquiryDTO inquiryDTO);

    InquiryDTO update(InquiryDTO inquiryDTO);

    void delete(Long inquiryId);
}
