import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student extends User {

    private String studentId;
    private List<Course> enrolledCourses;
    private List<Course> requestedCourses;
    private Transcript transcript;

    public Student(@JsonProperty("username") String username,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("password") String password,
                   @JsonProperty("studentID") String studentId,
                   @JsonProperty("enrolledCourses") List<Course> enrolledCourses,
                   @JsonProperty("requestedCourses") List<Course> requestedCourses,
                   @JsonProperty("transcript") Transcript transcript) {
        super(username, name, surname, password);
        this.studentId = studentId;
        this.enrolledCourses = enrolledCourses;
        this.requestedCourses = requestedCourses;
        this.transcript = transcript;
    }

    public Student(){

    }

    public Transcript viewTranscript() {

        return this.transcript;
    }

    public void clearRequestedCourses() {

        try {
            // JSON file will be read
            File jsonFile = new File("src/Students/150120001.json");

            // Read JSON data as follows
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            // Find the "requestedCourses" field
            JsonNode requestedCoursesNode = rootNode.get("requestedCourses");

            // If the "requestedCourses" field is not empty, delete the content
            if (requestedCoursesNode != null && requestedCoursesNode.isArray()) {
                ArrayNode requestedCoursesArray = (ArrayNode) requestedCoursesNode;
                requestedCoursesArray.removeAll();
                System.out.println("requestedCourses content has been deleted.");
            } else {
                System.out.println("The requestedCourses field is already empty or not found.");
            }

            // Write changes to JSON file
            objectMapper.writeValue(jsonFile, rootNode);

            System.out.println("The changes are saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.requestedCourses.clear();
    }

    public String getStudentId() {
        return studentId;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Surname: ").append(getSurname()).append("\n");
        sb.append("Student ID: ").append(getStudentId()).append("\n");
        return sb.toString();
    }

}
