package quan_li_nhan_vien;

import java.text.DecimalFormat;


public class Employee {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    public String name;
    public double salary;
    public int id;
    private String position;

    Employee() {
    }

    public Employee(String name, double salary, int id) {
        this.name = name;
        this.salary = salary;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", salary=" + df.format(salary) + ", id=" + id + "]";
    }
}
