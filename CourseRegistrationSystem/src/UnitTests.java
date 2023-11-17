import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UnitTests {

    @Test
    public void testCourseInequality() {
        // Create two different courses
        Course course1 = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall", "Borahan Tümer", 5, "active"));
        Course course2 = new Course("CSE2043", "Programming 1", 5, false, "None", new CourseSection("spring", "Mustafa Ağaoğlu", 5, "active"));

        // Assert that the courses are not equal
        assertNotEquals(course1, course2);
    }

    @Test
    void testViewTranscript() {
        // Create a sample student and transcript
        Transcript transcript = new Transcript();
        Student student = new Student("username", "John", "Doe", "password", "123", null, null, transcript);

        // Test the viewTranscript method
        assertEquals(transcript, student.viewTranscript());
    }

    @Test
    public void testStudentClearCourses() {
        // Create a sample student with requested courses
        Student student = new Student("o150120038", "muhammed enes", "gunduz", "marmara123038", "150120038", new ArrayList<>(),new ArrayList<>(),new Transcript());
        JSONMethods jsonMethods = new JSONMethods();

        // Add a requested course to the student
        Course requestedCourse = new Course("CS101", "Introduction to Computer Science", 3, false, "", null);
        student.getRequestedCourses().add(requestedCourse);

        // Test clearing requested courses
        assertTrue(jsonMethods.clearRequestedCourses(student));
        assertFalse(student.getRequestedCourses().size() > 0);
    }

    @Test
    void testListAvailableCourses() {
        // Create a student and a list of available courses
        Student student = new Student("student1", "John", "Doe", "password", "123", new ArrayList<>(), new ArrayList<>(), new Transcript(new ArrayList<>()));
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Introduction to Computer Science", 3, false, "", null));
        courses.add(new Course("MATH101", "Introduction to Mathematics", 3, false, "", null));
        courses.add(new Course("ENG101", "English Composition", 3, false, "", null));

        // Create a CourseRegistrationSystem object
        CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem(student, courses);

        // Test the listAvailableCourses method
        List<Course> availableCourses = courseRegistrationSystem.listAvailableCourses();
        assertNotNull(availableCourses);
        assertEquals(3, availableCourses.size());
    }

    @Test
    void testGetTerm() {
        // Create a CourseSection object
        CourseSection courseSection = new CourseSection("Fall 2023", "Dr. Smith", 30, "Open");

        // Test the getTerm method
        assertEquals("Fall 2023", courseSection.getTerm());
    }
}
