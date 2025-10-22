package film;

import base.DinhDangChieu;

public class DinhDang4DX extends DinhDangChieu{
    private static final double PHI_4DX = 50000;
    
    public DinhDang4DX() {
        super("4DX", "Phim 4DX");
    }
    
    @Override
    public double tinhThuPhi() {
        return PHI_4DX;
    }


} 
