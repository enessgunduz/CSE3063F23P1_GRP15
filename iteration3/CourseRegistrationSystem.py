import json


class CourseRegistrationSystem:
    def __init__(self, student, courses):
        self.student=student
        self.courses=courses
    
    def listAvailableCourses(self):
        availableCourses=[]
        coursesTaken=[]
        takens = self.student.transcript["grades"]
        for course in takens:
            courseIds = course["course"]["courseId"]
            coursesTaken.append(courseIds)
        for course in self.courses:
            if(course not in self.student.getEnrolledCourses()):
                if(course.courseID not in coursesTaken):
                    if(not course.prerequisite):
                        availableCourses.append(course)
                    else:
                        for c in coursesTaken:
                            print(c)
                            if(c==course.courseID):
                                print("c")
                                availableCourses.append(course)

        return availableCourses