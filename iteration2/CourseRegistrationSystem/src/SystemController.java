import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SystemController {

    Student student;
    List<Student> studentList;
    Advisor advisor;
    List<Advisor> advisorList;

    List<Course> courseList;

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the system!");
        SystemController sC = new SystemController();
        sC.login();
    }


    private void login() throws IOException {
        JSONParser jP = new JSONParser();
        studentList = jP.parseStudents();
        courseList = jP.parseCourses();
        advisorList = jP.parseAdvisor();

        System.out.println("1: Student\n2: Advisor\n0: Close System");
        int userType = scanner.nextInt();

        if (userType < 0 || userType > 2) {
            System.out.println("Invalid input, try again!");
            login();
            return;
        }

        if (userType == 0) {
            System.out.println("Exiting...");
            System.exit(1);
        }
/*
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
*/
        if (userType == 1) {
            loginAsStudent();
        } else if (userType == 2) {
            loginAsAdvisor();
        }
    }

    private void welcomeStudent() throws IOException {
        System.out.println("Please choose what you want to do:\n1: View transcript\n2: Course Registration\n3: Request Final Project\n4: View course schedule\n0: Log out");

        int i = scanner.nextInt();
        if (i < 0 || i > 4) {
            System.out.println("Invalid input, try again!");
            welcomeStudent();
            return;
        }
        if (i == 1) {
            ShowTranscriptScreen();
        } else if (i == 2) {
            CourseRegistrationScreen();
        } else if (i == 3) {
            ShowFinalRequest();
        } else if (i == 4) {
            ShowScheduleScreen();
        } else {
            System.out.println("Logging out...");
            login();
            return;
        }
    }

    private void welcomeAdvisor() throws IOException {
        List<Student> requestStudents = advisor.listRequestStudents();
        CourseRegistrationSystem crg = new CourseRegistrationSystem();

        if (requestStudents.isEmpty()) {
            handleNoCourseRequests();
        } else {
            handleCourseRequests(requestStudents, crg);
        }
    }

    private void loginAsStudent(String username, String password) throws IOException {
        Student student = findStudent(username);
        if (student != null && student.getPassword().equals(password)) {
            // Login successful
            this.student = student;
            System.out.println("Welcome " + student.getName());
            welcomeStudent();
        } else {
            System.out.println("Username or password is incorrect!");
            loginAsStudent();
        }
    }
    private void loginAsStudent() throws IOException {
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
        loginAsStudent(username, password);
    }

    private void loginAsAdvisor(String username, String password) throws IOException {
        Advisor advisor = findAdvisor(username);

        if (advisor != null && advisor.getPassword().equals(password)) {
            // Login successful
            this.advisor = advisor;
            System.out.println("Welcome " + advisor.getName());
            welcomeAdvisor();
        } else {
            System.out.println("Username or password is incorrect!");
            loginAsAdvisor();
        }
    }

    private void loginAsAdvisor() throws IOException {
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
        loginAsAdvisor(username, password);
    }

    private Student findStudent(String username) {
        for (Student student : studentList) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    private Advisor findAdvisor(String username) {
        for (Advisor advisor : advisorList) {
            if (advisor.getUsername().equals(username)) {
                return advisor;
            }
        }
        return null;
    }

    private void viewStudentTranscript() throws IOException {
        System.out.println(advisor.studentsToString());
        System.out.println("X: Enter the index of the student whose transcript you want to see.\n0: Go back to the main menu");

        int studentIndex = scanner.nextInt();

        if (studentIndex > 0 && studentIndex <= advisor.getAdvisedStudents().size()) {
            Student selectedStudent = advisor.getAdvisedStudents().get(studentIndex - 1);
            System.out.println("Transcript of the selected student: " + selectedStudent.getName() + " " + selectedStudent.getSurname() + "\n" + selectedStudent.viewTranscript());
            handleBackOption();
        } else if (studentIndex == 0) {
            welcomeAdvisor();
        } else {
            System.out.println("Invalid index, returning to the main menu.");
            welcomeAdvisor();
        }
    }

    private void handleBackOption() throws IOException {
        System.out.println("1: Back to view advised students\n0: Go back to the main menu");
        int backOption = scanner.nextInt();

        if (backOption == 1) {
            viewStudentTranscript();
        } else if (backOption == 0) {
            welcomeAdvisor();
        } else {
            System.out.println("Invalid option, returning to the main menu.");
            welcomeAdvisor();
        }
    }

    public void ShowScheduleScreen() throws IOException {
        if (!student.getEnrolledCourses().isEmpty()) {
            showStudentSchedule();
        } else {
            System.out.println("Your course schedule cannot be viewed because you are not registered for the courses. Return main menu...");
            welcomeStudent();
        }
    }

    public void ShowTranscriptScreen() throws IOException {
        System.out.print(student.viewTranscript().toString());
        for (Course c : student.getEnrolledCourses()) {
            Grade g = new Grade(c, "--");
            System.out.print(g.toString());
        }
        System.out.println("\n1: Go back to main menu");
        int l = scanner.nextInt();
        if (l != 1) {
            System.out.println("Invalid Input, going main menu");
        }
        welcomeStudent();
        return;
    }

    public void CourseRegistrationScreen() throws IOException {
        if (!student.getRequestedCourses().isEmpty()) {
            System.out.println("You already sent your list. Here is your courses that sent to advisor");
            List<Course> requested = student.getRequestedCourses();
            int l = 1;
            String headersRequestedCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                    "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
            System.out.println(headersRequestedCourses);
            for (Course course : requested) {
                System.out.println((l++) + "- " + course.toString());
            }
            //Maybe adding delete request
            welcomeStudent();
            return;

        }
        if (!student.getEnrolledCourses().isEmpty()) {
            System.out.println("You have already registered for courses. Here is your courses:");
            List<Course> enrolled = student.getEnrolledCourses();
            int l = 1;
            String headersEnrolledCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-23s %-20s",
                    "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Day-Hour", "Instructor");
            System.out.println(headersEnrolledCourses);
            for (Course course : enrolled) {
                System.out.println((l++) + "- " + course.toString());
            }
            //Maybe adding delete request
            welcomeStudent();
            return;
        }

        CourseRegistrationSystem crg = new CourseRegistrationSystem(student, courseList);
        List<Course> availableCourses = crg.listAvailableCourses();
        int n = 1;
        String headersavailableCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-23s %-20s",
                "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Day-Hour", "Instructor");
        System.out.println(headersavailableCourses);
        for (Course availableCours : availableCourses) {
            System.out.println(n++ + ": " + availableCours.toString());
        }
        System.out.println("Please write the number of the courses you want to enroll with commas");
        String[] selected = scanner.next().trim().split(",");

        for (String s : selected) {
            if (Integer.parseInt(s) > availableCourses.size() || Integer.parseInt(s) < 1) {
                System.out.println("Enter valid numbers 1 to " + availableCourses.size() + "\ntry again");
                welcomeStudent();
                return;
            }
        }
        if (student.getEnrolledCourses().size() + selected.length > 5) {
            System.out.println("You already enrolled " + student.getEnrolledCourses().size() + " courses," +
                    " you can select max " + (5 - (student.getEnrolledCourses().size())) + " courses");
            CourseRegistrationScreen();
            return;
        }

        List<Course> requestedCourses = new ArrayList<>();

        for (String s : selected) {
            requestedCourses.add(availableCourses.get(Integer.parseInt(s) - 1));
        }

        for (Course course : requestedCourses) {
            if (course.getCourseSection().getEnrollmentCapacity() == 0) {
                System.out.println("Your course selection " + course.getCourseName() + "'s capacity is full, do not" +
                        " select it");
                CourseRegistrationScreen();
                return;
            }
        }

        if (crg.checkForConflicts(requestedCourses)) {
            CourseRegistrationScreen();
            return;
        }


        for (String s : selected) {
            crg.requestInCourse(availableCourses.get(Integer.parseInt(s) - 1), student);
        }
        System.out.println("Selected courses sent to the advisor");
        welcomeStudent();
        return;
    }

    public void ShowFinalRequest() throws IOException {
        if (student.getTotalCredits() >= 165 && student.getSemester() >= 7) {
            if (student.getProjectAssistant().isEmpty()) {
                //selection
                System.out.println("Select your project assistant:\n1: Betül Boz\n2: Borahan Tümer\n3: Beste Turanlı\n4: Çiğdem Eroğlu");
                int selected_ass = scanner.nextInt();
                JSONMethods jM = new JSONMethods();
                if (selected_ass >= 1 && selected_ass <= 4) {
                    switch (selected_ass) {
                        case 1:
                            System.out.println("You now have taken the project\nProject Assistant: Betül Boz");
                            jM.updateProjectAssistant(student, "Betül Boz");
                            break;
                        case 2:
                            System.out.println("You now have taken the project\nProject Assistant: Borahan Tümer");
                            jM.updateProjectAssistant(student, "Borahan Tümer");
                            break;
                        case 3:
                            System.out.println("You now have taken the project\nProject Assistant: Beste Turanlı");
                            jM.updateProjectAssistant(student, "Beste Turanlı");
                            break;
                        case 4:
                            System.out.println("You now have taken the project\nProject Assistant: Çiğdem Eroğlu");
                            jM.updateProjectAssistant(student, "Çiğdem Eroğlu");
                            break;
                    }
                }
            } else {
                System.out.println("You already selected your project assistant\nYour project assistant is " + student.getProjectAssistant());
            }
            welcomeStudent();
            return;
        } else {
            System.out.println("You cannot take final project because your credit or semester is not satisfied for " +
                    "our department rules");
            welcomeStudent();
            return;
        }
    }

    public void showStudentSchedule() throws IOException {
        List<Course> enrolledCourses = student.getEnrolledCourses();

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : days) {
            System.out.println(day + ":");

            List<String> hours = getHourFromCourseSections(day);
            sortHours(hours);
            System.out.println("|     Time      | Course ID |               Course Name                |          Instructor        |");
            System.out.println("|---------------|-----------|------------------------------------------|----------------------------|");

            for (String hour : hours) {
                for (Course course : enrolledCourses) {
                    CourseSection courseSection = course.getCourseSection();
                    if (courseSection.getDay().equalsIgnoreCase(day) && courseSection.getHour().equalsIgnoreCase(hour)) {
                        System.out.printf("| %-13s | %-9s | %-40s | %-27s|\n",
                                hour, course.getCourseId(), course.getCourseName(), courseSection.getInstructor());
                    }
                }
            }

            System.out.println();

        }
        System.out.println("0: Back to the main menu");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 0) {
            welcomeStudent();
        } else {
            System.out.println("Invalid Input, return main menu..");
            welcomeStudent();
        }
    }


    private List<String> getHourFromCourseSections(String day) {
        List<CourseSection> courseSections = getCourseSectionsForDay(day);
        return courseSections.stream().map(CourseSection::getHour).collect(Collectors.toList());
    }

    private List<CourseSection> getCourseSectionsForDay(String day) {
        List<Course> enrolledCourses = student.getEnrolledCourses();
        return enrolledCourses.stream()
                .map(Course::getCourseSection)
                .filter(cs -> cs.getDay().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    private void sortHours(List<String> hours) {
        hours.sort(this::compareHours);
    }

    private int compareHours(String hour1, String hour2) {
        int hour1Int = Integer.parseInt(hour1.split(":")[0]);
        int hour2Int = Integer.parseInt(hour2.split(":")[0]);

        return Integer.compare(hour1Int, hour2Int);
    }
    private void handleNoCourseRequests() throws IOException {
        System.out.println("There is no student who has course requests.\n");
        System.out.println("Please choose what you want to do:\n1: View advised students\n0: Log out");

        int choice = scanner.nextInt();

        if (choice == 1) {
            viewStudentTranscript();
        } else if (choice == 0) {
            System.out.println("Logging out...");
            login();
        } else {
            System.out.println("Invalid input, try again!");
            welcomeAdvisor();
        }
    }

    private void handleCourseRequests(List<Student> requestStudents, CourseRegistrationSystem crg) throws IOException {
        System.out.println("There are student(s) who want to enroll in the course...\n");
        System.out.println("Please choose what you want to do:\n1: View advised students\n2: See advised students who " +
                "wants to enroll classes\n0: Log out");

        int choice = scanner.nextInt();

        if (choice == 1) {
            viewStudentTranscript();
        } else if (choice == 2) {
            viewAndApproveCourses(requestStudents, crg);
        } else if (choice == 0) {
            System.out.println("Logging out...");
            login();
        } else {
            System.out.println("Invalid input, try again!");
            welcomeAdvisor();
        }
    }
    private void viewAndApproveCourses(List<Student> requestStudents, CourseRegistrationSystem crg) throws IOException {
        int studentIndex = displayRequestedStudents(requestStudents);

        if (studentIndex == -1) {
            System.out.println("Enter valid number, returning to the main menu");
            welcomeAdvisor();
            return;
        }

        Student currentStudent = requestStudents.get(studentIndex - 1);
        List<Course> reqCourses = currentStudent.getRequestedCourses();

        displayRequestedCourses(reqCourses);

        System.out.println("Please write the number of the courses you want to approve, with commas (Others will be rejected)\n(For reject all, write 0)");
        String[] selected = scanner.next().trim().split(",");

        if (shouldRejectAll(selected, reqCourses)) {
            rejectAllCourses(reqCourses, crg);
        } else {
            approveSelectedCourses(selected, currentStudent, reqCourses, crg);
        }

        JSONMethods jM = new JSONMethods();
        jM.clearRequestedCourses(currentStudent);

        welcomeAdvisor();
    }

    private int displayRequestedStudents(List<Student> requestStudents) {
        int a = 1;

        String headersReqStudents = String.format("   %-30s %-30s %-8s", "Name", "Surname", "Student ID");
        System.out.println(headersReqStudents);

        for (Student s : requestStudents) {
            System.out.println((a++) + ": " + s.toString());
        }

        System.out.println("Please select which student you want to see: ");
        int studentIndex = scanner.nextInt();

        return (studentIndex > 0 && studentIndex <= requestStudents.size()) ? studentIndex : -1;
    }

    private void displayRequestedCourses(List<Course> reqCourses) {
        int t = 1;

        String headersReqCourse = String.format("      %-15s %-40s %-8s %-20s %-10s %-23s %-20s",
                "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Day-Hour", "Instructor");
        System.out.println(headersReqCourse);

        for (Course reqCourse : reqCourses) {
            System.out.println((t++) + ": " + reqCourse.toString());
        }
    }

    private boolean shouldRejectAll(String[] selected, List<Course> reqCourses) {
        return selected.length == 1 && Integer.parseInt(selected[0]) == 0;
    }

    private void rejectAllCourses(List<Course> reqCourses, CourseRegistrationSystem crg) throws IOException {
        for (Course course : reqCourses) {
            crg.rejectCourse(course);
        }
        System.out.println("All courses are rejected");
    }

    private void approveSelectedCourses(String[] selected, Student currentStudent, List<Course> reqCourses, CourseRegistrationSystem crg) throws IOException {
        List<Course> dummyReqCourses = new ArrayList<>(reqCourses);

        for (String s : selected) {
            int courseIndex = Integer.parseInt(s);

            if (courseIndex > 0 && courseIndex <= dummyReqCourses.size()) {
                advisor.approveCourseRegistration(currentStudent, dummyReqCourses.get(courseIndex - 1));
                currentStudent.getRequestedCourses().remove(dummyReqCourses.get(courseIndex - 1));
            }
        }

        for (Course course : currentStudent.getRequestedCourses()) {
            crg.rejectCourse(course);
        }

        System.out.println("Selected courses are approved, others' rejected");
    }


}
