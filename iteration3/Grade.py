class Grade:
    def __init__(self, course=None, grade_value=""):
        self.course = course
        self.grade_value = grade_value

    def get_course(self):
        return self.course

    def get_grade_value(self):
        return self.grade_value

    def __str__(self):
        grade_data = f"{self.course.course_id:<10} {self.course.course_name:<35} {self.grade_value:<10}\n"
        return grade_data
