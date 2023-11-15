
import java.util.List;

public class CourseSection {
    
    private String term;
    private String instructor;
    private int enrollmentCapacity;
    private List<Student> enrolledStudents;
    private String status;

    public CourseSection(String term, String instructor, int enrollmentCapacity, List<Student> enrolledStudents,String status,
    String CourseId, int capacity, int credit,String CourseName, String prerequisiteLesson,  boolean prerequisite) {
        this.term=term;
        this.instructor = instructor;
        this.status = status;
        this.enrollmentCapacity = enrollmentCapacity;
        this.enrolledStudents = enrolledStudents;
        
    }
    
    //public List<Student> getEnrolledStudents(){
    //
    //}
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CourseSection[term=").append(term)
          .append(", instructor=").append(instructor)
          .append(", status=").append(status)
          .append(", enrollmentCapacity=").append(enrollmentCapacity)
          .append(", enrolledStudents=").append(enrolledStudents)
          .append("]");
        return sb.toString();
    }


}
