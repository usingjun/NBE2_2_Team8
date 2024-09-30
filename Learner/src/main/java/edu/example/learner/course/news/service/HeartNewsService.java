package edu.example.learner.course.news.service;

import edu.example.learner.course.news.dto.HeartNewsReqDTO;
import edu.example.learner.course.news.entity.HeartNews;
import edu.example.learner.course.news.entity.NewsEntity;
import edu.example.learner.course.exception.HeartNewsAlreadyExistsException;
import edu.example.learner.course.exception.NotFoundException;
import edu.example.learner.course.news.repository.HeartNewsRepository;
import edu.example.learner.course.news.repository.NewsRepository;
import edu.example.learner.entity.Member;
import edu.example.learner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartNewsService {
    private final NewsRepository newsRepository;
    private final HeartNewsRepository heartNewsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void insert(HeartNewsReqDTO heartNewsReqDTO) throws Exception {
        Member member = memberRepository.findById(Math.toIntExact(heartNewsReqDTO.getMemberId()))   //임시
                .orElseThrow(() -> new NotFoundException("멤버아이디를 찾을 수 없습니다."));

        NewsEntity news = newsRepository.findById(heartNewsReqDTO.getNewsId())
                .orElseThrow(() -> new NotFoundException("새소식을 찾을 수 없습니다."));

        // 이미 좋아요 되어있으면 에러반환
        if (heartNewsRepository.findByMemberAndNewsEntity(member, news).isPresent()) {
            throw new HeartNewsAlreadyExistsException("이미 좋아요가 등록되어 있습니다.");
        }

        HeartNews heartNews = HeartNews.builder()
                .newsEntity(news)
                .member(member)
                .build();

        heartNewsRepository.save(heartNews);
        newsRepository.addLikeCount(news);
    }

    @Transactional
    public void delete(HeartNewsReqDTO heartNewsReqDTO) {
        Member member = memberRepository.findById(Math.toIntExact(heartNewsReqDTO.getMemberId()))   //임시
                .orElseThrow(() -> new NotFoundException("멤버아이디를 찾을 수 없습니다."));

        NewsEntity news = newsRepository.findById(heartNewsReqDTO.getNewsId())
                .orElseThrow(() -> new NotFoundException("새소식을 찾을 수 없습니다."));

        HeartNews heartNews = heartNewsRepository.findByMemberAndNewsEntity(member, news)
                .orElseThrow(() -> new NotFoundException("삭제할 좋아요가 없습니다."));

        heartNewsRepository.delete(heartNews);
        newsRepository.subLikeCount(news);
    }

}
