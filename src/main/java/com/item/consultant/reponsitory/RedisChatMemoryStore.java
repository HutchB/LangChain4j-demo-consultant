package com.item.consultant.reponsitory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public class RedisChatMemoryStore implements ChatMemoryStore {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // 获取会话消息
        String json = stringRedisTemplate.opsForValue().get(memoryId.toString());

        // 如果没有数据，返回空列表
        if (json == null || json.isEmpty() || "null".equals(json)) {
            return List.of();
        }

        // 把json字符串转List<ChatMessage>
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        // 更新会话消息
        // 1.list转json数据
        String json = ChatMessageSerializer.messagesToJson(list);
        // 2.把json数据保存到redis中
        stringRedisTemplate.opsForValue().set(memoryId.toString(), json, Duration.ofDays(1));
    }

    @Override
    public void deleteMessages(Object memoryId) {
        stringRedisTemplate.delete(memoryId.toString());
    }
}
