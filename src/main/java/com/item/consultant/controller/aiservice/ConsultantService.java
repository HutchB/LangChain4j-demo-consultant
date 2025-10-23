package com.item.consultant.controller.aiservice;


import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 手动装配
        chatModel = "openAiChatModel", // 指定模型
        streamingChatModel = "openAiStreamingChatModel"
)
public interface ConsultantService {
    // 用于阻塞聊天的方法
    String chat(String message);

    // 用于流式聊天的方法
    Flux<String> chatStream(String message);
}
