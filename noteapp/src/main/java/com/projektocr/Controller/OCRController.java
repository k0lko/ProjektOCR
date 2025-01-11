package com.projektocr.Controller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


@RestController
@RequestMapping("/api/ocr")
public class OCRController {

    private final String TESSDATA_PATH = "/usr/share/tesseract-ocr/4.00/tessdata";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Zapis pliku na dysku
        Path tempFile = Files.createTempFile("ocr_", file.getOriginalFilename());
        Files.write(tempFile, file.getBytes());

        // Wykonywanie OCR
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(TESSDATA_PATH); // Ścieżka do tessdata
        tesseract.setLanguage("eng");

        String extractedText;
        try {
            extractedText = tesseract.doOCR(tempFile.toFile());
        } catch (TesseractException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("OCR processing failed: " + e.getMessage());
        }

        // Zwróć wynik w formacie TXT
        return ResponseEntity.ok(extractedText);
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String content, @RequestParam String format) {
        try {
            byte[] fileContent;
            String fileName;

            if ("txt".equals(format)) {
                fileName = "result.txt";
                fileContent = content.getBytes(StandardCharsets.UTF_8);
            } else if ("docx".equals(format)) {
                fileName = "result.docx";
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try (XWPFDocument document = new XWPFDocument()) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(content);
                    document.write(outputStream);
                }
                fileContent = outputStream.toByteArray();
            } else if ("pdf".equals(format)) {
                fileName = "result.pdf";
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try (PdfWriter writer = new PdfWriter(outputStream)) {
                    PdfDocument pdf = new PdfDocument(writer);
                    Document document = new Document(pdf);
                    document.add(new Paragraph(content));
                }
                fileContent = outputStream.toByteArray();
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            ByteArrayResource resource = new ByteArrayResource(fileContent);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
