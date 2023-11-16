public class Course{

    private String CourseId;
    private String CourseName;
    private int credit;
    private boolean prerequisite;
    private String prerequisiteLessonId;
    private CourseSection courseSection;
    
    

    public Course(String CourseId, String CourseName, int credit, boolean prerequisite, String prerequisiteLessonId, CourseSection courseSection){
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLessonId = prerequisiteLessonId;
        this.courseSection = courseSection;
    }
    

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
        sb.append("Course[CourseId=").append(CourseId)
          .append(", CourseName=").append(CourseName)
          .append(", credit=").append(credit)
          .append(", prerequisite=").append(prerequisite)
          .append(", prerequisiteLesson=").append(prerequisiteLessonId)
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

    public String getPrerequisiteLessonId(){
        return prerequisiteLessonId;
    }


    public CourseSection getCourseSection(){
        return courseSection;
    }

}
