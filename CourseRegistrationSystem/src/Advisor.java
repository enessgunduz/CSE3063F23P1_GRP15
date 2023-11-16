import java.util.ArrayList;
import java.util.List;
public class Advisor extends User {
    private String advisorID;
    private ArrayList<Student> advisedStudent;
    private ArrayList<Student> requestedStudents;

    public Advisor(String username, String name, String surname, String password, String advisorID, ArrayList<Student> advisedStudent) {
        super(username, name, surname, password);
        this.advisorID = advisorID;
        this.advisedStudent = advisedStudent;
        requestedStudents = new ArrayList<>();
    }

    public Advisor(){}

    public  List<Student> listRequestStudents() {
        for(Student st:advisedStudent){
            if (st.getRequestedCourses().size()>0){
                requestedStudents.add(st);
            }
        }

        return requestedStudents;
    }


    public void approveCourseRegistration(Student student, Course course) {

        course.addEnrolledStudent(student);

    }

    public String studentsToString() {
        StringBuilder result = new StringBuilder();
        if (!advisedStudent.isEmpty()) {
            result.append("Advised Students:\n");
            for (Student student : advisedStudent) {
                result.append("Student ID: ").append(student.getStudentId())
                        .append(", Name: ").append(student.getName())
                        .append(", Username: ").append(student.getUsername()).append("\n");
            }
        } else {
            result.append("No advised students.\n");
        }


        return result.toString();
    }
}
