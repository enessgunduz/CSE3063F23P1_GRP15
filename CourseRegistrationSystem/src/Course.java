import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

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

    public void addEnrolledStudent(Student student, Course course){
        try {
            // Öğrenciye ait JSON dosyasını oku
            File studentJsonFile = new File("src/Students/"+student.getStudentId()+".json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode studentNode = objectMapper.readTree(studentJsonFile);

            // "requestedCourses" alanını bul
            JsonNode enrolledCoursesNode = studentNode.get("enrolledCourses");

            // Eğer "requestedCourses" alanı boşsa oluştur
            if (enrolledCoursesNode == null || !enrolledCoursesNode.isArray()) {
                enrolledCoursesNode = objectMapper.createArrayNode();
                ((ObjectNode) studentNode).set("enrolledCourses", enrolledCoursesNode);
            }

            // Course bilgilerini yeni bir JSON nesnesine ekle
            ObjectNode newCourseNode = objectMapper.createObjectNode();
            newCourseNode.put("courseId", course.getCourseId());
            newCourseNode.put("courseName", course.getCourseName());
            newCourseNode.put("credit", course.getCredit());
            newCourseNode.put("prerequisite", course.hasPrerequisite());
            newCourseNode.put("prerequisiteLessonId", course.getPrerequisiteLessonId());

            // CourseSection bilgilerini ekleyin (bu kısmı kendi ihtiyaçlarınıza göre düzenleyebilirsiniz)
            ObjectNode courseSectionNode = objectMapper.createObjectNode();
            courseSectionNode.put("term", course.getCourseSection().getTerm());
            courseSectionNode.put("instructor", course.getCourseSection().getInstructor());
            courseSectionNode.put("enrollmentCapacity", course.getCourseSection().getEnrollmentCapacity());
            courseSectionNode.put("status", course.getCourseSection().getStatus());

            newCourseNode.set("courseSection", courseSectionNode);

            // "requestedCourses" alanına yeni kursu ekle
            ((ArrayNode) enrolledCoursesNode).add(newCourseNode);

            // Değişiklikleri JSON dosyasına kaydet
            objectMapper.writeValue(studentJsonFile, studentNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
