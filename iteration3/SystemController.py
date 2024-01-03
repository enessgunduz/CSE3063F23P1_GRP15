from JsonParser import JSONParser

class SystemController:
    def __init__(self):
        self.student = None
        self.student_list = []
        self.advisor = None
        self.advisor_list = []
        self.course_list = []

    def main(self):
        print("Welcome to the system!")
        self.login()

    def login(self):
        jP = JSONParser()
        global student_list
        global course_list
        global advisor_list
        student_list = jP.parse_students()
        course_list = jP.parse_courses()
        advisor_list = jP.parse_advisor()

        for advisor in advisor_list:
            print(advisor.get_username())

        try:
            print("1: Student\n2: Advisor\n0: Close System")
            user_type = int(input())

            if user_type < 0 or user_type > 2:
                print("Invalid input, try again!")
                self.login()
                return

            if user_type == 0:
                print("Exiting...")
                exit(1)
            if user_type == 1:
                self.login_as_student()
            elif user_type == 2:
                self.login_as_advisor()
        except ValueError:
            print("Invalid input, try again!")
            self.login()

    def welcome_student(self):
        try:
            print("Please choose what you want to do:\n1: View transcript\n2: Course Registration\n3: Request Final Project\n4: View course schedule\n0: Log out")
            i = int(input())
            if i < 0 or i > 4:
                print("Invalid input, try again!")
                self.welcome_student()
                return
            if i == 1:
                self.show_transcript_screen()
            elif i == 2:
                self.course_registration_screen()
            elif i == 3:
                self.show_final_request()
            elif i == 4:
                self.show_schedule_screen()
            else:
                print("Logging out...")
                self.login()
                return
        except ValueError:
            print("Invalid input, try again!")
            self.welcome_student()
    
    def login_as_student(self):
        username = input("Username: ")
        password = input("Password: ")
        student = self.find_student(username)
        if student is not None and student.password == password:
            self.student = student
            print("Welcome", student.name)
            self.welcome_student()
        else:
            print("Username or password is incorrect!")
            self.login_as_student()

    def find_student(self, username):
        print(username)
        for student in student_list:
            print(student.username)
            if (student.username == username):
                return student
        return None

    
controller = SystemController()
controller.main()

