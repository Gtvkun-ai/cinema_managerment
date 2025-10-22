
public enum PhuongThucThanhToan {
    TIEN_MAT("Tien mat"),
    CHUYEN_KHOAN("Chuyen khoan"),
    THE_TIN_DUNG("The tin dung"),
    VI_DIEN_TU("Vi dien tu");
    
    private String moTa;
    PhuongThucThanhToan(String moTa) { this.moTa = moTa; }
    public String getMoTa() { return moTa; }

}
