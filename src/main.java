public class main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                       ║");
        System.out.println("║        🎬 HE THONG QUAN LY CUNG CAP PHIM CHO RAP CHIEU 🎬            ║");
        System.out.println("║                                                                       ║");
        System.out.println("║                    Nhom 9 __ luc 10h30 31/10/2025                    ║");
        System.out.println("║              Thanh vien:  YenHoa ____ HanDat                         ║");
        System.out.println("║                                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n🎯 TINH NANG CHINH:");
        System.out.println("  ✅ Quan ly nha phan phoi voi thong bao da kenh");
        System.out.println("  ✅ Quan ly rap chieu voi lich su & bao cao");
        System.out.println("  ✅ Quan ly phim voi danh gia & tinh toan");
        System.out.println("  ✅ Yeu cau thue phim voi thanh toan da phuong thuc");
        System.out.println("  ✅ Hop dong voi tinh thue, chiet khau");
        System.out.println("  ✅ Xac thuc & phan quyen nguoi dung");
        System.out.println("  ✅ Tim kiem nang cao");
        System.out.println("  ✅ Thong ke & bao cao chi tiet");
                
        System.out.println("\n🔐 THONG TIN DANG NHAP MAU:");
        System.out.println("  Tai khoan: ql001 | Mat khau: 123456 (Giam doc)");
        System.out.println("  Tai khoan: ql002 | Mat khau: 123456 (Truong phong)");
        System.out.println("  Tai khoan: ql003 | Mat khau: 123456 (Pho giam doc)");

        System.out.println("\n⏳ Dang khoi dong he thong...\n");
        
        try {
            Thread.sleep(1000);
            HeThongQuanLyCungCapPhim heThong = new HeThongQuanLyCungCapPhim();
            heThong.hienThiMenu();
        } catch (Exception e) {
            System.out.println("❌ Loi he thong: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
