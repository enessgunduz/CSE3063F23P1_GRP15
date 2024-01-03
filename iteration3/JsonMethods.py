import json

class JSONMethods:
    def clear_requested_courses(student):
        try:
            file_path = f"src/Students/{student.get_student_id()}.json"
            with open(file_path, 'r+') as json_file:
                data = json.load(json_file)
                data['requestedCourses'] = []
                json_file.seek(0)
                json.dump(data, json_file, indent=4)
                json_file.truncate()
            student.clear_requested_courses()
            return True
        except Exception as e:
            print(e)
            return False

    def add_enrolled_student(student, course):
        try:
            file_path = f"src/Students/{student.get_student_id()}.json"
            with open(file_path, 'r+') as json_file:
                data = json.load(json_file)
                enrolled_courses = data.get('enrolledCourses', [])
                new_course = {
                    "courseId": course.get_course_id(),
                    "courseName": course.get_course_name(),
                    "credit": course.get_credit(),
                    "prerequisite": course.has_prerequisite(),
                    "prerequisiteLessonId": course.get_prerequisite_lesson_id(),
                    "courseSection": {
                        "term": course.get_course_section().get_term(),
                        "day": course.get_course_section().get_day(),
                        "hour": course.get_course_section().get_hour(),
                        "status": course.get_course_section().get_status(),
                        "semester": course.get_course_section().get_semester(),
                        "instructor": course.get_course_section().get_instructor(),
                        "enrollmentCapacity": course.get_course_section().get_enrollment_capacity(),
                        "status": course.get_course_section().get_status()
                    }
                }
                enrolled_courses.append(new_course)
                data['enrolledCourses'] = enrolled_courses
                json_file.seek(0)
                json.dump(data, json_file, indent=4)
                json_file.truncate()
            return True
        except Exception as e:
            print(e)
            return False

    def add_request_course(course, student):
        try:
            file_path = f"src/Students/{student.get_student_id()}.json"
            with open(file_path, 'r+') as json_file:
                data = json.load(json_file)
                requested_courses = data.get('requestedCourses', [])
                new_course = {
                    "courseId": course.get_course_id(),
                    "courseName": course.get_course_name(),
                    "credit": course.get_credit(),
                    "prerequisite": course.has_prerequisite(),
                    "prerequisiteLessonId": course.get_prerequisite_lesson_id(),
                    "courseSection": {
                        "term": course.get_course_section().get_term(),
                        "day": course.get_course_section().get_day(),
                        "hour": course.get_course_section().get_hour(),
                        "status": course.get_course_section().get_status(),
                        "semester": course.get_course_section().get_semester(),
                        "instructor": course.get_course_section().get_instructor(),
                        "enrollmentCapacity": course.get_course_section().get_enrollment_capacity() - 1,
                        "status": course.get_course_section().get_status()
                    }
                }
                requested_courses.append(new_course)
                data['requestedCourses'] = requested_courses
                json_file.seek(0)
                json.dump(data, json_file, indent=4)
                json_file.truncate()
        except Exception as e:
            print(e)

    def update_enrollment_capacity(course_id, new_capacity):
        try:
            file_path = "src/course.json"
            with open(file_path, 'r+') as json_file:
                data = json.load(json_file)
                for course in data:
                    if course['courseId'] == course_id:
                        course['courseSection']['enrollmentCapacity'] = new_capacity
                        json_file.seek(0)
                        json.dump(data, json_file, indent=4)
                        json_file.truncate()
                        return True
                return False
        except Exception as e:
            print(e)
            return False

    def update_project_assistant(student, project_assistant):
        try:
            file_path = f"src/Students/{student.get_student_id()}.json"
            with open(file_path, 'r+') as json_file:
                data = json.load(json_file)
                data['projectAssistant'] = project_assistant
                json_file.seek(0)
                json.dump(data, json_file, indent=4)
                json_file.truncate()
        except Exception as e:
            print(e)
