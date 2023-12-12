import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student extends User {

    private String studentId;
    private float gpa;
    private int stSemester;
    private List<Course> enrolledCourses;
    private List<Course> requestedCourses;
    private Transcript transcript;
    private int totalCredits;

    public Student(@JsonProperty("username") String username,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("password") String password,
                   @JsonProperty("studentID") String studentId,
                   @JsonProperty("GPA") float gpa,
                   @JsonProperty("StSemester") int stSemester,
                   @JsonProperty("enrolledCourses") List<Course> enrolledCourses,
                   @JsonProperty("requestedCourses") List<Course> requestedCourses,
                   @JsonProperty("transcript") Transcript transcript) {
        super(username, name, surname, password);
        this.studentId = studentId;
        this.enrolledCourses = enrolledCourses;
        this.requestedCourses = requestedCourses;
        this.transcript = transcript;
        this.gpa=gpa;
        this.stSemester=stSemester;
        calculateTotalCredits();
    }

    public Student(){
        calculateTotalCredits();
    }

    private void calculateTotalCredits(){
        totalCredits=0;
        for (Grade grade:transcript.allGrades()) {
            if (!grade.getGradeValue().equals("--"))
                totalCredits += grade.getCourse().getCredit();
        }
    }
    public int getTotalCredits(){
        return totalCredits;
    }
    public Transcript viewTranscript() {

        return this.transcript;
    }

    public String getStudentId() {
        return studentId;
    }
    public float getGpa(){
        return gpa;
    }
    public float getSemester(){
        return stSemester;
    }
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }
    @Override
    public String toString() {

        String studentInfo = String.format("%-30s %-30s %-8s", getName(), getSurname(), getStudentId());

        return studentInfo;
    }

}
