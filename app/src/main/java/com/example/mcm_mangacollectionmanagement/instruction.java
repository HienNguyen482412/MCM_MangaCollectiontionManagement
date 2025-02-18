package com.example.mcm_mangacollectionmanagement;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class instruction extends BaseActivity {

    TextView txtInstroduce, txtFunction, txtInstruction, txtContent1, txtContent2, txtContent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_instruction, findViewById(R.id.frameContent));
        txtInstroduce = findViewById(R.id.txtInstroduce);
        txtFunction = findViewById(R.id.txtFunction);
        txtInstruction = findViewById(R.id.txtInstruction);
        txtContent1 = findViewById(R.id.txtContent1);
        txtContent2 = findViewById(R.id.txtContent2);
        txtContent3 = findViewById(R.id.txtContent3);
        txtContent2.setText("- Quản lý thông tin nhà xuất bản: có thể thêm, sửa, xóa và tìm kiếm thông tin nhà xuất bản của các bộ truyện bạn đang sở hữu, cung cấp thống kê số lượng truyện của các nhà xuất bản đang có trong bộ sưu tập.\n" +
                "- Quản lý thông tin tác giả: có thể thêm, sửa, xóa và tìm kiếm thông tin tác giả của các bộ truyện.\n" +
                "- Quản lý bộ truyện: có thể thêm, sửa, xóa và tìm kiếm thông tin các bộ truyện, ngoài ra cho phép thêm ảnh bìa đại diện cho bộ truyện.\n" +
                "- Quản lý các tập truyện: có thể thêm, sửa, xóa và tìm kiếm thông tin tập truyện theo phân loại và tập (có thể là tên, số hoặc cả tên và số).\n" +
                "- Thống kê: xem thống kê giá trị của bộ sưu tập, tình trạng và số lượng truyện theo nhà xuất bản, hiển thị danh sách các tập truyện trong bộ sưu tập theo giá tiền (từ đắt đến rẻ).\n" +
                "- Báo cáo và phản hồi: bạn có thể báo cáo lỗi gặp được khi sử dụng ứng dụng và đánh giá về ứng dụng, nhà phát triển luôn sẵn sàng lắng nghe ý kiến của bạn\n" +
                "- Thông tin người dùng: bạn có thể đăng kí, tạo tài khoản hoặc chọn quên mật khẩu để thiết lập lại tài khoản, ngoài ra có thể thêm ảnh đại diện và ảnh nền nếu muốn.");
        txtContent3.setText("1. Khi truy cập ứng dụng lần đẩu, cần đăng kí tài khoản (lưu ý: thông tin gmail phải chính xác). Sau khi truy thực hiện đăng kí tài khoản thành công tiến hành đăng nhập bằng tài khoản đã đăng kí trước đó. Trong trường hợp, người dùng quên mật khẩu, hãy thực hiện thiết lập lại tài khoản bằng cách chọn \"Quên mật khẩu\", sau khi nhập các thông tin yêu cầu người dùng chọn gửi mã xác nhận, mã xác nhận sẽ được gửi tới gmail đã đăng kí trước đó.\n" +
                "2. Sau khi người dùng đăng nhập thành công, có thể đổi tên đăng nhập, mật khẩu, hình đại diện hoặc hình nền nếu muốn.\n" +
                "3. Thực hiện thêm, sửa và xóa các thông tin cần thiết. Người dùng có thể chọn vào khung thông tin trong danh sách thông tin để sửa hoặc xóa. Có thể thêm mới bằng cách chọn dấu (+) ở danh sách hoặc chọn làm mới trong phần thông tin chi tiết để nhập mới.\n" +
                "4. Đối với thông tin bộ truyện, nếu người dùng nhấn một lần sẽ hiển thị danh sách các tập truyện trong bộ truyện đó. Để sửa thông tin bộ truyện cần nhấn giữ vào khung bộ truyện cần sửa khi đó sẽ hiển thị thông tin chi tiết bộ truyện.\n" +
                "5. Trong tìm kiếm, người dùng có thể làm mới danh sách bằng cách không nhập gì cả và chọn tìm kiếm. Tuy nhiên đối với tìm kiếm bộ truyện, làm mới danh sách bằng các nhấn giứ biểu tượng tìm kiếm.\n" +
                "6. Thêm ảnh bìa đại diện cho bộ truyện bằng cách nhấn vào hình ảnh bìa, khi đó hiển thị thư viện ảnh của bạn để chọn ảnh.\n" +
                "7. Khi báo lỗi hoặc đánh giá sản phẩm, người dùng chọn gửi và khi đó sẽ hiển thị ứng dụng gmail trên điện thoại, người dùng chỉ cần chọn gửi và đánh giá hoặc nhận xét sẽ được gửi đi.\n" +
                "8. Khi người dùng muốn xóa toàn bộ thông tin của ứng dụng, cần nhập mật khẩu và xác nhận xóa. Lưu ý: sau khi xóa người dùng sẽ tạm thời thoát ứng dụng đột ngột\n" +
                "9. Tại phần thông kê người dùng có thể chọn ngày và chọn các tiêu chí thì biểu đồ sẽ thể hiện tương tự. Tuy nhiên, khi người dùng chọn một tiêu chí và thay đổi ngày, thì biểu đồ sẽ không thay đổi ngay, người dùng cần chọn tiêu chí khác và quay trở lại tiêu chí ban đầu khi đó biểu đồ sẽ được cập nhật");
        txtInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtContent3.getVisibility() == View.GONE){
                    txtContent3.setVisibility(View.VISIBLE);
                }
                else{
                    txtContent3.setVisibility(View.GONE);
                }
            }
        });
        txtFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtContent2.getVisibility() == View.GONE){
                    txtContent2.setVisibility(View.VISIBLE);
                }
                else{
                    txtContent2.setVisibility(View.GONE);
                }
            }
        });
        txtInstroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtContent1.getVisibility() == View.GONE){
                    txtContent1.setVisibility(View.VISIBLE);
                }
                else{
                    txtContent1.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}