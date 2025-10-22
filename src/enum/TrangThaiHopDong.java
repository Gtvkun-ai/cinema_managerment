public enum TrangThaiHopDong {
    MOI_TAO("Moi tao"),
    DANG_HIEU_LUC("Dang hieu luc"),
    HET_HAN("Het han"),
    BI_HUY("Bi huy");
    
    private String moTa;
    TrangThaiHopDong(String moTa){
        this.moTa = moTa;
    }
    public String getMoTa() {
        return moTa;
    }
}
