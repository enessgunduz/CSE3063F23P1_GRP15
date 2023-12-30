from JsonParser import *
from CourseRegistrationSystem import *

students = studentParser()
advisors = advisorParser()
courses = courseParser()

def main():

    students[1].transcript["grades"][0]["gradeValue"] = "DC"   
    print(students[1].transcript["grades"][0]["gradeValue"])
    print(advisors[0].getName())
    print(courses[3].getCourseID())
    login()


def login():
    who = input("Who are you?\n1. Advisor\n2. Student\n0. Exit\n")
    if(who=="1"):
        advisorPanel()
    elif(who=="2"):
        studentPanel()
    elif(who=="0"):
        exit

def advisorPanel():
    username = input("Username: ")
    password = input("Password: ")
    for advisor in advisors:
        if(username==advisor.getUsername() and password==advisor.getPassword()):
            welcomeAdvisor(advisor)
            

def studentPanel():
    username = input("Username: ")
    password = input("Password: ")
    for student in students:
        if(username==student.getUsername() and password==student.getPassword()):
            welcomeStudent(student)

def welcomeAdvisor(advisor):
    requestedStudents = []
    advisorAction = input("Welcome dear " + advisor.getSurname() + "\nChoose what you want to do?\n1. See advised student\n2. See students wants to enroll\n3. Log out\n")
    if(advisorAction=="1"):
        seeAdvisedStudents(advisor)
    if(advisorAction=="2"):
        seeStudentsRequests(advisor)
    if(advisorAction=="3"):
        login()


def seeStudentsRequests(advisor):
    requestedStudents = []
    count = 1
    for student in advisor.getAdvisedStudents:
        if(student.getRequestedCourses != []):
            requestedStudents.append(student)
            print(f"{count}. Student full name: {student.name()} {student.surname()}, Student ID: {student.getstudentID()}, Number of courses: {len(student.getRequestedCourses)}")
            count += 1
    print("NOTIFICATION\nThere are students who wants to enroll")
    return requestedStudents

def seeAdvisedStudents(advisor):
    print(advisor.getAdvisedStudents)
    returnSelect = input("0. return main menu\n")
    if(returnSelect=="0"):
        welcomeAdvisor()

def welcomeStudent(student):
    studentAction = input("Welcome dear " + student.getSurname() + "\nChoose what you want to do?\n1. View transcript\n2. Course Registration\n3. Request Final Project\n0. Log out\n")
    if(studentAction=="1"):
        showTranscriptScreen(student)
    if(studentAction=="2"):
        courseRegistrationScreen(student)
    if(studentAction=="3"):
        requestFinalProject(student)
    if(studentAction=="0"):
        login()

def showTranscriptScreen(student):
    json_data = student.getTranscript()
    print(json.dumps(json_data, indent=4))
    returnInput = input("0. return main menu\n")
    if(returnInput=="0"):
        welcomeStudent(student)
    else:
        print("So what!!!")

def courseRegistrationScreen(student):
    index=1
    if(len(student.getRequestedCourses())!=0):
        print("You already have sent your requests")
        welcomeStudent()
    if(len(student.getEnrolledCourses())!=0):
        print("You already have enrolled courses")
        welcomeStudent()
    else:
        crs = CourseRegistrationSystem(student, courses)
        availableCourses = crs.listAvailableCourses()
        print("---------------------------------------------------------------------------------------------------------------------------\nHere is your available courses")
        for course in availableCourses:
            course_id = course.courseID
            course_name = course.courseName
            instructor = course.courseSection["instructor"]
            day_time = course.courseSection["day"] + " " + course.courseSection["hour"]
            semester = course.courseSection["semester"]
            print(f"{index}. Course ID: {course_id}, Course Name: {course_name}, Instructor: {instructor}, Day-Time: {day_time}, Semester: {semester}")
            index += 1

def requestFinalProject(student):
    if(student.getStSemester()>=7 and student.getTotalCredit() >= 105):
        if(student.getProjectAssistant == ""):
            selectedAss = input("Select your project assistant:\n1: Betül Boz\n2: Borahan Tümer\n3: Beste Turanlı\n4: Çiğdem Eroğlu\n")
            if(selectedAss=="1"):
                student.projectAssistant = "Betül Boz"
            else:
                print("Not a real choice bro!")
                welcomeStudent()
        else:
            print("Hey bro watch out. You already have chosen your assistant!")
    else:
        print("not enough credit or semester bro sorry!")

main()