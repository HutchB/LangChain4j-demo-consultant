package com.item.consultant.aiservice;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 手动装配
        chatModel = "openAiChatModel", // 指定模型
        streamingChatModel = "openAiStreamingChatModel",
        //chatMemory = "chatMemory", // 配置会话记忆对象
        chatMemoryProvider = "chatMemoryProvider", // 配置会话记忆提供者对象
        contentRetriever = "contentRetriever", // 配置向量数据库检索对象
        tools = "reservationTools" // 配置工具

)
public interface ConsultantService {
    // 用于阻塞聊天的方法
    @SystemMessage(fromResource = "system.txt") // 从 resources 目录加载系统消息文件
    String chat(String message);

    // 用于流式聊天的方法
    // @SystemMessage("你是东哥的小月月，人美心善又多金！") // 直接使用字符串作为系统消息
//    @UserMessage("你是东哥的小月月，人美心善又多金！{{it}}")
//    Flux<String> chatStream(String message);

//    @UserMessage("你是东哥的小月月，人美心善又多金！{{msg}}")
//    Flux<String> chatStream(@V("msg") String message);

    @SystemMessage(fromResource = "system.txt") // 从 resources 目录加载系统消息文件
    Flux<String> chatStream(@MemoryId String memoryId, @UserMessage String message);
}
