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
                    if (student.getGpa()>3){
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

        try {
            File studentJsonFile = new File("src/Students/"+student.getStudentId()+".json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode studentNode = objectMapper.readTree(studentJsonFile);

            JsonNode requestedCoursesNode = studentNode.get("requestedCourses");

            if (requestedCoursesNode == null || !requestedCoursesNode.isArray()) {
                requestedCoursesNode = objectMapper.createArrayNode();
                ((ObjectNode) studentNode).set("requestedCourses", requestedCoursesNode);
            }

            ObjectNode newCourseNode = objectMapper.createObjectNode();
            newCourseNode.put("courseId", course.getCourseId());
            newCourseNode.put("courseName", course.getCourseName());
            newCourseNode.put("credit", course.getCredit());
            newCourseNode.put("prerequisite", course.hasPrerequisite());
            newCourseNode.put("prerequisiteLessonId", course.getPrerequisiteLessonId());

            ObjectNode courseSectionNode = objectMapper.createObjectNode();
            courseSectionNode.put("term", course.getCourseSection().getTerm());
            courseSectionNode.put("instructor", course.getCourseSection().getInstructor());
            courseSectionNode.put("enrollmentCapacity", course.getCourseSection().getEnrollmentCapacity());
            courseSectionNode.put("status", course.getCourseSection().getStatus());

            newCourseNode.set("courseSection", courseSectionNode);

            ((ArrayNode) requestedCoursesNode).add(newCourseNode);

            objectMapper.writeValue(studentJsonFile, studentNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
