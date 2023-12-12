import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JSONMethods {
    public boolean clearRequestedCourses(Student student) {

        try {
            // JSON file will be read
            File jsonFile = new File("src/Students/"+student.getStudentId()+".json");

            // Read JSON data as follows
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            // Find the "requestedCourses" field
            JsonNode requestedCoursesNode = rootNode.get("requestedCourses");

            // If the "requestedCourses" field is not empty, delete the content
            if (requestedCoursesNode != null && requestedCoursesNode.isArray()) {
                ArrayNode requestedCoursesArray = (ArrayNode) requestedCoursesNode;
                requestedCoursesArray.removeAll();
            }

            // Write changes to JSON file
            objectMapper.writeValue(jsonFile, rootNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
        student.getRequestedCourses().clear();
        return true;
    }

    public boolean addEnrolledStudent(Student student, Course course){
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
        return true;
    }
}
