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

    private String projectAssistant;

    public Student(@JsonProperty("username") String username,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("password") String password,
                   @JsonProperty("studentID") String studentId,
                   @JsonProperty("GPA") float gpa,
                   @JsonProperty("StSemester") int stSemester,
                   @JsonProperty("enrolledCourses") List<Course> enrolledCourses,
                   @JsonProperty("requestedCourses") List<Course> requestedCourses,
                   @JsonProperty("transcript") Transcript transcript,
                   @JsonProperty("projectAssistant") String projectAssistant) {
        super(username, name, surname, password);
        this.studentId = studentId;
        this.enrolledCourses = enrolledCourses;
        this.requestedCourses = requestedCourses;
        this.transcript = transcript;
        this.gpa=gpa;
        this.stSemester=stSemester;
        this.projectAssistant=projectAssistant;
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
        System.out.println(getStudentInfo());
        System.out.println("-------------------------------------------------------------------");
        return this.transcript;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getProjectAssistant() {
        return projectAssistant;
    }
    public float getGpa(){
        return gpa;
    }
    public int getSemester(){
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
    public String getStudentInfo() {
        StringBuilder sbnames = new StringBuilder();
        String header = String.format("%-45s %-35s\n", "Name: " + getName(), "GPA: " + getGpa());
        String lowerheader = String.format("%-45s %-35s", "Surname: " + getSurname(), "Completed Credit: " + getTotalCredits());
        sbnames.toString();
        sbnames.append(header);
        sbnames.append(lowerheader);
        return  sbnames.toString();
    }

}
