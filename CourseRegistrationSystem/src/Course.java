import com.fasterxml.jackson.annotation.JsonProperty;

public class Course{

    private String courseId;
    private String courseName;
    private int credit;
    @JsonProperty("prerequisite")
    private boolean prerequisite;
    private String prerequisiteLessonId;
    private CourseSection courseSection;
    
    

    public Course(String CourseId, String CourseName, int credit, boolean prerequisite, String prerequisiteLessonId, CourseSection courseSection){
        this.courseId = CourseId;
        this.courseName = CourseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLessonId = prerequisiteLessonId;
        this.courseSection = courseSection;
    }

    public Course(){}

    

    //public List<Student> getEnrolledStudents(){
    //  
    //}

    public void addEnrolledStudent(Student student){
        //JSON
    }

    public boolean hasPrerequisite(){
        return prerequisite;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Course[CourseId=").append(courseId)
          .append(", CourseName=").append(courseName)
          .append(", credit=").append(credit)
          .append(", prerequisite=").append(prerequisite)
          .append(", prerequisiteLesson=").append(prerequisiteLessonId)
          .append(", courseSection=").append(courseSection)
          .append("]");
        return sb.toString();
    }

    public String getCourseId(){
        return courseId;
    }

    public int getCredit(){
        return credit;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getPrerequisiteLessonId(){
        return prerequisiteLessonId;
    }


    public CourseSection getCourseSection(){
        return courseSection;
    }

}
