package film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.ThucThe;

public class Phim extends ThucThe implements ITinhToan, IDanhGia, IThongKe {
    private int thoiLuong;
    private String theLoai;
    private String nhaSanXuat;
    private String quocGia;
    private LocalDate ngayPhatHanh;
    private DoanhThuPhim doanhThu;
    private List<DinhDangChieu> danhSachDinhDang;
    private int soLuongBanKhaDung;
    private double giaMotBan;
    private List<DanhGiaPhim> danhSachDanhGia;

    public Phim(String maPhim, String tenPhim, int thoiLuong, String theLoai, 
                String nhaSanXuat, String quocGia, LocalDate ngayPhatHanh, 
                int soLuongBan, double giaMotBan) {
        super(maPhim, tenPhim);
        this.thoiLuong = validThoiLuong(thoiLuong);
        this.theLoai = validTheLoai(theLoai);
        this.nhaSanXuat = validNhaSanXuat(nhaSanXuat);
        this.quocGia = validQuocGia(quocGia);
        this.ngayPhatHanh = validNgayPhatHanh(ngayPhatHanh);
        this.soLuongBanKhaDung = validSoLuongBan(soLuongBan);
        this.giaMotBan = validGia(giaMotBan);
        this.danhSachDinhDang = new ArrayList<>();
        this.doanhThu = new DoanhThuPhim(this);
        this.danhSachDanhGia = new ArrayList<>();
    }

    private int validThoiLuong(int thoiLuong){
        if(thoiLuong <= 0 || thoiLuong > 500){
            throw new IllegalArgumentException("Thoi luong phai tu 1 den 500 phut");
        }
        return thoiLuong;
    }
    private String validTheLoai(String tl){
        if(tl == null || tl.trim().isEmpty()){
            throw new IllegalArgumentException("The loai khong duoc de trong");
        }
        return tl.trim();
    }
    private String validNhaSanXuat(String nsx){
        if(nsx == null || nsx.trim().isEmpty()){
            throw new IllegalArgumentException("Nha san xuat khong duoc de trong");
        }
        return nsx.trim();
    }
    private String validQuocGia(String qg){
        if(qg == null || qg.trim().isEmpty()){
            throw new IllegalArgumentException("Quoc gia khong duoc de trong");
        }
        return qg.trim();
    }
    private LocalDate validNgayPhatHanh(LocalDate nph){
        if(nph == null || nph.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ngay phat hanh khong hop le");
        }
        return nph;
    }
    private int validSoLuongBan(int sl){
        if(sl < 0){
            throw new IllegalArgumentException("So luong ban khong duoc am");
        }
        return sl;
    }
    private double validGia(double gia){
        if(gia < 0){
            throw new IllegalArgumentException("Gia khong duoc am");
        }
        return gia;
    }
    public void themDinhDang(DinhDangChieu dinhDang){
        if(dinhDang == null){
            throw new IllegalArgumentException("Dinh dang khong duoc de trong");
        }
        danhSachDinhDang.add(dinhDang);
        capNhatThoiGian();
    }

    public boolean kiemTraKhaDung(int soLuongCanThue){
        return soLuongBanKhaDung >= soLuongCanThue;
        //Trả về true nếu số lượng bàn khả dụng đủ để thuê, ngược lại trả về false
    }

    public void giamSoLuongBan(int soLuong){
        if(soLuong > soLuongBanKhaDung){
            throw new IllegalArgumentException("So luong ban khong du de giam");
        }
        this.soLuongBanKhaDung -= soLuong;
        capNhatThoiGian();
    }
    public void tangSoLuongBan(int soLuong){
        if(soLuong < 0){
            throw new IllegalArgumentException("So luong ban tang phai la so duong");
        }
        this.soLuongBanKhaDung += soLuong;
        capNhatThoiGian();
    }

    //Implement ITinhToan
    @Override
    public double tinhTongGiaTri(){
        return giaMotBan * soLuongBanKhaDung;
    }
    //Implement IDanhGia
    @Override
    public double tinhTongGiaTri(){
        return giaMotBan * soLuongBanKhaDung;
    }
    @Override
    public double tinhChietKhau(double phanTram){
        if(phanTram < 0 || phanTram > 100){
            throw new IllegalArgumentException("Phan tram chiet khau khong hop le");
        }
        return giaMotBan * (phanTram / 100);
    }
    @Override
    public void themDanhGia(int diem, String nhanxet){
        if(diem < 1 || diem > 5){
            throw new IllegalArgumentException("Diem danh gia phai tu 1 den 5");
        }
        DanhSachPhim danhgia = new DanhGiaPhim(diem, nhanxet);
        danhSachDanhGia.add(danhgia);
    }
    @Override
    public double tinhDiemTrungBinh(){
        if(danhSachDanhGia.isEmpty()){
            return 0.0;
        }
        int tongDiem = 0;
        for(DanhGiaPhim dg : danhSachDanhGia){
            tongDiem += dg.getDiem();
        }
        return (double) tongDiem / danhSachDanhGia.size();
    }
    @Override
    public List<String> layDanhSachDanhGia(){
        List<String> result = new ArrayList<>();
        for (DanhGiaPhim dg : danhSachDanhGia) {
            result.add(dg.toString());
        }
        return result;
    }

    //Implement IThongKe
    @Override
    public Map<String, Integer> thongKeTheoLoai(){
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("So luong ban: ", soLuongBanKhaDung);
        thongKe.put("So luong danh gia: ", danhSachDanhGia.size());
        //.put là phương thức của Map để thêm một cặp khóa-giá trị vào bản đồ
        return thongKe;
    }
    @Override
    public Map<String, Double> thongKeDoanhThu() {
        Map<String, Double> doanhThu = new HashMap<>();
        doanhThu.put("Tổng doanh thu", this.doanhThu.getTongDoanhThu());
        doanhThu.put("Giá 1 bản", giaMotBan);
        doanhThu.put("Điểm TB", tinhDiemTrungBinh());
        return doanhThu;
    }
    @Override
    public int demSoLuong(){
        return soLuongBanKhaDung;
    }
    @Override
    public void hienThiThongTin(){
        System.out.println("Phim: " + getMa() + " - " + getTen());
        System.out.println("The loai: " + theLoai);
        System.out.println("Nha san xuat: " + nhaSanXuat);
        System.out.println("Quoc gia: " + quocGia);
        System.out.println("Ngay phat hanh: " + ngayPhatHanh);
        System.out.println("Thoi luong: " + thoiLuong + " phut");
        System.out.println("So luong ban kha dung: " + soLuongBanKhaDung);
        System.out.println("Gia mot ban: " + giaMotBan);
        System.out.println("So dinh dang chieu: " + danhSachDinhDang.size());
        System.out.println("So luong danh gia: " + danhSachDanhGia.size());
    }
    public int getThoiLuong(){
        return thoiLuong;
    }
    public String getTheLoai(){
        return theLoai;
    }
    public int getSoLuongBanKhaDung(){
        return soLuongBanKhaDung;
    }
    public double getGiaMotBan(){
        return giaMotBan;
    }
    public LocalDate getNgayPhatHanh(){
        return ngayPhatHanh;
    }
    public DoanhThuPhim getDoanhThu(){
        return doanhThu;
    }

}
