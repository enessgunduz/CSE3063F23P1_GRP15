from JsonMethods import JSONMethods


class Course:
    def __init__(self, course_id="", course_name="", credit=0, prerequisite=False, prerequisite_lesson_id="", course_section=None):
        self.course_id = course_id
        self.course_name = course_name
        self.credit = credit
        self.prerequisite = prerequisite
        self.prerequisite_lesson_id = prerequisite_lesson_id
        self.course_section = course_section

    def add_enrolled_student(self, student, course):
        # Assuming JSONMethods is handling the enrollment process
        jM = JSONMethods()
        return jM.add_enrolled_student(student, course)

    def has_prerequisite(self):
        return self.prerequisite

    def __str__(self):
        course_info = f"{self.course_id:<18} {self.course_name:<40} {self.credit:<8} {self.prerequisite_lesson_id:<20} {self.course_section.term:<10} {self.course_section.day}-{self.course_section.hour:<23} {self.course_section.instructor:<20}"
        return course_info

    def get_course_id(self):
        return self.course_id

    def get_credit(self):
        return self.credit

    def get_course_name(self):
        return self.course_name

    def get_prerequisite_lesson_id(self):
        return self.prerequisite_lesson_id

    def get_course_section(self):
        return self.course_section
