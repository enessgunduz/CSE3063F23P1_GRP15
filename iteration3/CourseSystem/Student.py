from typing import List

from User import User
from Transcript import Transcript

class Student:
    def __init__(self, username="", name="", surname="", password="", student_id="", gpa=0.0, st_semester=0,
                 enrolled_courses=None, requested_courses=None, transcript=None, project_assistant=""):
        self.username = username
        self.name = name
        self.surname = surname
        self.password = password
        self.student_id = student_id
        self.gpa = gpa
        self.st_semester = st_semester
        self.enrolled_courses = enrolled_courses if enrolled_courses else []
        self.requested_courses = requested_courses if requested_courses else []
        self.transcript = transcript 
        self.project_assistant = project_assistant
        self.total_credits = self.calculate_total_credits()

    def calculate_total_credits(self):
        total_credits = 0
        for grade in self.transcript.all_grades():
            if grade != "--":
                total_credits += grade.course.credit  
        return total_credits

    def clear_requested_courses(self):
        self.requested_courses = []

    def get_total_credits(self):
        return self.total_credits

    def view_transcript(self):
        print(self.get_student_info())
        print("-------------------------------------------------------------------")
        return self.transcript

    def get_student_id(self):
        return self.student_id

    def get_project_assistant(self):
        return self.project_assistant

    def get_gpa(self):
        return self.gpa

    def get_semester(self):
        return self.st_semester

    def get_enrolled_courses(self):
        return self.enrolled_courses

    def get_requested_courses(self):
        return self.requested_courses
    
    def get_name(self):
        return self.name
    
    def get_surname(self):
        return self.surname

    def __str__(self):
        student_info = f"{self.get_name():<30} {self.get_surname():<30} {self.get_student_id():<8}"
        return student_info

    def get_student_info(self):
        header = f"Name: {self.get_name():<45} GPA: {self.get_gpa():<35}\n"
        lower_header = f"Surname: {self.get_surname():<45} Completed Credit: {self.get_total_credits():<35}"
        return header + lower_header
