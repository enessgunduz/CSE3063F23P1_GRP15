import json


class CourseRegistrationSystem:
    def __init__(self, student, courses):
        self.student=student
        self.courses=courses
    
    def listAvailableCourses(self):
        availableCourses = []
        coursesTaken = [course["course"]["courseId"] for course in self.student.transcript["grades"]]

        for course in self.courses:
            if course not in self.student.enrolledCourses:
                if course.courseID not in coursesTaken:
                    if not course.prerequisite:
                        availableCourses.append(course)
                    else:
                        hasPrerequisite = False
                        for c in coursesTaken:
                            if c == course.prerequisiteLessonId:
                                hasPrerequisite = True
                                break
                        if hasPrerequisite:
                            availableCourses.append(course)

        return availableCourses
