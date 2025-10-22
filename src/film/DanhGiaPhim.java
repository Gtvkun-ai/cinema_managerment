package film;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
