class User:
    def __init__(self, username, name, surname, password):
        self.username = username
        self.name = name
        self.surname = surname
        self.password = password

    def get_username(self):
        return self.username

    def get_password(self):
        return self.password

    def get_name(self):
        return self.name

    def get_surname(self):
        return self.surname
