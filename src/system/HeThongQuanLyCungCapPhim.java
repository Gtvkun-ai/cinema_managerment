package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import base.ThucThe;
import cinema.RapChieuPhim;
import cinema.PhongChieu; // Thêm dòng này
import contract.HopDongCungCap;
import contract.YeuCauPhim;
import film.DinhDang2D;
import film.DinhDang3D;
import film.DinhDang4DX;
import film.Phim;
import models.distributor.NhaPhanPhoi;

public class HeThongQuanLyCungCapPhim implements IQuanLy<Object>, ITimKiem<Object>, IXuatBaoCao, IThongKe{
    private List<NhaPhanPhoi> danhSachNhaPhanPhoi;
    private List<RapChieuPhim> danhSachRap;
    private List<Phim> danhSachPhim;
    private List<HopDongCungCap> danhSachHopDong;
    private List<YeuCauPhim> danhSachYeuCau;
    private List<QuanLy> danhSachQuanLy;
    private Scanner sc;
    private QuanLy quanLyDangNhap;
    
    public HeThongQuanLyCungCapPhim() {
        danhSachNhaPhanPhoi = new ArrayList<>();
        danhSachRap = new ArrayList<>();
        danhSachPhim = new ArrayList<>();
        danhSachHopDong = new ArrayList<>();
        danhSachYeuCau = new ArrayList<>();
        danhSachQuanLy = new ArrayList<>();
        sc = new Scanner(System.in);
        loadDataFromFiles(); // Thay vì khoiTaoDuLieuMau()
    }
private void loadDataFromFiles() {
    System.out.println("\n⏳ Dang tai du lieu...");
    
    // Tải dữ liệu từ các file
    danhSachNhaPhanPhoi = DataManager.loadNhaPhanPhoi();
    danhSachPhim = DataManager.loadPhim();
    danhSachRap = DataManager.loadRap();
    
    // Nếu không có dữ liệu, khởi tạo dữ liệu mẫu
    if (danhSachNhaPhanPhoi.isEmpty() && danhSachPhim.isEmpty() && danhSachRap.isEmpty()) {
        System.out.println("ℹ️ Khong co du lieu, vui long khoi tao du lieu mau...");
        khoiTaoDuLieuMau();
        autoSave();
    } else {
        System.out.println("✅ Tai du lieu thanh cong!");
    }
}

// Thêm vào class HeThongQuanLyCungCapPhim
private void autoSave() {
    DataManager.saveNhaPhanPhoi(danhSachNhaPhanPhoi);
    DataManager.savePhim(danhSachPhim);
    DataManager.saveRap(danhSachRap);
    DataManager.saveQuanLy(danhSachQuanLy);
}

    
    private void khoiTaoDuLieuMau() {
        try {
            // Nhà phân phối
            NhaPhanPhoi npp1 = new NhaPhanPhoi("NPP001", "CGV Distribution", 
                "123 Nguyen Hue, Q1, HCM", "0901234567", "cgv@distribution.vn");
            NhaPhanPhoi npp2 = new NhaPhanPhoi("NPP002", "Galaxy Film", 
                "456 Le Loi, Q1, HCM", "0907654321", "galaxy@film.vn");
            NhaPhanPhoi npp3 = new NhaPhanPhoi("NPP003", "Lotte Cinema Supply", 
                "789 Tran Hung Dao, Q5, HCM", "0903456789", "lotte@supply.vn");
            danhSachNhaPhanPhoi.add(npp1);
            danhSachNhaPhanPhoi.add(npp2);
            danhSachNhaPhanPhoi.add(npp3);
            
            // Phim
            Phim phim1 = new Phim("P001", "Avengers: Endgame", 181, "Hành động", 
                "Marvel Studios", "Mỹ", LocalDate.of(2024, 4, 26), 50, 5000000);
            phim1.themDinhDang(new DinhDang2D());
            phim1.themDinhDang(new DinhDang3D());
            phim1.themDanhGia(5, "Phim hay, dang xem!");
            phim1.themDanhGia(4, "Ket thuc hoan hao cho series");
            
            Phim phim2 = new Phim("P002", "Inception", 148, "Khoa học viễn tưởng", 
                "Warner Bros", "Mỹ", LocalDate.of(2024, 7, 16), 30, 4000000);
            phim2.themDinhDang(new DinhDang2D());
            phim2.themDinhDang(new DinhDang4DX());
            phim2.themDanhGia(5, "Kiet tac dien anh");
            
            Phim phim3 = new Phim("P003", "Parasite", 132, "Tâm lý", 
                "Barunson E&A", "Hàn Quốc", LocalDate.of(2024, 5, 30), 25, 3500000);
            phim3.themDinhDang(new DinhDang2D());
            
            Phim phim4 = new Phim("P004", "The Batman", 176, "Hành động", 
                "DC Films", "Mỹ", LocalDate.of(2024, 3, 4), 40, 4500000);
            phim4.themDinhDang(new DinhDang2D());
            phim4.themDinhDang(new DinhDang3D());
            
            danhSachPhim.add(phim1);
            danhSachPhim.add(phim2);
            danhSachPhim.add(phim3);
            danhSachPhim.add(phim4);
            
            // Rạp chiếu
            RapChieuPhim rap1 = new RapChieuPhim("R001", "CGV Nguyen Du", 
                "456 Nguyen Du, Q1, HCM", "0281234567", "cgvnd@cgv.vn", 8);
            RapChieuPhim rap2 = new RapChieuPhim("R002", "Galaxy Nguyen Trai", 
                "789 Nguyen Trai, Q5, HCM", "0287654321", "glxnt@galaxy.vn", 6);
            RapChieuPhim rap3 = new RapChieuPhim("R003", "Lotte Cinema CCong Hoa", 
                "135 CCong Hoa, Tan Binh, HCM", "0289876543", "lotte@ch.vn", 10);
            
            // Thêm phòng chiếu cho rạp
            rap1.themPhongChieu(new PhongChieu("PC001", "Phong VIP 1", 150, "VIP"));
            rap1.themPhongChieu(new PhongChieu("PC002", "Phong 3D 1", 200, "3D"));
            rap2.themPhongChieu(new PhongChieu("PC003", "Phong
            
            danhSachRap.add(rap1);
            danhSachRap.add(rap2);
            danhSachRap.add(rap3);
            
            // Quản lý
            QuanLy ql1 = new QuanLy("QL001", "Cao Nguyen Yen Hoa", "0912345678", "nva@cgv.vn", "Giam doc");
            ql1.phanCongRap(rap1);

            QuanLy ql2 = new QuanLy("QL002", "Lam Han Dat", "0987654321", "ttb@galaxy.vn", "Truong phong");
            ql2.phanCongRap(rap2);
            
            QuanLy ql3 = new QuanLy("QL003", "Le Van C", "0976543210", "lvc@lotte.vn", "Pho giam doc");
            ql3.phanCongRap(rap3);
            
            danhSachQuanLy.add(ql1);
            danhSachQuanLy.add(ql2);
            danhSachQuanLy.add(ql3);
            
            // Tạo một số hợp đồng mẫu
            HopDongCungCap hd1 = new HopDongCungCap("HD001", npp1, rap1, phim1, 
                10, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 12, 31), 5000000);
            hd1.kichHoat();
            npp1.themHopDong(hd1);
            danhSachHopDong.add(hd1);
            
            HopDongCungCap hd2 = new HopDongCungCap("HD002", npp2, rap2, phim2, 
                5, LocalDate.of(2025, 10, 15), LocalDate.of(2025, 11, 30), 4000000);
            hd2.kichHoat();
            npp2.themHopDong(hd2);
            danhSachHopDong.add(hd2);
            
            System.out.println("✅ Khoi tao du lieu thanh cong!");
            
        } catch (Exception e) {
            System.out.println("❌ Loi khi khoi tao du lieu: " + e.getMessage());
        }
    }
    
    // Implement IQuanLy
    @Override
    public void them(Object item) {
        if (item instanceof NhaPhanPhoi) {
            danhSachNhaPhanPhoi.add((NhaPhanPhoi) item);
        } else if (item instanceof RapChieuPhim) {
            danhSachRap.add((RapChieuPhim) item);
        } else if (item instanceof Phim) {
            danhSachPhim.add((Phim) item);
        } else if (item instanceof HopDongCungCap) {
            danhSachHopDong.add((HopDongCungCap) item);
        } else if (item instanceof YeuCauPhim) {
            danhSachYeuCau.add((YeuCauPhim) item);
        } else if (item instanceof QuanLy) {
            danhSachQuanLy.add((QuanLy) item);
        }
        autoSave(); // Tự động lưu sau khi thêm
    }
    
    @Override
    public void capNhat(Object item) {
        if (item instanceof ThucThe) {
            String ma = ((ThucThe) item).getMa();
            xoa(ma);
            them(item);
        }
    }
    
    @Override
    public void xoa(String ma) {
        Object item = timTheoMa(ma);
        if (item instanceof NhaPhanPhoi) {
            danhSachNhaPhanPhoi.remove(item);
        } else if (item instanceof RapChieuPhim) {
            danhSachRap.remove(item);
        } else if (item instanceof Phim) {
            danhSachPhim.remove(item);
        } else if (item instanceof HopDongCungCap) {
            danhSachHopDong.remove(item);
        } else if (item instanceof YeuCauPhim) {
            danhSachYeuCau.remove(item);
        } else if (item instanceof QuanLy) {
            danhSachQuanLy.remove(item);
        }
        autoSave(); // Tự động lưu sau khi xóa
    }
    
    @Override
    public Object timTheoMa(String ma) {
        // Tìm trong danh sách nhà phân phối
        for (NhaPhanPhoi npp : danhSachNhaPhanPhoi) {
            if (npp.getMa().equalsIgnoreCase(ma)) {
                return npp;
            }
        }
        
        // Tìm trong danh sách rạp chiếu
        for (RapChieuPhim rap : danhSachRap) {
            if (rap.getMa().equalsIgnoreCase(ma)) {
                return rap;
            }
        }
        
        // Tìm trong danh sách phim
        for (Phim phim : danhSachPhim) {
            if (phim.getMa().equalsIgnoreCase(ma)) {
                return phim;
            }
        }
        
        // Tìm trong danh sách quản lý
        for (QuanLy ql : danhSachQuanLy) {
            if (ql.getMa().equalsIgnoreCase(ma)) {
                return ql;
            }
        }
        
        // Tìm trong danh sách hợp đồng
        for (HopDongCungCap hd : danhSachHopDong) {
            if (hd.getMaHopDong().equalsIgnoreCase(ma)) {
                return hd;
            }
        }
        
        // Tìm trong danh sách yêu cầu
        for (YeuCauPhim yc : danhSachYeuCau) {
            if (yc.getMaYeuCau().equalsIgnoreCase(ma)) {
                return yc;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Object> layDanhSach() {
        List<Object> all = new ArrayList<>();
        all.addAll(danhSachPhim);
        all.addAll(danhSachRap);
        all.addAll(danhSachNhaPhanPhoi);
        return all;
    }
    
    // Implement ITimKiem
    @Override
    public List<Object> timKiemTheoTen(String ten) {
        List<Object> ketQua = new ArrayList<>();
        String tenLowerCase = ten.toLowerCase();
        
        for (Phim p : danhSachPhim) {
            if (p.getTen().toLowerCase().contains(tenLowerCase)) {
                ketQua.add(p);
            }
        }
        for (RapChieuPhim r : danhSachRap) {
            if (r.getTen().toLowerCase().contains(tenLowerCase)) {
                ketQua.add(r);
            }
        }
        return ketQua;
    }
    
    @Override
    public List<Object> timKiemTheoMa(String ma) {
        List<Object> ketQua = new ArrayList<>();
        Object found = timTheoMa(ma);
        if (found != null) {
            ketQua.add(found);
        }
        return ketQua;
    }
    
    @Override
    public List<Object> timKiemNangCao(Map<String, Object> tieuChi) {
        // Implementation tùy chỉnh theo tiêu chí
        return new ArrayList<>();
    }
    
    // Implement IXuatBaoCao
    @Override
    public void xuatBaoCaoTheoNgay(LocalDate ngay) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HE THONG THEO NGAY                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Ngay: " + ngay);
        System.out.println("║ Tong so yeu cau: " + danhSachYeuCau.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HE THONG THEO THANG                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Thang/Nam: " + thang + "/" + nam);
        System.out.println("║ Tong hop dong: " + danhSachHopDong.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTongQuat() {
        thongKeBaoCao();
    }
    
    // Implement IThongKe
    @Override
    public Map<String, Integer> thongKeTheoLoai() {
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("So NPP", danhSachNhaPhanPhoi.size());
        thongKe.put("So rap", danhSachRap.size());
        thongKe.put("So phim", danhSachPhim.size());
        thongKe.put("So yeu cau", danhSachYeuCau.size());
        thongKe.put("So hop dong", danhSachHopDong.size());
        return thongKe;
    }
    
    @Override
    public Map<String, Double> thongKeDoanhThu() {
        Map<String, Double> doanhThu = new HashMap<>();
        double tongDoanhThu = 0;
        for (Phim phim : danhSachPhim) {
            tongDoanhThu += phim.getDoanhThu().getTongDoanhThu();
        }
        doanhThu.put("Tong doanh thu", tongDoanhThu);
        return doanhThu;
    }
    
    @Override
    public int demSoLuong() {
        return danhSachPhim.size() + danhSachRap.size() + danhSachNhaPhanPhoi.size();
    }
    
    public void hienThiMenu() {
        while(true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║          HE THONG QUAN LY CUNG CAP PHIM CHO RAP CHIEU              ║");
            if (quanLyDangNhap != null) {
                System.out.println("║          👤 Dang nhap: " + quanLyDangNhap.getMa() + " - " + quanLyDangNhap.getTen());
            }
            System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. 📋 Quan ly nha phan phoi                                        ║");
            System.out.println("║  2. 🎬 Quan ly rap chieu phim                                       ║");
            System.out.println("║  3. 🎥 Quan ly phim                                                 ║");
            System.out.println("║  4. 📝 Quan ly yeu cau phim                                        ║");
            System.out.println("║  5. 📜 Quan ly hop dong cung cap                                   ║");
            System.out.println("║  7. 📊 Thong ke bao cao                                            ║");
            System.out.println("║  8. 🔍 Tim kiem                                                   ║");
            if (quanLyDangNhap != null) {
                System.out.println("║  9. 🚪 Dang xuat                                                 ║");
                System.out.println("║  0. 🚪 Thoat                                                     ║");
            } else {
                System.out.println("║  9. 🚪 Thoat                                                     ║");
            }
            System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
            System.out.print("👉 Chọn chức năng (1-9): ");
            
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch(choice) {
                    case 1:
                        quanLyNhaPhanPhoi();
                        break;
                    case 2:
                        quanLyRapChieu();
                        break;
                    case 3:
                        quanLyPhim();
                        break; // Thêm dòng này
                    case 4:
                        quanLyYeuCauPhim();
                        break;
                    case 5:
                        quanLyHopDong();
                        break;
                    case 9: 
                        if (quanLyDangNhap != null) {
                            System.out.println("\n👋 Dang xuat thanh cong! Tam biet " + quanLyDangNhap.getTen() + "!");
                            quanLyDangNhap = null;
                        } else {
                            System.out.println("\n👋 Cam on ban da su dung he thong! Tam biet!");
                            return;
                        }
                        break;
                    case 0:
                        if (quanLyDangNhap != null) {
                            System.out.println("\n👋 Cam on ban da su dung he thong! Tam biet!");
                            return;
                        }
                        break;
                    case 6: 
                        quanLyQuanLyRap(); 
                        break;
                    case 7: 
                        thongKeBaoCao(); 
                        break;
                    case 8: 
                        timKiem(); 
                        break;
                    default:
                        System.out.println("❌ Lua chon khong hop le. Vui long chon tu 1-9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Vui long nhap so!");
                sc.nextLine();
            }
        }
    }
            
    private void timKiem() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         CHUC NANG TIM KIEM           ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Tim theo ten                     ║");
        System.out.println("║  2. Tim theo ma                      ║");
        System.out.println("║  3. Quay lai                         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("👉 Chon (1-3): ");
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        switch(choice) {
            case 1:
                System.out.print("Nhap ten can tim: ");
                String ten = sc.nextLine();
                List<Object> ketQua = timKiemTheoTen(ten);
                System.out.println("\n🔍 TTim thay " + ketQua.size() + " ket qua:");
                for (Object obj : ketQua) {
                    if (obj instanceof ThucThe) {
                        ((ThucThe) obj).hienThiThongTin();
                    }
                }
                break;
            case 2:
                System.out.print("Nhap ma can tim: ");
                String ma = sc.nextLine();
                Object found = timTheoMa(ma);
                if (found != null) {
                    System.out.println("\n✅ Tim thay:");
                    if (found instanceof ThucThe) {
                        ((ThucThe) found).hienThiThongTin();
                    }
                } else {
                    System.out.println("❌ Khong tim thay!");
                }
                break;
            case 3:
                return;
        }
    }
    
    private void quanLyNhaPhanPhoi() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                    DANH SACH NHA PHAN PHOI                                                              ║");
        System.out.println("╠════════════╪═══════════════════════════╪═════════════════╪═══════════════════════════╪════════════════════════════════╪═══════╣");
        System.out.println("║ Ma NPP     │ Ten nha phan phoi         │ So dien thoai   │ Email                     │ Dia chi                        │ So HD ║");
        System.out.println("╠════════════╪═══════════════════════════╪═════════════════╪═══════════════════════════╪════════════════════════════════╪═══════╣");
        
        for (NhaPhanPhoi npp : danhSachNhaPhanPhoi) {
            npp.hienThiThongTin();
        }
        System.out.println("╚════════════╧═══════════════════════════╧═════════════════╧═══════════════════════════╧════════════════════════════════╧═══════╝");
        System.out.println("Tong so nha phan phoi: " + danhSachNhaPhanPhoi.size());
    }
    
    private void quanLyRapChieu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                  DANH SACH RAP CHIEU PHIM                                                       ║");
        System.out.println("╠════════════╪═══════════════════════════╪══════════════════════════════════════════════╪═════════════════╪═════════════╪═══════╣");
        System.out.println("║ Ma rap     │ TTen rap                   │ Dia chi                                      │ So dien thoai   │ Phong       │ YC    ║");
        System.out.println("╠════════════╪═══════════════════════════╪══════════════════════════════════════════════╪═════════════════╪═════════════╪═══════╣");
        
        for (RapChieuPhim rap : danhSachRap) {
            rap.hienThiThongTin();
        }
        System.out.println("╚════════════╧═══════════════════════════╧══════════════════════════════════════════════╧═════════════════╧═════════════╧═══════╝");
        System.out.println("Tong so rap chieu: " + danhSachRap.size());
    }
    
    private void quanLyPhim() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           DANH SACH PHIM                                                                             ║");
        System.out.println("╠════════════╪════════════════════════════════════╪══════╪═════════════════╪══════════════════════╪════════════╪═══════╪═════════════════╣");
        System.out.println("║ Ma phim    │ TTen phim                           │ TL   │ The loai        │ Nha san xuat         │ Quoc gia   │ BBan   │ Gia (VND)       ║");
        System.out.println("╠════════════╪════════════════════════════════════╪══════╪═════════════════╪══════════════════════╪════════════╪═══════╪═════════════════╣");
        
        for (Phim phim : danhSachPhim) {
            phim.hienThiThongTin();
        }
        System.out.println("╚════════════╧════════════════════════════════════╧══════╧═════════════════╧══════════════════════╧════════════╧═══════╧═════════════════╝");
        System.out.println("Tong so phim: " + danhSachPhim.size());
        
        // Hiển thị thêm thông tin đánh giá
        System.out.println("\n📊 THONG TIN DANH GIA PHIM:");
        for (Phim phim : danhSachPhim) {
            System.out.printf("  %s (%s): %.1f⭐ (%d danh gia)%n",
                phim.getMa(), phim.getTen(), 
                phim.tinhDiemTrungBinh(),
                phim.layDanhSachDanhGia().size());
        }
    }
    
    private void quanLyYeuCauPhim() {
        System.out.println("\n╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║            QUAN LY YEU CAU THUE PHIM                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Tao yeu cau moi                                        ║");
        System.out.println("║  2. Xem danh sach yeu cau                                  ║");
        System.out.println("║  3. Duyet yeu cau                                          ║");
        System.out.println("║  4. Bat dau thue (kich hoat)                               ║");
        System.out.println("║  5. Hoan thanh yeu cau                                     ║");
        System.out.println("║  6. Huy yeu cau                                            ║");
        System.out.println("║  7. Thanh toan yeu cau                                     ║");
        System.out.println("║  8. Quay lai                                               ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Chon (1-8): ");
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        switch(choice) {
            case 1: taoYeuCauMoi(); break;
            case 2: xemDanhSachYeuCau(); break;
            case 3: duyetYeuCau(); break;
            case 4: batDauThue(); break;
            case 5: hoanThanhYeuCau(); break;
            case 6: huyYeuCau(); break;
            case 7: thanhToanYeuCau(); break;
            case 8: return;
            default: System.out.println("❌ Lua chon khong hop le.");
        }
    }
    
    private void taoYeuCauMoi() {
        try {
            System.out.println("\n=== TAO YEU CAU THUE PHIM MOI ===");
            System.out.print("Nhap ma quan ly: ");
            String maQL = sc.nextLine().toUpperCase();
            
            QuanLy ql = null;
            for (QuanLy q : danhSachQuanLy) {
                if (q.getMa().equalsIgnoreCase(maQL)) {
                    ql = q;
                    break;
                }
            }
            
            if (ql == null) {
                System.out.println("❌ Khong tim thay quan ly!");
                return;
            }

            System.out.print("Nhap ma phim: ");
            String maPhim = sc.nextLine().toUpperCase();
            
            Phim phim = null;
            for (Phim p : danhSachPhim) {
                if (p.getMa().equalsIgnoreCase(maPhim)) {
                    phim = p;
                    break;
                }
            }
            
            if (phim == null) {
                System.out.println("❌ Khong tim thay phim!");
                return;
            }

            System.out.println("So luong ban kha dung: " + phim.getSoLuongBanKhaDung());
            System.out.print("So luong ban can thue: ");
            int soLuong = sc.nextInt();
            sc.nextLine();

            System.out.print("Ngay bat dau (yyyy-MM-dd): ");
            LocalDate ngayBD = LocalDate.parse(sc.nextLine());
            
            System.out.print("Ngay ket thuc (yyyy-MM-dd): ");
            LocalDate ngayKT = LocalDate.parse(sc.nextLine());
            
            YeuCauPhim yc = ql.taoYeuCauThuePhim(phim, soLuong, ngayBD, ngayKT);
            danhSachYeuCau.add(yc);
            
            System.out.println("✅ TTao yeu cau thanh cong!");
            System.out.println("Ma yeu cau: " + yc.getMaYeuCau());
            System.out.println("Chi phi du kien: " + String.format("%,.0f VND", yc.tinhChiPhi()));
            System.out.println("Thue VAT (10%): " + String.format("%,.0f VND", yc.tinhThue(10)));
            System.out.println("Tong cong: " + String.format("%,.0f VND", yc.tinhChiPhi() + yc.tinhThue(10)));

            ql.guiThongBao("Yeu cau " + yc.getMaYeuCau() + " da duoc tao cho phim " + phim.getTen());

        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachYeuCau() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                        DANH SACH YEU CAU THUE PHIM                                                                   ║");
        System.out.println("╠══════════════╦═════════════════╦══════════════════════╦═══════╦════════════╦════════════╦═════════════════╦═════════════════╣");
        System.out.println("║ Ma yeu cau   ║ Ma rap          ║ Ten phim             ║ SL    ║ Ngay BD    ║ Ngay KT    ║ Trang thai      ║ Chi phi (VND)   ║");
        System.out.println("╠══════════════╬═════════════════╬══════════════════════╬═══════╬════════════╬════════════╬═════════════════╬═════════════════╣");
        
        if (danhSachYeuCau.isEmpty()) {
            System.out.println("║                                                          Chua co yeu cau nao                                                                      ║");
        } else {
            for (YeuCauPhim yc : danhSachYeuCau) {
                yc.hienThiThongTin();
            }
        }
        System.out.println("╚══════════════╧═════════════════╧══════════════════════╧═══════╧════════════╧════════════╧═════════════════╧═════════════════╝");
        System.out.println("Tong so yeu cau: " + danhSachYeuCau.size());
        }
        
    private void duyetYeuCau() {
        try {
            System.out.print("Nhap ma yeu cau can duyet: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Khong tim thay yeu cau!");
                return;
            }
            
            yc.duyetYeuCau();
            System.out.println("✅ Da duyet yeu cau " + maYC + " thanh cong!");
            
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void batDauThue() {
        try {
            System.out.print("Nhap ma yeu cau can kich hoat: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Khong tim thay yeu cau!");
                return;
            }
            
            yc.batDauThue();
            yc.getPhim().getDoanhThu().capNhatDoanhThu(yc.tinhChiPhi());
            System.out.println("✅ Da bat dau thue phim cho yeu cau " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void hoanThanhYeuCau() {
            try {
                System.out.print("Nhap ma yeu cau can hoan thanh: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Khong tim thay yeu cau!");
                return;
            }
            
            yc.hoanThanh();
            System.out.println("✅ Da hoan thanh yeu cau " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void huyYeuCau() {
        try {
            System.out.print("Nhap ma yeu cau can huy: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Khong tim thay yeu cau!");
                return;
            }
            
            yc.huyYeuCau();
            System.out.println("✅ Da huy yeu cau " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void thanhToanYeuCau() {
        try {
            System.out.print("Nhap ma yeu cau can thanh toan: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Khong tim thay yeu cau!");
                return;
            }
            
            System.out.println("\nThong tin thanh toan:");
            System.out.println("TTong tien: " + String.format("%,.0f VND", yc.tinhChiPhi()));
            System.out.println("Da thanh toan: " + String.format("%,.0f VND", yc.tinhTongThanhToan()));
            System.out.println("Con lai: " + String.format("%,.0f VND", yc.tinhChiPhi() - yc.tinhTongThanhToan()));
            
            System.out.print("\nNhap so tien can thanh toan: ");
            double soTien = sc.nextDouble();
            sc.nextLine();

            System.out.println("Chon phuong thuc thanh toan:");
            System.out.println("1. Tien mat");
            System.out.println("2. Chuyen khoan");
            System.out.println("3. The tin dung");
            System.out.println("4. Vi dien tu");
            System.out.print("Chon (1-4): ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            String phuongThuc = "";
            switch(choice) {
                case 1: phuongThuc = "TIEN_MAT"; break;
                case 2: phuongThuc = "CHUYEN_KHOAN"; break;
                case 3: phuongThuc = "THE_TIN_DUNG"; break;
                case 4: phuongThuc = "VI_DIEN_TU"; break;
                default: 
                    System.out.println("❌ Phuong thuc khong hop le!");
                    return;
            }
            
            yc.thanhToan(soTien, phuongThuc);
            
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
        }
    }
    
    private void quanLyHopDong() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                  
        System.out.println("║ Ma hop dong  │ MA NPP          │ Ma rap          │ Ten phim             │ SL    │ Trang thai      │ Gia tri (VND)   ║");
        System.out.println("╠══════════════╪═════════════════╪═════════════════╪══════════════════════╪═══════╪═════════════════╪═════════════════╣");
        
        if (danhSachHopDong.isEmpty()) {
            System.out.println("║                                                     Chua co hop dong nao                                                            ║");
        } else {
            for (HopDongCungCap hd : danhSachHopDong) {
                hd.hienThiThongTin();
            }
        }
        System.out.println("╚══════════════╧═════════════════╧═════════════════╧══════════════════════╧═══════╧═════════════════╧═════════════════╝");
        System.out.println("TTong so hop dong: " + danhSachHopDong.size());
        
        // Menu phụ
        System.out.println("\n1. Xem chi tiet hop dong");
        System.out.println("2. Xuat bao cao hop dong");
        System.out.println("3. Quay lai");
        System.out.print("👉 Chon (1-3): ");

        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhap ma hop dong: ");
                    String maHD = sc.nextLine().toUpperCase();
                    for (HopDongCungCap hd : danhSachHopDong) {
                        if (hd.getMaHopDong().equals(maHD)) {
                            hd.xuatBaoCaoTongQuat();
                            System.out.println("\n💰 THONG TIN TAI CHINH:");
                            System.out.println("  TTong gia tri: " + String.format("%,.0f VND", hd.tinhTongGiaTri()));
                            System.out.println("  Chiet khau 5%: " + String.format("%,.0f VND", hd.tinhChietKhau(5)));
                            System.out.println("  Thue VAT 10%: " + String.format("%,.0f VND", hd.tinhThue(10)));
                            System.out.println("  Thanh tien: " + String.format("%,.0f VND", 
                                hd.tinhTongGiaTri() - hd.tinhChietKhau(5) + hd.tinhThue(10)));
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Nhap ma hop dong: ");
                    String maHD2 = sc.nextLine().toUpperCase();
                    for (HopDongCungCap hd : danhSachHopDong) {
                        if (hd.getMaHopDong().equals(maHD2)) {
                            System.out.println("\n1. Bao cao theo ngay");
                            System.out.println("2. Bao cao theo thang");
                            System.out.println("3. Bao cao tong quat");
                            System.out.print("Chon: ");
                            int bcChoice = sc.nextInt();
                            sc.nextLine();
                            
                            switch(bcChoice) {
                                case 1:
                                    System.out.print("Nhap ngay (yyyy-MM-dd): ");
                                    LocalDate ngay = LocalDate.parse(sc.nextLine());
                                    hd.xuatBaoCaoTheoNgay(ngay);
                                    break;
                                case 2:
                                    System.out.print("Nhap thang: ");
                                    int thang = sc.nextInt();
                                    System.out.print("Nhap nam: ");
                                    int nam = sc.nextInt();
                                    sc.nextLine();
                                    hd.xuatBaoCaoTheoThang(thang, nam);
                                    break;
                                case 3:
                                    hd.xuatBaoCaoTongQuat();
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 3:
                    return;
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
            sc.nextLine();
        }
    }
    
    private void quanLyQuanLyRap() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                DANH SACH QUAN LY RAP                                                          ║");
        System.out.println("╠════════════╪══════════════════════╪═════════════════╪═════════════════╪═══════════════════════════╪══════════════════════╣");
        System.out.println("║ Ma QL      │ Ten                  │ Chuc vu         │ So dien thoai   │ Email                     │ Rap quan ly          ║");
        System.out.println("╠════════════╪══════════════════════╪═════════════════╪═════════════════╪═══════════════════════════╪══════════════════════╣");
        
        for (QuanLy ql : danhSachQuanLy) {
            ql.hienThiThongTin();
        }
        System.out.println("╚════════════╧══════════════════════╧═════════════════╧═════════════════╧═══════════════════════════╧══════════════════════╝");
        System.out.println("Tong so quan ly: " + danhSachQuanLy.size());

        // Menu phu
        System.out.println("\n1. Xem lich su hoat dong");
        System.out.println("2. Dang nhap quan ly");
        System.out.println("3. Doi mat khau");
        System.out.println("4. GGui thong bao");
        System.out.println("5. Quay lai");
        System.out.print("👉 Chon (1-5): ");

        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhap ma quan ly: ");
                    String maQL = sc.nextLine().toUpperCase();
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQL)) {
                            System.out.println("\n📜 LICH SU HOAT DONG:");
                            List<String> lichSu = ql.xemLichSu();
                            if (lichSu.isEmpty()) {
                                System.out.println("  Chua co hoat dong nao");
                            } else {
                                for (String ls : lichSu) {
                                    System.out.println("  " + ls);
                                }
                            }
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Nhap tai khoan: ");
                    String tk = sc.nextLine();
                    System.out.print("Nhap mat khau: ");
                    String mk = sc.nextLine();
                    
                    boolean found = false;
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getTaiKhoan().equals(tk)) {
                            found = true;
                            if (ql.xacThucTaiKhoan(tk, mk)) {
                                quanLyDangNhap = ql;
                                System.out.println("✅ Ban co cac quyen sau:");
                                System.out.println("  - Xem thong tin");
                                System.out.println("  - Tao yeu cau");
                                if (ql.xacThucQuyen("DUYET_YEU_CAU")) {
                                    System.out.println("  - Duyet yeu cau");
                                    System.out.println("  - Ky hop dong");
                                    System.out.println("  - Xem bao cao");
                                }
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("❌ Tai khoan khong ton tai!");
                    }
                    break;
                case 3:
                    System.out.print("Nhap ma quan ly: ");
                    String maQL2 = sc.nextLine().toUpperCase();
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQL2)) {
                            System.out.print("Nhap mat khau cu: ");
                            String mkCu = sc.nextLine();
                            System.out.print("Nhap mat khau moi: ");
                            String mkMoi = sc.nextLine();
                            try {
                                ql.doiMatKhau(mkCu, mkMoi);
                            } catch (Exception e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.print("Nhap ma quan ly gui: ");
                    String maQLGui = sc.nextLine().toUpperCase();
                    QuanLy qlGui = null;
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQLGui)) {
                            qlGui = ql;
                            break;
                        }
                    }
                    
                    if (qlGui == null) {
                        System.out.println("❌ Khong tim thay quan ly!");
                        break;
                    }

                    System.out.println("\n1. Gui thong bao");
                    System.out.println("2. Gui email");
                    System.out.println("3. Gui SMS");
                    System.out.print("Chon: ");
                    int guiChoice = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nhap noi dung: ");
                    String noiDung = sc.nextLine();
                    
                    switch(guiChoice) {
                        case 1:
                            qlGui.guiThongBao(noiDung);
                            break;
                        case 2:
                            System.out.print("Nhap email nguoi nhan: ");
                            String emailNhan = sc.nextLine();
                            qlGui.guiEmail(emailNhan, noiDung);
                            break;
                        case 3:
                            System.out.print("Nhap so dien thoai nguoi nhan: ");
                            String sdtNhan = sc.nextLine();
                            qlGui.guiSMS(sdtNhan, noiDung);
                            break;
                    }
                    break;
                case 5:
                    return;
            }
        } catch (Exception e) {
            System.out.println("❌ Loi: " + e.getMessage());
            sc.nextLine();
        }
    }
    
    private void thongKeBaoCao() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              THONG KE BAO CAO HE THONG                    ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Thống kê cơ bản
        Map<String, Integer> thongKeLoai = thongKeTheoLoai();
        for (Map.Entry<String, Integer> entry : thongKeLoai.entrySet()) {
            System.out.printf("║  %-40s: %15d  ║%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Thống kê yêu cầu theo trạng thái
        int choDuyet = 0, daDuyet = 0, dangThue = 0, hoanThanh = 0, huy = 0;
        for (YeuCauPhim yc : danhSachYeuCau) {
            switch(yc.getTrangThai()) {
                case CHO_DUYET: choDuyet++; break;
                case DA_DUYET: daDuyet++; break;
                case DANG_THUE: dangThue++; break;
                case HOAN_THANH: hoanThanh++; break;
                case HUY: huy++; break;
            }
        }
        
        System.out.println("║  Thong ke yeu cau theo trang thai:                       ║");
        System.out.printf("║    - Cho duyet:                           %15d  ║%n", choDuyet);
        System.out.printf("║    - Da duyet:                            %15d  ║%n", daDuyet);
        System.out.printf("║    - Dang thue:                           %15d  ║%n", dangThue);
        System.out.printf("║    - Hoan thanh:                          %15d  ║%n", hoanThanh);
        System.out.printf("║    - Da huy:                              %15d  ║%n", huy);
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Thống kê hợp đồng theo trạng thái
        int hdMoiTao = 0, hdHieuLuc = 0, hdHetHan = 0, hdHuy = 0;
        for (HopDongCungCap hd : danhSachHopDong) {
            switch(hd.getTrangThai()) {
                case MOI_TAO: hdMoiTao++; break;
                case DANG_HIEU_LUC: hdHieuLuc++; break;
                case HET_HAN: hdHetHan++; break;
                case BI_HUY: hdHuy++; break;
            }
        }
        
        System.out.println("║  Thong ke hop dong theo trang thai:                      ║");
        System.out.printf("║    - Moi tao:                             %15d  ║%n", hdMoiTao);
        System.out.printf("║    - Dang hieu luc:                       %15d  ║%n", hdHieuLuc);
        System.out.printf("║    - Het han:                             %15d  ║%n", hdHetHan);
        System.out.printf("║    - Bi huy:                              %15d  ║%n", hdHuy);
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Tổng doanh thu
        Map<String, Double> doanhThu = thongKeDoanhThu();
        for (Map.Entry<String, Double> entry : doanhThu.entrySet()) {
            System.out.printf("║  %-35s: %,20.0f VND  ║%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Thống kê phim theo thể loại
        Map<String, Integer> thongKeTheLoai = new HashMap<>();
        for (Phim phim : danhSachPhim) {
            String theLoai = phim.getTheLoai();
            thongKeTheLoai.put(theLoai, thongKeTheLoai.getOrDefault(theLoai, 0) + 1);
        }
        
        System.out.println("║  Thong ke phim theo the loai:                            ║");
        for (Map.Entry<String, Integer> entry : thongKeTheLoai.entrySet()) {
            //entry là một cặp khóa-giá trị trong bản đồ thongKeTheLoai
            // cấu trúc: entry.getKey() trả về thể loại phim (String)
            //          entry.getValue() trả về số lượng phim trong thể loại đó (Integer)
            System.out.printf("║    - %-35s: %15d  ║%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Top 3 phim có đánh giá cao nhất
        System.out.println("║  Top 3 phim danh gia cao nhat:                           ║");
        List<Phim> danhSachPhimCopy = new ArrayList<>(danhSachPhim);
        danhSachPhimCopy.sort((p1, p2) -> Double.compare(p2.tinhDiemTrungBinh(), p1.tinhDiemTrungBinh()));
        
        for (int i = 0; i < Math.min(3, danhSachPhimCopy.size()); i++) {
            Phim p = danhSachPhimCopy.get(i);
            System.out.printf("║    %d. %-30s: %.1f⭐ (%d danh gia)%n", 
                i + 1, p.getTen(), p.tinhDiemTrungBinh(), p.layDanhSachDanhGia().size());
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        // Menu phụ
        System.out.println("\n1. Xuat bao cao theo ngay");
        System.out.println("2. Xuat bao cao theo thang");
        System.out.println("3. Xuat bao cao chi tiet nha phan phoi");
        System.out.println("4. Xuat bao cao chi tiet rap chieu");
        System.out.println("5. Quay lai");
        System.out.print("👉 Chon (1-5): ");
        
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhap ngay (yyyy-MM-dd): ");
                    LocalDate ngay = LocalDate.parse(sc.nextLine());
                    xuatBaoCaoTheoNgay(ngay);
                    break;
                case 2:
                    System.out.print("Nhap thang: ");
                    int thang = sc.nextInt();
                    System.out.print("Nhap nam: ");
                    int nam = sc.nextInt();
                    sc.nextLine();
                    xuatBaoCaoTheoThang(thang, nam);
                    break;
                case 3:
                    System.out.print("Nhap ma nha phan phoi: ");
                    String maNPP = sc.nextLine().toUpperCase();
                    for (NhaPhanPhoi npp : danhSachNhaPhanPhoi) {
                        if (npp.getMa().equals(maNPP)) {
                            System.out.println("\n📊 THONG KE CHI TIET NHA PHAN PHOI");
                            npp.hienThiThongTin();
                            
                            Map<String, Integer> tkNPP = npp.thongKeTheoLoai();
                            Map<String, Double> dtNPP = npp.thongKeDoanhThu();

                            System.out.println("\nThong ke:");
                            for (Map.Entry<String, Integer> e : tkNPP.entrySet()) {
                                System.out.println("  " + e.getKey() + ": " + e.getValue());
                            }
                            for (Map.Entry<String, Double> e : dtNPP.entrySet()) {
                                System.out.println("  " + e.getKey() + ": " + String.format("%,.0f VND", e.getValue()));
                            }

                            System.out.println("\n📜 Lich su giao dich (5 gan nhat):");
                            List<String> lichSu = npp.xemLichSu();
                            int count = 0;
                            for (int i = lichSu.size() - 1; i >= 0 && count < 5; i--, count++) {
                                System.out.println("  " + lichSu.get(i));
                            }
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.print("Nhap ma rap: ");
                    String maRap = sc.nextLine().toUpperCase();
                    for (RapChieuPhim rap : danhSachRap) {
                        if (rap.getMa().equals(maRap)) {
                            rap.xuatBaoCaoTongQuat();

                            System.out.println("\n📜 Lich su hoat dong (5 gan nhat):");
                            List<String> lichSu = rap.xemLichSu();
                            int count = 0;
                            for (int i = lichSu.size() - 1; i >= 0 && count < 5; i--, count++) {
                                System.out.println("  " + lichSu.get(i));
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    return;
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
            sc.nextLine();
        }
    }
}

