import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


// ==================== ABSTRACT CLASS - LỚP TRỪU TƯỢNG ====================
abstract class ThucThe {
    protected String ma;
    protected String ten;
    protected LocalDateTime ngayTao;
    protected LocalDateTime ngayCapNhat;
    
    public ThucThe(String ma, String ten) {
        if (ma == null || ma.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã không được để trống");
        }
        if (ten == null || ten.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        this.ma = ma.trim().toUpperCase();
        this.ten = ten.trim();
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
    }
    
    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    
    protected void capNhatThoiGian() {
        this.ngayCapNhat = LocalDateTime.now();
    }
    
    public abstract void hienThiThongTin();
    public abstract boolean kiemTraHopLe();
}

// ==================== INTERFACES - NHIỀU INTERFACE ====================

// Interface cho thông báo
interface IThongBao {
    void guiThongBao(String message);
    void guiEmail(String email, String message);
    void guiSMS(String soDienThoai, String message);
}

// Interface cho quản lý CRUD
interface IQuanLy<T> {
    void them(T item);
    void capNhat(T item);
    void xoa(String ma);
    T timTheoMa(String ma);
    List<T> layDanhSach();
}

// Interface cho xuất báo cáo
interface IXuatBaoCao {
    void xuatBaoCaoTheoNgay(LocalDate ngay);
    void xuatBaoCaoTheoThang(int thang, int nam);
    void xuatBaoCaoTongQuat();
}

// Interface cho tìm kiếm
interface ITimKiem<T> {
    List<T> timKiemTheoTen(String ten);
    List<T> timKiemTheoMa(String ma);
    List<T> timKiemNangCao(Map<String, Object> tieuChi);
}

// Interface cho xác thực
interface IXacThuc {
    boolean xacThucThongTin();
    boolean xacThucTaiKhoan(String username, String password);
    boolean xacThucQuyen(String chucNang);
}

// Interface cho tính toán
interface ITinhToan {
    double tinhTongGiaTri();
    double tinhChietKhau(double phanTram);
    double tinhThue(double tyLeThue);
}

// Interface cho lịch sử
interface ILichSu {
    void themLichSu(String hanhDong);
    List<String> xemLichSu();
    void xoaLichSu();
}

// Interface cho thanh toán
interface IThanhToan {
    boolean thanhToan(double soTien, String phuongThuc);
    boolean hoanTien(double soTien);
    double tinhTongThanhToan();
}

// Interface cho đánh giá
interface IDanhGia {
    void themDanhGia(int diem, String nhanXet);
    double tinhDiemTrungBinh();
    List<String> layDanhSachDanhGia();
}

// Interface cho thống kê
interface IThongKe {
    Map<String, Integer> thongKeTheoLoai();
    Map<String, Double> thongKeDoanhThu();
    int demSoLuong();
}

// ==================== ENUM ====================
enum TrangThaiYeuCau {
    CHO_DUYET("Chờ duyệt"),
    DA_DUYET("Đã duyệt"),
    DANG_THUE("Đang thuê"),
    HOAN_THANH("Hoàn thành"),
    HUY("Đã hủy");
    
    private String moTa;
    TrangThaiYeuCau(String moTa) { this.moTa = moTa; }
    public String getMoTa() { return moTa; }
}

enum TrangThaiHopDong {
    MOI_TAO("Mới tạo"),
    DANG_HIEU_LUC("Đang hiệu lực"),
    HET_HAN("Hết hạn"),
    BI_HUY("Bị hủy");
    
    private String moTa;
    TrangThaiHopDong(String moTa) { this.moTa = moTa; }
    public String getMoTa() { return moTa; }
}

enum PhuongThucThanhToan {
    TIEN_MAT("Tiền mặt"),
    CHUYEN_KHOAN("Chuyển khoản"),
    THE_TIN_DUNG("Thẻ tín dụng"),
    VI_DIEN_TU("Ví điện tử");
    
    private String moTa;
    PhuongThucThanhToan(String moTa) { this.moTa = moTa; }
    public String getMoTa() { return moTa; }
}
// ==================== DATA MANAGER - QUẢN LÝ FILE ====================
class DataManager {
    private static final String NPP_FILE = "nhaphanphoi.txt";
    private static final String RAP_FILE = "rapchieu.txt";
    private static final String PHIM_FILE = "phim.txt";
    private static final String QUANLY_FILE = "quanly.txt";
    
    // Lưu Nhà phân phối
    public static void saveNhaPhanPhoi(List<NhaPhanPhoi> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(NPP_FILE))) {
            for (NhaPhanPhoi npp : list) {
                writer.println(npp.getMa() + "|" + npp.getTen() + "|" + 
                             npp.getDiaChi() + "|" + npp.getSoDienThoai() + "|" + npp.getEmail());
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Lỗi lưu NPP: " + e.getMessage());
        }
    }
    
    // Đọc Nhà phân phối
    public static List<NhaPhanPhoi> loadNhaPhanPhoi() {
        List<NhaPhanPhoi> list = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(NPP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    list.add(new NhaPhanPhoi(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
            System.out.println("✅ Đã tải " + list.size() + " nhà phân phối");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File NPP chưa tồn tại");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc NPP: " + e.getMessage());
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
            System.out.println("❌ Lỗi lưu Phim: " + e.getMessage());
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
            System.out.println("✅ Đã tải " + list.size() + " phim");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File Phim chưa tồn tại");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc Phim: " + e.getMessage());
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
            System.out.println("❌ Lỗi lưu Rạp: " + e.getMessage());
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
            System.out.println("✅ Đã tải " + list.size() + " rạp");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("ℹ️ File Rạp chưa tồn tại");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc Rạp: " + e.getMessage());
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
            System.out.println("❌ Lỗi lưu Quản lý: " + e.getMessage());
        }
    }
}


// ==================== NHÀ PHÂN PHỐI PHIM ====================
class NhaPhanPhoi extends ThucThe implements IThongBao, ILichSu, IThongKe {
    private String diaChi;
    private String soDienThoai;
    private String email;
    private List<HopDongCungCap> danhSachHopDong;
    private List<String> lichSuGiaoDich;
    
    public NhaPhanPhoi(String ma, String ten, String diaChi, String soDienThoai, String email) {
        super(ma, ten);
        this.diaChi = validDiaChi(diaChi);
        this.soDienThoai = validSoDienThoai(soDienThoai);
        this.email = validEmail(email);
        this.danhSachHopDong = new ArrayList<>();
        this.lichSuGiaoDich = new ArrayList<>();
    }
    
    private String validSoDienThoai(String sdt) {
        if (sdt == null || !sdt.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải 10 chữ số)");
        }
        return sdt;
    }
    
    private String validEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        return email;
    }
    
    private String validDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        return diaChi.trim();
    }
    
    public void themHopDong(HopDongCungCap hopDong) {
        if (hopDong == null) {
            throw new IllegalArgumentException("Hợp đồng không được null");
        }
        danhSachHopDong.add(hopDong);
        themLichSu("Thêm hợp đồng " + hopDong.getMaHopDong());
        capNhatThoiGian();
    }
    
    // Implement IThongBao
    @Override
    public void guiThongBao(String message) {
        System.out.println("🔔 [NPP " + ma + "] Thông báo: " + message);
        themLichSu("Gửi thông báo: " + message);
    }
    
    @Override
    public void guiEmail(String emailNhan, String message) {
        System.out.println("📧 Email từ " + email + " đến " + emailNhan);
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi email đến " + emailNhan);
    }
    
    @Override
    public void guiSMS(String soDienThoai, String message) {
        System.out.println("📱 SMS từ " + this.soDienThoai + " đến " + soDienThoai);
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi SMS đến " + soDienThoai);
    }
    
    // Implement ILichSu
    @Override
    public void themLichSu(String hanhDong) {
        String lichSu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) 
                        + " - " + hanhDong;
        lichSuGiaoDich.add(lichSu);
    }
    
    @Override
    public List<String> xemLichSu() {
        return new ArrayList<>(lichSuGiaoDich);
    }
    
    @Override
    public void xoaLichSu() {
        lichSuGiaoDich.clear();
        System.out.println("✅ Đã xóa lịch sử giao dịch của NPP " + ma);
    }
    
    // Implement IThongKe
    @Override
    public Map<String, Integer> thongKeTheoLoai() {
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("Tổng số hợp đồng", danhSachHopDong.size());
        
        int hdHieuLuc = 0;
        for (HopDongCungCap hd : danhSachHopDong) {
            if (hd.getTrangThai() == TrangThaiHopDong.DANG_HIEU_LUC) {
                hdHieuLuc++;
            }
        }
        thongKe.put("Hợp đồng hiệu lực", hdHieuLuc);
        return thongKe;
    }
    
    @Override
    public Map<String, Double> thongKeDoanhThu() {
        Map<String, Double> doanhThu = new HashMap<>();
        double tongGiaTri = 0;
        
        for (HopDongCungCap hd : danhSachHopDong) {
            tongGiaTri += hd.tinhTongGiaTri();
        }
        
        doanhThu.put("Tổng giá trị hợp đồng", tongGiaTri);
        return doanhThu;
    }
    
    @Override
    public int demSoLuong() {
        return danhSachHopDong.size();
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-25s │ %-15s │ %-25s │ %-30s │ %5d ║%n",
            ma, ten, soDienThoai, email, diaChi, danhSachHopDong.size());
    }
    
    @Override
    public boolean kiemTraHopLe() {
        return ma != null && !ma.isEmpty() && 
               ten != null && !ten.isEmpty() &&
               soDienThoai != null && email != null;
    }
    
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }
    public String getDiaChi() { return diaChi; }
    public List<HopDongCungCap> getDanhSachHopDong() { 
        return Collections.unmodifiableList(danhSachHopDong); 
    }
}

// ==================== PHIM ====================
class Phim extends ThucThe implements ITinhToan, IDanhGia, IThongKe {
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
    
    private int validThoiLuong(int tl) {
        if (tl <= 0 || tl > 500) {
            throw new IllegalArgumentException("Thời lượng phải từ 1-500 phút");
        }
        return tl;
    }
    
    private String validTheLoai(String tl) {
        if (tl == null || tl.trim().isEmpty()) {
            throw new IllegalArgumentException("Thể loại không được để trống");
        }
        return tl.trim();
    }
    
    private String validNhaSanXuat(String nsx) {
        if (nsx == null || nsx.trim().isEmpty()) {
            throw new IllegalArgumentException("Nhà sản xuất không được để trống");
        }
        return nsx.trim();
    }
    
    private String validQuocGia(String qg) {
        if (qg == null || qg.trim().isEmpty()) {
            throw new IllegalArgumentException("Quốc gia không được để trống");
        }
        return qg.trim();
    }
    
    private LocalDate validNgayPhatHanh(LocalDate ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngày phát hành không được null");
        }
        return ngay;
    }
    
    private int validSoLuongBan(int sl) {
        if (sl < 0) {
            throw new IllegalArgumentException("Số lượng bản phải >= 0");
        }
        return sl;
    }
    
    private double validGia(double gia) {
        if (gia < 0) {
            throw new IllegalArgumentException("Giá phải >= 0");
        }
        return gia;
    }
    
    public void themDinhDang(DinhDangChieu dinhDang) {
        if (dinhDang == null) {
            throw new IllegalArgumentException("Định dạng không được null");
        }
        danhSachDinhDang.add(dinhDang);
        capNhatThoiGian();
    }
    
    public boolean kiemTraKhaDung(int soLuongCanThue) {
        return soLuongBanKhaDung >= soLuongCanThue;
    }
    
    public void giamSoLuongBan(int soLuong) {
        if (soLuong > soLuongBanKhaDung) {
            throw new IllegalStateException("Không đủ số lượng bản khả dụng");
        }
        this.soLuongBanKhaDung -= soLuong;
        capNhatThoiGian();
    }
    
    public void tangSoLuongBan(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải > 0");
        }
        this.soLuongBanKhaDung += soLuong;
        capNhatThoiGian();
    }
    
    // Implement ITinhToan
    @Override
    public double tinhTongGiaTri() {
        return giaMotBan * soLuongBanKhaDung;
    }
    
    @Override
    public double tinhChietKhau(double phanTram) {
        if (phanTram < 0 || phanTram > 100) {
            throw new IllegalArgumentException("Phần trăm phải từ 0-100");
        }
        return giaMotBan * (phanTram / 100);
    }
    
    @Override
    public double tinhThue(double tyLeThue) {
        if (tyLeThue < 0) {
            throw new IllegalArgumentException("Tỷ lệ thuế không được âm");
        }
        return giaMotBan * (tyLeThue / 100);
    }
    
    // Implement IDanhGia
    @Override
    public void themDanhGia(int diem, String nhanXet) {
        if (diem < 1 || diem > 5) {
            throw new IllegalArgumentException("Điểm đánh giá phải từ 1-5");
        }
        DanhGiaPhim danhGia = new DanhGiaPhim(diem, nhanXet);
        danhSachDanhGia.add(danhGia);
    }
    
    @Override
    public double tinhDiemTrungBinh() {
        if (danhSachDanhGia.isEmpty()) {
            return 0;
        }
        double tong = 0;
        for (DanhGiaPhim dg : danhSachDanhGia) {
            tong += dg.getDiem();
        }
        return tong / danhSachDanhGia.size();
    }
    
    @Override
    public List<String> layDanhSachDanhGia() {
        List<String> result = new ArrayList<>();
        for (DanhGiaPhim dg : danhSachDanhGia) {
            result.add(dg.toString());
        }
        return result;
    }
    
    // Implement IThongKe
    @Override
    public Map<String, Integer> thongKeTheoLoai() {
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("Số lượng bản", soLuongBanKhaDung);
        thongKe.put("Số lượt đánh giá", danhSachDanhGia.size());
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
    public int demSoLuong() {
        return soLuongBanKhaDung;
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-30s │ %4d │ %-15s │ %-20s │ %-10s │ %5d │ %,15.0f ║%n",
            ma, ten, thoiLuong, theLoai, nhaSanXuat, quocGia, soLuongBanKhaDung, giaMotBan);
    }
    
    @Override
    public boolean kiemTraHopLe() {
        return ma != null && !ma.isEmpty() && 
               ten != null && !ten.isEmpty() &&
               thoiLuong > 0 && giaMotBan >= 0;
    }
    
    public int getThoiLuong() { return thoiLuong; }
    public String getTheLoai() { return theLoai; }
    public int getSoLuongBanKhaDung() { return soLuongBanKhaDung; }
    public double getGiaMotBan() { return giaMotBan; }
    public LocalDate getNgayPhatHanh() { return ngayPhatHanh; }
    public DoanhThuPhim getDoanhThu() { return doanhThu; }
}

// ==================== CLASS HỖ TRỢ - ĐÁNH GIÁ PHIM ====================
class DanhGiaPhim {
    private int diem;
    private String nhanXet;
    private LocalDateTime ngayDanhGia;
    
    public DanhGiaPhim(int diem, String nhanXet) {
        this.diem = diem;
        this.nhanXet = nhanXet;
        this.ngayDanhGia = LocalDateTime.now();
    }
    
    public int getDiem() { return diem; }
    
    @Override
    public String toString() {
        return diem + " sao - " + nhanXet + " (" + 
               ngayDanhGia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")";
    }
}

// ==================== RẠP CHIẾU PHIM ====================
class RapChieuPhim extends ThucThe implements IThongBao, ILichSu, IXuatBaoCao {
    private String diaChi;
    private String soDienThoai;
    private String email;
    private int soPhongChieu;
    private List<PhongChieu> danhSachPhong;
    private List<YeuCauPhim> danhSachYeuCau;
    private QuanLy quanLy;
    private List<String> lichSuHoatDong;
    
    public RapChieuPhim(String ma, String ten, String diaChi, String soDienThoai, String email, int soPhongChieu) {
        super(ma, ten);
        this.diaChi = validDiaChi(diaChi);
        this.soDienThoai = validSoDienThoai(soDienThoai);
        this.email = validEmail(email);
        this.soPhongChieu = validSoPhong(soPhongChieu);
        this.danhSachPhong = new ArrayList<>();
        this.danhSachYeuCau = new ArrayList<>();
        this.lichSuHoatDong = new ArrayList<>();
    }
    
    private String validSoDienThoai(String sdt) {
        if (sdt == null || !sdt.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        return sdt;
    }
    
    private String validEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        return email;
    }
    
    private String validDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        return diaChi.trim();
    }
    
    private int validSoPhong(int soPhong) {
        if (soPhong <= 0) {
            throw new IllegalArgumentException("Số phòng chiếu phải > 0");
        }
        return soPhong;
    }
    
    public void themPhongChieu(PhongChieu phong) {
        if (phong == null) {
            throw new IllegalArgumentException("Phòng chiếu không được null");
        }
        if (danhSachPhong.size() >= soPhongChieu) {
            throw new IllegalStateException("Đã đủ số lượng phòng chiếu");
        }
        phong.setRap(this);
        danhSachPhong.add(phong);
        themLichSu("Thêm phòng chiếu " + phong.getMa());
        capNhatThoiGian();
    }
    
    public void datQuanLy(QuanLy quanLy) {
        if (quanLy == null) {
            throw new IllegalArgumentException("Quản lý không được null");
        }
        this.quanLy = quanLy;
        themLichSu("Phân công quản lý " + quanLy.getTen());
    }
    
    public YeuCauPhim taoYeuCauPhim(Phim phim, int soLuongBan, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        if (phim == null) {
            throw new IllegalArgumentException("Phim không được null");
        }
        if (soLuongBan <= 0) {
            throw new IllegalArgumentException("Số lượng bản phải > 0");
        }
        if (ngayBatDau == null || ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày không được null");
        }
        if (ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }
        
        String maYeuCau = "YC" + System.currentTimeMillis();
        YeuCauPhim yeuCau = new YeuCauPhim(maYeuCau, this, phim, soLuongBan, ngayBatDau, ngayKetThuc);
        danhSachYeuCau.add(yeuCau);
        themLichSu("Tạo yêu cầu thuê phim " + phim.getTen());
        capNhatThoiGian();
        return yeuCau;
    }
    
    // Implement IThongBao
    @Override
    public void guiThongBao(String message) {
        System.out.println("🔔 [Rạp " + ma + "] Thông báo: " + message);
        themLichSu("Gửi thông báo: " + message);
    }
    
    @Override
    public void guiEmail(String emailNhan, String message) {
        System.out.println("📧 Email từ rạp " + ten + " (" + email + ") đến " + emailNhan);
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi email đến " + emailNhan);
    }
    
    @Override
    public void guiSMS(String soDienThoaiNhan, String message) {
        System.out.println("📱 SMS từ rạp " + ten + " (" + soDienThoai + ") đến " + soDienThoaiNhan);
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi SMS đến " + soDienThoaiNhan);
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
        System.out.println("✅ Đã xóa lịch sử hoạt động của rạp " + ma);
    }
    
    // Implement IXuatBaoCao
    @Override
    public void xuatBaoCaoTheoNgay(LocalDate ngay) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO HOẠT ĐỘNG RẠP THEO NGÀY                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Rạp: " + ten);
        System.out.println("║ Ngày: " + ngay);
        System.out.println("║ Số yêu cầu: " + danhSachYeuCau.size());
        System.out.println("║ Số phòng chiếu: " + danhSachPhong.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO HOẠT ĐỘNG RẠP THEO THÁNG                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Rạp: " + ten);
        System.out.println("║ Tháng/Năm: " + thang + "/" + nam);
        System.out.println("║ Tổng số yêu cầu: " + danhSachYeuCau.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTongQuat() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO TỔNG QUÁT HOẠT ĐỘNG RẠP                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Rạp: " + ten);
        System.out.println("║ Địa chỉ: " + diaChi);
        System.out.println("║ Tổng phòng chiếu: " + danhSachPhong.size() + "/" + soPhongChieu);
        System.out.println("║ Tổng yêu cầu thuê phim: " + danhSachYeuCau.size());
        System.out.println("║ Quản lý: " + (quanLy != null ? quanLy.getTen() : "Chưa có"));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-25s │ %-40s │ %-15s │ %5d/%-5d │ %5d ║%n",
            ma, ten, diaChi, soDienThoai, danhSachPhong.size(), soPhongChieu, danhSachYeuCau.size());
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

// ==================== YÊU CẦU PHIM ====================
class YeuCauPhim implements ITinhToan, IThanhToan {
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
            throw new IllegalStateException("Yêu cầu đã được xử lý");
        }
        if (!phim.kiemTraKhaDung(soLuongBan)) {
            throw new IllegalStateException("Phim không đủ số lượng bản");
        }
        this.trangThai = TrangThaiYeuCau.DA_DUYET;
    }
    
    public void batDauThue() {
        if (trangThai != TrangThaiYeuCau.DA_DUYET) {
            throw new IllegalStateException("Yêu cầu chưa được duyệt");
        }
        phim.giamSoLuongBan(soLuongBan);
        this.trangThai = TrangThaiYeuCau.DANG_THUE;
    }
    
    public void hoanThanh() {
        if (trangThai != TrangThaiYeuCau.DANG_THUE) {
            throw new IllegalStateException("Yêu cầu không trong trạng thái thuê");
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
            throw new IllegalArgumentException("Phần trăm phải từ 0-100");
        }
        return tinhChiPhi() * (phanTram / 100);
    }
    
    @Override
    public double tinhThue(double tyLeThue) {
        if (tyLeThue < 0) {
            throw new IllegalArgumentException("Tỷ lệ thuế không được âm");
        }
        return tinhChiPhi() * (tyLeThue / 100);
    }
    
    // Implement IThanhToan
    @Override
    public boolean thanhToan(double soTien, String phuongThuc) {
        if (soTien <= 0) {
            System.out.println("❌ Số tiền thanh toán phải > 0");
            return false;
        }
        
        double conLai = tinhChiPhi() - soTienDaThanhToan;
        if (soTien > conLai) {
            System.out.println("❌ Số tiền thanh toán vượt quá số tiền còn lại");
            return false;
        }
        
        this.soTienDaThanhToan += soTien;
        this.phuongThucThanhToan = PhuongThucThanhToan.valueOf(phuongThuc);
        System.out.println("✅ Thanh toán thành công " + String.format("%,.0f VND", soTien));
        System.out.println("   Phương thức: " + this.phuongThucThanhToan.getMoTa());
        System.out.println("   Còn lại: " + String.format("%,.0f VND", conLai - soTien));
        return true;
    }
    
    @Override
    public boolean hoanTien(double soTien) {
        if (soTien <= 0 || soTien > soTienDaThanhToan) {
            System.out.println("❌ Số tiền hoàn không hợp lệ");
            return false;
        }
        this.soTienDaThanhToan -= soTien;
        System.out.println("✅ Hoàn tiền thành công " + String.format("%,.0f VND", soTien));
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
// ==================== HỢP ĐỒNG CUNG CẤP ====================
class HopDongCungCap implements ITinhToan, IXuatBaoCao {
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
            throw new IllegalArgumentException("Mã hợp đồng không được để trống");
        }
        return ma.trim().toUpperCase();
    }
    
    private NhaPhanPhoi validNhaPhanPhoi(NhaPhanPhoi npp) {
        if (npp == null) {
            throw new IllegalArgumentException("Nhà phân phối không được null");
        }
        return npp;
    }
    
    private RapChieuPhim validRap(RapChieuPhim r) {
        if (r == null) {
            throw new IllegalArgumentException("Rạp không được null");
        }
        return r;
    }
    
    private Phim validPhim(Phim p) {
        if (p == null) {
            throw new IllegalArgumentException("Phim không được null");
        }
        return p;
    }
    
    private int validSoLuong(int sl) {
        if (sl <= 0) {
            throw new IllegalArgumentException("Số lượng bản phải > 0");
        }
        return sl;
    }
    
    private LocalDate validNgayBatDau(LocalDate ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được null");
        }
        return ngay;
    }
    
    private LocalDate validNgayKetThuc(LocalDate ngayKT, LocalDate ngayBD) {
        if (ngayKT == null) {
            throw new IllegalArgumentException("Ngày kết thúc không được null");
        }
        if (ngayKT.isBefore(ngayBD)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }
        return ngayKT;
    }
    
    private double validGiaThue(double gia) {
        if (gia < 0) {
            throw new IllegalArgumentException("Giá thuê phải >= 0");
        }
        return gia;
    }
    
    public void kichHoat() {
        if (trangThai != TrangThaiHopDong.MOI_TAO) {
            throw new IllegalStateException("Hợp đồng đã được kích hoạt");
        }
        this.trangThai = TrangThaiHopDong.DANG_HIEU_LUC;
    }
    
    public void ketThuc() {
        if (trangThai != TrangThaiHopDong.DANG_HIEU_LUC) {
            throw new IllegalStateException("Hợp đồng không trong trạng thái hiệu lực");
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
            throw new IllegalArgumentException("Phần trăm phải từ 0-100");
        }
        return tinhTongGiaTri() * (phanTram / 100);
    }
    
    @Override
    public double tinhThue(double tyLeThue) {
        if (tyLeThue < 0) {
            throw new IllegalArgumentException("Tỷ lệ thuế không được âm");
        }
        return tinhTongGiaTri() * (tyLeThue / 100);
    }
    
    // Implement IXuatBaoCao
    @Override
    public void xuatBaoCaoTheoNgay(LocalDate ngay) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO HỢP ĐỒNG THEO NGÀY                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Mã hợp đồng: " + maHopDong);
        System.out.println("║ Ngày: " + ngay);
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO HỢP ĐỒNG THEO THÁNG                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Mã hợp đồng: " + maHopDong);
        System.out.println("║ Tháng/Năm: " + thang + "/" + nam);
        System.out.println("║ Tổng giá trị: " + String.format("%,.0f VND", tinhTongGiaTri()));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTongQuat() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO TỔNG QUÁT HỢP ĐỒNG                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Mã hợp đồng: " + maHopDong);
        System.out.println("║ Nhà phân phối: " + nhaPhanPhoi.getTen());
        System.out.println("║ Rạp chiếu: " + rap.getTen());
        System.out.println("║ Phim: " + phim.getTen());
        System.out.println("║ Số lượng: " + soLuongBan + " bản");
        System.out.println("║ Thời gian: " + ngayBatDau + " đến " + ngayKetThuc);
        System.out.println("║ Tổng giá trị: " + String.format("%,.0f VND", tinhTongGiaTri()));
        System.out.println("║ Trạng thái: " + trangThai.getMoTa());
        System.out.println("║ Ngày ký: " + ngayKy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
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

// ==================== QUẢN LÝ ====================
class QuanLy extends ThucThe implements IThongBao, IXacThuc, ILichSu {
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
        if (chucVu.equals("Giám đốc") || chucVu.equals("Phó giám đốc")) {
            quyenTruyCap.add("DUYET_YEU_CAU");
            quyenTruyCap.add("KY_HOP_DONG");
            quyenTruyCap.add("XEM_BAO_CAO");
        }
    }
    
    private String validSoDienThoai(String sdt) {
        if (sdt == null || !sdt.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        return sdt;
    }
    
    private String validEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        return email;
    }
    
    public void phanCongRap(RapChieuPhim rap) {
        if (rap == null) {
            throw new IllegalArgumentException("Rạp không được null");
        }
        this.rapQuanLy = rap;
        rap.datQuanLy(this);
        themLichSu("Được phân công quản lý rạp " + rap.getTen());
    }
    
    public YeuCauPhim taoYeuCauThuePhim(Phim phim, int soLuong, LocalDate ngayBD, LocalDate ngayKT) {
        if (rapQuanLy == null) {
            throw new IllegalStateException("Chưa được phân công rạp");
        }
        YeuCauPhim yc = rapQuanLy.taoYeuCauPhim(phim, soLuong, ngayBD, ngayKT);
        themLichSu("Tạo yêu cầu thuê phim " + phim.getTen() + " - Mã YC: " + yc.getMaYeuCau());
        return yc;
    }
    
    // Implement IThongBao
    @Override
    public void guiThongBao(String message) {
        System.out.println("🔔 Thông báo đến Quản lý " + ten + ": " + message);
        themLichSu("Nhận thông báo: " + message);
    }
    
    @Override
    public void guiEmail(String emailNhan, String message) {
        System.out.println("📧 Email từ " + email + " đến " + emailNhan);
        System.out.println("   Người gửi: " + ten + " (" + chucVu + ")");
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi email đến " + emailNhan);
    }
    
    @Override
    public void guiSMS(String soDienThoaiNhan, String message) {
        System.out.println("📱 SMS từ " + soDienThoai + " đến " + soDienThoaiNhan);
        System.out.println("   Người gửi: " + ten);
        System.out.println("   Nội dung: " + message);
        themLichSu("Gửi SMS đến " + soDienThoaiNhan);
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
            themLichSu("Đăng nhập thành công");
            System.out.println("✅ Đăng nhập thành công! Chào mừng " + ten);
        } else {
            System.out.println("❌ Sai tài khoản hoặc mật khẩu!");
        }
        return ketQua;
    }
    
    @Override
    public boolean xacThucQuyen(String chucNang) {
        boolean coQuyen = quyenTruyCap.contains(chucNang);
        if (!coQuyen) {
            System.out.println("❌ Bạn không có quyền truy cập chức năng: " + chucNang);
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
        System.out.println("✅ Đã xóa lịch sử hoạt động của quản lý " + ma);
    }
    
    public void doiMatKhau(String matKhauCu, String matKhauMoi) {
        if (!this.matKhau.equals(matKhauCu)) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        }
        if (matKhauMoi == null || matKhauMoi.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }
        this.matKhau = matKhauMoi;
        themLichSu("Đổi mật khẩu thành công");
        System.out.println("✅ Đổi mật khẩu thành công!");
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-20s │ %-15s │ %-15s │ %-25s │ %-20s ║%n",
            ma, ten, chucVu, soDienThoai, email, 
            (rapQuanLy != null ? rapQuanLy.getTen() : "Chưa phân công"));
    }
    
    @Override
    public boolean kiemTraHopLe() {
        return xacThucThongTin();
    }
    
    public RapChieuPhim getRapQuanLy() { return rapQuanLy; }
    public String getEmail() { return email; }
    public String getTaiKhoan() { return taiKhoan; }
}

// ==================== DOANH THU PHIM ====================
class DoanhThuPhim {
    private Phim phim;
    private double tongDoanhThu;
    private int soLanThue;
    
    public DoanhThuPhim(Phim phim) {
        this.phim = phim;
        this.tongDoanhThu = 0;
        this.soLanThue = 0;
    }
    
    public void capNhatDoanhThu(double soTien) {
        if (soTien < 0) {
            throw new IllegalArgumentException("Số tiền không được âm");
        }
        this.tongDoanhThu += soTien;
        this.soLanThue++;
    }
    
    public double getTongDoanhThu() { return tongDoanhThu; }
    public int getSoLanThue() { return soLanThue; }
    public Phim getPhim() { return phim; }
}

class PhongChieu extends ThucThe {
    private int soGhe;
    private String loaiPhong;
    private RapChieuPhim rap;
    
    public PhongChieu(String maPhong, String tenPhong, int soGhe, String loaiPhong) {
        super(maPhong, tenPhong);
        this.soGhe = validSoGhe(soGhe);
        this.loaiPhong = validLoaiPhong(loaiPhong);
    }
    
    public RapChieuPhim getRap() {
        return rap;
    }
    
    public void setRap(RapChieuPhim rap) {
        this.rap = rap;
    }

    
    private int validSoGhe(int sg) {
        if (sg <= 0 || sg > 500) {
            throw new IllegalArgumentException("Số ghế phải từ 1-500");
        }
        return sg;
    }
    
    private String validLoaiPhong(String lp) {
        if (lp == null || lp.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại phòng không được để trống");
        }
        return lp.trim();
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("║ %-10s │ %-20s │ %5d │ %-15s ║%n",
            ma, ten, soGhe, loaiPhong);
    }
    
    @Override
    public boolean kiemTraHopLe() {
        return ma != null && !ma.isEmpty() && soGhe > 0;
    }
    
    public int getSoGhe() { return soGhe; }
}

// ==================== ĐỊNH DẠNG CHIẾU ====================
abstract class DinhDangChieu {
    protected String maDinhDang;
    private String tenDinhDang;
    
    public DinhDangChieu(String ma, String ten) {
        this.maDinhDang = ma;
        this.tenDinhDang = ten;
    }
    
    public abstract double tinhThuPhi();
    public String getTenDinhDang() { return tenDinhDang; }
}

class DinhDang2D extends DinhDangChieu {
    public DinhDang2D() {
        super("2D", "Phim 2D");
    }
    
    @Override
    public double tinhThuPhi() {
        return 0;
    }
}

class DinhDang3D extends DinhDangChieu {
    private static final double PHI_3D = 20000;
    
    public DinhDang3D() {
        super("3D", "Phim 3D");
    }
    
    @Override
    public double tinhThuPhi() {
        return PHI_3D;
    }
}

class DinhDang4DX extends DinhDangChieu {
    private static final double PHI_4DX = 50000;
    
    public DinhDang4DX() {
        super("4DX", "Phim 4DX");
    }
    
    @Override
    public double tinhThuPhi() {
        return PHI_4DX;
    }
}

// ==================== HỆ THỐNG QUẢN LÝ ====================
class HeThongQuanLyCungCapPhim implements IQuanLy<Object>, ITimKiem<Object>, IXuatBaoCao, IThongKe {
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
    System.out.println("\n⏳ Đang tải dữ liệu từ file...");
    
    // Tải dữ liệu từ các file
    danhSachNhaPhanPhoi = DataManager.loadNhaPhanPhoi();
    danhSachPhim = DataManager.loadPhim();
    danhSachRap = DataManager.loadRap();
    
    // Nếu không có dữ liệu, khởi tạo dữ liệu mẫu
    if (danhSachNhaPhanPhoi.isEmpty() && danhSachPhim.isEmpty() && danhSachRap.isEmpty()) {
        System.out.println("ℹ️ Không có dữ liệu, khởi tạo dữ liệu mẫu...");
        khoiTaoDuLieuMau();
        autoSave();
    } else {
        System.out.println("✅ Tải dữ liệu thành công!");
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
                "123 Nguyễn Huệ, Q1, HCM", "0901234567", "cgv@distribution.vn");
            NhaPhanPhoi npp2 = new NhaPhanPhoi("NPP002", "Galaxy Film", 
                "456 Lê Lợi, Q1, HCM", "0907654321", "galaxy@film.vn");
            NhaPhanPhoi npp3 = new NhaPhanPhoi("NPP003", "Lotte Cinema Supply", 
                "789 Trần Hưng Đạo, Q5, HCM", "0903456789", "lotte@supply.vn");
            danhSachNhaPhanPhoi.add(npp1);
            danhSachNhaPhanPhoi.add(npp2);
            danhSachNhaPhanPhoi.add(npp3);
            
            // Phim
            Phim phim1 = new Phim("P001", "Avengers: Endgame", 181, "Hành động", 
                "Marvel Studios", "Mỹ", LocalDate.of(2024, 4, 26), 50, 5000000);
            phim1.themDinhDang(new DinhDang2D());
            phim1.themDinhDang(new DinhDang3D());
            phim1.themDanhGia(5, "Phim hay, đáng xem!");
            phim1.themDanhGia(4, "Kết thúc hoàn hảo cho series");
            
            Phim phim2 = new Phim("P002", "Inception", 148, "Khoa học viễn tưởng", 
                "Warner Bros", "Mỹ", LocalDate.of(2024, 7, 16), 30, 4000000);
            phim2.themDinhDang(new DinhDang2D());
            phim2.themDinhDang(new DinhDang4DX());
            phim2.themDanhGia(5, "Kiệt tác điện ảnh");
            
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
            RapChieuPhim rap1 = new RapChieuPhim("R001", "CGV Nguyễn Du", 
                "456 Nguyễn Du, Q1, HCM", "0281234567", "cgvnd@cgv.vn", 8);
            RapChieuPhim rap2 = new RapChieuPhim("R002", "Galaxy Nguyễn Trãi", 
                "789 Nguyễn Trãi, Q5, HCM", "0287654321", "glxnt@galaxy.vn", 6);
            RapChieuPhim rap3 = new RapChieuPhim("R003", "Lotte Cinema Cộng Hòa", 
                "135 Cộng Hòa, Tân Bình, HCM", "0289876543", "lotte@ch.vn", 10);
            
            // Thêm phòng chiếu cho rạp
            rap1.themPhongChieu(new PhongChieu("PC001", "Phòng VIP 1", 150, "VIP"));
            rap1.themPhongChieu(new PhongChieu("PC002", "Phòng 3D 1", 200, "3D"));
            rap2.themPhongChieu(new PhongChieu("PC003", "Phòng Thường 1", 180, "Thường"));
            rap2.themPhongChieu(new PhongChieu("PC004", "Phòng 4DX", 120, "4DX"));
            
            danhSachRap.add(rap1);
            danhSachRap.add(rap2);
            danhSachRap.add(rap3);
            
            // Quản lý
            QuanLy ql1 = new QuanLy("QL001", "Nguyễn Văn A", "0912345678", "nva@cgv.vn", "Giám đốc");
            ql1.phanCongRap(rap1);
            
            QuanLy ql2 = new QuanLy("QL002", "Trần Thị B", "0987654321", "ttb@galaxy.vn", "Trưởng phòng");
            ql2.phanCongRap(rap2);
            
            QuanLy ql3 = new QuanLy("QL003", "Lê Văn C", "0976543210", "lvc@lotte.vn", "Phó giám đốc");
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
            
            System.out.println("✅ Khởi tạo dữ liệu mẫu thành công!");
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi khởi tạo dữ liệu: " + e.getMessage());
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
        System.out.println("║         BÁO CÁO HỆ THỐNG THEO NGÀY                       ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Ngày: " + ngay);
        System.out.println("║ Tổng số yêu cầu: " + danhSachYeuCau.size());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void xuatBaoCaoTheoThang(int thang, int nam) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BÁO CÁO HỆ THỐNG THEO THÁNG                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ Tháng/Năm: " + thang + "/" + nam);
        System.out.println("║ Tổng hợp đồng: " + danhSachHopDong.size());
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
        thongKe.put("Số NPP", danhSachNhaPhanPhoi.size());
        thongKe.put("Số rạp", danhSachRap.size());
        thongKe.put("Số phim", danhSachPhim.size());
        thongKe.put("Số yêu cầu", danhSachYeuCau.size());
        thongKe.put("Số hợp đồng", danhSachHopDong.size());
        return thongKe;
    }
    
    @Override
    public Map<String, Double> thongKeDoanhThu() {
        Map<String, Double> doanhThu = new HashMap<>();
        double tongDoanhThu = 0;
        for (Phim phim : danhSachPhim) {
            tongDoanhThu += phim.getDoanhThu().getTongDoanhThu();
        }
        doanhThu.put("Tổng doanh thu", tongDoanhThu);
        return doanhThu;
    }
    
    @Override
    public int demSoLuong() {
        return danhSachPhim.size() + danhSachRap.size() + danhSachNhaPhanPhoi.size();
    }
    
    public void hienThiMenu() {
        while(true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║          HỆ THỐNG QUẢN LÝ CUNG CẤP PHIM CHO RẠP CHIẾU              ║");
            if (quanLyDangNhap != null) {
                System.out.println("║          👤 Đăng nhập: " + quanLyDangNhap.getMa() + " - " + quanLyDangNhap.getTen());
            }
            System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. 📋 Quản lý nhà phân phối                                        ║");
            System.out.println("║  2. 🎬 Quản lý rạp chiếu phim                                       ║");
            System.out.println("║  3. 🎥 Quản lý phim                                                 ║");
            System.out.println("║  4. 📝 Quản lý yêu cầu phim                                         ║");
            System.out.println("║  5. 📜 Quản lý hợp đồng cung cấp                                    ║");
            System.out.println("║  7. 📊 Thống kê báo cáo                                             ║");
            System.out.println("║  8. 🔍 Tìm kiếm                                                     ║");
            if (quanLyDangNhap != null) {
                System.out.println("║  9. 🚪 Đăng xuất                                                 ║");
                System.out.println("║  0. 🚪 Thoát                                                     ║");
            } else {
                System.out.println("║  9. 🚪 Thoát                                                     ║");
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
                    case 4:
                        quanLyYeuCauPhim();
                        break;
                    case 5:
                        quanLyHopDong();
                        break;
                    case 9: 
                        if (quanLyDangNhap != null) {
                            System.out.println("\n👋 Đăng xuất thành công! Tạm biệt " + quanLyDangNhap.getTen() + "!");
                            quanLyDangNhap = null;
                        } else {
                            System.out.println("\n👋 Cảm ơn bạn đã sử dụng hệ thống! Tạm biệt!");
                            return;
                        }
                        break;
                    case 0:
                        if (quanLyDangNhap != null) {
                            System.out.println("\n👋 Cảm ơn bạn đã sử dụng hệ thống! Tạm biệt!");
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
                        System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng chọn từ 1-9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Vui lòng nhập số!");
                sc.nextLine();
            }
        }
    }
            
    private void timKiem() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         CHỨC NĂNG TÌM KIẾM           ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Tìm theo tên                     ║");
        System.out.println("║  2. Tìm theo mã                      ║");
        System.out.println("║  3. Quay lại                         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("👉 Chọn (1-3): ");
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        switch(choice) {
            case 1:
                System.out.print("Nhập tên cần tìm: ");
                String ten = sc.nextLine();
                List<Object> ketQua = timKiemTheoTen(ten);
                System.out.println("\n🔍 Tìm thấy " + ketQua.size() + " kết quả:");
                for (Object obj : ketQua) {
                    if (obj instanceof ThucThe) {
                        ((ThucThe) obj).hienThiThongTin();
                    }
                }
                break;
            case 2:
                System.out.print("Nhập mã cần tìm: ");
                String ma = sc.nextLine();
                Object found = timTheoMa(ma);
                if (found != null) {
                    System.out.println("\n✅ Tìm thấy:");
                    if (found instanceof ThucThe) {
                        ((ThucThe) found).hienThiThongTin();
                    }
                } else {
                    System.out.println("❌ Không tìm thấy!");
                }
                break;
            case 3:
                return;
        }
    }
    
    private void quanLyNhaPhanPhoi() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                    DANH SÁCH NHÀ PHÂN PHỐI                                                              ║");
        System.out.println("╠════════════╪═══════════════════════════╪═════════════════╪═══════════════════════════╪════════════════════════════════╪═══════╣");
        System.out.println("║ Mã NPP     │ Tên nhà phân phối         │ Số điện thoại   │ Email                     │ Địa chỉ                        │ Số HĐ ║");
        System.out.println("╠════════════╪═══════════════════════════╪═════════════════╪═══════════════════════════╪════════════════════════════════╪═══════╣");
        
        for (NhaPhanPhoi npp : danhSachNhaPhanPhoi) {
            npp.hienThiThongTin();
        }
        System.out.println("╚════════════╧═══════════════════════════╧═════════════════╧═══════════════════════════╧════════════════════════════════╧═══════╝");
        System.out.println("Tổng số nhà phân phối: " + danhSachNhaPhanPhoi.size());
    }
    
    private void quanLyRapChieu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                  DANH SÁCH RẠP CHIẾU PHIM                                                       ║");
        System.out.println("╠════════════╪═══════════════════════════╪══════════════════════════════════════════════╪═════════════════╪═════════════╪═══════╣");
        System.out.println("║ Mã rạp     │ Tên rạp                   │ Địa chỉ                                      │ Số điện thoại   │ Phòng       │ YC    ║");
        System.out.println("╠════════════╪═══════════════════════════╪══════════════════════════════════════════════╪═════════════════╪═════════════╪═══════╣");
        
        for (RapChieuPhim rap : danhSachRap) {
            rap.hienThiThongTin();
        }
        System.out.println("╚════════════╧═══════════════════════════╧══════════════════════════════════════════════╧═════════════════╧═════════════╧═══════╝");
        System.out.println("Tổng số rạp chiếu: " + danhSachRap.size());
    }
    
    private void quanLyPhim() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           DANH SÁCH PHIM                                                                             ║");
        System.out.println("╠════════════╪════════════════════════════════════╪══════╪═════════════════╪══════════════════════╪════════════╪═══════╪═════════════════╣");
        System.out.println("║ Mã phim    │ Tên phim                           │ TL   │ Thể loại        │ Nhà sản xuất         │ Quốc gia   │ Bản   │ Giá (VND)       ║");
        System.out.println("╠════════════╪════════════════════════════════════╪══════╪═════════════════╪══════════════════════╪════════════╪═══════╪═════════════════╣");
        
        for (Phim phim : danhSachPhim) {
            phim.hienThiThongTin();
        }
        System.out.println("╚════════════╧════════════════════════════════════╧══════╧═════════════════╧══════════════════════╧════════════╧═══════╧═════════════════╝");
        System.out.println("Tổng số phim: " + danhSachPhim.size());
        
        // Hiển thị thêm thông tin đánh giá
        System.out.println("\n📊 THÔNG TIN ĐÁNH GIÁ PHIM:");
        for (Phim phim : danhSachPhim) {
            System.out.printf("  %s (%s): %.1f⭐ (%d đánh giá)%n", 
                phim.getMa(), phim.getTen(), 
                phim.tinhDiemTrungBinh(),
                phim.layDanhSachDanhGia().size());
        }
    }
    
    private void quanLyYeuCauPhim() {
        System.out.println("\n╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║            QUẢN LÝ YÊU CẦU THUÊ PHIM                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Tạo yêu cầu mới                                        ║");
        System.out.println("║  2. Xem danh sách yêu cầu                                  ║");
        System.out.println("║  3. Duyệt yêu cầu                                          ║");
        System.out.println("║  4. Bắt đầu thuê (kích hoạt)                               ║");
        System.out.println("║  5. Hoàn thành yêu cầu                                     ║");
        System.out.println("║  6. Hủy yêu cầu                                            ║");
        System.out.println("║  7. Thanh toán yêu cầu                                     ║");
        System.out.println("║  8. Quay lại                                               ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Chọn (1-8): ");
        
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
            default: System.out.println("❌ Lựa chọn không hợp lệ.");
        }
    }
    
    private void taoYeuCauMoi() {
        try {
            System.out.println("\n=== TẠO YÊU CẦU THUÊ PHIM MỚI ===");
            System.out.print("Nhập mã quản lý: ");
            String maQL = sc.nextLine().toUpperCase();
            
            QuanLy ql = null;
            for (QuanLy q : danhSachQuanLy) {
                if (q.getMa().equalsIgnoreCase(maQL)) {
                    ql = q;
                    break;
                }
            }
            
            if (ql == null) {
                System.out.println("❌ Không tìm thấy quản lý!");
                return;
            }
            
            System.out.print("Nhập mã phim: ");
            String maPhim = sc.nextLine().toUpperCase();
            
            Phim phim = null;
            for (Phim p : danhSachPhim) {
                if (p.getMa().equalsIgnoreCase(maPhim)) {
                    phim = p;
                    break;
                }
            }
            
            if (phim == null) {
                System.out.println("❌ Không tìm thấy phim!");
                return;
            }
            
            System.out.println("Số lượng bản khả dụng: " + phim.getSoLuongBanKhaDung());
            System.out.print("Số lượng bản cần thuê: ");
            int soLuong = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Ngày bắt đầu (yyyy-MM-dd): ");
            LocalDate ngayBD = LocalDate.parse(sc.nextLine());
            
            System.out.print("Ngày kết thúc (yyyy-MM-dd): ");
            LocalDate ngayKT = LocalDate.parse(sc.nextLine());
            
            YeuCauPhim yc = ql.taoYeuCauThuePhim(phim, soLuong, ngayBD, ngayKT);
            danhSachYeuCau.add(yc);
            
            System.out.println("✅ Tạo yêu cầu thành công!");
            System.out.println("Mã yêu cầu: " + yc.getMaYeuCau());
            System.out.println("Chi phí dự kiến: " + String.format("%,.0f VND", yc.tinhChiPhi()));
            System.out.println("Thuế VAT (10%): " + String.format("%,.0f VND", yc.tinhThue(10)));
            System.out.println("Tổng cộng: " + String.format("%,.0f VND", yc.tinhChiPhi() + yc.tinhThue(10)));
            
            ql.guiThongBao("Yêu cầu " + yc.getMaYeuCau() + " đã được tạo cho phim " + phim.getTen());
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachYeuCau() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                        DANH SÁCH YÊU CẦU THUÊ PHIM                                                                   ║");
        System.out.println("╠══════════════╪═════════════════╪══════════════════════╪═══════╪════════════╪════════════╪═════════════════╪═════════════════╣");
        System.out.println("║ Mã yêu cầu   │ Mã rạp          │ Tên phim             │ SL    │ Ngày bắt đầu│ Ngày kết thúc│ Trạng thái    │ Chi phí (VND)   ║");
        System.out.println("╠══════════════╪═════════════════╪══════════════════════╪═══════╪════════════╪════════════╪═════════════════╪═════════════════╣");
        
        if (danhSachYeuCau.isEmpty()) {
            System.out.println("║                                                          Chưa có yêu cầu nào                                                                      ║");
        } else {
            for (YeuCauPhim yc : danhSachYeuCau) {
                yc.hienThiThongTin();
            }
        }
        System.out.println("╚══════════════╧═════════════════╧══════════════════════╧═══════╧════════════╧════════════╧═════════════════╧═════════════════╝");
        System.out.println("Tổng số yêu cầu: " + danhSachYeuCau.size());
    }
    
    private void duyetYeuCau() {
        try {
            System.out.print("Nhập mã yêu cầu cần duyệt: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Không tìm thấy yêu cầu!");
                return;
            }
            
            yc.duyetYeuCau();
            System.out.println("✅ Đã duyệt yêu cầu " + maYC + " thành công!");
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void batDauThue() {
        try {
            System.out.print("Nhập mã yêu cầu cần kích hoạt: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Không tìm thấy yêu cầu!");
                return;
            }
            
            yc.batDauThue();
            yc.getPhim().getDoanhThu().capNhatDoanhThu(yc.tinhChiPhi());
            System.out.println("✅ Đã bắt đầu thuê phim cho yêu cầu " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void hoanThanhYeuCau() {
        try {
            System.out.print("Nhập mã yêu cầu cần hoàn thành: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Không tìm thấy yêu cầu!");
                return;
            }
            
            yc.hoanThanh();
            System.out.println("✅ Đã hoàn thành yêu cầu " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void huyYeuCau() {
        try {
            System.out.print("Nhập mã yêu cầu cần hủy: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Không tìm thấy yêu cầu!");
                return;
            }
            
            yc.huyYeuCau();
            System.out.println("✅ Đã hủy yêu cầu " + maYC);
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void thanhToanYeuCau() {
        try {
            System.out.print("Nhập mã yêu cầu cần thanh toán: ");
            String maYC = sc.nextLine().toUpperCase();
            
            YeuCauPhim yc = null;
            for (YeuCauPhim y : danhSachYeuCau) {
                if (y.getMaYeuCau().equalsIgnoreCase(maYC)) {
                    yc = y;
                    break;
                }
            }
            
            if (yc == null) {
                System.out.println("❌ Không tìm thấy yêu cầu!");
                return;
            }
            
            System.out.println("\nThông tin thanh toán:");
            System.out.println("Tổng tiền: " + String.format("%,.0f VND", yc.tinhChiPhi()));
            System.out.println("Đã thanh toán: " + String.format("%,.0f VND", yc.tinhTongThanhToan()));
            System.out.println("Còn lại: " + String.format("%,.0f VND", yc.tinhChiPhi() - yc.tinhTongThanhToan()));
            
            System.out.print("\nNhập số tiền cần thanh toán: ");
            double soTien = sc.nextDouble();
            sc.nextLine();
            
            System.out.println("Chọn phương thức thanh toán:");
            System.out.println("1. Tiền mặt");
            System.out.println("2. Chuyển khoản");
            System.out.println("3. Thẻ tín dụng");
            System.out.println("4. Ví điện tử");
            System.out.print("Chọn (1-4): ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            String phuongThuc = "";
            switch(choice) {
                case 1: phuongThuc = "TIEN_MAT"; break;
                case 2: phuongThuc = "CHUYEN_KHOAN"; break;
                case 3: phuongThuc = "THE_TIN_DUNG"; break;
                case 4: phuongThuc = "VI_DIEN_TU"; break;
                default: 
                    System.out.println("❌ Phương thức không hợp lệ!");
                    return;
            }
            
            yc.thanhToan(soTien, phuongThuc);
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    private void quanLyHopDong() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                   DANH SÁCH HỢP ĐỒNG CUNG CẤP                                                        ║");
        System.out.println("╠══════════════╪═════════════════╪═════════════════╪══════════════════════╪═══════╪═════════════════╪═════════════════╣");
        System.out.println("║ Mã hợp đồng  │ Mã NPP          │ Mã rạp          │ Tên phim             │ SL    │ Trạng thái      │ Giá trị (VND)   ║");
        System.out.println("╠══════════════╪═════════════════╪═════════════════╪══════════════════════╪═══════╪═════════════════╪═════════════════╣");
        
        if (danhSachHopDong.isEmpty()) {
            System.out.println("║                                                     Chưa có hợp đồng nào                                                            ║");
        } else {
            for (HopDongCungCap hd : danhSachHopDong) {
                hd.hienThiThongTin();
            }
        }
        System.out.println("╚══════════════╧═════════════════╧═════════════════╧══════════════════════╧═══════╧═════════════════╧═════════════════╝");
        System.out.println("Tổng số hợp đồng: " + danhSachHopDong.size());
        
        // Menu phụ
        System.out.println("\n1. Xem chi tiết hợp đồng");
        System.out.println("2. Xuất báo cáo hợp đồng");
        System.out.println("3. Quay lại");
        System.out.print("👉 Chọn (1-3): ");
        
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhập mã hợp đồng: ");
                    String maHD = sc.nextLine().toUpperCase();
                    for (HopDongCungCap hd : danhSachHopDong) {
                        if (hd.getMaHopDong().equals(maHD)) {
                            hd.xuatBaoCaoTongQuat();
                            System.out.println("\n💰 THÔNG TIN TÀI CHÍNH:");
                            System.out.println("  Tổng giá trị: " + String.format("%,.0f VND", hd.tinhTongGiaTri()));
                            System.out.println("  Chiết khấu 5%: " + String.format("%,.0f VND", hd.tinhChietKhau(5)));
                            System.out.println("  Thuế VAT 10%: " + String.format("%,.0f VND", hd.tinhThue(10)));
                            System.out.println("  Thành tiền: " + String.format("%,.0f VND", 
                                hd.tinhTongGiaTri() - hd.tinhChietKhau(5) + hd.tinhThue(10)));
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Nhập mã hợp đồng: ");
                    String maHD2 = sc.nextLine().toUpperCase();
                    for (HopDongCungCap hd : danhSachHopDong) {
                        if (hd.getMaHopDong().equals(maHD2)) {
                            System.out.println("\n1. Báo cáo theo ngày");
                            System.out.println("2. Báo cáo theo tháng");
                            System.out.println("3. Báo cáo tổng quát");
                            System.out.print("Chọn: ");
                            int bcChoice = sc.nextInt();
                            sc.nextLine();
                            
                            switch(bcChoice) {
                                case 1:
                                    System.out.print("Nhập ngày (yyyy-MM-dd): ");
                                    LocalDate ngay = LocalDate.parse(sc.nextLine());
                                    hd.xuatBaoCaoTheoNgay(ngay);
                                    break;
                                case 2:
                                    System.out.print("Nhập tháng: ");
                                    int thang = sc.nextInt();
                                    System.out.print("Nhập năm: ");
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
        System.out.println("║                                                DANH SÁCH QUẢN LÝ RẠP                                                          ║");
        System.out.println("╠════════════╪══════════════════════╪═════════════════╪═════════════════╪═══════════════════════════╪══════════════════════╣");
        System.out.println("║ Mã QL      │ Tên                  │ Chức vụ         │ Số điện thoại   │ Email                     │ Rạp quản lý          ║");
        System.out.println("╠════════════╪══════════════════════╪═════════════════╪═════════════════╪═══════════════════════════╪══════════════════════╣");
        
        for (QuanLy ql : danhSachQuanLy) {
            ql.hienThiThongTin();
        }
        System.out.println("╚════════════╧══════════════════════╧═════════════════╧═════════════════╧═══════════════════════════╧══════════════════════╝");
        System.out.println("Tổng số quản lý: " + danhSachQuanLy.size());
        
        // Menu phụ
        System.out.println("\n1. Xem lịch sử hoạt động");
        System.out.println("2. Đăng nhập quản lý");
        System.out.println("3. Đổi mật khẩu");
        System.out.println("4. Gửi thông báo");
        System.out.println("5. Quay lại");
        System.out.print("👉 Chọn (1-5): ");
        
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhập mã quản lý: ");
                    String maQL = sc.nextLine().toUpperCase();
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQL)) {
                            System.out.println("\n📜 LỊCH SỬ HOẠT ĐỘNG:");
                            List<String> lichSu = ql.xemLichSu();
                            if (lichSu.isEmpty()) {
                                System.out.println("  Chưa có hoạt động nào");
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
                    System.out.print("Nhập tài khoản: ");
                    String tk = sc.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String mk = sc.nextLine();
                    
                    boolean found = false;
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getTaiKhoan().equals(tk)) {
                            found = true;
                            if (ql.xacThucTaiKhoan(tk, mk)) {
                                quanLyDangNhap = ql;
                                System.out.println("✅ Bạn có các quyền sau:");
                                System.out.println("  - Xem thông tin");
                                System.out.println("  - Tạo yêu cầu");
                                if (ql.xacThucQuyen("DUYET_YEU_CAU")) {
                                    System.out.println("  - Duyệt yêu cầu");
                                    System.out.println("  - Ký hợp đồng");
                                    System.out.println("  - Xem báo cáo");
                                }
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("❌ Tài khoản không tồn tại!");
                    }
                    break;
                case 3:
                    System.out.print("Nhập mã quản lý: ");
                    String maQL2 = sc.nextLine().toUpperCase();
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQL2)) {
                            System.out.print("Nhập mật khẩu cũ: ");
                            String mkCu = sc.nextLine();
                            System.out.print("Nhập mật khẩu mới: ");
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
                    System.out.print("Nhập mã quản lý gửi: ");
                    String maQLGui = sc.nextLine().toUpperCase();
                    QuanLy qlGui = null;
                    for (QuanLy ql : danhSachQuanLy) {
                        if (ql.getMa().equals(maQLGui)) {
                            qlGui = ql;
                            break;
                        }
                    }
                    
                    if (qlGui == null) {
                        System.out.println("❌ Không tìm thấy quản lý!");
                        break;
                    }
                    
                    System.out.println("\n1. Gửi thông báo");
                    System.out.println("2. Gửi email");
                    System.out.println("3. Gửi SMS");
                    System.out.print("Chọn: ");
                    int guiChoice = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.print("Nhập nội dung: ");
                    String noiDung = sc.nextLine();
                    
                    switch(guiChoice) {
                        case 1:
                            qlGui.guiThongBao(noiDung);
                            break;
                        case 2:
                            System.out.print("Nhập email người nhận: ");
                            String emailNhan = sc.nextLine();
                            qlGui.guiEmail(emailNhan, noiDung);
                            break;
                        case 3:
                            System.out.print("Nhập số điện thoại người nhận: ");
                            String sdtNhan = sc.nextLine();
                            qlGui.guiSMS(sdtNhan, noiDung);
                            break;
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
    
    private void thongKeBaoCao() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              THỐNG KÊ BÁO CÁO HỆ THỐNG                   ║");
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
        
        System.out.println("║  Thống kê yêu cầu theo trạng thái:                       ║");
        System.out.printf("║    - Chờ duyệt:                           %15d  ║%n", choDuyet);
        System.out.printf("║    - Đã duyệt:                            %15d  ║%n", daDuyet);
        System.out.printf("║    - Đang thuê:                           %15d  ║%n", dangThue);
        System.out.printf("║    - Hoàn thành:                          %15d  ║%n", hoanThanh);
        System.out.printf("║    - Đã hủy:                              %15d  ║%n", huy);
        
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
        
        System.out.println("║  Thống kê hợp đồng theo trạng thái:                      ║");
        System.out.printf("║    - Mới tạo:                             %15d  ║%n", hdMoiTao);
        System.out.printf("║    - Đang hiệu lực:                       %15d  ║%n", hdHieuLuc);
        System.out.printf("║    - Hết hạn:                             %15d  ║%n", hdHetHan);
        System.out.printf("║    - Bị hủy:                              %15d  ║%n", hdHuy);
        
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
        
        System.out.println("║  Thống kê phim theo thể loại:                            ║");
        for (Map.Entry<String, Integer> entry : thongKeTheLoai.entrySet()) {
            System.out.printf("║    - %-35s: %15d  ║%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        
        // Top 3 phim có đánh giá cao nhất
        System.out.println("║  Top 3 phim đánh giá cao nhất:                           ║");
        List<Phim> danhSachPhimCopy = new ArrayList<>(danhSachPhim);
        danhSachPhimCopy.sort((p1, p2) -> Double.compare(p2.tinhDiemTrungBinh(), p1.tinhDiemTrungBinh()));
        
        for (int i = 0; i < Math.min(3, danhSachPhimCopy.size()); i++) {
            Phim p = danhSachPhimCopy.get(i);
            System.out.printf("║    %d. %-30s: %.1f⭐ (%d đánh giá)%n", 
                i + 1, p.getTen(), p.tinhDiemTrungBinh(), p.layDanhSachDanhGia().size());
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        // Menu phụ
        System.out.println("\n1. Xuất báo cáo theo ngày");
        System.out.println("2. Xuất báo cáo theo tháng");
        System.out.println("3. Xuất báo cáo chi tiết nhà phân phối");
        System.out.println("4. Xuất báo cáo chi tiết rạp chiếu");
        System.out.println("5. Quay lại");
        System.out.print("👉 Chọn (1-5): ");
        
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Nhập ngày (yyyy-MM-dd): ");
                    LocalDate ngay = LocalDate.parse(sc.nextLine());
                    xuatBaoCaoTheoNgay(ngay);
                    break;
                case 2:
                    System.out.print("Nhập tháng: ");
                    int thang = sc.nextInt();
                    System.out.print("Nhập năm: ");
                    int nam = sc.nextInt();
                    sc.nextLine();
                    xuatBaoCaoTheoThang(thang, nam);
                    break;
                case 3:
                    System.out.print("Nhập mã nhà phân phối: ");
                    String maNPP = sc.nextLine().toUpperCase();
                    for (NhaPhanPhoi npp : danhSachNhaPhanPhoi) {
                        if (npp.getMa().equals(maNPP)) {
                            System.out.println("\n📊 THỐNG KÊ CHI TIẾT NHÀ PHÂN PHỐI");
                            npp.hienThiThongTin();
                            
                            Map<String, Integer> tkNPP = npp.thongKeTheoLoai();
                            Map<String, Double> dtNPP = npp.thongKeDoanhThu();
                            
                            System.out.println("\nThống kê:");
                            for (Map.Entry<String, Integer> e : tkNPP.entrySet()) {
                                System.out.println("  " + e.getKey() + ": " + e.getValue());
                            }
                            for (Map.Entry<String, Double> e : dtNPP.entrySet()) {
                                System.out.println("  " + e.getKey() + ": " + String.format("%,.0f VND", e.getValue()));
                            }
                            
                            System.out.println("\n📜 Lịch sử giao dịch (5 gần nhất):");
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
                    System.out.print("Nhập mã rạp: ");
                    String maRap = sc.nextLine().toUpperCase();
                    for (RapChieuPhim rap : danhSachRap) {
                        if (rap.getMa().equals(maRap)) {
                            rap.xuatBaoCaoTongQuat();
                            
                            System.out.println("\n📜 Lịch sử hoạt động (5 gần nhất):");
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

// ==================== CLASS MAIN ====================
public class baithuchanh {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                       ║");
        System.out.println("║        🎬 HỆ THỐNG QUẢN LÝ CUNG CẤP PHIM CHO RẠP CHIẾU 🎬           ║");
        System.out.println("║                                                                       ║");
        System.out.println("║                    Version 2.0 - October 2025                        ║");
        System.out.println("║              Tích hợp đầy đủ Interface & OOP                         ║");
        System.out.println("║                                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n🎯 TÍNH NĂNG CHÍNH:");
        System.out.println("  ✅ Quản lý nhà phân phối với thông báo đa kênh");
        System.out.println("  ✅ Quản lý rạp chiếu với lịch sử & báo cáo");
        System.out.println("  ✅ Quản lý phim với đánh giá & tính toán");
        System.out.println("  ✅ Yêu cầu thuê phim với thanh toán đa phương thức");
        System.out.println("  ✅ Hợp đồng với tính thuế, chiết khấu");
        System.out.println("  ✅ Xác thực & phân quyền người dùng");
        System.out.println("  ✅ Tìm kiếm nâng cao");
        System.out.println("  ✅ Thống kê & báo cáo chi tiết");
        
        System.out.println("\n📝 DANH SÁCH INTERFACE ĐÃ ÁP DỤNG:");
        System.out.println("  • IThongBao: Gửi thông báo, email, SMS");
        System.out.println("  • IQuanLy: CRUD operations");
        System.out.println("  • IXuatBaoCao: Báo cáo theo ngày/tháng/tổng quát");
        System.out.println("  • ITimKiem: Tìm kiếm theo tên/mã/nâng cao");
        System.out.println("  • IXacThuc: Xác thực thông tin/tài khoản/quyền");
        System.out.println("  • ITinhToan: Tính tổng giá trị/chiết khấu/thuế");
        System.out.println("  • ILichSu: Quản lý lịch sử hoạt động");
        System.out.println("  • IThanhToan: Thanh toán/hoàn tiền");
        System.out.println("  • IDanhGia: Đánh giá & tính điểm trung bình");
        System.out.println("  • IThongKe: Thống kê theo loại/doanh thu/số lượng");
        
        System.out.println("\n🔐 THÔNG TIN ĐĂNG NHẬP MẪU:");
        System.out.println("  Tài khoản: ql001 | Mật khẩu: 123456 (Giám đốc)");
        System.out.println("  Tài khoản: ql002 | Mật khẩu: 123456 (Trưởng phòng)");
        System.out.println("  Tài khoản: ql003 | Mật khẩu: 123456 (Phó giám đốc)");
        
        System.out.println("\n⏳ Đang khởi động hệ thống...\n");
        
        try {
            Thread.sleep(1000);
            HeThongQuanLyCungCapPhim heThong = new HeThongQuanLyCungCapPhim();
            heThong.hienThiMenu();
        } catch (Exception e) {
            System.out.println("❌ Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
