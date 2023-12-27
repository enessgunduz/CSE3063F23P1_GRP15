class Advisor:
    def __init__(self, name, surname, username, password, advisedStudents):
        self.name=name
        self.surname=surname
        self.username=username
        self.password=password
        self.advisedStudents=advisedStudents
    def getName(self):
        return self.name
    def getSurname(self):
        return self.surname
    def getUsername(self):
        return self.username
    def getPassword(self):
        return self.password
    def getAdvisedStudents(self):
        return self.advisedStudents
    