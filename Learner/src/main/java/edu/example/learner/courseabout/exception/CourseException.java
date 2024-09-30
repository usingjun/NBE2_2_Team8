package edu.example.learner.courseabout.exception;

public enum CourseException {
    COURSE_NOT_FOUND("COURSE NOT FOUND",404),
    COURSE_ADD_FAILED("COURSE ADD FALIED", 412),
    COURSE_NOT_MODIFIED("COURSE NOT MODIFIED", 422),
    COURSE_NOT_DELETED("COURSE NOT DELETED", 423);


    private CourseTaskException courseTaskException;

    CourseException(String message, int code){
        courseTaskException = new CourseTaskException(message, code);
    }
    public CourseTaskException get() {
        return courseTaskException;
    }
}
