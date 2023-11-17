import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
public class Advisor extends User {
    @JsonProperty("advisedStudents")
    private ArrayList<String> advisedStudentIDs;
    private ArrayList<Student> requestedStudents;
    private ArrayList<Student> advisedStudents;

    public Advisor(String username, String name, String surname, String password, ArrayList<String> advisedStudentIDs) {
        super(username, name, surname, password);
        this.advisedStudentIDs = advisedStudentIDs;

    }

    public Advisor(){}

    void setAdvisedStudentsInit(List<Student> students){
        advisedStudents= new ArrayList<>();
        for (Student s: students ){
            if (advisedStudentIDs.stream().anyMatch(p -> p.equals(s.getUsername()))){
                advisedStudents.add(s);
            }
        }
    }

    public  List<Student> listRequestStudents() {
        requestedStudents = new ArrayList<>();
        for(Student st: advisedStudents){
            if (st.getRequestedCourses().size()>0){
                requestedStudents.add(st);
            }
        }

        return requestedStudents;
    }


    public void approveCourseRegistration(Student student, Course course) {

        course.addEnrolledStudent(student,course);

    }

    public String studentsToString() {
        StringBuilder result = new StringBuilder();

        // Headers
        String headers = String.format("%-15s %-40s\n", "Student ID", "Full Name");


        if (!advisedStudents.isEmpty()) {
            result.append("Advised Students:\n");
            result.append(headers);
            for (Student student : advisedStudents) {

                String studentInfo = String.format("%-15s %-40s\n",
                        student.getStudentId(), student.getName() + " " + student.getSurname());
                result.append(studentInfo);
            }
        } else {
            result.append("No advised students.\n");
        }

        return result.toString();
    }
}
