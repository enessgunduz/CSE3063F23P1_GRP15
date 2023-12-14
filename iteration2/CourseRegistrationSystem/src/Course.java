import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Course{

    private String courseId;
    private String courseName;
    private int credit;
    @JsonProperty("prerequisite")
    private boolean prerequisite;
    private String prerequisiteLessonId;
    private CourseSection courseSection;
    
    

    public Course(String CourseId, String CourseName, int credit, boolean prerequisite, String prerequisiteLessonId, CourseSection courseSection){
        this.courseId = CourseId;
        this.courseName = CourseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLessonId = prerequisiteLessonId;
        this.courseSection = courseSection;
    }

    public Course(){}

    

    //public List<Student> getEnrolledStudents(){
    //  
    //}

    public boolean addEnrolledStudent(Student student, Course course){
        JSONMethods jM = new JSONMethods();
        return jM.addEnrolledStudent(student,course);
    }

    public boolean hasPrerequisite(){
        return prerequisite;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

         // Course Information
        String courseInfo = String.format("%-18s %-40s %-8s %-20s %-10s %-23s %-20s","   "+
                courseId, courseName, credit, prerequisiteLessonId, courseSection.getTerm(),courseSection.getDay()+"-"+ courseSection.getHour(),courseSection.getInstructor());
        sb.append(courseInfo);

        return sb.toString();
    }


    public String getCourseId(){
        return courseId;
    }

    public int getCredit(){
        return credit;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getPrerequisiteLessonId(){
        return prerequisiteLessonId;
    }


    public CourseSection getCourseSection(){
        return courseSection;
    }

}
