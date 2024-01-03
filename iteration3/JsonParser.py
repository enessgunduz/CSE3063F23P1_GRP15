import json
import os
from typing import List
from Student import Student
from Course import Course
from Advisor import Advisor
from Transcript import Transcript
from Grade import Grade
from CourseSection import CourseSection

class JSONParser:
    def convert_json_to_student(self, json_text: str):
        return json.loads(json_text, object_hook=lambda d: Student(**d))

    def parse_students(self):
        folder_path = "Students//"

        student_files = os.listdir(folder_path)
        students = []
        file_path=[]
        for file in student_files:
            file_path = "Students//" + file
            with open(file_path, "r", encoding="utf-8") as file:
                data = json.load(file)

                username = data["username"]
                name = data["name"]
                surname = data["surname"]
                password = data["password"]
                studentID = data["studentID"]
                GPA = data["GPA"]
                StSemester = data["StSemester"]
                enrolledCoursesString = data["enrolledCourses"]
                enrolledCourses = []
                for course in enrolledCoursesString:
                    c = Course(course["courseId"], course["courseName"], course["credit"], course["prerequisite"],
                                     course["prerequisiteLessonId"], CourseSection(course["courseSection"]["term"], course["courseSection"]["day"],
                                    course["courseSection"]["hour"], course["courseSection"]["semester"], course["courseSection"]["instructor"],
                                      course["courseSection"]["enrollmentCapacity"],
                                    course["courseSection"]["status"]))
                    enrolledCourses.append(c)
                requestedCoursesString = data["requestedCourses"] 
                requestedCourses = []
                for course in requestedCoursesString:
                    c = Course(course["courseId"], course["courseName"], course["credit"], course["prerequisite"],
                                     course["prerequisiteLessonId"], CourseSection(course["courseSection"]["term"], course["courseSection"]["day"],
                                    course["courseSection"]["hour"], course["courseSection"]["semester"], course["courseSection"]["instructor"],
                                      course["courseSection"]["enrollmentCapacity"],
                                    course["courseSection"]["status"]))
                    requestedCourses.append(c)
                projectAssistant = data["projectAssistant"]
                gradeList=[]
                for grade in data["transcript"]["grades"]:
                    g = Grade(Course(grade["course"]["courseId"], grade["course"]["courseName"], grade["course"]["credit"], grade["course"]["prerequisite"],
                                      grade["course"]["prerequisiteLessonId"], CourseSection(grade["course"]["courseSection"]["term"], grade["course"]["courseSection"]["day"],
                                    grade["course"]["courseSection"]["hour"], grade["course"]["courseSection"]["semester"], grade["course"]["courseSection"]["instructor"],
                                      grade["course"]["courseSection"]["enrollmentCapacity"],
                                    grade["course"]["courseSection"]["status"])), grade["gradeValue"])   
                    gradeList.append(g)
                transcript = Transcript(gradeList)

                studentInst = Student(username, name, surname, password, studentID, GPA, StSemester, enrolledCourses, requestedCourses, transcript, projectAssistant)
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
                courseSection = CourseSection(course["courseSection"]["term"],course["courseSection"]["day"],
                                    course["courseSection"]["hour"],course["courseSection"]["semester"], course["courseSection"]["instructor"],
                                     course["courseSection"]["enrollmentCapacity"],
                                    course["courseSection"]["status"])

                courseInst = Course(courseId, courseName, credit, prerequisite, prerequisiteLessonId, courseSection)
                courses.append(courseInst)
            return courses

    def convert_json_to_advisor(self, json_text: str):
        return json.loads(json_text, object_hook=lambda d: Advisor(**d))

    def parse_advisor(self):
        folder_path="Advisors//"
        advisor_files = os.listdir(folder_path)
        advisors=[]
        for file in advisor_files:
            file_path = "Advisors//" + file
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
