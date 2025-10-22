package base;

import java.time.LocalDateTime;

// ==================== ABSTRACT CLASS - LỚP TRỪU TƯỢNG ====================
public abstract class ThucThe {
    protected String ma;
    protected String ten; //dùng protected để các lớp con có thể truy cập
    protected LocalDateTime ngayTao;
    protected LocalDateTime ngayCapNhat;

    public ThucThe(String ma, String ten){
        if(ma == null || ma.trim().isEmpty()){
            // .trim() loại bỏ khoảng trắng đầu và cuối chuỗi

            throw new IllegalArgumentException("Ma khong duoc de trong");
        }
        if(ten == null || ten.trim().isEmpty()){
            throw new IllegalArgumentException("Ten khong duoc de trong");
            //throw new là cách ném ra một ngoại lệ trong Java
            // Ngoại lệ này sẽ được bắt bởi khối catch trong phương thức gọi
            // hoặc sẽ được truyền lên cao hơn trong chuỗi gọi phương thức
            //IllegalArgumentException là một loại ngoại lệ được sử dụng để chỉ ra rằng một phương thức đã nhận được một đối số không hợp lệ
            // Trong trường hợp này, nếu mã hoặc tên không hợp lệ (null hoặc chuỗi rỗng), ngoại lệ sẽ được ném ra để thông báo lỗi
        }
        this.ma = ma.trim().toUpperCase();
        this.ten = ten.trim();
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
    }
    public String getMa() {
        return ma;
    }
    public String getTen() {
        return ten;
    }
    public LocalDateTime getNgayTao() {
        return ngayTao;
    }
    protected void capNhatThoiGian(){
        this.ngayCapNhat = LocalDateTime.now();
        //phương thức này được sử dụng để cập nhật thời gian hiện tại cho thuộc tính ngayCapNhat
    }

    public abstract void hienThiThongTin();
    public abstract boolean kiemTraHopLe();
}
