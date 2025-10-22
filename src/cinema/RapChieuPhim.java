package cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import base.ThucThe;
import film.Phim;

public class RapChieuPhim extends ThucThe implements IThongBao, ILichSu, IXuatBaoCao {
    private String diaChi;
    private String soDienThoai;
    private String email;
    private int soPhongChieu;
    private List<PhongChieu> danhSachPhong;
    private List<YeuCauPhim> danhSachYeuCau;
    private QuanLy quanLy;
    private List<String> lichSuHoatDong;

    public RapChieuPhim(String maRap, String tenRap, String diaChi, String soDienThoai, String email, int soPhongChieu) {
        super(maRap, tenRap);
        this.diaChi = validDiaChi(diaChi);
        this.soDienThoai = validSoDienThoai(soDienThoai);
        this.email = validEmail(email);
        this.soPhongChieu = validSoPhong(soPhongChieu);
        this.danhSachPhong = new ArrayList<>();
        this.danhSachYeuCau = new ArrayList<>();
        this.lichSuHoatDong = new ArrayList<>();
    }
    private String validDiaChi(String diaChi){
        if(diaChi == null || diaChi.trim().isEmpty()){
            throw new IllegalArgumentException("Dia chi khong duoc de trong");
        }
        return diaChi.trim();
    }
    private String validEmail(String email){
        if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("Email khong hop le");
        }
        return email;
    }
    private String validDiaChi(String diaChi){
        if(diaChi == null || diaChi.trim().isEmpty()){
            throw new IllegalArgumentException("Dia chi khong duoc de trong");
        }
        return diaChi.trim();
    }
    private int validSoPhong(int soPhongChieu){
        if(soPhongChieu <= 0){
            throw new IllegalArgumentException("So phong chieu phai lon hon 0");
        }
        return soPhongChieu;
    }

    public void themPhongChieu(PhongChieu phong){
        if(phong == null){
            throw new IllegalArgumentException("Phong chieu khong duoc de trong");
        }
        if(danhSachPhong.size() >= soPhongChieu){
            throw new IllegalStateException("Da dat toi da so phong chieu");
        }
        phong.setRap(this);
        danhSachPhong.add(phong);
        themLichSu("Them phong chieu: " + phong.getMa());
        capNhatThoiGian();
    }

    public void datQuanLy(QuanLy quanly){
        if(quanly == null){
            throw new IllegalArgumentException("Quan ly khong duoc de trong");
        }
        this.quanLy = quanly;
        themLichSu("Dat quan ly: " + quanly.getTen());
    }

    public YeuCauPhim taoYeuCauPhim(Phim phim, int soLuongBan, LocalDate ngayBatDau, LocalDate ngayKetThuc){
        if(phim == null){
            throw new IllegalArgumentException("Phim khong duoc de trong");
        }
        if(soLuongBan <= 0){
            throw new IllegalArgumentException("So luong ban phai lon hon 0");
        }
        if(ngayBatDau == null || ngayKetThuc == null){
            throw new IllegalArgumentException("Ngay bat dau va ngay ket thuc khong duoc de trong");
        }
        if(ngayKetThuc.isBefore(ngayBatDau)){
            throw new IllegalArgumentException("Ngay ket thuc phai sau ngay bat dau");
        }
        String maYeuCau = "YC" + System.currentTimeMillis();
        // currentTimeMillis de tranh trung ma
        YeuCauPhim yeuCau = new YeuCauPhim(maYeuCau, this, phim, soLuongBan, ngayBatDau, ngayKetThuc);
        danhSachYeuCau.add(yeuCau);
        themLichSu("Tao yeu cau thue phim: " + phim.getTen());
        capNhatThoiGian();
        return yeuCau;
    }

    //Implement IThongBao
    @Override
    public void guiThongBao(String message){
        System.out.println("Thong bao den rap " + ma + ": " + message);
        themLichSu("Gui thong bao: " + message);
    }

    @Override
    public void guiEmail(String emailNhan, String message){
        System.out.println("Email tu rap: " + ten + " (" + email +") den " + emailNhan);
        System.out.println("Noi dung: " + message);
        themLichSu("Gui email den " + emailNhan);
    }

    @Override
    public void guiSMS(String soDienThoaiNhan, String message){
        System.out.println("SMS tu rap: " + ten + " (" + soDienThoai +") den " + soDienThoaiNhan);
        System.out.println("Noi dung: " + message);
        themLichSu("Gui SMS den " + soDienThoaiNhan);
    }
    //Implement ILichSu
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
        System.out.println("✅ Da xoa lich su hoat dong cua Rap " + ma);
    }
    //Implement IXuatBaoCao

    @Override
    public void xuatBaoCaoTheoNgay(LocalDate ngay) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HOAT DONG RAP THEO NGAY                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ RRap: " + ten);
        System.out.println("║ Ngay: " + ngay);
        System.out.println("║ So yeu cau: " + danhSachYeuCau.size());
        System.out.println("║ So phong chieu: " + danhSachPhong.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

        @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO HOAT DONG RAP THEO THANG                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ RRap: " + ten);
        System.out.println("║ Thang/Nam: " + thang + "/" + nam);
        System.out.println("║ TTong so yeu cau: " + danhSachYeuCau.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

        @Override
    public void xuatBaoCaoTongQuat() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BAO CAO TONG QUAT HOAT DONG RAP                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ RRap: " + ten);
        System.out.println("║ Dia chi: " + diaChi);
        System.out.println("║ TTong phong chieu: " + danhSachPhong.size() + "/" + soPhongChieu);
        System.out.println("║ TTong yeu cau thue phim: " + danhSachYeuCau.size());
        System.out.println("║ Quan ly: " + (quanLy != null ? quanLy.getTen() : "Chua co"));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    @Override
    public void hienThiThongTin() {
        System.out.println("Ma Rap: " + ma);
        System.out.println("Ten Rap: " + ten);
        System.out.println("Dia Chi: " + diaChi);
        System.out.println("So Dien Thoai: " + soDienThoai);
        System.out.println("Email: " + email);
        System.out.println("So Phong Chieu: " + soPhongChieu);
        System.out.println("Quan Ly: " + (quanLy != null ? quanLy.getTen() : "Chua co"));
        System.out.println("So Phong Da Them: " + danhSachPhong.size());
        System.out.println("So Yeu Cau Da Tao: " + danhSachYeuCau.size());
    }
    @Override
    public boolean kiemTraHopLe() {
        return ma != null && !ma.isEmpty() && 
               ten != null && !ten.isEmpty() &&
               soPhongChieu > 0;
    }
    
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }
    public List<PhongChieu> getDanhSachPhong() { 
        return Collections.unmodifiableList(danhSachPhong); 
    }
    public List<YeuCauPhim> getDanhSachYeuCau() { 
        return Collections.unmodifiableList(danhSachYeuCau); 
    }
}












    

}
