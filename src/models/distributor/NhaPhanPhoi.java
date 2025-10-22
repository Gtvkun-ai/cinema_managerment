package models.distributor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import base.ThucThe;

public class NhaPhanPhoi extends ThucThe implements IThongBao, ILichSu, IThongKe {
    private String diaChi;
    private String soDienThoai;
    private String email;
    private List<HopDongCungCap> danhsachHopDong;
    private List<String> lichSuGiaoDich;
    public NhaPhanPhoi(String ma, String ten, String diaChi, String soDienThoai, String email) {
        super(ma, ten);
        this.diaChi = validDiaChi(diaChi);
        this.soDienThoai = validSoDienThoai(soDienThoai);
        this.email = validEmail(email);
        this.danhSachHopDong = new ArrayList<>();
        this.lichSuGiaoDich = new ArrayList<>();
    }

    private String validSoDienThoai(String sdt){
        if(sdt == null || !sdt.matches("^[0,9]{10}$")){
            //Phương thức matches() của lớp String kiểm tra xem toàn bộ chuỗi có khớp với mẫu regex hay không.
            //^	Bắt đầu chuỗi
            // [0-9]	Cho phép ký tự là chữ số từ 0 đến 9
            // {10}	Phải có chính xác 10 ký tự (10 chữ số)
            // $	Kết thúc chuỗi
            throw new IllegalArgumentException("So dien thoai khong hop le");
            //IllegalArgumentException là một loại ngoại lệ được sử dụng để chỉ ra rằng một phương thức đã nhận được một đối số không hợp lệ
        }
        return sdt;
    }
    private String validDiaChi(String diaChi){
        if(diaChi == null || diaChi.trim().isEmpty()){
            throw new IllegalArgumentException("Dia chi khong duoc de trong");
        }
        return diaChi.trim();
    }
    private String validEmail(String email){
        if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            //Phương thức matches() của lớp String kiểm tra xem toàn bộ chuỗi có khớp với mẫu regex hay không.
            // ^	Bắt đầu chuỗi
            // [A-Za-z0-9+_.-]	Cho phép các ký tự chữ cái (chữ hoa và chữ thường), chữ số và các ký tự đặc biệt +, _, ., -
            // +	Phải có ít nhất một ký tự từ tập hợp trên
            // @	Ký tự @ phải xuất hiện trong chuỗi
            // (.+)	Phần sau @ phải có ít nhất một ký tự (có thể là tên miền)
            // $	Kết thúc chuỗi
            throw new IllegalArgumentException("Email khong hop le");
        }
        return email;
    }
    public void themHopDong(HopDongCungCap hopDong){
        if(hopDong == null){
            throw new IllegalArgumentException("Hop dong khong duoc de trong");
            //IllegalArgumentException là một loại ngoại lệ được sử dụng để chỉ ra rằng một phương thức đã nhận được một đối số không hợp lệ
        }
        danhsachHopDong.add(hopDong);
        themLichSu("Them hop dong: " + hopDong.getMaHopDong());
        capNhatThoiGian();
    }
    // Implement IThongBao
    @Override
    public void guiThongBao(String thongBao) {
        System.out.println("🔔 [NPP] " + ma + ": " + thongBao);
        themLichSu("Nhan thong bao: " + thongBao);
    }

    @Override
    public void guiEmail(String emailNhan, String message){
        System.out.println("📧 Email tu " + email + " den " + emailNhan + ": " + message);
        themLichSu("Gui email den " + emailNhan + ": ");
    }

    @Override
    public void guiSMS(String soDienThoai, String message){
        System.out.println("📱 SMS tu " + this.soDienThoai + " den " + soDienThoai + ": " + message);
        themLichSu("Gui SMS den " + soDienThoai + ": ");
    }
    

    // Implement ILichSu
    @Override
    public void themLichSu(String hanhdong){
        String lichSu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy--MM-dd:ss")) + " - " + hanhdong;
    //LocalDateTime.now() lấy thời gian hiện tại
    //DateTimeFormatter.ofPattern("yyyy--MM-dd:ss") định dạng thời gian theo mẫu "năm-tháng-ngày:giây"
        lichSuGiaoDich.add(lichSu);}

    @Override
    public List<String> xemLichSu(){
        return new ArrayList<>(lichSuGiaoDich);
        //Trả về một bản sao của danh sách lịch sử giao dịch để tránh việc bên ngoài có thể sửa đổi trực tiếp danh sách gốc
    }
    
    public void xoaLichSu(){
        lichSuGiaoDich.clear();
        System.out.println("Da xoa lich su giao dich cua NPP " + ma);
    }

    // Implement IThongKe
    @Override
    public Map<String, Integer> thongKeTheoLoai(){
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("Tong so hop dong", danhsachHopDong.size());

        int hdHieuLuc = 0;

        for(HopDongCungCap hd : danhsachHopDong){
            if(hd.getTrangThai() == TrangThaiHopDong.DANG_HIEU_LUC){
                hdHieuLuc++;
            }
        }
        thongKe.put("Hop dong dang hieu luc", hdHieuLuc);
        return thongKe;
    }

    @Override
    public Map<String, Double> thongKeDoanhThu(){
        Map<String, Double> doanhThu = new HashMap<>();
        // dùng để lưu trữ doanh thu với khóa là loại doanh thu và giá trị là số tiền tương ứng
        //HashMap là một triển khai cụ thể của giao diện Map trong Java, sử dụng bảng băm để lưu trữ các cặp khóa-giá trị.
        //bảng băm (hash table) là một cấu trúc dữ liệu cho phép lưu trữ và truy xuất dữ liệu một cách nhanh chóng dựa trên khóa.
        double tongGiaTri = 0;
        for(HopDongCungCap hd : danhsachHopDong){
            tongGiaTri += hd.tinhTongGiaTriHopDong();
        }
        doanhThu.put("Tong gia tri hop dong", tongGiaTri);
        return doanhThu;

    }

    @Override
    public int demSoLuong(){
        return danhsachHopDong.size();
    }

    @Override
    public void hienThiThongTin(){
        System.out.println("Nha Phan Phoi: " + ma + " - " + ten);
        System.out.println("Dia Chi: " + diaChi);
        System.out.println("So Dien Thoai: " + soDienThoai);
        System.out.println("Email: " + email);
        System.out.println("So Hop Dong: " + danhsachHopDong.size());
    }

    @Override
    public String getSoDienThoai(){
        return soDienThoai;
    }

    public String getDiaChi(){
        return diaChi;
    }
    public String getEmail(){
        return email;
    }
    public List<HopDongCungCap> getDanhSachHopDong(){
        return Collections.unmodifiableList(danhsachHopDong);
        //Trả về một danh sách không thể sửa đổi để tránh việc bên ngoài có thể thay đổi trực tiếp danh sách gốc
    }

}
