class Transcript:
    def __init__(self, grades=None):
        self.grades = grades if grades else []

    def all_grades(self):
        return self.grades

    def __str__(self):
        sb = []
        # Headers
        header = f"{'Course ID':<10} {'Course Name':<35} {'Grade Value':<10}\n"
        sb.append(header)
        # Grades
        for grade in self.all_grades():
            sb.append(str(grade))
        return ''.join(sb)
