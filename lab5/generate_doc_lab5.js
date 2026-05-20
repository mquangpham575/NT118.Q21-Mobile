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
          children: [new TextRun({ text: "Báo cáo Bài thực hành - Lập trình Mobile | Lab 5: Broadcast Receiver", size: 18, color: "6B7280", font: "Times New Roman", italics: true })]
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
        children: [new TextRun({ text: "BÀI THỰC HÀNH SỐ 5:", bold: true, size: 32, font: "Times New Roman", color: "1E40AF" })]
      }),
      new Paragraph({
        alignment: AlignmentType.CENTER, spacing: { before: 100, after: 800 },
        children: [new TextRun({ text: "BROADCAST INTENT & BROADCAST RECEIVER TRONG ANDROID", bold: true, size: 28, font: "Times New Roman", color: "1E40AF" })]
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
      bullet("Tìm hiểu mô hình thiết kế Publisher-Subscriber (Xuất bản - Đăng ký) được hiện thực thông qua Broadcast Intent và Broadcast Receiver trong hệ điều hành Android."),
      bullet("Phân biệt sự khác nhau, ưu và nhược điểm của hai phương pháp đăng ký Broadcast Receiver:"),
      bullet("Đăng ký tĩnh (Static Registration trong AndroidManifest.xml)", 1),
      bullet("Đăng ký động (Dynamic Registration thông qua mã nguồn Java)", 1),
      bullet("Nắm vững các nguyên tắc bảo mật và hạn chế chạy nền (Background Limits) trên các phiên bản Android hiện đại (từ Android 8.0 trở lên)."),

      emptyLine(),
      heading2("2. Về kỹ năng"),
      bullet("Khai báo và cấu hình thành công các quyền hệ thống nguy hiểm (Dangerous Permissions) liên quan đến việc tiếp nhận và gửi tin nhắn SMS trên thiết bị di động."),
      bullet("Lập trình giải mã mảng dữ liệu PDU (Protocol Description Unit) để trích xuất nội dung tin nhắn và số điện thoại gửi đến."),
      bullet("Lập trình tương tác phần cứng và giao tiếp mạng viễn thông thông qua lớp hỗ trợ SmsManager."),
      bullet("Điều phối luồng Activity chạy từ nền (Background context) lên màn hình người dùng bằng cách áp dụng hệ thống cờ định tuyến Intent (FLAG_ACTIVITY_NEW_TASK và FLAG_ACTIVITY_CLEAR_TOP)."),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // SECTION II
      sectionDivider("II. HIỆN THỰC VÀ NGUYÊN LÝ HOẠT ĐỘNG"),
      emptyLine(),

      // BAI 1
      heading2("Bài 1: Đăng ký BroadcastReceiver bằng code để nhận tin nhắn SMS"),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Khảo sát phương pháp đăng ký động trong mã nguồn, nắm vững cơ chế vòng đời của Activity khi tích hợp bộ lọc Intent, giải mã mảng dữ liệu PDU để hiển thị thông tin SMS lên giao diện đồ họa động."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Khai báo quyền nhận tin nhắn trong tệp tin AndroidManifest.xml:"),
      codeBlock("<uses-permission android:name=\"android.permission.RECEIVE_SMS\" />"),
      numbered("2.", "Trong MainActivity.java, khai báo đối tượng BroadcastReceiver và bộ lọc IntentFilter lắng nghe hành động android.provider.Telephony.SMS_RECEIVED."),
      numbered("3.", "Triển khai đăng ký bộ nhận tin nhắn trong phương thức vòng đời onResume() và thực hiện hủy đăng ký bằng hàm unregisterReceiver() trong phương thức onStop() để tránh rò rỉ tài nguyên hệ thống."),

      emptyLine(),
      body("Đoạn mã tham khảo:", { bold: true }),
      codeBlock("// Đăng ký trong onResume()"),
      codeBlock("intentFilter = new IntentFilter(\"android.provider.Telephony.SMS_RECEIVED\");"),
      codeBlock("registerReceiver(smsReceiver, intentFilter);"),
      emptyLine(),
      codeBlock("// Hủy đăng ký trong onStop()"),
      codeBlock("unregisterReceiver(smsReceiver);"),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      bullet("Khi ứng dụng đang hiển thị trên màn hình và có tin nhắn SMS đi vào thiết bị, hệ thống Android phát ra Broadcast Intent chứa dữ liệu tin nhắn dưới dạng mảng byte (pdus)."),
      bullet("Bộ thu nhận tín hiệu, giải mã dữ liệu thông qua đối tượng SmsMessage, sau đó trích xuất thông tin số điện thoại gửi và nội dung tin nhắn để cập nhật trực tiếp lên thành phần TextView của giao diện."),
      bullet("Do được đăng ký động gắn liền với vòng đời Activity: khi ứng dụng bị tắt hoàn toàn (Stop/Destroy), tiến trình đăng ký đã bị hủy bỏ nên hệ thống sẽ không chuyển tiếp sự kiện tin nhắn đến bộ nhận này nữa."),

      emptyLine(),
      heading3("d) Kết quả thực nghiệm"),
      body("(Mô tả kết quả chạy ứng dụng tại đây: chụp ảnh màn hình hoặc mô tả kết quả quan sát được trong quá trình thực hành.)", { italics: true, color: "6B7280" }),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // BAI 2
      heading2("Bài 2: Đăng ký BroadcastReceiver trong AndroidManifest nhận biết trạng thái sạc pin"),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Nghiên cứu phương pháp khai báo tĩnh qua Manifest để lắng nghe tín hiệu phần cứng hệ thống (cấp/ngắt nguồn sạc), phân tích các thay đổi về chính sách tối ưu hóa năng lượng của Google (Background Limits) và phương án lập trình tương thích."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Tạo lớp xử lý PowerStateChangeReceiver kế thừa từ lớp BroadcastReceiver gốc."),
      numbered("2.", "Nâng cấp tương thích thực tế: Do các giới hạn bảo mật chạy nền nghiêm ngặt kể từ phiên bản Android 8.0 (API 26) đối với các sự kiện hệ thống ngầm, lớp receiver đã được chuyển đổi đăng ký động linh hoạt trong Java code của MainActivity tại hàm onResume() và hủy tại onStop() thay vì đăng ký tĩnh trong Manifest."),

      emptyLine(),
      body("Đoạn mã tham khảo:", { bold: true }),
      codeBlock("IntentFilter filter = new IntentFilter();"),
      codeBlock("filter.addAction(Intent.ACTION_POWER_CONNECTED);"),
      codeBlock("filter.addAction(Intent.ACTION_POWER_DISCONNECTED);"),
      codeBlock("registerReceiver(powerReceiver, filter);"),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      bullet("Khi thiết bị di động được kết nối với cáp sạc (cấp nguồn điện) hoặc ngắt cáp sạc, hệ thống Android sẽ tự động phát ra tín hiệu phần cứng tương ứng."),
      bullet("Bộ thu nhận tín hiệu phần cứng và gọi phương thức onReceive(), thực hiện hiển thị tức thì một thông báo dạng Toast bên dưới màn hình để xác nhận trạng thái nguồn điện (Power Connected / Power Disconnected)."),

      emptyLine(),
      heading3("d) So sánh đăng ký tĩnh và đăng ký động"),
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
                children: [new Paragraph({ children: [new TextRun({ text: "Đăng ký tĩnh (Manifest)", bold: true, size: 22, font: "Times New Roman", color: "FFFFFF" })] })]
              }),
              new TableCell({
                width: { size: 3480, type: WidthType.DXA },
                shading: { fill: "1E40AF" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [new Paragraph({ children: [new TextRun({ text: "Đăng ký động (Java Code)", bold: true, size: 22, font: "Times New Roman", color: "FFFFFF" })] })]
              }),
            ]
          }),
          ...([
            ["Vòng đời", "Độc lập vòng đời Activity", "Gắn liền vòng đời Activity/Fragment"],
            ["Khi app tắt", "Vẫn nhận được sự kiện", "Không nhận được sự kiện"],
            ["Android 8.0+", "Bị hạn chế đối với sự kiện ngầm", "Hoạt động bình thường"],
            ["Quản lý tài nguyên", "Tự động bởi hệ thống", "Phải hủy thủ công (onStop)"],
            ["Ưu điểm", "Ổn định, chạy khi app tắt", "Linh hoạt, ít rò rỉ tài nguyên"],
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
      heading2("Bài 3: Ứng dụng phản hồi tin nhắn khẩn cấp khi nhận keyword \"Are you OK?\""),
      emptyLine(),

      heading3("a) Mục tiêu bài tập"),
      body("Ứng dụng kỹ thuật Broadcast để xây dựng hệ thống phản hồi tự động hóa phức tạp, rèn luyện kỹ năng điều phối luồng Activity từ nền bằng hệ thống cờ (FLAG_ACTIVITY_NEW_TASK), và tương tác trực tiếp với API viễn thông SmsManager."),

      emptyLine(),
      heading3("b) Cách thức hiện thực"),
      numbered("1.", "Đăng ký tĩnh lớp SmsReceiver trong tệp tin AndroidManifest.xml để đảm bảo khả năng đón nhận tin nhắn SMS kể cả khi ứng dụng bị đóng hoàn toàn."),
      numbered("2.", "Cấp thêm quyền gửi tin nhắn trong tệp cấu hình:"),
      codeBlock("<uses-permission android:name=\"android.permission.SEND_SMS\" />"),
      numbered("3.", "Triển khai mã nguồn đánh thức Activity trong SmsReceiver.java khi nhận tin nhắn có từ khóa:"),
      codeBlock("Intent intent = new Intent(context, MainActivity.class);"),
      codeBlock("intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);"),
      codeBlock("intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);"),
      codeBlock("intent.putStringArrayListExtra(\"phoneList\", phoneList);"),
      codeBlock("context.startActivity(intent);"),
      numbered("4.", "Trong MainActivity.java, triển khai hàm initVariables() đón nhận danh sách số điện thoại từ Intent và đẩy vào ListView."),

      emptyLine(),
      heading3("c) Nguyên lý hoạt động"),
      body("Trường hợp ứng dụng đang chạy:", { bold: true }),
      bullet("Khi tin nhắn SMS chứa từ khóa \"Are you OK?\" đi vào máy, SmsReceiver đón nhận và gửi tín hiệu Forward thông qua Broadcast nội bộ trực tiếp đến MainActivity."),
      bullet("Bộ lọc động trong Activity nhận danh sách và hiển thị Số điện thoại lên ListView."),

      emptyLine(),
      body("Trường hợp ứng dụng đang tắt:", { bold: true }),
      bullet("SmsReceiver nhận tin nhắn, tự động khởi tạo và bật màn hình MainActivity lên thông qua cờ điều hướng hệ thống."),
      bullet("MainActivity khởi động từ đầu, đọc dữ liệu danh sách số điện thoại đính kèm trong Intent và hiển thị lên giao diện."),

      emptyLine(),
      body("Cơ chế phản hồi:", { bold: true }),
      bullet("Người dùng nhấn nút Safe hoặc Mayday để gửi nội dung soạn sẵn thủ công bằng đối tượng SmsManager."),
      bullet("Nếu kích hoạt nút gạt Auto Response, hệ thống sẽ tự động gọi hàm gửi tin nhắn phản hồi trở lại ngay lập tức khi phát hiện tin nhắn đến chứa từ khóa."),

      emptyLine(),
      heading3("d) Sơ đồ luồng xử lý"),
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [4680, 4680],
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
                width: { size: 4680, type: WidthType.DXA },
                shading: { fill: "DBEAFE" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [
                  new Paragraph({ children: [new TextRun({ text: "Ứng dụng đang chạy (Foreground)", bold: true, size: 22, font: "Times New Roman", color: "1E3A8A" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "1. SMS đến -> SmsReceiver.onReceive()", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "2. Gửi Broadcast nội bộ -> MainActivity", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "3. Hiển thị danh sách lên ListView", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "4. Người dùng gửi phản hồi (SmsManager)", size: 20, font: "Times New Roman" })] }),
                ]
              }),
              new TableCell({
                width: { size: 4680, type: WidthType.DXA },
                shading: { fill: "FEF3C7" },
                margins: { top: 100, bottom: 100, left: 160, right: 160 },
                children: [
                  new Paragraph({ children: [new TextRun({ text: "Ứng dụng đang tắt (Background)", bold: true, size: 22, font: "Times New Roman", color: "92400E" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "1. SMS đến -> SmsReceiver.onReceive()", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "2. Thêm cờ FLAG_ACTIVITY_NEW_TASK", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "3. Đánh thức & khởi chạy MainActivity", size: 20, font: "Times New Roman" })] }),
                  new Paragraph({ spacing: { before: 40 }, children: [new TextRun({ text: "4. Đọc dữ liệu Intent Extra -> Hiển thị ListView", size: 20, font: "Times New Roman" })] }),
                ]
              }),
            ]
          })
        ]
      }),

      emptyLine(),
      heading3("e) Kết quả thực nghiệm"),
      body("(Mô tả kết quả chạy ứng dụng tại đây: chụp ảnh màn hình hoặc mô tả kết quả quan sát được trong quá trình thực hành.)", { italics: true, color: "6B7280" }),

      emptyLine(),
      new Paragraph({ children: [new PageBreak()] }),

      // SECTION III - Tong ket
      sectionDivider("III. NHẬN XÉT VÀ KẾT LUẬN"),
      emptyLine(),

      heading2("1. Điểm nổi bật đã học được"),
      bullet("Mô hình Publisher-Subscriber giúp tách rời các thành phần hệ thống, tăng tính mở rộng và khả năng tái sử dụng code."),
      bullet("Đăng ký động (Dynamic Registration) phù hợp cho các BroadcastReceiver gắn liền vòng đời UI, tránh rò rỉ tài nguyên và phù hợp với chính sách Background Limits từ Android 8.0."),
      bullet("Đăng ký tĩnh (Static Registration) vẫn cần thiết cho các trường hợp cần xử lý sự kiện ngay cả khi ứng dụng bị đóng."),
      bullet("Hệ thống cờ định tuyến Intent (FLAG_ACTIVITY_NEW_TASK, FLAG_ACTIVITY_CLEAR_TOP) là công cụ quan trọng để điều phối giao diện từ nền."),

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

const outputPath = path.join(__dirname, 'BaoCao_Lab5_BroadcastReceiver.docx');

Packer.toBuffer(doc).then(buffer => {
  fs.writeFileSync(outputPath, buffer);
  console.log("SUCCESS: Word Document generated at " + outputPath);
}).catch(err => {
  console.error("ERROR generating Word Document: ", err);
});
