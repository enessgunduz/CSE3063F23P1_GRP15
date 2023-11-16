import java.util.ArrayList;
import java.util.List;

class Student extends User {

    private String studentId;
    private List<Course> enrolledCourses;
    private List<Course> requestedCourses;
    private Transcript transcript;

    public Student(String name, String surname, String username, String password, String studentId,
            List<Course> enrolledCourses, List<Course> requestedCourses, Transcript transcript) {
        super(username, surname, name, password);
        this.studentId = studentId;
        this.enrolledCourses = enrolledCourses;
        this.requestedCourses = requestedCourses;
        this.transcript = transcript;
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
