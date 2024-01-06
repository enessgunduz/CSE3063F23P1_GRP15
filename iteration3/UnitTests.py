import unittest
from Student import Student
from Course import Course
from CourseSection import CourseSection
from Advisor import Advisor
from Transcript import Transcript
from Grade import Grade
from JsonMethods import JSONMethods
from CourseRegistrationSystem import CourseRegistrationSystem

class TestCourseRegistrationSystem(unittest.TestCase):
    def setUp(self):
        # Önce test verilerini hazırlayalım
        self.student_data = {
            "username": "o150120025",
            "name": "Murat",
            "surname": "Albayrak",
            "password": "marmara123025",
            "student_id": "150120025",
            "gpa": 2.7,
            "st_semester": 5,
            "enrolled_courses": [],
            "requested_courses": [],
            "transcript": Transcript([Grade(Course("CSE2043", "Programming I", 5, False, "None", CourseSection("Fall", "Tuesday", "9:00-11:50", 1, "Mustafa Ağaoğlu", 5, "Active")), "CC")]),
            "project_assistant": ""
        }

        self.course_data = {
            "course_id": "CSE3045",
            "course_name": "Digital Logic Design",
            "credit": 4,
            "prerequisite": False,
            "prerequisite_lesson_id": None,
            "course_section": CourseSection("Fall", "Monday", "13:00-14:50", 5, "Borahan Tümer", 1, "Active")
        }

        self.advisor_data = {
            "name": "Betül",
            "surname": "Boz",
            "username": "o2001",
            "password": "o20011000",
            "advised_student_ids": ["o150118056", "o150119024", "o150120009", "o150120001", "o150120022", "o150120025", "o150120053", "o150120038"]
        }

        self.student = Student(**self.student_data)
        self.course = Course(**self.course_data)
        self.advisor = Advisor(**self.advisor_data)

        self.json_methods = JSONMethods()
        self.course_registration_system = CourseRegistrationSystem(self.student, [self.course])

    def test_list_available_courses(self):
        available_courses = self.course_registration_system.list_available_courses()
        self.assertEqual(len(available_courses), 1)
        self.assertEqual(available_courses[0].get_course_id(), "CSE3045")

    def test_request_in_course(self):
        self.course_registration_system.request_in_course(self.course, self.student)
        self.assertEqual(len(self.student.get_requested_courses()), 1)
        self.assertEqual(self.student.get_requested_courses()[0].get_course_id(), "CSE3045")

    def test_reject_course(self):
        self.course_registration_system.reject_course(self.course)
        self.assertEqual(self.course.get_course_section().get_enrollment_capacity(), 1)

    def test_approve_course_registration(self):
        self.advisor.approve_course_registration(self.student, self.course)
        self.assertEqual(len(self.student.get_enrolled_courses()), 1)
        self.assertEqual(self.student.get_enrolled_courses()[0].get_course_id(), "CSE2043")

    # def test_students_to_string_with_advised_students(self):
    #     result = self.advisor.students_to_string()
    #     self.assertIn("Advised Students:", result)

    # def test_students_to_string_with_no_advised_students(self):
    #     advisor_with_no_students = Advisor(name="Betül", surname="Boz", username="o2001", password="o20011000", advised_student_ids=["o150118056", "o150119024", "o150120009", "o150120001", "o150120022", "o150120025", "o150120053", "o150120038"])
    #     result = advisor_with_no_students.students_to_string()
    #     self.assertIn("No advised students.", result)

    # def test_add_enrolled_student(self):
    #     success = self.course.add_enrolled_student(self.student, self.course)
    #     self.assertTrue(success)
    #     self.assertEqual(len(self.student.get_enrolled_courses()), 1)

    # def test_has_prerequisite(self):
    #     self.assertTrue(self.course.has_prerequisite())

if __name__ == "__main__":
    unittest.main()
