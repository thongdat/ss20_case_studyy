package quan_li_nhan_vien;

public class Developer extends Employee {
    public String programingLanguage;

    Developer() {
    }

    public Developer(String name, double salary, int id, String programingLanguage) {
        super(name, salary, id);
        this.programingLanguage = programingLanguage;
    }

    public String getProgrammingLanguage() {
        return programingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programingLanguage = programmingLanguage;
    }


    @Override
    public String toString() {
        return super.toString() + "Developer{" + "programingLanguage=" + programingLanguage + '}';
    }
}
