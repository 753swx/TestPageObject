package BusinessObjects;


public class User {
    private String name;
    private String surname;
    private int age;

    private String login;
    private String password;


    public User(String login, String password, String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.name = "Unknown";
        this.surname = "Unknown";
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User:\nname: " + name + "\nsurname: " + surname + "\nage: " + age + "\nlogin: " + login +
                "\npassword: " + password;
    }
}
