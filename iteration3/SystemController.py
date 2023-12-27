from JsonParser import *

students = studentParser()
advisors = advisorParser()
courses = courseParser()

print(students[0].name)
print(advisors[0].getName())
print(courses[3].getCourseID())
