package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trade")
public class TradeDto {
    @Id
    @GeneratedValue
    private int id;

    private int rank;

    private int amount;

    @ManyToOne @JoinColumn(name = "user_id")
    private UserDto user;

    @ManyToOne @JoinColumn(name = "rs_event_id") private RsEventDto rsEvent;
}
