import java.util.List;

//quản lý cruid
//cruid là đểm tạo, đọc, cập nhật, xóa
//T là kiểu dữ liệu tổng quát
//ví dụ: T có thể là lớp Phim, Lịch Chiếu, Vé, Ghế, v.v.
interface IQuanLy<T> {
    void them(T item);
    void capNhat(T item);
    void xoa(String ma);
    T timTheoMa(String ma);
    List<T> layDanhSach();
}
