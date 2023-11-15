import java.util.List;
import java.util.Scanner;

public class SystemController {

    Student student;
    List<Student> studentList;
    Advisor advisor;
    List<Advisor> advisorList;

    List<Course> courseList;

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the system!");
        SystemController sC = new SystemController();
        sC.login();
    }

    private void login(){
        //JSON Section

        System.out.println("1-Student\n2-Advisor");
        int i = scanner.nextInt();
        if (i<1 || i>2){
            System.out.println("Invalid input, try again!");
            login();
            return;
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
        }
    }

    private void welcomeStudent(){
        //Maybe need database refresh for student and advisor
        System.out.println("Please choose what you want to do:\n1-View transcript\n2-Course Registration\n3-Log out");
        int i=scanner.nextInt();
        if (i<1 || i>3){
            System.out.println("Invalid input, try again!");
            welcomeStudent();
            return;
        }
        if (i==1){
            System.out.println(student.viewTranscript().toString());
            System.out.println("\n1-Go back to main menu");
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
                for (Course course : requested) {
                    System.out.println(course.toString());
                }
                //Maybe adding delete request
                welcomeStudent();
                return;

            }
            CourseRegistrationSystem crg= new CourseRegistrationSystem(student,courseList);
            List<Course> availableCourses= crg.listAvailableCourses();
            for (Course availableCours : availableCourses) {
                System.out.println(availableCours.toString());
            }
            System.out.println("Please write the number of the courses you want to enroll with spaces");
            String[] selected = scanner.next().split(" ");
            for (String s : selected) {
                crg.requestInCourse(availableCourses.get(Integer.parseInt(s)), student);
            }
            System.out.println("Selected courses sent to the advisor");
            welcomeStudent();
            return;

        } else if (i==3){
            System.out.println("Logging out...");
            login();
        }
    }

    private void welcomeAdvisor(){
        System.out.println("Please choose what you want to do:\n1-View advised students\n2-See advised students who" +
                " wants to enroll classes\n3-Log out");
        int i = scanner.nextInt();
        if (i<1 || i>3){
            System.out.println("Invalid input, try again!");
            welcomeAdvisor();
            return;
        }
        if (i==1){
            System.out.println(advisor.studentsToString());
            System.out.println("\n1-Go back to main menu");
            i=scanner.nextInt();
            if (i!=1){
                System.out.println("Invalid Input, going main menu");
            }
            welcomeAdvisor();
            return;
        } else if(i==2){
            int a=1;
            for (Student s: advisor.listRequestStudents()) {
                System.out.println(""+(a++) + s.toString());
            }
            System.out.println("Please select which student you want to see: ");
            int k= scanner.nextInt();
            Student currentStudent=advisor.listRequestStudents().get(a-1);
            List<Course> reqCourses = advisor.listStudentRequests(currentStudent);
            int t=1;
            for (Course reqCourse: reqCourses) {
                System.out.println(""+(t++) +reqCourse.toString());
            }
            System.out.println("Please write the number of the courses you want to approve, with spaces (Others will be rejected");
            String[] selected = scanner.next().split(" ");
            for (int d=0;d<reqCourses.size();d++){
                boolean flag=true;
                for (String s : selected) {
                    if (d==Integer.parseInt(s)-1){
                        advisor.approveCourseRegistration(currentStudent, reqCourses.get(d));
                        flag=false;
                        break;
                    }
                }
                if (flag){
                    advisor.rejectCourseRegistration(currentStudent,reqCourses.get(d));
                }
            }

            System.out.println("Selected courses are approved, others' rejected");
            welcomeAdvisor();
            return;
        } else if (i==3){
            System.out.println("Logging out...");
            login();
        }
    }
}