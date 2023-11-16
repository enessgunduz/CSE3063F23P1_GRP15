import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class Student extends User {

    private String studentId;
    private List<Course> enrolledCourses;
    private List<Course> requestedCourses;
    private Transcript transcript;

    public Student(@JsonProperty("username") String username,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("password") String password,
                   @JsonProperty("studentID") String studentId,
                   @JsonProperty("enrolledCourses") List<Course> enrolledCourses,
                   @JsonProperty("requestedCourses") List<Course> requestedCourses,
                   @JsonProperty("transcript") Transcript transcript) {
        super(username, name, surname, password);
        this.studentId = studentId;
        this.enrolledCourses = enrolledCourses;
        this.requestedCourses = requestedCourses;
        this.transcript = transcript;
    }

    public Student(){

    }

    public Transcript viewTranscript() {

        return this.transcript;
    }

    public void clearRequestedCourses() {
        this.requestedCourses.clear();
        // JSONDA REQUEST SİLİNECEK
    }

    public String getStudentId() {
        return studentId;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Surname: ").append(getSurname()).append("\n");
        sb.append("Student ID: ").append(getStudentId()).append("\n");
        return sb.toString();
    }

}
