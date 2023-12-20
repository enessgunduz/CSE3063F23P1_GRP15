import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UnitTests {

    @Test
    public void testCourseInequality() {
        // Create two different courses
        Course course1 = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall","Monday",
                "13:00-14:50", 2,"Borahan Tümer", 5, "active"));
        Course course2 = new Course("CSE2043", "Programming 1", 5, false, "None", new CourseSection("spring","Monday",
                "13:00-14:50",2, "Mustafa Ağaoğlu", 5, "active"));

        // Assert that the courses are not equal
        assertNotEquals(course1, course2);
    }
    
    @Test
    void testListAvailableCourses() {
        // Create a student and a list of available courses
        Student student = new Student("student1", "John", "Doe", "password", "123",3,4, new ArrayList<>(), new ArrayList<>(), new Transcript(new ArrayList<>()), "");
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
        CourseSection courseSection = new CourseSection("Fall 2023","Monday",
                "13:00-14:50",2, "Dr. Smith", 30, "Open");

        // Test the getTerm method
        assertEquals("Fall 2023", courseSection.getTerm());
    }
    @Test
    void testGetHour() {
        // Create a CourseSection object
        CourseSection courseSection = new CourseSection("Fall 2023", "Monday",
                "13:00-14:50", 2, "Dr. Smith", 30, "Open");

        // Test the getTerm method
        assertEquals("13:00-14:50", courseSection.getHour());
    }

    @Test
    public void testCourseCreation() {
        CourseSection courseSection = new CourseSection("Fall", "Monday", "10:00-12:00", 1, "John Doe", 30, "Open");

        Course course = new Course("C101", "Introduction to Computer Science", 3, true, "CS101", courseSection);

        assertEquals("C101", course.getCourseId());
        assertEquals("Introduction to Computer Science", course.getCourseName());
        assertEquals(3, course.getCredit());
        assertEquals("CS101", course.getPrerequisiteLessonId());

        CourseSection retrievedSection = course.getCourseSection();
        assertEquals("Fall", retrievedSection.getTerm());
        assertEquals("Monday", retrievedSection.getDay());
        assertEquals("10:00-12:00", retrievedSection.getHour());
        assertEquals(1, retrievedSection.getSemester());
        assertEquals("John Doe", retrievedSection.getInstructor());
        assertEquals(30, retrievedSection.getEnrollmentCapacity());
        assertEquals("Open", retrievedSection.getStatus());
}
}
