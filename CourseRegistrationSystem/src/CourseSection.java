
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CourseSection {

    @JsonProperty("term")

    private String term;
    @JsonProperty("instructor")

    private String instructor;
    @JsonProperty("enrollmentCapacity")

    private int enrollmentCapacity;
    //private List<Student> enrolledStudents;
    @JsonProperty("status")

    private String status;

    public CourseSection(String term, String instructor, int enrollmentCapacity,String status) {
        this.term=term;
        this.instructor = instructor;
        this.status = status;
        this.enrollmentCapacity = enrollmentCapacity;
        //this.enrolledStudents = enrolledStudents;
        
    }

    public CourseSection(){
    }
    
    //public List<Student> getEnrolledStudents(){
    //
    //}


    public String getTerm() {
        return term;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public String getStatus() {
        return status;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CourseSection[term=").append(term)
          .append(", instructor=").append(instructor)
          .append(", status=").append(status)
          .append(", enrollmentCapacity=").append(enrollmentCapacity)
          //.append(", enrolledStudents=").append(enrolledStudents)
          .append("]");
        return sb.toString();
    }


}
