public class Grade {
    private Course course;
    private String gradeValue;

    public Grade(Course course, String gradeValue) {
        this.course = course;
        this.gradeValue = gradeValue;
    }

    public Course getCourse() {
        return course;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    @Override
    public String toString() {
        String gradeDatas = String.format("%-10s %-30s %-10s\n",
                getCourse().getCourseId(),
                getCourse().getCourseName(),
                getGradeValue());
        return gradeDatas;
    }
}
