import java.util.ArrayList;
import java.util.List;
public class Advisor extends User {
    private String advisorId;
    private ArrayList<Student> advisedStudent;
    private ArrayList<Student> requestedStudents;

    public Advisor(String username, String name, String surname, String password, String advisorId, ArrayList<Student> advisedStudent, ArrayList<Student> requestedStudents) {
        super(username, name, surname, password);
        this.advisorId = advisorId;
        this.advisedStudent = advisedStudent;
        this.requestedStudents = requestedStudents;
    }

    public  List<Student> listRequestStudents() {
        if (requestedStudents != null && !requestedStudents.isEmpty()){
            return requestedStudents;
        }
        else{
            System.out.println("There is no student requesting");
            return null;
        }
    }

    public List<Course> listStudentRequests(Student student) {

        List<Course> studentRequests = student.getRequestedCourses();

        if (studentRequests != null && !studentRequests.isEmpty()) {

            return studentRequests;

        } else {

            System.out.println("No course requests for Student " + student.getUsername() + ".");

            return null;
        }

    }

    public void approveCourseRegistration(Student student, Course course) {

        course.addEnrolledStudent(student);

    }

    public void rejectCourseRegistration(Student student, Course course) {

        student.clearRequestedCourses();

    }

    public String studentsToString() {
        StringBuilder result = new StringBuilder();
        result.append("Requested Students:\n");
        result.append("\nRequested Students:\n");
        if (requestedStudents != null && !requestedStudents.isEmpty()) {
            for (Student student : requestedStudents) {
                result.append("Student ID: ").append(student.getStudentId())
                        .append(", Name: ").append(student.getName())
                        .append(", Username: ").append(student.getUsername()).append("\n");
            }
        } else {
            result.append("No requested students.\n");
        }

        return result.toString();
    }
}
