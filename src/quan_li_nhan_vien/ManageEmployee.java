package quan_li_nhan_vien;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;


public class ManageEmployee implements IManageEmployee {
    private final Employee[] listEmploy;
    private int count = 0;
    private final Scanner scanner;
    private final String FILE_NAME = "employee_data.txt";

    public ManageEmployee(int size) {
        listEmploy = new Employee[size];
        scanner = new Scanner(System.in);
        readFromFile();
    }

    public void addEmploy() {
        if (this.count == listEmploy.length) {
            System.out.println("Danh sách nhân viên đã đầy!");
        } else {
            System.out.println("Nhập tên nhân viên:");
            String name = scanner.nextLine();
            System.out.println("Nhập lương của nhân viên:");
            double salary = scanner.nextDouble();
            scanner.nextLine();
            int id = generateUniqueID();

            System.out.println("Chọn loại nhân viên: 1. Developer 2. Manager");
            int choice = scanner.nextInt();
            scanner.nextLine();
            Employee employee;

            if (choice == 1) {
                System.out.println("Chọn ngôn ngữ lập trình:");
                String programmingLanguage = scanner.nextLine();
                employee = new Developer(name, salary, id, programmingLanguage);
            } else {
                System.out.println("Nhập vào số tiền bonus:");
                double bonus = scanner.nextDouble();
                scanner.nextLine();
                employee = new Manager(name, salary, id, bonus);
            }
            listEmploy[count] = employee;
            count++;
            saveToFile(employee);
            System.out.println("Đã thêm nhân viên: " + employee);
        }
    }

    private int generateUniqueID() {
        int id = 1;
        Set<Integer> existingIDs = new HashSet<>();

        for (int i = 0; i < count; i++) {
            existingIDs.add(listEmploy[i].getId());
        }

        while (existingIDs.contains(id)) {
            id++;
        }
        return id;
    }

    public void displayEmploy() {
        if (count == 0) {
            System.out.println("Không có nhân viên nào.");
        } else {
            System.out.println("Danh sách nhân viên:");
            for (int i = 0; i < count; i++) {
                System.out.println(listEmploy[i]);
            }
        }
    }

    public void deleteEmploy() {
        System.out.println("Nhập tên nhân viên muốn xóa:");
        String name = scanner.nextLine();

        for (int i = 0; i < count; i++) {
            if (listEmploy[i].getName().equals(name)) {
                System.out.println("Bạn có chắc chắn muốn xóa nhân viên: " + listEmploy[i].getName() + "? (Nhấn 'y' để xác nhận, phím khác để hủy)");
                String confirm = scanner.nextLine().trim().toLowerCase();

                if (confirm.equals("y")) {
                    System.out.println("Đã xóa nhân viên: " + listEmploy[i].getName());
                    for (int j = i; j < count - 1; j++) {
                        listEmploy[j] = listEmploy[j + 1];
                    }
                    listEmploy[count - 1] = null;
                    count--;
                    saveAllToFile();
                } else {
                    System.out.println("Hủy xóa nhân viên.");
                }
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên với tên: " + name);
    }


    public void searchEmployByID() {
        System.out.println("Nhập ID người bạn muốn tìm:");
        int id = scanner.nextInt();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (listEmploy[i].getId() == id) {
                System.out.println(listEmploy[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.printf("Không có nhân viên nào có ID %d\n", id);
        }
    }

    public void searchEmployByName() {
        System.out.println("Nhập tên mà bạn muốn tìm:");
        String name = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (listEmploy[i].getName().contains(name)) {
                System.out.println(listEmploy[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.printf("Không có nhân viên nào có tên %s\n", name);
        }
    }

    public void sortEmploy() {
        Arrays.sort(listEmploy, 0, count, Comparator.comparing(Employee::getName));
        System.out.println("Danh sách nhân viên sau khi sắp xếp theo tên:");
        displayEmploy();
    }

    private void saveToFile(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(formatEmployee(employee));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file: " + e.getMessage());
        }
    }

    private void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < count; i++) {
                writer.write(formatEmployee(listEmploy[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file: " + e.getMessage());
        }
    }

    private void readFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employee employee = parseEmployee(line);
                if (employee != null && count < listEmploy.length) {
                    listEmploy[count++] = employee;
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    private Employee parseEmployee(String line) {
        String[] parts = line.split(", ");
        if (parts.length < 4) return null;

        String name = parts[0];
        double salary = Double.parseDouble(parts[1].replace(",", ""));
        int id = Integer.parseInt(parts[2]);
        String type = parts[3];

        if (type.equals("Developer")) {
            String programmingLanguage = parts[4];
            return new Developer(name, salary, id, programmingLanguage);
        } else if (type.equals("Manager")) {
            double bonus = Double.parseDouble(parts[4]);
            return new Manager(name, salary, id, bonus);
        }
        return null;
    }

    private String formatEmployee(Employee employee) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (employee instanceof Developer) {
            Developer dev = (Developer) employee;
            return String.join(", ",
                    dev.getName(),
                    df.format(dev.getSalary()),
                    String.valueOf(dev.getId()),
                    "Developer",
                    dev.getProgrammingLanguage());
        } else if (employee instanceof Manager) {
            Manager mgr = (Manager) employee;
            return String.join(", ",
                    mgr.getName(),
                    df.format(mgr.getSalary()),
                    String.valueOf(mgr.getId()),
                    "Manager",
                    df.format(mgr.getBonus()));
        }
        return "";
    }
    public void editEmploy() {
        System.out.println("Nhập tên nhân viên muốn sửa:");
        String name = scanner.nextLine();

        for (int i = 0; i < count; i++) {
            if (listEmploy[i].getName().equals(name)) {
                System.out.println("Nhân viên tìm thấy: " + listEmploy[i].getName());
                System.out.println("Nhập thông tin mới (nhấn Enter để giữ nguyên giá trị cũ):");

                System.out.print("Tên mới: ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    listEmploy[i].setName(newName);
                }

                System.out.print("Lương mới: ");
                String newSalary = scanner.nextLine().trim();
                if (!newSalary.isEmpty()) {
                    try {
                        double salary = Double.parseDouble(newSalary.replace(",", ""));
                        listEmploy[i].setSalary(salary);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Lương không hợp lệ, giữ nguyên lương cũ.");
                    }
                }

                System.out.print("Chức vụ mới: ");
                String newPosition = scanner.nextLine().trim();
                if (!newPosition.isEmpty()) {
                    listEmploy[i].setPosition(newPosition);
                }

                saveAllToFile();
                System.out.println("Thông tin nhân viên đã được cập nhật.");
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên với tên: " + name);
    }
}


