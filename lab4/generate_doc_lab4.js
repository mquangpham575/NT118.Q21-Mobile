const {
  Document, Packer, Paragraph, TextRun, Table, TableRow, TableCell,
  AlignmentType, HeadingLevel, BorderStyle, WidthType,
  PageNumber, Header, Footer, PageBreak
} = require('docx');
const fs = require('fs');
const path = require('path');

const border = { style: BorderStyle.SINGLE, size: 4, color: "CCCCCC" };
const thickBorder = { style: BorderStyle.SINGLE, size: 8, color: "2563EB" };

function heading1(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_1,
    spacing: { before: 320, after: 160 },
    children: [new TextRun({ text, bold: true, size: 28, color: "1E40AF", font: "Times New Roman" })]
  });
}

function heading2(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_2,
    spacing: { before: 240, after: 120 },
    children: [new TextRun({ text, bold: true, size: 26, color: "1D4ED8", font: "Times New Roman" })]
  });
}

function heading3(text) {
  return new Paragraph({
    spacing: { before: 200, after: 100 },
    children: [new TextRun({ text, bold: true, size: 24, color: "2563EB", font: "Times New Roman" })]
  });
}

function body(text, options = {}) {
  return new Paragraph({
    spacing: { before: 80, after: 80 },
    alignment: AlignmentType.JUSTIFIED,
    children: [new TextRun({ text, size: 24, font: "Times New Roman", ...options })]
  });
}

// Bán tự động tạo danh sách Bullet và Numbered để tránh lỗi XML Schema của docx library trên MS Word
function bullet(text, level = 0) {
  const indentSize = level === 0 ? 360 : 720;
  const bulletSymbol = level === 0 ? "•   " : "◦   ";
  return new Paragraph({
    indent: { left: indentSize },
    spacing: { before: 60, after: 60 },
    alignment: AlignmentType.JUSTIFIED,
    children: [
      new TextRun({ text: bulletSymbol, bold: true, size: 24, font: "Times New Roman", color: "1D4ED8" }),
      new TextRun({ text: text, size: 24, font: "Times New Roman" })
    ]
  });
}

function numbered(numStr, text) {
  return new Paragraph({
    indent: { left: 360 },
    spacing: { before: 60, after: 60 },
    alignment: AlignmentType.JUSTIFIED,
    children: [
      new TextRun({ text: numStr + "  ", bold: true, size: 24, font: "Times New Roman", color: "1D4ED8" }),
      new TextRun({ text: text, size: 24, font: "Times New Roman" })
    ]
  });
}

function codeBlock(text) {
  return new Paragraph({
    spacing: { before: 80, after: 80 },
    shading: { fill: "F8FAFC" },
    border: {
      left: { style: BorderStyle.SINGLE, size: 8, color: "3B82F6", space: 4 }
    },
    indent: { left: 360 },
    children: [new TextRun({ text, size: 20, font: "Courier New", color: "1E293B" })]
  });
}

function emptyLine() {
  return new Paragraph({ spacing: { before: 120, after: 120 } });
}

function sectionDivider(title) {
  return new Paragraph({
    spacing: { before: 280, after: 160 },
    border: { bottom: { style: BorderStyle.SINGLE, size: 6, color: "3B82F6", space: 2 } },
    children: [new TextRun({ text: title, bold: true, size: 28, color: "1E40AF", font: "Times New Roman" })]
  });
}

const doc = new Document({
  styles: {
    default: { document: { run: { font: "Times New Roman", size: 24 } } },
    paragraphStyles: [
      { id: "Heading1", name: "Heading 1", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: 28, bold: true, font: "Times New Roman", color: "1E40AF" },
        paragraph: { spacing: { before: 320, after: 160 }, outlineLevel: 0 } },
      { id: "Heading2", name: "Heading 2", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: 26, bold: true, font: "Times New Roman", color: "1D4ED8" },
        paragraph: { spacing: { before: 240, after: 120 }, outlineLevel: 1 } },
    ]
  },
  sections: [{
    properties: {
      page: {
        size: { width: 11906, height: 16838 },
        margin: { top: 1440, right: 1134, bottom: 1440, left: 1701 }
      }
    },
    headers: {
      default: new Header({
        children: [new Paragraph({
          alignment: AlignmentType.RIGHT,
          border: { bottom: { style: BorderStyle.SINGLE, size: 4, color: "3B82F6", space: 4 } },
          children: [new TextRun({ text: "Báo cáo Bài thực hành - Lập trình Mobile | Lab 4: View Animation", size: 18, color: "6B7280", font: "Times New Roman", italics: true })]
        })]
      })
    },
    footers: {
      default: new Footer({
        children: [new Paragraph({
          alignment: AlignmentType.CENTER,
          border: { top: { style: BorderStyle.SINGLE, size: 4, color: "CCCCCC", space: 4 } },
          children: [
            new TextRun({ text: "Trang ", size: 18, color: "9CA3AF", font: "Times New Roman" }),
            new TextRun({
              children: [PageNumber.CURRENT],
              size: 18,
              color: "9CA3AF",
              font: "Times New Roman"
            })
          ]
        })]
      })
    },
    children: [
      // COVER PAGE
      emptyLine(), emptyLine(),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 400, after: 200 },
        children: [new TextRun({ text: "TRƯỜNG ĐẠI HỌC CÔNG NGHỆ THÔNG TIN", bold: true, size: 26, font: "Times New Roman", color: "374151" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 0, after: 600 },
        children: [new TextRun({ text: "KHOA CÔNG NGHỆ PHẦN MỀM", bold: true, size: 26, font: "Times New Roman", color: "374151" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 200, after: 200 },
        border: { top: { style: BorderStyle.DOUBLE, size: 6, color: "2563EB" }, bottom: { style: BorderStyle.DOUBLE, size: 6, color: "2563EB" } },
        children: [new TextRun({ text: "BÁO CÁO BÀI THỰC HÀNH", bold: true, size: 40, font: "Times New Roman", color: "1E3A8A" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 200, after: 600 },
        children: [new TextRun({ text: "LẬP TRÌNH ỨNG DỤNG TRÊN THIẾT BỊ DI ĐỘNG", bold: true, size: 30, font: "Times New Roman", color: "2563EB" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 100, after: 100 },
        children: [new TextRun({ text: "BÀI THỰC HÀNH SỐ 4:", bold: true, size: 32, font: "Times New Roman", color: "1E40AF" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 100, after: 800 },
        children: [new TextRun({ text: "VIEW ANIMATION TRÊN HỆ ĐIỀU HÀNH ANDROID", bold: true, size: 28, font: "Times New Roman", color: "1E40AF" })]
      }),
      emptyLine(),
      new Table({
        width: { size: 7000, type: WidthType.DXA },
        columnWidths: [2600, 4400],
        borders: {
          top: thickBorder,
          bottom: thickBorder,
          left: thickBorder,
          right: thickBorder,
          insideHorizontal: thickBorder,
          insideVertical: thickBorder
        },
        rows: [
          ["Môn học", "Lập trình ứng dụng trên thiết bị di động"],
          ["Lớp", "NT118.Q21-Mobile"],
          ["Sinh viên thực hiện", "[Họ và tên học viên]"],
          ["MSSV", "[MSSV]"],
          ["Giáo viên hướng dẫn", "[Tên GVHD]"],
          ["Ngày nộp", ""],
        ].map(([label, value]) => new TableRow({
          children: [
            new TableCell({
              width: { size: 2600, type: WidthType.DXA },
              shading: { fill: "DBEAFE" },
              margins: { top: 100, bottom: 100, left: 160, right: 160 },
              children: [new Paragraph({ children: [new TextRun({ text: label, bold: true, size: 22, font: "Times New Roman" })] })]
            }),
            new TableCell({
              width: { size: 4400, type: WidthType.DXA },
              margins: { top: 100, bottom: 100, left: 160, right: 160 },
              children: [new Paragraph({ children: [new TextRun({ text: value, size: 22, font: "Times New Roman" })] })]
            })
          ]
        }))
      }),
      emptyLine(), emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // SECTION I
      sectionDivider("I. MỤC TIÊU BÀI THỰC HÀNH"),
      emptyLine(),

      heading2("1. Về kiến thức"),
      bullet("Tìm hiểu cơ chế hoạt động của hệ thống hoạt ảnh View Animation trong hệ điều hành Android."),
      bullet("Phân biệt sự khác nhau, ưu và nhược điểm của hai cách khai báo hoạt ảnh:"),
      bullet("Sử dụng tệp cấu hình tài nguyên tĩnh XML (XML-based)", 1),
      bullet("Lập trình động bằng mã nguồn Java (Code-based)", 1),
      bullet("Hiểu rõ nguyên lý hoạt động của các dạng biến đổi hình học cơ bản bao gồm: Alpha (độ mờ), Scale (kích thước), Translate (dịch chuyển tọa độ), Rotate (góc xoay) và sự kết hợp giữa chúng (AnimationSet)."),
      bullet("Nắm vững khái niệm về hệ số gia tốc Interpolator (như Bounce, Accelerate, Decelerate) giúp kiểm soát vận tốc chuyển động theo thời gian vật lý thực tế."),

      emptyLine(),
      heading2("2. Về kỹ năng"),
      bullet("Xây dựng và tối ưu hóa các tệp tin cấu hình hoạt ảnh XML đặt trong thư mục tài nguyên chuyên biệt res/anim/."),
      bullet("Lập trình điều khiển hoạt ảnh bằng các lớp hướng đối tượng trong thư viện Android (AlphaAnimation, ScaleAnimation, TranslateAnimation, RotateAnimation, AnimationSet)."),
      bullet("Tùy biến hiệu ứng chuyển cảnh đồ họa (Activity Transition) khi điều hướng qua lại giữa các Activity thông qua hàm hệ thống overridePendingTransition()."),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // SECTION II
      sectionDivider("II. HIỆN THỰC VÀ NGUYÊN LÝ HOẠT ĐỘNG"),
      emptyLine(),

      // BAI 1
      heading2("Bài 1: Khai báo hiệu ứng chuyển động bằng XML"),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Khảo sát phương pháp xây dựng hoạt ảnh tĩnh thông qua các tệp tin cấu hình XML trong thư mục res/anim/, rèn luyện cách cấu hình các thuộc tính thời gian (duration), chu kỳ lặp (repeatCount), trạng thái lưu giữ (fillAfter) và hệ số gia tốc Interpolator."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Định nghĩa 10 tệp tin XML hoạt ảnh tương ứng trong thư mục res/anim/ (anim_fade_in.xml đến anim_combine.xml) sử dụng các thẻ đặc trưng: <alpha>, <scale>, <translate>, <rotate> và <set>."),
      numbered("2.", "Trong MainActivity.java, nạp động hiệu ứng hoạt ảnh từ tài nguyên bằng lớp hỗ trợ:"),
      codeBlock("Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_fade_in);"),
      numbered("3.", "Kích hoạt hoạt ảnh lên View đích (biểu tượng Logo trường) bằng cách gọi lệnh:"),
      codeBlock("ivUitLogo.startAnimation(animation);"),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      bullet("Khi ứng dụng kích hoạt sự kiện, hệ thống Android tiến hành phân tích cú pháp (parse) tệp tin XML tương ứng trong bộ nhớ, tính toán các ma trận dịch chuyển đồ họa theo thời gian được thiết lập bởi thuộc tính duration."),
      bullet("Hệ số gia tốc Interpolator sẽ điều phối vận tốc hiển thị khung hình để tạo ra hiệu ứng tự nhiên (như gia tốc nhanh dần, chậm dần hay hiệu ứng nảy vật lý)."),

      emptyLine(),
      heading3("d) Kết quả thực nghiệm"),
      body("(Mô tả kết quả chạy ứng dụng tại đây: chụp ảnh màn hình hoặc mô tả kết quả quan sát được trong quá trình thực hành.)", { italics: true, color: "6B7280" }),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // BAI 2
      heading2("Bài 2: Khai báo hiệu ứng chuyển động bằng code Java"),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Lập trình xây dựng toàn bộ 10 hiệu ứng hoạt ảnh hoàn toàn bằng mã nguồn Java không phụ thuộc vào tài nguyên XML, nắm vững cách sử dụng các lớp hướng đối tượng trong gói android.view.animation."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Khởi tạo trực tiếp các lớp đối tượng chuyển động tương đương trong Java code:"),
      bullet("Hiệu ứng mờ/tỏ: Khởi tạo lớp AlphaAnimation(float fromAlpha, float toAlpha)", 1),
      bullet("Hiệu ứng phóng to/thu nhỏ: Khởi tạo lớp ScaleAnimation đi kèm các điểm mốc tỷ lệ và tâm xoay", 1),
      bullet("Hiệu ứng xoay: Khởi tạo lớp RotateAnimation thiết lập góc bắt đầu, góc kết thúc và tâm xoay hình học", 1),
      bullet("Hiệu ứng dịch chuyển: Khởi tạo lớp TranslateAnimation xác định tọa độ xuất phát và điểm đích", 1),
      bullet("Hiệu ứng kết hợp: Khởi tạo lớp gộp AnimationSet(boolean shareInterpolator) và đẩy các đối tượng chuyển động đơn lẻ vào thông qua hàm addAnimation()", 1),
      numbered("2.", "Thiết lập các thuộc tính vật lý tương đương bằng các hàm lập trình:"),
      codeBlock("codeAnimation.setDuration(1000);"),
      codeBlock("codeAnimation.setRepeatCount(1);"),
      codeBlock("codeAnimation.setFillAfter(true);"),
      numbered("3.", "Kích hoạt hoạt ảnh lên View bằng lệnh: ivUitLogo.startAnimation(codeAnimation);"),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      bullet("Toàn bộ quá trình tính toán hình học được thực hiện động trong bộ nhớ RAM ngay khi sự kiện click xảy ra mà không thông qua bước phân tích XML."),
      bullet("Các ma trận biến đổi 2D (Affine Transformation Matrix) được tính toán thời gian thực và áp dụng trực tiếp lên Canvas đồ họa của đối tượng View để dựng hình để tối ưu hóa khả năng vẽ."),

      emptyLine(),
      heading3("d) So sánh khai báo hoạt ảnh bằng XML và Code Java"),
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [2400, 3480, 3480],
        borders: {
          top: border,
          bottom: border,
          left: border,
          right: border,
          insideHorizontal: border,
          insideVertical: border
        },
        rows: [
          new TableRow({
            children: [
              new TableCell({
                width: { size: 2400, type: WidthType.DXA },
                shading: { fill: "1E40AF" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: "Tiêu chí", bold: true, size: 22, font: "Times New Roman", color: "FFFFFF" })] })]
              }),
              new TableCell({
                width: { size: 3480, type: WidthType.DXA },
                shading: { fill: "1E40AF" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: "Khai báo bằng XML (Tĩnh)", bold: true, size: 22, font: "Times New Roman", color: "FFFFFF" })] })]
              }),
              new TableCell({
                width: { size: 3480, type: WidthType.DXA },
                shading: { fill: "1E40AF" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: "Khai báo bằng Code Java (Động)", bold: true, size: 22, font: "Times New Roman", color: "FFFFFF" })] })]
              }),
            ]
          }),
          ...([
            ["Vòng đời & Quản lý", "Dễ quản lý, tách biệt hoàn toàn giữa giao diện và mã logic của ứng dụng.", "Khó quản lý khi số lượng hiệu ứng tăng lên do trộn lẫn với code xử lý logic."],
            ["Hiệu năng phân tích", "Tốn tài nguyên lúc khởi đầu để phân tích cú pháp tệp XML từ đĩa cứng.", "Tốc độ xử lý tức thì do khởi tạo trực tiếp các đối tượng đồ họa trong bộ nhớ RAM."],
            ["Khả năng tái sử dụng", "Dễ dàng tái sử dụng trên nhiều View khác nhau bằng cách gọi lại ID tài nguyên.", "Khó tái sử dụng hơn, phải viết helper class hoặc viết lại các khối mã khởi tạo."],
            ["Tính tùy biến động", "Bị giới hạn, không thể thay đổi thông số động dựa trên tương tác thời gian thực.", "Linh hoạt tuyệt đối, dễ dàng cập nhật thông số chuyển động dựa vào dữ liệu tính toán."],
            ["Tính trực quan", "Trực quan tốt, có thể xem trước hoạt ảnh qua hệ thống dựng hình của Android Studio.", "Không trực quan, bắt buộc phải chạy chương trình lên thiết bị thật để kiểm thử."],
          ].map(([a, b, c], i) => new TableRow({
            children: [
              new TableCell({
                width: { size: 2400, type: WidthType.DXA },
                shading: { fill: i % 2 === 0 ? "EFF6FF" : "FFFFFF" },
                margins: { top: 80, bottom: 80, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: a, bold: true, size: 20, font: "Times New Roman" })] })]
              }),
              new TableCell({
                width: { size: 3480, type: WidthType.DXA },
                shading: { fill: i % 2 === 0 ? "EFF6FF" : "FFFFFF" },
                margins: { top: 80, bottom: 80, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: b, size: 20, font: "Times New Roman" })] })]
              }),
              new TableCell({
                width: { size: 3480, type: WidthType.DXA },
                shading: { fill: i % 2 === 0 ? "EFF6FF" : "FFFFFF" },
                margins: { top: 80, bottom: 80, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: c, size: 20, font: "Times New Roman" })] })]
              }),
            ]
          })))
        ]
      }),

      emptyLine(),
      heading3("e) Kết quả thực nghiệm"),
      body("(Mô tả kết quả chạy ứng dụng tại đây: chụp ảnh màn hình hoặc mô tả kết quả quan sát được trong quá trình thực hành.)", { italics: true, color: "6B7280" }),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // BAI 3
      heading2("Bài 3: Hiệu ứng chuyển đổi (Transition) giữa hai Activity"),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Nắm vững cách xây dựng hiệu ứng chuyển dịch màn hình (Activity Transition) tùy biến, thay thế các hiệu ứng chuyển đổi mặc định nhàm chán của hệ điều hành."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Thiết kế 4 tệp tin XML dịch chuyển trong thư mục res/anim/: slide_in_right.xml, slide_out_left.xml (phục vụ chiều mở) và slide_in_left.xml, slide_out_right.xml (phục vụ chiều đóng/trở lại)."),
      numbered("2.", "Tại MainActivity.java (Chiều đi vào): Ngay sau lệnh khởi chạy Activity mới, gọi hàm ghi đè hiệu ứng chuyển cảnh của hệ thống:"),
      codeBlock("Intent intent = new Intent(MainActivity.this, NewActivity.class);"),
      codeBlock("startActivity(intent);"),
      codeBlock("overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);"),
      numbered("3.", "Tại NewActivity.java (Chiều quay về): Ghi đè phương thức đóng Activity finish() để thiết lập hiệu ứng dịch chuyển ngược lại:"),
      codeBlock("@Override"),
      codeBlock("public void finish() {"),
      codeBlock("    super.finish();"),
      codeBlock("    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);"),
      codeBlock("}"),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      bullet("Khi có tín hiệu chuyển đổi màn hình, hệ điều hành Android tạm thời đóng băng hiển thị của cả hai Activity để chuẩn bị áp dụng hiệu ứng hoạt họa."),
      bullet("Hệ thống áp dụng song song hiệu ứng trượt đi (slide_out) cho Activity cũ hiện tại và hiệu ứng trượt vào (slide_in) cho Activity mới kế tiếp trong cùng một khung thời gian thiết lập."),
      bullet("Điều này tạo ra cảm giác thị giác trơn tru, màn hình mới đẩy màn hình cũ từ phải sang trái khi mở và quay ngược lại trượt từ trái sang phải khi đóng."),

      emptyLine(),
      heading3("d) Kết quả thực nghiệm"),
      body("(Mô tả kết quả chạy ứng dụng tại đây: chụp ảnh màn hình hoặc mô tả kết quả quan sát được trong quá trình thực hành.)", { italics: true, color: "6B7280" }),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // SECTION III - Tong ket
      sectionDivider("III. NHẬN XÉT VÀ KẾT LUẬN"),
      emptyLine(),

      heading2("1. Điểm nổi bật đã học được"),
      bullet("View Animation đóng vai trò cốt lõi giúp giao diện người dùng trở nên trực quan, sinh động và tăng cường trải nghiệm tương tác đồ họa."),
      bullet("Hiểu rõ cơ sở hình học đằng sau các phép chuyển đổi đồ họa (Alpha, Scale, Translate, Rotate) và cách thiết lập hệ số gia tốc Interpolator tối ưu."),
      bullet("Sử dụng linh hoạt và kết hợp nhuần nhuyễn cả hai phương pháp XML và Java Code để giải quyết tối ưu các yêu cầu thực tế trong phát triển ứng dụng di động."),
      bullet("Kỹ năng tùy biến hiệu ứng chuyển màn hình Activity Transition giúp sản phẩm di động đạt độ hoàn thiện cao, mượt mà và chuyên nghiệp."),

      emptyLine(),
      heading2("2. Khó khăn gặp phải"),
      body("(Sinh viên điền vào những khó khăn gặp phải trong quá trình thực hành và cách giải quyết)", { italics: true, color: "6B7280" }),

      emptyLine(),
      heading2("3. Đề xuất cải tiến"),
      body("(Sinh viên đề xuất các hướng phát triển, cải tiến cho bài thực hành)", { italics: true, color: "6B7280" }),

      emptyLine(),
    ]
  }]
});

const outputPath = path.join(__dirname, 'BaoCao_Lab4_Animation.docx');

Packer.toBuffer(doc).then(buffer => {
  fs.writeFileSync(outputPath, buffer);
  console.log("SUCCESS: Word Document generated at " + outputPath);
}).catch(err => {
  console.error("ERROR generating Word Document: ", err);
});
