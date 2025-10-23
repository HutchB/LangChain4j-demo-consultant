package com.item.consultant.controller;

import com.item.consultant.controller.aiservice.ConsultantService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
//    @Resource
//    private OpenAiChatModel openAiChatModel;
//    @GetMapping("/chat")
//    public String chat(String message) {
//        return openAiChatModel.chat(message);
//    }

    @Resource
    private ConsultantService consultantService;
    @GetMapping("/chat")
    public String chat(String message) {
        return consultantService.chat(message);
    }

    @GetMapping(value = "/streamChat",produces = "text/html;charset=utf-8")
    public Flux<String> streamChat(String memoryId,String message) {
        Flux<String> result = consultantService.chatStream(memoryId, message);
        return result;
    }
}
