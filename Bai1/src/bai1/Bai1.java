package bai1;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

class Sach {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private double donGia;

    public Sach() {}

    public Sach(int maSach, String tenSach, String tacGia, double donGia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.donGia = donGia;
    }

    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public void hienThiThongTin() {
        System.out.printf("Mã: %-5d | Tên: %-20s | TG: %-15s | Giá: %,.0f\n", 
                          maSach, tenSach, tacGia, donGia);
    }
}

public class Bai1 {
    
    static Scanner sc;
    static ArrayList<Sach> danhSachSach = new ArrayList<>();

    public static void main(String[] args) {
        // Cấu hình UTF-8
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
            sc = new Scanner(System.in, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            sc = new Scanner(System.in);
        }

        int chon;
        do {
            buildMenu();
            System.out.print("Mời bạn chọn chức năng (1-8): ");
            try {
                String input = sc.nextLine();
                if(input.isEmpty()) input = sc.nextLine(); 
                chon = Integer.parseInt(input);
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1: themSach(); break;
                case 2: xoaSach(); break;
                case 3: suaSach(); break;
                case 4: xuatTatCa(); break;
                case 5: timSachTheoTuaDe(); break; 
                case 6: layKSachTheoGia(); break;
                case 7: timTheoDanhSachTacGia(); break;
                case 8: System.out.println("Thoát chương trình."); break;
                default: System.out.println("Chọn sai, vui lòng chọn lại!");
            }
            System.out.println("--------------------------------------");
        } while (chon != 8);
    }

    static void buildMenu() {
        System.out.println("\n=== QUẢN LÝ SÁCH ===");
        System.out.println("1. Thêm 1 cuốn sách");
        System.out.println("2. Xóa 1 cuốn sách");
        System.out.println("3. Thay đổi thông tin sách");
        System.out.println("4. Xuất thông tin tất cả sách");
        System.out.println("5. Tìm sách theo tựa đề"); 
        System.out.println("6. Lấy tối đa K cuốn sách có giá <= P");
        System.out.println("7. Tìm sách theo danh sách tác giả nhập vào");
        System.out.println("8. Thoát");
    }

    static void themSach() {
        System.out.println("--- Thêm sách mới ---");
        try {
            System.out.print("Nhập mã sách (số nguyên): ");
            int ma = Integer.parseInt(sc.nextLine());
            
            for(Sach s : danhSachSach) {
                if(s.getMaSach() == ma) {
                    System.out.println("Mã sách đã tồn tại!");
                    return;
                }
            }

            System.out.print("Nhập tên sách: ");
            String ten = sc.nextLine();
            System.out.print("Nhập tác giả: ");
            String tg = sc.nextLine();
            System.out.print("Nhập đơn giá: ");
            double gia = Double.parseDouble(sc.nextLine());

            Sach s = new Sach(ma, ten, tg, gia);
            danhSachSach.add(s);
            System.out.println("Thêm thành công!");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Mã và giá phải là số!");
        }
    }

    static void xoaSach() {
        System.out.print("Nhập mã sách cần xóa: ");
        try {
            int ma = Integer.parseInt(sc.nextLine());
            boolean timThay = false;
            for (int i = 0; i < danhSachSach.size(); i++) {
                if (danhSachSach.get(i).getMaSach() == ma) {
                    danhSachSach.remove(i);
                    System.out.println("Đã xóa sách có mã " + ma);
                    timThay = true;
                    break;
                }
            }
            if (!timThay) System.out.println("Không tìm thấy mã sách này.");
        } catch (Exception e) {
            System.out.println("Mã sách không hợp lệ.");
        }
    }

    static void suaSach() {
        System.out.print("Nhập mã sách cần sửa: ");
        try {
            int ma = Integer.parseInt(sc.nextLine());
            Sach s = null;
            for (Sach item : danhSachSach) {
                if (item.getMaSach() == ma) {
                    s = item;
                    break;
                }
            }

            if (s != null) {
                System.out.println("Thông tin cũ: ");
                s.hienThiThongTin();
                System.out.println("Nhập thông tin mới (Enter để giữ nguyên):");
                
                System.out.print("Tên sách mới: ");
                String tenMoi = sc.nextLine();
                if(!tenMoi.isEmpty()) s.setTenSach(tenMoi);

                System.out.print("Tác giả mới: ");
                String tgMoi = sc.nextLine();
                if(!tgMoi.isEmpty()) s.setTacGia(tgMoi);

                System.out.print("Giá mới (nhập -1 để giữ nguyên): ");
                String giaStr = sc.nextLine();
                if(!giaStr.isEmpty()) {
                    double giaMoi = Double.parseDouble(giaStr);
                    if(giaMoi != -1) s.setDonGia(giaMoi);
                }
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy sách.");
            }
        } catch (Exception e) {
             System.out.println("Lỗi nhập liệu.");
        }
    }

    static void xuatTatCa() {
        if(danhSachSach.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("--- Danh sách sách ---");
        for (Sach s : danhSachSach) {
            s.hienThiThongTin();
        }
    }
    static void timSachTheoTuaDe() {
        System.out.print("Nhập tựa đề sách cần tìm (ví dụ: Lập trình): ");
        String tuKhoa = sc.nextLine(); 
        
        System.out.println("--- Kết quả tìm kiếm từ khóa '" + tuKhoa + "' ---");
        boolean co = false;
        
        for (Sach s : danhSachSach) {
            if (s.getTenSach().toLowerCase().contains(tuKhoa.toLowerCase())) {
                s.hienThiThongTin();
                co = true;
            }
        }
        
        if (!co) {
            System.out.println("Không tìm thấy cuốn nào chứa từ khóa này.");
        }
    }

    static void layKSachTheoGia() {
        try {
            System.out.print("Nhập số lượng K cần lấy: ");
            int k = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập mức giá P trần: ");
            double p = Double.parseDouble(sc.nextLine());

            System.out.println("--- Các sách thỏa mãn ---");
            int dem = 0;
            for (Sach s : danhSachSach) {
                if (s.getDonGia() <= p) {
                    s.hienThiThongTin();
                    dem++;
                    if (dem == k) break;
                }
            }
            if (dem == 0) System.out.println("Không có sách nào giá <= " + p);
        } catch (Exception e) {
            System.out.println("Nhập số không hợp lệ.");
        }
    }

    static void timTheoDanhSachTacGia() {
        System.out.println("Nhập danh sách tác giả (cách nhau bởi dấu phẩy):");
        String input = sc.nextLine();
        String[] cacTacGia = input.split(",");
        
        System.out.println("--- Kết quả ---");
        boolean timThay = false;
        
        for (Sach s : danhSachSach) {
            for (String tacGiaCanTim : cacTacGia) {
                if (s.getTacGia().trim().equalsIgnoreCase(tacGiaCanTim.trim())) {
                    s.hienThiThongTin();
                    timThay = true;
                    break; 
                }
            }
        }
        if (!timThay) System.out.println("Không tìm thấy sách của các tác giả trên.");
    }
}