package com.cham.virtualthreads.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Data
@RedisHash("trades")
public class Trade {
    private @Id int tradeId;
    private String traderName;
    private long tradeValue;
    private String tradeDate;
}
