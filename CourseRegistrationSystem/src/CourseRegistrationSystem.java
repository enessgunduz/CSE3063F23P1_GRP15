import com.sun.org.apache.xml.internal.security.keys.storage.implementations.CertsInFilesystemDirectoryResolver;

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
        List<Course> availableCourse = new ArrayList<>();
        List<Course> coursesTaken= new ArrayList<>();
        for (Grade gr:student.viewTranscript().allGrades()){
            coursesTaken.add(gr.getCourse());
        }
        for(Course course : courses){
            if(!(student.getEnrolledCourses().contains(course)) && !(coursesTaken.contains(course))) {
                if (course.hasPrerequisite()){
                    for (Course crs : coursesTaken){
                        if (crs.getCourseId().equals(course.getPrerequisiteLessonId())){
                            availableCourse.add(course);
                        }
                    }
                } else{
                    availableCourse.add(course);
                }
            }
        }
        return availableCourse;
    }
    public void requestInCourse(Course course, Student student){
        student.getRequestedCourses().add(course);
        //JSON 
    }

}
