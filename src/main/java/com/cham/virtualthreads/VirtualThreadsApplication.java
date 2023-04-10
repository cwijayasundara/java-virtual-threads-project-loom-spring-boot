package com.cham.virtualthreads;

import com.cham.virtualthreads.tradeservice.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class VirtualThreadsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VirtualThreadsApplication.class, args);
	}
	@Autowired
	private TradeService tradeService;
	private final int TRADE_COUNT = 100000;
	private final long NANO_SECONDS = 5_000_000_000L;
	@Override
	public void run(String... args) throws Exception {
		separateVirtualThreads();
		virtualExecutorService();
		plainSpringTemplateBlocking();
	}
	private void plainSpringTemplateBlocking() throws InterruptedException {
		publishToRedisUsingPlainSpring();
		Thread.sleep(120000);
		findTradeListSizeInRedis();
		tradeService.removeAllRecords();
	}
	private void virtualExecutorService() throws InterruptedException {
		publishToRedisByUsingVirtualExecutor();
		Thread.sleep(120000);
		findTradeListSizeInRedis();
		tradeService.removeAllRecords();
	}
	private void separateVirtualThreads() throws InterruptedException {
		publishToRedisUsingSeparateVirtualThreads();
		Thread.sleep(120000);
		findTradeListSizeInRedis();
		tradeService.removeAllRecords();
	}
	private void publishToRedisUsingSeparateVirtualThreads() throws InterruptedException {
		log.info("Inside VirtualThreadsApplication.publishToRedisUsingSeparateVirtualThreads()");
		long startTime = System.nanoTime();
		tradeService.publishTradesUsingSeparateVirtualThreads(TRADE_COUNT);
		long endTime   = System.nanoTime();
		double elipsedTime = (double)(endTime - startTime) / NANO_SECONDS;
		log.info("Time taken to persist " + TRADE_COUNT + " trades is " + elipsedTime + " seconds");
		Thread.sleep(2000);
	}
	private void findTradeListSizeInRedis(){
		log.info("Trade List in Redis is " + tradeService.findTradeListSize());
	}
	private void publishToRedisByUsingVirtualExecutor() throws InterruptedException{
		log.info("Inside VirtualThreadsApplication.publishToRedisByUsingVirtualExecutor()");
		long startTime = System.nanoTime();
		tradeService.publishTradesUsingVirtualExecutor(TRADE_COUNT);
		long endTime   = System.nanoTime();
		double elipsedTime = (double)(endTime - startTime) / NANO_SECONDS;
		log.info("Time taken to save " + TRADE_COUNT + " trades is " + elipsedTime + " seconds");

	}
	private void publishToRedisUsingPlainSpring() throws InterruptedException{
		log.info("Inside VirtualThreadsApplication.saveTradesUsingPlainSpring()");
		long startTime = System.nanoTime();
		tradeService.publishTradesUsingPlainSpring(TRADE_COUNT);
		long endTime   = System.nanoTime();
		double elipsedTime = (double)(endTime - startTime) / NANO_SECONDS;
		log.info("Time taken to save " + TRADE_COUNT + " trades is " + elipsedTime + " seconds");
		Thread.sleep(1000);
	}
}
