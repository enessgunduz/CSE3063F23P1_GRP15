import json
import os
from typing import List
from Student import Student
from Course import Course
from Advisor import Advisor

class JSONParser:
    def convert_json_to_student(self, json_text: str):
        return json.loads(json_text, object_hook=lambda d: Student(**d))

    def parse_students(self):
        folder_path = "Students\\"

        student_files = os.listdir(folder_path)
        students = []
        file_path=[]
        for file in student_files:
            file_path = "Students\\" + file
            with open(file_path, "r", encoding="utf-8") as file:
                data = json.load(file)

                username = data["username"]
                name = data["name"]
                surname = data["surname"]
                password = data["password"]
                studentID = data["studentID"]
                GPA = data["GPA"]
                StSemester = data["StSemester"]
                enrolledCourses = data["enrolledCourses"]
                requestedCourses = data["requestedCourses"]
                projectAssistant = data["projectAssistant"]
                transcript = data["transcript"]

                studentInst = Student(username, name, surname, password, studentID, GPA, StSemester, enrolledCourses, requestedCourses, projectAssistant, transcript)
                students.append(studentInst)
        return students

    def parse_courses(self):
        file_path="course.json"
        courses=[]
        with open(file_path, "r", encoding="utf-8") as file:
            data = json.load(file)
            for course in data:
                courseId = course["courseId"]
                courseName = course["courseName"]
                credit = course["credit"]
                prerequisite = course["prerequisite"]
                prerequisiteLessonId = course["prerequisiteLessonId"]
                courseSection = course["courseSection"]
                courseInst = Course(courseId, courseName, credit, prerequisite, prerequisiteLessonId, courseSection)
                courses.append(courseInst)
            return courses

    def convert_json_to_advisor(self, json_text: str):
        return json.loads(json_text, object_hook=lambda d: Advisor(**d))

    def parse_advisor(self):
        folder_path="Advisors\\"
        advisor_files = os.listdir(folder_path)
        advisors=[]
        for file in advisor_files:
            file_path = "Advisors\\" + file
            with open(file_path, "r", encoding="utf-8") as file:
                data = json.load(file)

                name = data["name"]
                surname = data["surname"]
                username = data["username"]
                password = data["password"]
                advisedStudents = data["advisedStudents"]

                advisorInst = Advisor(name, surname, username, password, advisedStudents)
                advisors.append(advisorInst)
        return advisors 
