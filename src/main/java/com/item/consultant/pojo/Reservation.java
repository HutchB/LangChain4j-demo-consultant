package com.item.consultant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String gender;
    private String phone;
    private LocalDateTime communicationTime;
    private String province;
    private Integer estimatedScore;
}
