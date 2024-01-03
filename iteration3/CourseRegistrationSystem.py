from JsonMethods import JSONMethods


class CourseRegistrationSystem:
    def __init__(self, student=None, courses=None):
        self.student = student
        self.courses = courses
        
    def list_available_courses(self):
        available_courses = []
        courses_taken = [grade.get_course() for grade in self.student.view_transcript().all_grades()]
        
        for course in self.courses:
            enrolled_courses_ids = [enrolled_course.get_course_id() for enrolled_course in self.student.get_enrolled_courses()]
            
            if (course.get_course_id() not in enrolled_courses_ids and 
                course.get_course_id() not in [taken_course.get_course_id() for taken_course in courses_taken]):
                
                if course.has_prerequisite():
                    for taken_course in courses_taken:
                        if taken_course.get_course_id() == course.get_prerequisite_lesson_id():
                            available_courses.append(course)
                            break
                else:
                    if self.student.get_gpa() >= 3 or self.student.get_semester() >= course.get_course_section().get_semester():
                        available_courses.append(course)
        
        return available_courses

    def request_in_course(self, course, student):
        student.get_requested_courses().append(course)
        jM = JSONMethods()
        jM.add_request_course(course, student)
        jM.update_enrollment_capacity(course.get_course_id(), course.get_course_section().get_enrollment_capacity() - 1)

    def reject_course(self, course):
        jM = JSONMethods()
        jM.update_enrollment_capacity(course.get_course_id(), course.get_course_section().get_enrollment_capacity() + 1)

    def check_for_conflicts(self, courses):
        for i in range(len(courses)):
            course1 = courses[i]
            section1 = course1.get_course_section()

            for j in range(i + 1, len(courses)):
                course2 = courses[j]
                section2 = course2.get_course_section()
                if section1.get_day() == section2.get_day() and ((self.do_time_ranges_overlap(section1.get_hour(), section2.get_hour()))or(self.do_time_ranges_overlap(section2.get_hour(), section1.get_hour()))):
                    return f"Conflict found between:\n{course1.__str__()}\n{course2.__str__()}\nPlease select only one of them"

        return ""

    def do_time_ranges_overlap(self, time_range1, time_range2):
        time1 = time_range1.split("-")
        start_hour1 = time1[0]
        end_hour1 = time1[1]
        time2=time_range2.split("-")
        start_hour2 = time2[0]
        end_hour2 = time2[1]

        return start_hour1 <= start_hour2 <= end_hour1 or start_hour1 <= end_hour2 <= end_hour1
