package com.finassist.document_service.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PdfProcessingService {

    public String extractText(MultipartFile file) throws IOException {
        try(InputStream inputStream=file.getInputStream()){
            InputStreamResource inputStreamResource=new InputStreamResource(inputStream);
            PdfDocumentReaderConfig config=PdfDocumentReaderConfig.builder()
                    .withPageTopMargin(0)
                    .withPagesPerDocument(1)
                    .build();
            PagePdfDocumentReader reader=new PagePdfDocumentReader(inputStreamResource,config);
            List<Document> documents=reader.read();
            return documents.stream()
                    .map(Document::getText)
                    .filter(text ->
                            text != null && !text.isBlank())
                    .reduce(
                            "",
                            (first, second) ->
                                    first + "\n\n" + second
                    );
        }
    }
}
