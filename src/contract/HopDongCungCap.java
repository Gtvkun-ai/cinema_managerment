package contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import cinema.RapChieuPhim;
import enum.TrangThaiHopDong;
import film.Phim;
import models.distributor.NhaPhanPhoi;

public class HopDongCungCap implements ITinhToan,  IThanhToan {
    private String maHopDong;
    private NhaPhanPhoi nhaPhanPhoi;
    private RapChieuPhim rap;
    private Phim phim;
    private int soLuongBan;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private double giaThue;
    private TrangThaiHopDong trangThai;
    private LocalDateTime ngayKy;

    public HopDongCungCap(String maHopDong, NhaPhanPhoi nhaPhanPhoi, 
                          RapChieuPhim rap, Phim phim, int soLuongBan,
                          LocalDate ngayBatDau, LocalDate ngayKetThuc, double giaThue) {
        this.maHopDong = validMaHopDong(maHopDong);
        this.nhaPhanPhoi = validNhaPhanPhoi(nhaPhanPhoi);
        this.rap = validRap(rap);
        this.phim = validPhim(phim);
        this.soLuongBan = validSoLuong(soLuongBan);
        this.ngayBatDau = validNgayBatDau(ngayBatDau);
        this.ngayKetThuc = validNgayKetThuc(ngayKetThuc, ngayBatDau);
        this.giaThue = validGiaThue(giaThue);
        this.trangThai = TrangThaiHopDong.MOI_TAO;
        this.ngayKy = LocalDateTime.now();
    }

    private String validMaHopDong(String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma hop dong khong duoc de trong");
        }
        return ma.trim().toUpperCase();
    }
    
    private NhaPhanPhoi validNhaPhanPhoi(NhaPhanPhoi npp) {
        if (npp == null) {
            throw new IllegalArgumentException("Nha phan phoi khong duoc null");
        }
        return npp;
    }
    
    private RapChieuPhim validRap(RapChieuPhim r) {
        if (r == null) {
            throw new IllegalArgumentException("Rap khong duoc null");
        }
        return r;
    }
    
    private Phim validPhim(Phim p) {
        if (p == null) {
            throw new IllegalArgumentException("Phim khong duoc null");
        }
        return p;
    }
    
    private int validSoLuong(int sl) {
        if (sl <= 0) {
            throw new IllegalArgumentException("So luong ban phai > 0");
        }
        return sl;
    }
    
    private LocalDate validNgayBatDau(LocalDate ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngay bat dau khong duoc null");
        }
        return ngay;
    }
    
    private LocalDate validNgayKetThuc(LocalDate ngayKT, LocalDate ngayBD) {
        if (ngayKT == null) {
            throw new IllegalArgumentException("Ngay ket thuc khong duoc null");
        }
        if (ngayKT.isBefore(ngayBD)) {
            throw new IllegalArgumentException("Ngay ket thuc phai sau ngay bat dau");
        }
        return ngayKT;
    }
    
    private double validGiaThue(double gia) {
        if (gia < 0) {
            throw new IllegalArgumentException("Gia thue phai >= 0");
        }
        return gia;
    }
    
    public void kichHoat() {
        if (trangThai != TrangThaiHopDong.MOI_TAO) {
            throw new IllegalStateException("Hop dong da duoc kich hoat");
            // ILLEGAILSTATEEXCEPTION là ngoại lệ được ném ra khi trạng thái hiện tại của một đối tượng không hợp lệ cho hành động được yêu cầu.
            // trạng thái là kiểu dữ liệu enum TrangThaiHopDong
        }
        this.trangThai = TrangThaiHopDong.DANG_HIEU_LUC;
    }
    
    public void ketThuc() {
        if (trangThai != TrangThaiHopDong.DANG_HIEU_LUC) {
            throw new IllegalStateException("HHop dong khong trong trang thai hieu luc");
        }
        this.trangThai = TrangThaiHopDong.HET_HAN;
    }
    
    public void huy() {
        this.trangThai = TrangThaiHopDong.BI_HUY;
    }
    
    // Implement ITinhToan
    @Override
    public double tinhTongGiaTri() {
        long soNgay = ChronoUnit.DAYS.between(ngayBatDau, ngayKetThuc);
        return giaThue * soLuongBan * soNgay;
    }
    
    @Override
    public double tinhChietKhau(double phanTram) {
        if (phanTram < 0 || phanTram > 100) {
            throw new IllegalArgumentException("Phan tram phai tu 0-100");
        }
        return tinhTongGiaTri() * (phanTram / 100);
    }
    
    @Override
    public double tinhThue(double tyLeThue) {
        if (tyLeThue < 0) {
            throw new IllegalArgumentException("Ty le thue khong duoc am");
        }
        return tinhTongGiaTri() * (tyLeThue / 100);
    }
    
    // Implement IXuatBaoCao
    @Override
    public void xuatBaoCaoTheoNgay(LocalDate ngay) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HOP DONG THEO NGAY                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Ma hop dong: " + maHopDong);
        System.out.println("║ Ngay: " + ngay);
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HOP DONG THEO THANG                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Ma hop dong: " + maHopDong);
        System.out.println("║ Thang/Nam: " + thang + "/" + nam);
        System.out.println("║ TTong gia tri: " + String.format("%,.0f VND", tinhTongGiaTri()));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTongQuat() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO TONG QUAT HOP DONG                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Ma hop dong: " + maHopDong);
        System.out.println("║ Nha phan phoi: " + nhaPhanPhoi.getTen());
        System.out.println("║ Rap chieu: " + rap.getTen());
        System.out.println("║ Phim: " + phim.getTen());
        System.out.println("║ So luong: " + soLuongBan + " ban");
        System.out.println("║ Thoi gian: " + ngayBatDau + " den " + ngayKetThuc);
        System.out.println("║ TTong gia tri: " + String.format("%,.0f VND", tinhTongGiaTri()));
        System.out.println("║ Trang thai: " + trangThai.getMoTa());
        System.out.println("║ Ngay ky: " + ngayKy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    public void hienThiThongTin() {
        System.out.printf("║ %-12s │ %-15s │ %-15s │ %-20s │ %5d │ %-15s │ %,15.0f ║%n",
            maHopDong, nhaPhanPhoi.getMa(), rap.getMa(), phim.getTen(), 
            soLuongBan, trangThai.getMoTa(), tinhTongGiaTri());
    }
    
    public String getMaHopDong() { return maHopDong; }
    public TrangThaiHopDong getTrangThai() { return trangThai; }
    public double getGiaThue() { return giaThue; }
    public NhaPhanPhoi getNhaPhanPhoi() { return nhaPhanPhoi; }
    public RapChieuPhim getRap() { return rap; }
    public Phim getPhim() { return phim; }
}

