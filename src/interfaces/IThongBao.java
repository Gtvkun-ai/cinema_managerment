interface IThongBao {
    void guiThongBao(String message);
    void guiEmail(String email, String message);
    void guiSMS(String soDienThoai, String message);
}

//mặc định interface trong java là public abstract