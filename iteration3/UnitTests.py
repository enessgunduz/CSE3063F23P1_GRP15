import unittest
from Student import Student
from Course import Course
from CourseSection import CourseSection
from Advisor import Advisor
from Transcript import Transcript
from Grade import Grade
from JsonMethods import JSONMethods
from CourseRegistrationSystem import CourseRegistrationSystem

class UnitTests(unittest.TestCase):
    def setUp(self):
       
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
        grade1 = Grade(Course("CSE101", "Introduction to Computer Science", 3, False, "",
                              CourseSection("Fall", "Monday", "10:00-12:00", 1, "Instructor 1", 50, "Open")), "A")
        grade2 = Grade(Course("CSE202", "Data Structures", 4, False, "",
                              CourseSection("Fall", "Wednesday", "14:00-16:00", 3, "Instructor 2", 40, "Open")), "B+")
        self.transcript = Transcript([grade1, grade2])

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

 
    def test_course_constructor(self):
        course_section = CourseSection("Fall", "Monday", "13:00-14:50", 5, "Borahan Tümer", 1, "Active")
        course = Course("CSE3045", "Digital Logic Design", 4, False, None, course_section)

        self.assertEqual("CSE3045", course.get_course_id())
        self.assertEqual("Digital Logic Design", course.get_course_name())
        self.assertEqual(4, course.get_credit())
        self.assertEqual(None, course.get_prerequisite_lesson_id())
        retrieved_section = course.get_course_section()
        self.assertEqual("Fall", retrieved_section.get_term())
        self.assertEqual("Monday", retrieved_section.get_day())
        self.assertEqual("13:00-14:50", retrieved_section.get_hour())
        self.assertEqual(5, retrieved_section.get_semester())
        self.assertEqual("Borahan Tümer", retrieved_section.get_instructor())
        self.assertEqual(1, retrieved_section.get_enrollment_capacity())
        self.assertEqual("Active", retrieved_section.get_status())

    def welcome_advisor(self, advisor) -> None:
        logging.info(f"Advisor {advisor.get_name() + ' ' + advisor.get_surname()} logged in.")
        global student_list
        advisor.set_advised_students_init(student_list)
        request_students = advisor.list_requested_students()
        crg = CourseRegistrationSystem()

        if not request_students:
            logging.info(f"No course requests found for advisor {advisor.get_name() + ' ' + advisor.get_surname()}.")
            self.handle_no_course_requests()
        else:
            logging.info(f"Handling course requests for advisor {advisor.get_name() + ' ' + advisor.get_surname()}.")
            self.handle_course_requests(request_students, crg)
            
    def test_students_to_string_with_advised_students(self):
            self.advisor.set_advised_students_init([self.student])
            result = self.advisor.students_to_string()
            self.assertIn("Advised Students:", result)
            self.assertIn(self.student.get_student_id(), result)
 
    def test_has_prerequisite(self):
        self.assertFalse(self.course.has_prerequisite())

    def test_reject_course(self):
            self.course_registration_system.reject_course(self.course)
            self.assertEqual(self.course.get_course_section().get_enrollment_capacity(), 1)


    def test_calculate_total_credits_with_transcript(self):
        student = Student(transcript=self.transcript)
        total_credits = student.calculate_total_credits()
        self.assertEqual(total_credits, 7, "Total credits should be 7")


    def test_view_transcript(self):
        student = Student(transcript=self.transcript)
        transcript = student.view_transcript()
        self.assertEqual(len(transcript.all_grades()), 2, "Transcript should contain 2 grades")

    def test_get_total_credits(self):
        student = Student(transcript=self.transcript)
        total_credits = student.get_total_credits()
        self.assertEqual(total_credits, 7, "Total credits should be 7")

    def test_get_semester(self):
        semester = self.student.get_semester()
        self.assertEqual(semester, 5, "Semester should be 5")

    def test_get_name(self):
        name = self.student.get_name()
        self.assertEqual(name, "Murat", "Name should be Murat")

    def test_get_surname(self):
        surname = self.student.get_surname()
        self.assertEqual(surname, "Albayrak", "Surname should be Albayrak")


    def test_clear_requested_courses(self):
        self.student.clear_requested_courses()
        self.assertEqual(len(self.student.get_requested_courses()), 0, "Requested courses should be cleared")

   
if __name__ == "__main__":
    unittest.main()
