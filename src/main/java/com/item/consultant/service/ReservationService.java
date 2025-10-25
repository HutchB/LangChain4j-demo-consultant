package com.item.consultant.service;

import com.item.consultant.mapper.ReservationMapper;
import com.item.consultant.pojo.Reservation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Resource
    private ReservationMapper reservationMapper;

    // 添加预约信息
    public void insert(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    // 根据手机号查询信息
    public Reservation findByPhone(String phone) {
        return reservationMapper.findByPhone(phone);
    }
}
