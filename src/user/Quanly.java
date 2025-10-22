package user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import base.ThucThe;
import cinema.RapChieuPhim;
import contract.YeuCauPhim;
import film.Phim;

public class Quanly extends ThucThe implements IThongBao, IXacThuc, ILichSu{
    private String soDienThoai;
    private String email;
    private String chucVu;
    private RapChieuPhim rapQuanLy;
    private String taiKhoan;
    private String matKhau;
    private List<String> lichSuHoatDong;
    private List<String> quyenTruyCap;
    
    public QuanLy(String ma, String ten, String soDienThoai, String email, String chucVu) {
        super(ma, ten);
        this.soDienThoai = validSoDienThoai(soDienThoai);
        this.email = validEmail(email);
        this.chucVu = chucVu;
        this.taiKhoan = ma.toLowerCase();
        this.matKhau = "123456"; // Default password
        this.lichSuHoatDong = new ArrayList<>();
        this.quyenTruyCap = new ArrayList<>();
        ganQuyenMacDinh();
    }
    
    private void ganQuyenMacDinh() {
        quyenTruyCap.add("XEM_THONG_TIN");
        quyenTruyCap.add("TAO_YEU_CAU");
        if (chucVu.equals("Giam doc") || chucVu.equals("Pho giam doc")) {
            quyenTruyCap.add("DUYET_YEU_CAU");
            quyenTruyCap.add("KY_HOP_DONG");
            quyenTruyCap.add("XEM_BAO_CAO");
        }
    }
    
    private String validSoDienThoai(String sdt) {
        if (sdt == null || !sdt.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("So dien thoai khong hop le");
        }
        return sdt;
    }
    
    private String validEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email khong hop le");
        }
        return email;
    }
    public void phanCongRap(RapChieuPhim rap) {
        if (rap == null) {
            throw new IllegalArgumentException("Rap khong duoc null");
        }
        this.rapQuanLy = rap;
        rap.datQuanLy(this);
        themLichSu("Duoc phan cong quan ly rap " + rap.getTen());
    }
    
    public YeuCauPhim taoYeuCauThuePhim(Phim phim, int soLuong, LocalDate ngayBD, LocalDate ngayKT) {
        if (rapQuanLy == null) {
            throw new IllegalStateException("Chua duoc phan cong rap");
        }
        YeuCauPhim yc = rapQuanLy.taoYeuCauPhim(phim, soLuong, ngayBD, ngayKT);
        themLichSu("Tao yeu cau thue phim " + phim.getTen() + " - Ma YC: " + yc.getMaYeuCau());
        return yc;
    }
    
    // Implement IThongBao
    @Override
    public void guiThongBao(String message) {
        System.out.println("🔔 Thong bao den Quan ly " + ten + ": " + message);
        themLichSu("Nhan thong bao: " + message);
    }
    
    @Override
    public void guiEmail(String emailNhan, String message) {
        System.out.println("📧 Email tu " + email + " den " + emailNhan);
        System.out.println("   Nguoi gui: " + ten + " (" + chucVu + ")");
        System.out.println("   Noi dung: " + message);
        themLichSu("GGui email den " + emailNhan);
    }
    
    @Override
    public void guiSMS(String soDienThoaiNhan, String message) {
        System.out.println("📱 SMS tu " + soDienThoai + " den " + soDienThoaiNhan);
        System.out.println("   Nguoi gui: " + ten);
        System.out.println("   NNoi dung: " + message);
        themLichSu("Gui SMS den " + soDienThoaiNhan);
    }
    
    // Implement IXacThuc
    @Override
    public boolean xacThucThongTin() {
        return ma != null && !ma.isEmpty() && 
               ten != null && !ten.isEmpty() &&
               soDienThoai != null && email != null;
    }
    
    @Override
    public boolean xacThucTaiKhoan(String username, String password) {
        boolean ketQua = this.taiKhoan.equals(username) && this.matKhau.equals(password);
        if (ketQua) {
            themLichSu("Dang nhap thanh cong");
            System.out.println("✅ Dang nhap thanh cong! Chao mung " + ten);
        } else {
            System.out.println("❌ Sai tai khoan hoac mat khau!");
        }
        return ketQua;
    }
    
    @Override
    public boolean xacThucQuyen(String chucNang) {
        boolean coQuyen = quyenTruyCap.contains(chucNang);
        if (!coQuyen) {
            System.out.println("❌ Ban khong co quyen truy cap chuc nang: " + chucNang);
        }
        return coQuyen;
    }
    
    // Implement ILichSu
    @Override
    public void themLichSu(String hanhDong) {
        String lichSu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) 
                        + " - " + hanhDong;
        lichSuHoatDong.add(lichSu);
    }
    
    @Override
    public List<String> xemLichSu() {
        return new ArrayList<>(lichSuHoatDong);
    }
    
    @Override
    public void xoaLichSu() {
        lichSuHoatDong.clear();
        System.out.println("✅ Da xoa lich su hoat dong cua quan ly " + ma);
    }
    
    public void doiMatKhau(String matKhauCu, String matKhauMoi) {
        if (!this.matKhau.equals(matKhauCu)) {
            throw new IllegalArgumentException("Mat khau cu khong dung");
        }
        if (matKhauMoi == null || matKhauMoi.length() < 6) {
            throw new IllegalArgumentException("Mat khau moi phai co it nhat 6 ky tu");
        }
        this.matKhau = matKhauMoi;
        themLichSu("Doi mat khau thanh cong");
        System.out.println("✅ Doi mat khau thanh cong!");
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-20s │ %-15s │ %-15s │ %-25s │ %-20s ║%n",
            ma, ten, chucVu, soDienThoai, email, 
            (rapQuanLy != null ? rapQuanLy.getTen() : "Chua phan cong"));
    }
    
    @Override
    public boolean kiemTraHopLe() {
        return xacThucThongTin();
    }
    
    public RapChieuPhim getRapQuanLy() { return rapQuanLy; }
    public String getEmail() { return email; }
    public String getTaiKhoan() { return taiKhoan; }

}
