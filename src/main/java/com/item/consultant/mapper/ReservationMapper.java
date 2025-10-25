package com.item.consultant.mapper;


import com.item.consultant.pojo.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {
    // 1.添加预约信息
    @Insert("insert into volunteer.reservation (name, gender, phone, communication_time, province, estimated_score) values (#{name},#{gender},#{phone},#{communicationTime},#{province},#{estimatedScore})")
    void insert(Reservation reservation);

    //2.根据手机号查询信息
    @Select("select * from volunteer.reservation where phone = #{phone}")
    Reservation findByPhone(String phone);
}
