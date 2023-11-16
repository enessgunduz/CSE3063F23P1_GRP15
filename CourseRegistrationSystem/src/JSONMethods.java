import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;

public class JSONMethods {
    public void clearRequestedCourses(Student student) {

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
    }
}
