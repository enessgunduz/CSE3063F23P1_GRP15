import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.org.apache.xml.internal.security.keys.storage.implementations.CertsInFilesystemDirectoryResolver;

import java.io.File;
import java.io.IOException;
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

            if(!(student.getEnrolledCourses().stream().anyMatch(p -> p.getCourseId().equals(course.getCourseId()))) &&
                    !(coursesTaken.stream().anyMatch(p -> p.getCourseId().equals(course.getCourseId())))) {
                if (course.hasPrerequisite()){
                    for (Course crs : coursesTaken){
                        if (crs.getCourseId().equals(course.getPrerequisiteLessonId())){
                            availableCourse.add(course);
                        }
                    }
                } else{
                    if (student.getGpa()>=3){
                        availableCourse.add(course);
                    } else if (student.getSemester()==course.getCourseSection().getSemester()){
                        availableCourse.add(course);
                    }

                }
            }
        }
        return availableCourse;
    }
    public void requestInCourse(Course course, Student student) throws IOException {
        student.getRequestedCourses().add(course);

        JSONMethods jM = new JSONMethods();
        jM.addRequestCourse(course,student);
        jM.updateEnrollmentCapacity(course.getCourseId(),course.getCourseSection().getEnrollmentCapacity()-1);
    }

    public void rejectCourse(Course course) throws IOException {
        JSONMethods jM = new JSONMethods();
        jM.updateEnrollmentCapacity(course.getCourseId(),course.getCourseSection().getEnrollmentCapacity()+1);

    }

    public boolean checkForConflicts(List<Course> courses) {
        System.out.println("\nChecking for schedule conflicts:");

        for (int i = 0; i < courses.size() - 1; i++) {
            Course course1 = courses.get(i);
            CourseSection section1 = course1.getCourseSection();

            for (int j = i + 1; j < courses.size(); j++) {
                Course course2 = courses.get(j);
                CourseSection section2 = course2.getCourseSection();

                if (section1.getDay().equals(section2.getDay()) &&
                        doTimeRangesOverlap(section1.getHour(), section2.getHour())) {
                    System.out.println("Conflict found between:");
                    System.out.println(course1);
                    System.out.println(course2);
                    System.out.println();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean doTimeRangesOverlap(String timeRange1, String timeRange2) {
        String[] parts1 = timeRange1.split("-");
        String[] parts2 = timeRange2.split("-");

        String startTime1 = parts1[0];
        String endTime1 = parts1[1];

        String startTime2 = parts2[0];
        String endTime2 = parts2[1];

        return !(endTime1.compareTo(startTime2) <= 0 || startTime1.compareTo(endTime2) >= 0);
    }

}
