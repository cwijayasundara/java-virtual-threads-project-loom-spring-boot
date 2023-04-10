package com.cham.virtualthreads.tradeservice;

import com.cham.virtualthreads.domain.Trade;
import com.cham.virtualthreads.repository.RedisTradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.Thread.startVirtualThread;

@Service
@Slf4j
public class TradeService {
    @Autowired
    private RedisTradeRepository redisTradeRepository;
    private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public void publishTradesUsingSeparateVirtualThreads(int tradeCount){
        log.info("Inside TradeService.publishTradesUsingSeparateVirtualThreads()");
        for (int i = 1; i < tradeCount; i++) {
            Trade trade = getTrade(i);
            startVirtualThread(() -> redisTradeRepository.save(trade));
        }
    }
    public long findTradeListSize(){
        return redisTradeRepository.count();
    }

    public void publishTradesUsingVirtualExecutor(int tradeCount){
        log.info("Inside TradeService.publishTradesUsingVirtualExecutor()");
        executor.submit(() -> {
            for (int i = 1; i < tradeCount; i++) {
                Trade trade = getTrade(i);
                redisTradeRepository.save(trade);
            }
        });
        executor.shutdown();
    }
    public void publishTradesUsingPlainSpring(int tradeCount ) {
        log.info("Inside TradeService.publishTradesUsingPlainSpring()");
        for (int i = 1; i < tradeCount; i++) {
            Trade trade = getTrade(i);
            redisTradeRepository.save(trade);
        }
    }
    private static Trade getTrade(int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Trade trade = Trade
                .builder()
                .tradeId(i)
                .traderName("Tom")
                .tradeValue(100 + i)
                .tradeDate(formatter.format(new Date()))
                .build();
        return trade;
    }

    public void removeAllRecords(){
        redisTradeRepository.deleteAll();
    }
}
