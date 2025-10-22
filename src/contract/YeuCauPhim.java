package contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import cinema.RapChieuPhim;
import film.Phim;
import models.distributor.NhaPhanPhoi;

public class YeuCauPhim implements ITinhToan, IThanhToan{
    private String maYeuCau;
    private RapChieuPhim rap;
    private Phim phim;
    private int soLuongBan;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private TrangThaiYeuCau trangThai;
    private LocalDateTime ngayTao;
    private double soTienDaThanhToan;
    private PhuongThucThanhToan phuongThucThanhToan;
    
    public YeuCauPhim(String maYeuCau, RapChieuPhim rap, Phim phim, 
                      int soLuongBan, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maYeuCau = maYeuCau;
        this.rap = rap;
        this.phim = phim;
        this.soLuongBan = soLuongBan;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = TrangThaiYeuCau.CHO_DUYET;
        this.ngayTao = LocalDateTime.now();
        this.soTienDaThanhToan = 0;
    }
    
    public LocalDateTime getNgayTao() { 
        return ngayTao; 
    }
    
    public void duyetYeuCau() {
        if (trangThai != TrangThaiYeuCau.CHO_DUYET) {
            throw new IllegalStateException("Yeu cau da duoc xu ly");
        }
        if (!phim.kiemTraKhaDung(soLuongBan)) {
            throw new IllegalStateException("Phim khong du so luong ban");
        }
        this.trangThai = TrangThaiYeuCau.DA_DUYET;
    }
    
    public void batDauThue() {
        if (trangThai != TrangThaiYeuCau.DA_DUYET) {
            throw new IllegalStateException("Yeu cau chua duoc duyet");
        }
        phim.giamSoLuongBan(soLuongBan);
        this.trangThai = TrangThaiYeuCau.DANG_THUE;
    }
    
    public void hoanThanh() {
        if (trangThai != TrangThaiYeuCau.DANG_THUE) {
            throw new IllegalStateException("Yeu cau khong trong trang thai thue");
        }
        phim.tangSoLuongBan(soLuongBan);
        this.trangThai = TrangThaiYeuCau.HOAN_THANH;
    }
    
    public void huyYeuCau() {
        if (trangThai == TrangThaiYeuCau.DANG_THUE) {
            phim.tangSoLuongBan(soLuongBan);
        }
        this.trangThai = TrangThaiYeuCau.HUY;
    }
    
    public double tinhChiPhi() {
        long soNgay = ChronoUnit.DAYS.between(ngayBatDau, ngayKetThuc);
        return phim.getGiaMotBan() * soLuongBan * soNgay;
    }
    
    // Implement ITinhToan
    @Override
    public double tinhTongGiaTri() {
        return tinhChiPhi();
    }
    
    @Override
    public double tinhChietKhau(double phanTram) {
        if (phanTram < 0 || phanTram > 100) {
            throw new IllegalArgumentException("Phan tram phai tu 0-100");
        }
        return tinhChiPhi() * (phanTram / 100);
    }
    
    @Override
    public double tinhThue(double tyLeThue) {
        if (tyLeThue < 0) {
            throw new IllegalArgumentException("Ty le thue khong duoc am");
        }
        return tinhChiPhi() * (tyLeThue / 100);
    }
    
    // Implement IThanhToan
    @Override
    public boolean thanhToan(double soTien, String phuongThuc) {
        if (soTien <= 0) {
            System.out.println("❌ So tien thanh toan phai > 0");
            return false;
        }
        
        double conLai = tinhChiPhi() - soTienDaThanhToan;
        if (soTien > conLai) {
            System.out.println("❌ So tien thanh toan vuot qua so tien con lai");
            return false;
        }
        
        this.soTienDaThanhToan += soTien;
        this.phuongThucThanhToan = PhuongThucThanhToan.valueOf(phuongThuc);
        System.out.println("✅ Thanh toan thanh cong " + String.format("%,.0f VND", soTien));
        System.out.println("   Phuong thuc: " + this.phuongThucThanhToan.getMoTa());
        System.out.println("   Con lai: " + String.format("%,.0f VND", conLai - soTien));
        return true;
    }
    
    @Override
    public boolean hoanTien(double soTien) {
        if (soTien <= 0 || soTien > soTienDaThanhToan) {
            System.out.println("❌ So tien hoan tra khong hop le");
            return false;
        }
        this.soTienDaThanhToan -= soTien;
        System.out.println("✅ Hoan tien thanh cong " + String.format("%,.0f VND", soTien));
        return true;
    }
    
    @Override
    public double tinhTongThanhToan() {
        return soTienDaThanhToan;
    }
    
    public void hienThiThongTin() {
        System.out.printf("║ %-12s │ %-15s │ %-20s │ %5d │ %10s │ %10s │ %-15s │ %,15.0f ║%n",
            maYeuCau, rap.getMa(), phim.getTen(), soLuongBan, 
            ngayBatDau, ngayKetThuc, trangThai.getMoTa(), tinhChiPhi());
    }
    
    public String getMaYeuCau() { return maYeuCau; }
    public RapChieuPhim getRap() { return rap; }
    public Phim getPhim() { return phim; }
    public TrangThaiYeuCau getTrangThai() { return trangThai; }
    public int getSoLuongBan() { return soLuongBan; }
}
