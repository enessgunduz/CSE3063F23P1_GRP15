import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Transcript {
    private List<Grade> grades;

    public Transcript(@JsonProperty("grades") List<Grade> grades) {
        this.grades = grades;
    }

    public Transcript(){

    }

    @JsonProperty("grades")
    public List<Grade> allGrades() {

        return grades;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Headers
        String header = String.format("%-10s %-30s %-10s\n", "CourseID", "CourseName", "GradeValue");
        sb.append(header);
        // Grades
        for (Grade grade : allGrades()) {
            sb.append(grade.toString());
        }

        return sb.toString();
    }
}
