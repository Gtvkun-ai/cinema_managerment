public enum TrangThaiYeuCau {
    CHO_DUYET("Cho duyet"),
    DA_DUYET("Da duyet"),
    DANG_THUE("Dang thue"),
    HOAN_THANH("Hoan thanh"),
    HUY("Huy");

    private String moTa;
    TrangThaiYeuCau(String moTa){
        this.moTa = moTa;
    }
    public String getMoTa() {
        return moTa;
    }

}
