package base;

public abstract class DinhDangChieu {
    protected String maDinhDang;
    private String tenDinhDang;
    
    public DinhDangChieu(String ma, String ten) {
        this.maDinhDang = ma;
        this.tenDinhDang = ten;
    }
    
    public abstract double tinhThuPhi();
    public String getTenDinhDang() { return tenDinhDang; }

}
