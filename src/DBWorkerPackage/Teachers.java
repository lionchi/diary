package DBWorkerPackage;

public class Teachers {

    private int  id;
    private String login;
    private String password;
    private String classes;
    private String subjects;
    private  String mainClass;

    public Teachers() {
    }

    public Teachers(int id, String login, String password, String classes, String subjects) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.classes = classes;
        this.subjects = subjects;
        this.mainClass = mainClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubjects() {
        return subjects;
    }
    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }


    public String getMainClass() {
        return subjects;
    }
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}

