package com.item.consultant;

import com.item.consultant.pojo.Reservation;
import com.item.consultant.service.ReservationService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReservationServiceTest {

    @Resource
    private ReservationService reservationService;

    // 测试插入预约信息
    @Test
     void insert() {
        Reservation reservation = new Reservation();
        reservation.setName("张三");
        reservation.setGender("男");
        reservation.setPhone("13888888888");
        reservation.setProvince("北京");
        reservation.setCommunicationTime(LocalDateTime.now());
        reservation.setEstimatedScore(580);
        reservationService.insert(reservation);
    }

    // 测试根据手机号查询预约信息
    @Test
    void findByPhone() {
        Reservation reservation = reservationService.findByPhone("13888888888");
        System.out.println(reservation);
    }
}
