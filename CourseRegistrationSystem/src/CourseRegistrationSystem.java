import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationSystem {
    private Student student;
    private List<Student> students;
    private List<Course> courses;

    public CourseRegistrationSystem(Student student, List<Course> courses){
        this.student = student;
        this.courses = courses;
    }
    public CourseRegistrationSystem(){

    }
    public List<Course>listAvailableCourses(){
        List<Course> avaliableCourse = new ArrayList<>();
        for(Course course : courses){
            if((!student.getEnrolledCourses().contains(course)) && (course.hasPrerequisite())) {
                avaliableCourse.add(course);
            }
        }
        return avaliableCourse;
    }
    public void requestInCourse(Course course, Student student){
        student.getRequestedCourses().add(course);
        //JSON 
    }

}
