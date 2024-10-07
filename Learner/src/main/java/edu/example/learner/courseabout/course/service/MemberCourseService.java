package edu.example.learner.courseabout.course.service;

import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.MemberCourse;
import edu.example.learner.courseabout.course.repository.CourseRepository;
import edu.example.learner.courseabout.course.repository.MemberCourseRepository;
import edu.example.learner.courseabout.exception.CourseException;
import edu.example.learner.member.entity.Member;
import edu.example.learner.member.exception.MemberException;
import edu.example.learner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberCourseService {
    private final MemberCourseRepository memberCourseRepository;
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberCourse purchaseCourse(Long memberId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseException.COURSE_NOT_FOUND::get);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MEMBER_NOT_FOUND::getMemberTaskException);
        MemberCourse memberCourse = MemberCourse.builder()
                .member(member) // 위에서 가져온 member
                .course(course) // 위에서 가져온 course
                .purchaseDate(new Date())
                .build();

        return memberCourseRepository.save(memberCourse);
    }
}
