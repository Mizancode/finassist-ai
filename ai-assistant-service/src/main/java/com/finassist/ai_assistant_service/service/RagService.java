package com.finassist.ai_assistant_service.service;

import com.finassist.ai_assistant_service.dto.RagSearchResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public RagSearchResult ask(String question) {
        // =====================================================
        // STEP 1: RETRIEVE RELEVANT DOCUMENT CHUNKS
        // =====================================================
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.60)
                .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        // =====================================================
        // STEP 2: HANDLE NO RELEVANT DOCUMENT
        // =====================================================
        if (documents == null || documents.isEmpty()) {
            RagSearchResult ragSearchResult=new RagSearchResult();
            ragSearchResult.setAnswer("I could not find relevant information " + "in the uploaded financial documents.");
            ragSearchResult.setSources(List.of());
            return ragSearchResult;
        }

        // =====================================================
        // STEP 3: BUILD CONTEXT
        // =====================================================
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n--- DOCUMENT CHUNK ---\n\n"));

        // =====================================================
        // STEP 4: BUILD GROUNDED PROMPT
        // =====================================================
        String systemPrompt = """
                You are FinAssist AI, an enterprise financial intelligence assistant. Answer the user's question using ONLY the supplied document context. Rules: 1. Do not invent financial facts. 2. Do not use outside knowledge when the answer is not present in the context. 3. If the context does not contain enough information, clearly say so. 4. Give a concise but useful explanation. 5. When possible, mention the relevant policy, threshold, rule or financial fact found in the documents. Retrieved document context: %s 
                """.formatted(context);

        // =====================================================
        // STEP 5: CALL OLLAMA LLM
        // =====================================================
        String answer = chatClient
                .prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();

        // =====================================================
        // STEP 6: RETURN SOURCES
        // =====================================================
        List<RagSearchResult.SourceDocument> sources = documents
                .stream()
                .map(document -> new RagSearchResult.SourceDocument(String.valueOf(document.getMetadata().getOrDefault("fileName", "unknown")), document.getMetadata().get("documentId"), document.getText())).toList();
        RagSearchResult result=new RagSearchResult();
        result.setAnswer(answer);
        result.setSources(sources);
        return result;
    }
}
