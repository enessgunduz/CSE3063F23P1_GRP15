import java.io.IOException;
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
        studentList=jP.parseStudents();
        courseList=jP.parseCourses();
        advisorList=jP.parseAdvisor();

        System.out.println("1: Student\n2: Advisor\n3: Close System");
        int i = scanner.nextInt();
        if (i<1 || i>3){
            System.out.println("Invalid input, try again!");
            login();
            return;
        }
        if (i==3) {
            System.out.println("Exiting...");
            System.exit(1);
        }

        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password:");
        String password = scanner.next();
        if (i==1){
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
        } else if (i==2){
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
        System.out.println("Please choose what you want to do:\n1: View transcript\n2: Course Registration\n3: Log out");
        int i=scanner.nextInt();
        if (i<1 || i>3){
            System.out.println("Invalid input, try again!");
            welcomeStudent();
            return;
        }
        if (i==1){
            System.out.print(student.viewTranscript().toString());
            for (Course c: student.getEnrolledCourses()){
                Grade g = new Grade(c,"--");
                System.out.print(g.toString());
            }
            System.out.println("\n1: Go back to main menu");
            i=scanner.nextInt();
            if (i!=1){
                System.out.println("Invalid Input, going main menu");
            }
            welcomeStudent();
            return;

        } else if (i==2){
            if (student.getRequestedCourses().size()>0){
                System.out.println("You already sent your list. Here is your courses that sent to advisor");
                List<Course> requested = student.getRequestedCourses();
                int l = 1;
                String headersRequestedCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                        "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
                System.out.println(headersRequestedCourses);
                for (Course course : requested) {
                    System.out.println( (l++)+"- "+course.toString());
                }
                //Maybe adding delete request
                welcomeStudent();
                return;

            }
            CourseRegistrationSystem crg= new CourseRegistrationSystem(student,courseList);
            List<Course> availableCourses= crg.listAvailableCourses();
            int n=1;
            String headersavailableCourses = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                    "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
            System.out.println(headersavailableCourses);
            for (Course availableCours : availableCourses) {
                System.out.println(n++ + ": " + availableCours.toString());
            }
            System.out.println("Please write the number of the courses you want to enroll with commas");
            String[] selected = scanner.next().trim().split(",");

            for (String s: selected){
                if (Integer.parseInt(s) > availableCourses.size() || Integer.parseInt(s) < 1){
                    System.out.println("Enter valid numbers 1 to " + availableCourses.size() + "\ntry again");
                    welcomeStudent();
                    return;
                }
            }
            if (selected.length>5){
                System.out.println("You can select max 5 courses");
                welcomeStudent();
                return;
            }
            for (String s : selected) {
                crg.requestInCourse(availableCourses.get(Integer.parseInt(s)-1), student);
            }
            System.out.println("Selected courses sent to the advisor");
            welcomeStudent();
            return;

        } else if (i==3){
            System.out.println("Logging out...");
            login();
        }
    }

    private void welcomeAdvisor() throws IOException {
        System.out.println("Please choose what you want to do:\n1: View advised students\n2: See advised students who" +
                " wants to enroll classes\n3: Log out");
        int i = scanner.nextInt();
        if (i<1 || i>3){
            System.out.println("Invalid input, try again!");
            welcomeAdvisor();
            return;
        }
        if (i==1){
            System.out.println(advisor.studentsToString());
            System.out.println("\n1: Go back to main menu");
            i=scanner.nextInt();
            if (i!=1){
                System.out.println("Invalid Input, going main menu");
            }
            welcomeAdvisor();
            return;
        } else if(i==2){
            List<Student> requestStudents = advisor.listRequestStudents();
            if (requestStudents.size()==0){
                System.out.println("There is no student who has course request");
                welcomeAdvisor();
                return;
            }
            int a=1;
            String headersreqStudents = String.format("   %-30s %-30s %-8s", "Name", "Surname", "Student ID");

            System.out.println(headersreqStudents);
            for (Student s: requestStudents) {
                System.out.println((a++)+": " + s.toString());
            }
            System.out.println("Please select which student you want to see: ");
            int k= scanner.nextInt();
            if (k>requestStudents.size()){
                System.out.println("Enter valid number, returning main menu");
                welcomeAdvisor();
                return;
            }
            Student currentStudent=requestStudents.get(k-1);
            List<Course> reqCourses = currentStudent.getRequestedCourses();
            int t=1;
            String headersreqCourse = String.format("      %-15s %-40s %-8s %-20s %-10s %-20s",
                    "Course ID", "Course Name", "Credit", "Prerequisite Lesson", "Term", "Instructor");
            System.out.println(headersreqCourse);
            for (Course reqCourse: reqCourses) {
                System.out.println((t++)+": " +reqCourse.toString());
            }
            System.out.println("Please write the number of the courses you want to approve, with commas (Others will be rejected)\n(For reject all, write 0)");
            String[] selected = scanner.next().trim().split(",");
            boolean rejectAll=false;
            for (String s: selected){
                if (Integer.parseInt(s)==0 && selected.length==1){
                    rejectAll=true;
                    System.out.println("All courses are rejected");
                    break;
                }
                if (Integer.parseInt(s) > reqCourses.size() || Integer.parseInt(s) < 1){
                    System.out.println("Enter valid numbers 1 to " + reqCourses.size() + "\ntry again");
                    welcomeAdvisor();
                    return;
                }
            }

            if (!rejectAll){
                for (String s : selected) {

                    advisor.approveCourseRegistration(currentStudent, reqCourses.get(Integer.parseInt(s)-1));
                }
                System.out.println("Selected courses are approved, others' rejected");
            }

            JSONMethods jM = new JSONMethods();
            jM.clearRequestedCourses(currentStudent);
            welcomeAdvisor();
            return;
        } else if (i==3){
            System.out.println("Logging out...");
            login();
        }
    }
}