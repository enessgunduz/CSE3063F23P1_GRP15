class Course:
    def __init__(self, courseId, courseName, credit, prerequisite, prerequisiteLessonId, courseSection):
        self.courseID=courseId
        self.courseName=courseName
        self.credit= credit
        self.prerequisite=prerequisite
        self.prerequisiteLessonId=prerequisiteLessonId
        self.courseSection=courseSection
    def getCourseID(self):
        return self.courseID
    def getCourseName(self):
        return self.courseName
    def getCredit(self):
        return self.credit
    def getPrerequisite(self):
        return self.prerequisite
    def getPrerequisiteLessonId(self):
        return self.prerequisiteLessonId
    def getCourseSection(self):
        return self.courseSection
    
        