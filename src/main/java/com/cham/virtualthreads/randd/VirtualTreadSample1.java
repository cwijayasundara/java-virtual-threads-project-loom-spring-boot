package com.cham.virtualthreads.randd;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.startVirtualThread;

@Slf4j
public class VirtualTreadSample1 {

        public static void main(String[] args) {
            startVirtualThreads();
        }
        //The below will create 1 million virtual threads in my Mac M1 machine and finishes in few seconds!
        private static void startVirtualThreads() {
            for (int i = 0; i < 1000000; i++) {
                startVirtualThread(() -> System.out.println(Thread.currentThread()));
            }
        }
}
