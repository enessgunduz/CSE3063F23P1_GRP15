import json
from Student import Student
from Advisor import Advisor
from Course import Course
import os

def studentParser():
    folder_path = "iteration3\\Students\\"

    student_files = os.listdir(folder_path)
    students = []
    for file in student_files:
        file_path = "iteration3\\Students\\" + file

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

def advisorParser():
    folder_path="iteration3\\Advisors\\"
    advisor_files = os.listdir(folder_path)
    advisors=[]
    for file in advisor_files:
        file_path = "iteration3\\Advisors\\" + file
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

def courseParser():
    file_path="iteration3\\course.json"
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