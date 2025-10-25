package com.item.consultant.tools;

import com.item.consultant.pojo.Reservation;
import com.item.consultant.service.ReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationTools {

    @Resource
    private ReservationService reservationService;

    // 1， 定义添加预约信息
    @Tool("预约志愿填报服务")
    public void insert(
            @P("考生姓名") String name,
            @P("考生性别") String gender,
            @P("考生手机号") String phone,
            @P("考生所在省份") String province,
            @P("预约沟通时间，格式为：yyyy-MM-dd'T'HH:mm") String communicationTime,
            @P("考生预估分数") Integer estimatedScore
    ) {
        Reservation reservation = new Reservation()
                .setName(name)
                .setGender(gender)
                .setPhone(phone)
                .setProvince(province)
                .setCommunicationTime(LocalDateTime.parse(communicationTime))
                .setEstimatedScore(estimatedScore);
        reservationService.insert(reservation);
    }
    // 2， 定义根据手机号查询预约信息
    @Tool("根据考生手机号查询预约信息")
    public Reservation findByPhone(@P("考生手机号") String phone) {
        return reservationService.findByPhone(phone);
    }

    // 获取时间
    @Tool("获取当前时间")
    public String getTime() {
        return LocalDateTime.now().toString();
    }

}
