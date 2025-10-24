package com.item.consultant.controller.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {


    @Resource
    private OpenAiChatModel openAiChatModel;

    @Resource
    private ChatMemoryStore redisChatMemoryStore;

//    @Bean
//    public ConsultantService consultantService(){
//        ConsultantService consultantService = AiServices.builder(ConsultantService.class)
//                .chatModel(openAiChatModel)
//                .build();
//        return consultantService;
//    }

    // 构建会话记忆对象
    @Bean
    public ChatMemory chatMemory() {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20).build();
        return chatMemory;
    }

    // 构建ChatMemoryProvider对象
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .chatMemoryStore(redisChatMemoryStore)
                        .maxMessages(20)
                        .build();
            }
        };
        return chatMemoryProvider;
    }

    // 构建向量数据库操作对象
    @Bean
    public EmbeddingStore store() {
        // 1.加载文档进内存 类路径加载 文件磁盘加载 url加载
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        //List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\codeSpace\\source\\CodeSource\\study\\consultant\\consultant\\src\\main\\resources\\content");
        // 2.构建向量数据库操作对象
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();
        // 3.构建一个embeddingStoreIngestor对象,完成文本数据切割，向量化，存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    // 构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)//设置向量数据库操作对象
                .minScore(0.5) //设置最小分数
                .maxResults(3) //设置最大片段数量
                .build();
    }

}
