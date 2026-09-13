package com.finassist.document_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextChunkingService {
    private static final int CHUNK_SIZE = 1200;

    private static final int OVERLAP = 200;

    public List<String> splitText(String text) {

        if (text == null || text.isBlank()) {
            return List.of();
        }

        String normalizedText = text
                .replace("\r\n", "\n")
                .replaceAll("\\s+", " ")
                .trim();

        List<String> chunks = new ArrayList<>();

        int start = 0;

        while (start < normalizedText.length()) {

            int end = Math.min(
                    start + CHUNK_SIZE,
                    normalizedText.length()
            );

            if (end < normalizedText.length()) {

                int lastSpace =
                        normalizedText.lastIndexOf(" ", end);

                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            String chunk =
                    normalizedText.substring(start, end).trim();

            if (!chunk.isBlank()) {
                chunks.add(chunk);
            }

            if (end >= normalizedText.length()) {
                break;
            }

            start = Math.max(
                    end - OVERLAP,
                    start + 1
            );
        }

        return chunks;
    }
}
