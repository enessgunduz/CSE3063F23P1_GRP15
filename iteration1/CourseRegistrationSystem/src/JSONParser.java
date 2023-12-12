import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    List<Student> students = new ArrayList<>();
    public Student convertJsonToStudent(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON into Student object
            return objectMapper.readValue(json, Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (e.g., log or throw a custom exception)
            return null;
        }
    }

    public List<Student> parseStudents(){
        // Your JSON string
        File dir = new File("src/Students");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Do something with child
                String jsonText = "";
                try{
                    BufferedReader br = new BufferedReader(new FileReader(child));
                    String line;
                    while((line=br.readLine()) != null){
                        jsonText += line + "\n";
                    }
                    br.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                // Convert JSON to Student object
                Student student = convertJsonToStudent(jsonText);
                students.add(student);
            }
        }

        return students;
    }

    public List<Course> parseCourses() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/course.json");

        // Use TypeReference to specify that you're expecting a List<Course>
        return objectMapper.readValue(file, new TypeReference<List<Course>>() {});

    }

    public Advisor convertJsonToAdvisor(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON into Student object
            return objectMapper.readValue(json, Advisor.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (e.g., log or throw a custom exception)
            return null;
        }
    }

    public List<Advisor> parseAdvisor(){
        List<Advisor> advisors = new ArrayList<>();
        // Your JSON string
        File dir = new File("src/Advisors");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Do something with child
                String jsonText = "";
                try{
                    BufferedReader br = new BufferedReader(new FileReader(child));
                    String line;
                    while((line=br.readLine()) != null){
                        jsonText += line + "\n";
                    }
                    br.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                // Convert JSON to Student object
                Advisor advisor = convertJsonToAdvisor(jsonText);
                advisor.setAdvisedStudentsInit(students);
                advisors.add(advisor);
            }
        }

        return advisors;
    }






}
