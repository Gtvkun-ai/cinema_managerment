import java.util.List;
import java.util.Map;

interface ITimKiem<T> {
    List<T> timKiemTheoTen(String ten);
    List<T> timKiemTheoMa(String ma);
    List<T> timKiemNangCao(Map<String, Object> tieuChi);
}
//list là danh sách các đối tượng trả về với kiểu T 
