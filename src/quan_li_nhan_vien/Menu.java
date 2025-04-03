package quan_li_nhan_vien;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ManageEmployee managerEmploy = new ManageEmployee(100);
        int choice;
        do {
            System.out.println("--------Quản lí nhân viên----------");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Xoá nhân viên");
            System.out.println("4. Tìm kiếm nhân viên bằng ID");
            System.out.println("5. Tìm kiếm nhân viên bằng tên");
            System.out.println("6. Sửa nhân viên");
            System.out.println("7. Sắp xếp nhân viên theo tên");
            System.out.println("0. Thoát");
            System.out.println(" nhập vào lựa chọn của bạn");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    managerEmploy.displayEmploy();
                    break;
                case 2:
                    managerEmploy.addEmploy();
                    break;
                case 3:
                    managerEmploy.deleteEmploy();
                    break;
                case 4:
                    managerEmploy.searchEmployByID();
                    break;
                case 5:
                    managerEmploy.searchEmployByName();
                    break;
                case 6:
                    managerEmploy.editEmploy();
                    break;
                case 7:
                    managerEmploy.sortEmploy();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("nhập sai vui lòng nhập lại");
                    break;
            }
        } while (choice != 0);
    }


}
