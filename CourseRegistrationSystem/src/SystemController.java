import java.util.List;
import java.util.Scanner;

public class SystemController {

    Student student;
    List<Student> studentList;
    Advisor advisor;
    List<Advisor> advisorList;

    List<CourseSection> courseList;

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the system!");
        SystemController sC = new SystemController();
        sC.login();
    }

    private void login(){
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
            for (int a= 0; a<studentList.lenght(); a++){
                if(studentList.get(a).getUsername().equals(username)){
                    if(studentList.get(a).getPassword().equals(password)){
                        //login succesfull
                        student = studentList.get(a);
                        System.out.println("Welcome "+student.getName());
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
            for (int a= 0; a<advisorList.lenght(); a++){
                if(advisorList.get(a).getUsername().equals(username)){
                    if(advisorList.get(a).getPassword().equals(password)){
                        //login succesfull
                        advisor = advisorList.get(a);
                        System.out.println("Welcome "+advisor.getName());
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
            CourseRegistrationSystem crg= new CourseRegistrationSystem(student,courseList);
            //here

        } else if (i==3){
            System.out.println("Logging out...");
            login();
        }
    }

    private void welcomeAdvisor(){

    }
}