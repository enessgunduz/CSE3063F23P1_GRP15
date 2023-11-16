import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UnitTests {
    @Test
    public void testCourseEquality() {
        Course course1 = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall", "Borahan Tümer", 5, "aktif"));
        Course course2 = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall", "Borahan Tümer", 5, "aktif"));

        assertEquals(course1, course2);
    }

    @Test
    public void testCourseInequality() {
        Course course1 = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall", "Borahan Tümer", 5, "aktif"));
        Course course2 = new Course("CSE2043", "Programming 1", 5, false, "None", new CourseSection("spring", "Mustafa Ağaoğlu", 5, "aktif"));

        assertNotEquals(course1, course2);
    }

    @Test
    public void testStudentEnrollCourse() {
        Student student = new Student("o150120038", "muhammed enes", "gunduz", "marmara123038", "001", new ArrayList<>(),new ArrayList<>(),new Transcript());

        Course course = new Course("CSE3045", "Digital Logic Design", 4, false, "None", new CourseSection("fall", "Borahan Tümer", 5, "aktif"));
        assertTrue(course.addEnrolledStudent(student,course));
        assertTrue(student.getEnrolledCourses().contains(course));
    }

    @Test
    public void testStudentClearCourses() {
        Student student = new Student("o150120038", "muhammed enes", "gunduz", "marmara123038", "001", new ArrayList<>(),new ArrayList<>(),new Transcript());
        JSONMethods jS = new JSONMethods();


        assertTrue(jS.clearRequestedCourses(student));
        assertFalse(student.getEnrolledCourses().size()>0);
    }

    @Test
    public void testCourseCreditValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Course("CSE1020", "Invalid Course", -1, false, "None", new CourseSection("spring", "Instructor", 5, "aktif"));
        });
    }

}
