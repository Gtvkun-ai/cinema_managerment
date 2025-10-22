package services;

import java.util.ArrayList;
import java.util.List;

// ==================== DATA MANAGER - QUẢN LÝ FILE ====================
class DataManager {
    private static final String NPP_FILE = "nhaphanphoi.txt";
    private static final String RAP_FILE = "rapchieu.txt";
    private static final String PHIM_FILE = "phim.txt";
    private static final String QUANLY_FILE = "quanly.txt";
    
    // Lưu Nhà phân phối
    public static void saveNhaPhanPhoi(List<NhaPhanPhoi> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(NPP_FILE))) {
            //java.io.PrintWriter là lớp dùng để ghi dữ liệu vào file với các phương thức tiện lợi như println()
            //java.io.FileWriter là lớp dùng để mở file để ghi dữ liệu vào

            for (NhaPhanPhoi npp : list) {
                writer.println(npp.getMa() + "|" + npp.getTen() + "|" + 
                             npp.getDiaChi() + "|" + npp.getSoDienThoai() + "|" + npp.getEmail());
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Loi luu NPP: " + e.getMessage());
            //IOException là ngoại lệ liên quan đến việc đọc/ghi file hoặc các thao tác I/O khác
        }
    }
    
    // Đọc Nhà phân phối
    public static List<NhaPhanPhoi> loadNhaPhanPhoi() {
        List<NhaPhanPhoi> list = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(NPP_FILE))) {
            String line;
            //BufferedReader là lớp dùng để đọc dữ liệu từ file một cách hiệu quả
            //FileReader là lớp dùng để mở file để đọc dữ liệu từ đó
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    list.add(new NhaPhanPhoi(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
            System.out.println("✅ Da tai " + list.size() + " nha phan phoi");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File NPP chua ton tai");
        } catch (Exception e) {
            System.out.println("❌ Loi doc NPP: " + e.getMessage());
        }
        return list;
    }
    
    // Lưu Phim
    public static void savePhim(List<Phim> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(PHIM_FILE))) {
            for (Phim p : list) {
                writer.println(p.getMa() + "|" + p.getTen() + "|" + p.getThoiLuong() + "|" + 
                             p.getTheLoai() + "|" + p.getNgayPhatHanh() + "|" + 
                             p.getSoLuongBanKhaDung() + "|" + p.getGiaMotBan());
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Loi luu phim: " + e.getMessage());
        }
    }
    
    // Đọc Phim
    public static List<Phim> loadPhim() {
        List<Phim> list = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(PHIM_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    list.add(new Phim(parts[0], parts[1], Integer.parseInt(parts[2]), 
                                    parts[3], "Unknown", "Unknown", LocalDate.parse(parts[4]),
                                    Integer.parseInt(parts[5]), Double.parseDouble(parts[6])));
                }
            }
            System.out.println("✅ Da tai " + list.size() + " phim");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File Phim chua ton tai");
        } catch (Exception e) {
            System.out.println("❌ Loi doc Phim: " + e.getMessage());
        }
        return list;
    }
    
    // Lưu Rạp
    public static void saveRap(List<RapChieuPhim> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(RAP_FILE))) {
            for (RapChieuPhim r : list) {
                writer.println(r.getMa() + "|" + r.getTen() + "|" + r.getDiaChi() + "|" + 
                             r.getSoDienThoai() + "|" + r.getEmail());
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Loi luu Rap: " + e.getMessage());
        }
    }
    
    // Đọc Rạp
    public static List<RapChieuPhim> loadRap() {
        List<RapChieuPhim> list = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(RAP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    list.add(new RapChieuPhim(parts[0], parts[1], parts[2], parts[3], parts[4], 8));
                }
            }
            System.out.println("✅ Da tai " + list.size() + " rap");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File Rap chua ton tai");
        } catch (Exception e) {
            System.out.println("❌ Loi doc Rap: " + e.getMessage());
        }
        return list;
    }
    
    // Lưu Quản lý
    public static void saveQuanLy(List<QuanLy> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(QUANLY_FILE))) {
            for (QuanLy ql : list) {
                writer.println(ql.getMa() + "|" + ql.getTen() + "|" + ql.getEmail());
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Loi luu Quan ly: " + e.getMessage());
        }
    }
}