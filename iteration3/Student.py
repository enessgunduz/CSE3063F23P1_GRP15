class Student:
    total_credit = 0
    def __init__(self, username, name, surname, password, studentID, GPA, StSemester, enrolledCourses, requestedCourses, projectAssistant, transcript):
        self.username=username
        self.name=name
        self.surname=surname
        self.password=password
        self.studentID=studentID
        self.GPA=GPA
        self.StSemester=StSemester
        self.enrolledCourses=enrolledCourses
        self.requestedCourses=requestedCourses
        self.projectAssistant=projectAssistant
        self.transcript=transcript
    def getUsername(self):
        return self.username
    def getName(self):
        return self.name
    def getSurname(self):
        return self.surname
    def getPassword(self):
        return self.password
    def getstudentID(self):
        return self.studentID
    def getGPA(self):
        return self.GPA
    def getStSemester(self):
        return self.StSemester
    def getEnrolledCourses(self):
        return self.enrolledCourses
    def getRequestedCourses(self):
        return self.requestedCourses
    def getProjectAssistant(self):
        return self.projectAssistant
    def getTranscript(self):
        return self.transcript
    def calculateTotalCredit(self):
        global total_credit
        for course in self.getEnrolledCourses:
            total_credit += course.credit
        return total_credit
    def getTotalCredit():
        global total_credit
        return total_credit
        