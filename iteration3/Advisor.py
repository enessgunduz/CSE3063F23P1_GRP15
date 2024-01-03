from typing import List
from User import User
from Student import Student
from Course import Course

class Advisor(User):
    def __init__(self, username="", name="", surname="", password="", advised_student_ids=None):
        super().__init__(username, name, surname, password)
        self.advised_student_ids = advised_student_ids if advised_student_ids else []
        self.requested_students = []
        self.advised_students = []

    def set_advised_students_init(self, students: List['Student']):
        self.advised_students = [s for s in students if s.username in self.advised_student_ids]

    def list_requested_students(self) -> List['Student']:
        self.requested_students = [st for st in self.advised_students if len(st.requested_courses) > 0]
        return self.requested_students

    def approve_course_registration(self, student: 'Student', course: 'Course'):
        course.add_enrolled_student(student, course)

    def students_to_string(self) -> str:
        result = ""
        headers = f"  {'Student ID':<15} {'Full Name':<40}\n"

        if self.advised_students:
            result += "Advised Students:\n"
            result += headers
            for i, student in enumerate(self.advised_students, 1):
                student_info = f"{i}- {student.student_id:<15} {student.name} {student.surname}\n"
                result += student_info
        else:
            result += "No advised students.\n"

        return result

    def get_advised_students(self) -> List['Student']:
        return self.advised_students
