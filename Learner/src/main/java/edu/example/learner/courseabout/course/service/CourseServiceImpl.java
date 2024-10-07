package edu.example.learner.courseabout.course.service;

import edu.example.learner.courseabout.course.dto.CourseDTO;
import edu.example.learner.courseabout.course.dto.MemberCourseDTO;
import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.CourseAttribute;
import edu.example.learner.courseabout.course.entity.MemberCourse;
import edu.example.learner.courseabout.course.repository.CourseRepository;
import edu.example.learner.courseabout.course.repository.MemberCourseRepository;
import edu.example.learner.courseabout.exception.CourseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final MemberCourseRepository memberCourseRepository;

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = courseRepository.save(courseDTO.toEntity());
        if (course != null) {
            return courseDTO;
        }else {
            return null;
        }
    }

    @Override
    public CourseDTO read(Long courseId) {
        Optional<Course> courseRepositoryById = courseRepository.findById(courseId);
        if (courseRepositoryById.isPresent()) {
            return new CourseDTO(courseRepositoryById.get());
        }
        return null;
    }

    @Override
    public List<CourseDTO> readByAttribute(CourseAttribute courseAttribute) {
        List<Course> courseList = courseRepository.readByCourseAttribute(courseAttribute);
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            courseDTOList.add(new CourseDTO(course));
        }
        return courseDTOList;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Optional<Course> courseRepositoryById = courseRepository.findById(courseDTO.getCourseId());
        courseRepositoryById.ifPresent(course -> {
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseName(courseDTO.getCourseName());
            course.changeCourseDescription(courseDTO.getCourseDescription());
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseStatus(CourseAttribute.valueOf(courseDTO.getCourseAttribute()));
            course.changeSale(courseDTO.isSale());
        });
        return courseDTO;
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<CourseDTO> readAll() {
        List<Course> courseList = courseRepository.findAll();

        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            courseDTOList.add(new CourseDTO(course));
        }

        return courseDTOList;
    }

    @Override
    public List<CourseDTO> getCoursesByNickname(String nickname) {
        List<Course> byMemberNickname = courseRepository.getByMemberNickname(nickname);
        if (byMemberNickname != null && !byMemberNickname.isEmpty()) {
            List<CourseDTO> courseDTOList = new ArrayList<>();
            for (Course course : byMemberNickname) {
                courseDTOList.add(new CourseDTO(course));
            }
            return courseDTOList;
        } else {
            return new ArrayList<>(); // 빈 리스트 반환
        }
    }

    //수강 중인 강의 목록
    @Override
    public List<MemberCourseDTO> getMemberCoursesByMemberId(Long memberId) {
        List<MemberCourse> memberCourseList = memberCourseRepository.getMemberCourse(memberId);

        if (memberCourseList == null && memberCourseList.isEmpty()) {
            throw CourseException.MEMBER_COURSE_NOT_FOUND.get();
        }

        List<MemberCourseDTO> memberCourseDTOList = new ArrayList<>();

        for (MemberCourse memberCourse : memberCourseList) {
            memberCourseDTOList.add(new MemberCourseDTO(memberCourse));
        }
        log.info(memberCourseDTOList);
        return memberCourseDTOList;
    }


    // 강사 닉네임 반환
    public String getInstructorNicknameByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 코스를 찾을 수 없습니다."));

        // 해당 코스의 Member(강사)의 닉네임을 반환
        return course.getMember().getNickname();
    }
}
