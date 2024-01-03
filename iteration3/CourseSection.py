class CourseSection:
    def __init__(self, term="", day="", hour="", semester=0, instructor="", enrollment_capacity=0, status=""):
        self.term = term
        self.day = day
        self.hour = hour
        self.semester = semester
        self.instructor = instructor
        self.enrollment_capacity = enrollment_capacity
        self.status = status

    def get_term(self):
        return self.term

    def get_day(self):
        return self.day

    def get_hour(self):
        return self.hour

    def get_semester(self):
        return self.semester

    def get_instructor(self):
        return self.instructor

    def get_enrollment_capacity(self):
        return self.enrollment_capacity

    def get_status(self):
        return self.status

    def __str__(self):
        return (f"CourseSection[term={self.term}, instructor={self.instructor}, status={self.status}, "
                f"enrollmentCapacity={self.enrollment_capacity}]")
