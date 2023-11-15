import java.util.List;

public class Course{

    private String CourseId;
    private String CourseName;
    private int credit;
    private boolean prerequisite;
    private String prerequisiteLesson;
    private CourseSection courseSection;
    
    

    public Course(String CourseId, String CourseName, int credit, boolean prerequisite, String prerequisiteLesson, CourseSection courseSection){
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLesson = prerequisiteLesson;
        this.courseSection = courseSection;
    }
    

    //public List<Student> getEnrolledStudents(){
    //  
    //}

    //public void addEnrolledStudent(Student student){
    //
    //}

    public boolean hasPrerequisite(){
        return prerequisite;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Course[CourseId=").append(CourseId)
          .append(", CourseName=").append(CourseName)
          .append(", credit=").append(credit)
          .append(", prerequisite=").append(prerequisite)
          .append(", prerequisiteLesson=").append(prerequisiteLesson)
          .append(", courseSection=").append(courseSection)
          .append("]");
        return sb.toString();
    }

    public String getCourseId(){
        return CourseId;
    }

    public int getCredit(){
        return credit;
    }

    public String getCourseName(){
        return CourseName;
    }

    public String getprerequisiteLesson(){
        return prerequisiteLesson;
    }

    public boolean getPrerequisite(){
        return prerequisite;
    }

    public CourseSection getCourseSection(){
        return courseSection;
    }

}
