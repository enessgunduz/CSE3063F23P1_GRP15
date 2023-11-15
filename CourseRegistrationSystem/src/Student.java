import java.util.ArrayList;
import java.util.List;

class Student extends User {

    private String studentId;
    private List<Course> enrolledCourses;
    private List<Course> requestedCourses;
    private Transcript transcript;

    public Student(String name, String surname, String username, String password, String studentId,
            List<Course> enrolledCourses, List<Course> requestedCourses, Transcript transcript) {
        super(name, surname, username, password);
        this.studentId = studentId;
        this.enrolledCourses = new ArrayList<>();
        this.requestedCourses = new ArrayList<>();
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

}
