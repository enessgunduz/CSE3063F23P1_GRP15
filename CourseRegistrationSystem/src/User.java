abstract class User {
    private String username;
    private String name;
    private String surname;
    private String password;



    public User(String username, String name, String surname, String password){
        this.username = username;
        this.name =  name;
        this.surname = surname;
        this.password = password;
    }

    public User(){

    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
