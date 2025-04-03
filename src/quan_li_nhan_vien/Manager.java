package quan_li_nhan_vien;

import java.text.DecimalFormat;

public class Manager extends Employee {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    private double bonus;

    public Manager() {}

    public Manager(String name, double salary, int id, double bonus) {
        super(name, salary, id);
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double calculateSalary() {
        return getSalary() + (getSalary() * bonus / 100);
    }

    @Override
    public String toString() {
        return super.toString() + " Manager{" + "Salary with Bonus=" + df.format(calculateSalary()) + '}';
    }
}
