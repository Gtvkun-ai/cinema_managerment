package film;

import base.DinhDangChieu;

public class DinhDang3D extends DinhDangChieu{
    private static final double PHI_3D = 20000;
    
    public DinhDang3D() {
        super("3D", "Phim 3D");
    }
    
    @Override
    public double tinhThuPhi() {
        return PHI_3D;
    }

} 
