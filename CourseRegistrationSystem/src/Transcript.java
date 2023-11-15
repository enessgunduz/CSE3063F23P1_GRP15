import java.util.ArrayList;
import java.util.List;

public class Transcript {
    private Student student;
    private List<Grade> grades;

    public Transcript(Student student, List<Grade> grades) {
        this.student = student;
        this.grades = grades;
    }

    public Student getStudent() {
        return student;
    }

    public List<Grade> allGrades() {

        return grades;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student: ").append(getStudent().getName()).append(" ").append(getStudent().getSurname())
                .append("\n");
        sb.append("Student ID: ").append(getStudent().getStudentID()).append("\n");
        // Headers
        String header = String.format("%-10s %-30s %-10s\n", "CourseID", "CourseName", "GradeValue");
        sb.append(header);
        // Grades
        for (Grade grade : allGrades()) {
            sb.append(grade.toString()).append("\n");
        }

        return sb.toString();
    }
}
