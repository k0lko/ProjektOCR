package com.projektocr.Controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@RestController
public class OCRController {

    // Lista dopuszczalnych formatów
    private static final Set<String> ALLOWED_FORMATS = Set.of("txt", "docx", "pdf");

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String content, @RequestParam String format) {
        try {
            if (!ALLOWED_FORMATS.contains(format)) {
                return ResponseEntity.badRequest().body(null); // Walidacja formatu
            }

            byte[] fileContent = generateFileContent(content, format);
            if (fileContent == null) {
                return ResponseEntity.badRequest().body(null); // Błąd generacji treści
            }

            String fileName = URLEncoder.encode(getFileName(format), StandardCharsets.UTF_8);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName)
                    .contentType(getMediaTypeForFormat(format))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów (do zamiany na logger)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Generuje zawartość pliku na podstawie formatu.
     *
     * @param content Treść pliku
     * @param format  Format pliku
     * @return Zawartość pliku w postaci byte[]
     * @throws IOException W przypadku błędów I/O
     */
    private byte[] generateFileContent(String content, String format) throws IOException {
        switch (format) {
            case "txt":
                return content.getBytes(StandardCharsets.UTF_8);
            case "docx":
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                     XWPFDocument document = new XWPFDocument()) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(content);
                    document.write(outputStream);
                    return outputStream.toByteArray();
                }
            case "pdf":
                if (content == null || content.isEmpty()) return null; // Walidacja zawartości PDF
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                     PdfWriter writer = new PdfWriter(outputStream);
                     PdfDocument pdf = new PdfDocument(writer);
                     Document document = new Document(pdf)) {
                    document.add(new Paragraph(content));
                    return outputStream.toByteArray();
                }
            default:
                return null; // Nieobsługiwany format
        }
    }

    /**
     * Zwraca nazwę pliku dla danego formatu.
     *
     * @param format Format pliku
     * @return Nazwa pliku
     */
    private String getFileName(String format) {
        switch (format) {
            case "txt":
                return "result.txt";
            case "docx":
                return "result.docx";
            case "pdf":
                return "result.pdf";
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    /**
     * Zwraca typ MediaType odpowiedni dla danego formatu.
     *
     * @param format Format pliku (np. "txt", "docx", "pdf")
     * @return Typ MediaType
     */
    private MediaType getMediaTypeForFormat(String format) {
        switch (format) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "docx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}