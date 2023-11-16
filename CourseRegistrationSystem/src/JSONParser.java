import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;

public class JSONParser {

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

    public static void main(String[] args) {
        // Your JSON string
        String jsonText = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/Students/150120001.json"));
            String line;
            while((line=br.readLine()) != null){
                jsonText += line + "\n";
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JSONParser jP = new JSONParser();

        // Convert JSON to Student object
        Student student = jP.convertJsonToStudent(jsonText);

        // Use the student object as needed
        if (student != null) {
            System.out.println("Successfully converted JSON to Student object:");
            System.out.println(student.viewTranscript().allGrades().get(0));
        } else {
            System.out.println("Failed to convert JSON to Student object.");
        }
    }


}
