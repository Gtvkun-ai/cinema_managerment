package film;

public class DoanhThuPhim {
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
            throw new IllegalArgumentException("So tien khong duoc am");
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
            throw new IllegalArgumentException("So ghe phai tu 1 den 500");
        }
        return sg;
    }
    
    private String validLoaiPhong(String lp) {
        if (lp == null || lp.trim().isEmpty()) {
            throw new IllegalArgumentException("Loai phong khong duoc de trong");
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

