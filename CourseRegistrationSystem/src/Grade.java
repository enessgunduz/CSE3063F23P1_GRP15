public class Grade {
    private Course course;
    private Student student;
    private String gradeValue;

    public Grade(Course course, Student student, String gradeValue) {
        this.course = course;
        this.student = student;
        this.gradeValue = gradeValue;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    @Override
    public String toString() {
        String gradeDatas = String.format("%-10s %-30s %-10s\n",
                getCourse().getCourseID(),
                getCourse().getCourseName(),
                getGradeValue());
        return gradeDatas;
    }
}
