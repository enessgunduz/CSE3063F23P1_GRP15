import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        //JSON Section
        JSONParser jP = new JSONParser();
        studentList = jP.parseStudents();
        courseList = jP.parseCourses();
        advisorList = jP.parseAdvisor();

        System.out.println("1: Student\n2: Advisor\n0: Close System");
        int i = scanner.nextInt();
        if (i < 0 || i > 2) {
            System.out.println("Invalid input, try again!");
            login();
            return;
        }
        if (i == 0) {
            System.out.println("Exiting...");
            System.exit(1);
        }

        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
        if (i == 1) {
            for (Student value : studentList) {
                if (value.getUsername().equals(username)) {
                    if (value.getPassword().equals(password)) {
                        //login succesfull
                        student = value;
                        System.out.println("Welcome " + student.getName());
                        welcomeStudent();
                        return;
                    } else {
                        System.out.println("Password is incorrect");
                        login();
                        return;
                    }
                }
            }
            System.out.println("User couldn't found");
            login();
        } else if (i == 2) {
            for (Advisor value : advisorList) {
                if (value.getUsername().equals(username)) {
                    if (value.getPassword().equals(password)) {
                        //login succesfull
                        advisor = value;
                        System.out.println("Welcome " + advisor.getName());
                        welcomeAdvisor();
                        return;
                    } else {
                        System.out.println("Password is incorrect");
                        login();
                        return;
                    }
                }
            }
            System.out.println("User couldn't found");
            login();
        }
    }

    private void welcomeStudent() throws IOException {
        //Maybe need database refresh for student and advisor
        System.out.println("Please choose what you want to do:\n1: View transcript\n2: Course Registration\n3: Request Final Project\n0: Log out");

        int i = scanner.nextInt();
        if (i < 0 || i > 3) {
            System.out.println("Invalid input, try again!");
            welcomeStudent();
            return;
        }
        if (i == 1) {
            System.out.print(student.viewTranscript().toString());
            for (Course c : student.getEnrolledCourses()) {
                Grade g = new Grade(c, "--");
                System.out.print(g.toString());
            }
            System.out.println("\n1: Go back to main menu");
            i = scanner.nextInt();
            if (i != 1) {
                System.out.println("Invalid Input, going main menu");
            }
            welcomeStudent();
            return;

        } else if (i == 2) {
            if (student.getRequestedCourses().size() > 0) {
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
            if (student.getEnrolledCourses().size()>0){
                System.out.println("You have already registered for courses. Here is your courses:");
                List<Course> enrolled = student.getEnrolledCourses();
                int l = 1;
                String headersEnrolledCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                        "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
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
                    "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term","Day-Hour", "Instructor");
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
                welcomeStudent();
                return;
            }

            List<Course> requestedCourses = new ArrayList<>();

            for (String s:selected){
                requestedCourses.add(availableCourses.get(Integer.parseInt(s)-1));
            }

            for (Course course:requestedCourses){
                if (course.getCourseSection().getEnrollmentCapacity()==0){
                    System.out.println("Your course selection "+course.getCourseName()+"'s capacity is full, do not" +
                            " select it");
                    welcomeStudent();
                    return;
                }
            }

            if (crg.checkForConflicts(requestedCourses)){
                welcomeStudent();
                return;
            }



            for (String s : selected) {
                crg.requestInCourse(availableCourses.get(Integer.parseInt(s) - 1), student);
            }
            System.out.println("Selected courses sent to the advisor");
            welcomeStudent();
            return;

        } else if (i==3) {
            if (student.getTotalCredits()>=165 && student.getSemester()>=7){
                if (student.getProjectAssistant().isEmpty()){
                    //selection
                    System.out.println("Select your project assistant:\n1: Betül Boz\n2: Borahan Tümer\n3: Beste Turanlı\n4: Çiğdem Eroğlu");
                    int selected_ass = scanner.nextInt();
                    JSONMethods jM = new JSONMethods();
                    if (selected_ass>=1 && selected_ass<=4){
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
                }
                else {
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
            System.out.println("There is no student who has course request.\n");
            System.out.println("Please choose what you want to do:\n1: View advised students\n0: Log out");
            int i = scanner.nextInt();
            if (i < 0 || i > 1) {
                System.out.println("Invalid input, try again!");
                welcomeAdvisor();
                return;
            }
            if (i == 1) {
                viewStudentTranscript();
                return;
            } else if (i == 0) {
                System.out.println("Logging out...");
                login();
            }
        } else {
            System.out.println("There are student(s) who want to enroll in the course...\n");
            System.out.println("Please choose what you want to do:\n1: View advised students\n2: See advised students who " +
                    "wants to enroll classes\n0: Log out");
            int i = scanner.nextInt();
            if (i < 0 || i > 2) {
                System.out.println("Invalid input, try again!");
                welcomeAdvisor();
                return;
            }
            if (i == 1) {
                viewStudentTranscript();
                return;
            } else if (i == 2) {
                int a = 1;

                String headersreqStudents = String.format("   %-30s %-30s %-8s", "Name", "Surname", "Student ID");
                System.out.println(headersreqStudents);

                for (Student s : requestStudents) {
                    System.out.println((a++) + ": " + s.toString());
                }
                System.out.println("Please select which student you want to see: ");
                int k = scanner.nextInt();
                if (k > requestStudents.size()) {
                    System.out.println("Enter valid number, returning main menu");
                    welcomeAdvisor();
                    return;
                }
                Student currentStudent = requestStudents.get(k - 1);
                List<Course> reqCourses = currentStudent.getRequestedCourses();
                int t = 1;
                String headersreqCourse = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                        "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
                System.out.println(headersreqCourse);
                for (Course reqCourse : reqCourses) {
                    System.out.println((t++) + ": " + reqCourse.toString());
                }
                System.out.println("Please write the number of the courses you want to approve, with commas (Others will be rejected)\n(For reject all, write 0)");
                String[] selected = scanner.next().trim().split(",");
                boolean rejectAll = false;
                for (String s : selected) {
                    if (Integer.parseInt(s) == 0 && selected.length == 1) {
                        rejectAll = true;
                        System.out.println("All courses are rejected");
                        break;
                    }
                    if (Integer.parseInt(s) > reqCourses.size() || Integer.parseInt(s) < 1) {
                        System.out.println("Enter valid numbers 1 to " + reqCourses.size() + "\ntry again");
                        welcomeAdvisor();
                        return;
                    }
                }

                List<Course> dummyReqCourses = new ArrayList<>(currentStudent.getRequestedCourses());
                if (!rejectAll) {
                    for (String s : selected) {

                        advisor.approveCourseRegistration(currentStudent, dummyReqCourses.get(Integer.parseInt(s) - 1));
                        currentStudent.getRequestedCourses().remove(dummyReqCourses.get(Integer.parseInt(s) - 1));
                    }

                    for (Course course:currentStudent.getRequestedCourses())
                        crg.rejectCourse(course);


                    System.out.println("Selected courses are approved, others' rejected");
                } else {
                    for (Course course: currentStudent.getRequestedCourses()){
                        crg.rejectCourse(course);
                    }
                }



                JSONMethods jM = new JSONMethods();
                jM.clearRequestedCourses(currentStudent);
                welcomeAdvisor();
                return;
            } else if (i == 0) {
                System.out.println("Logging out...");
                login();
            }
        }
    }

    private void viewStudentTranscript() throws IOException {
        System.out.println(advisor.studentsToString());
        System.out.println("Enter the index of the student whose transcript you want to see.\n0: Go back to the main menu");

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


}