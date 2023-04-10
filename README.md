Virtual threads

Thread also supports the creation of virtual threads. Virtual threads are typically user-mode threads scheduled by the Java runtime rather than the operating system. Virtual threads will typically require few resources and a single Java virtual machine may support millions of virtual threads. Virtual threads are suitable for executing tasks that spend most of the time blocked, often waiting for I/O operations to complete. Virtual threads are not intended for long running CPU intensive operations.

Virtual threads typically employ a small set of platform threads used as carrier threads. Locking and I/O operations are examples of operations where a carrier thread may be re-scheduled from one virtual thread to another. Code executing in a virtual thread is not aware of the underlying carrier thread. The currentThread() method, used to obtain a reference to the current thread, will always return the Thread object for the virtual thread.

Virtual threads do not have a thread name by default. The getName method returns the empty string if a thread name is not set.

Some stats as follows

2023-04-10T21:36:34.376+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Inside VirtualThreadsApplication.publishToRedisUsingSeparateVirtualThreads()
2023-04-10T21:36:34.376+01:00  INFO 529 --- [           main] c.c.v.tradeservice.TradeService          : Inside TradeService.publishTradesUsingSeparateVirtualThreads()
2023-04-10T21:36:34.619+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Time taken to persist 100000 trades is 0.0485310084 seconds
2023-04-10T21:38:36.641+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Trade List in Redis is 99999
2023-04-10T21:38:36.849+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Inside VirtualThreadsApplication.publishToRedisByUsingVirtualExecutor()
2023-04-10T21:38:36.849+01:00  INFO 529 --- [           main] c.c.v.tradeservice.TradeService          : Inside TradeService.publishTradesUsingVirtualExecutor()
2023-04-10T21:38:36.850+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Time taken to save 100000 trades is 1.358E-4 seconds
2023-04-10T21:40:36.852+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Trade List in Redis is 97419
2023-04-10T21:40:36.952+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Inside VirtualThreadsApplication.saveTradesUsingPlainSpring()
2023-04-10T21:40:36.952+01:00  INFO 529 --- [           main] c.c.v.tradeservice.TradeService          : Inside TradeService.publishTradesUsingPlainSpring()
2023-04-10T21:42:36.026+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Time taken to save 100000 trades is 23.8146474416 seconds
2023-04-10T21:44:37.035+01:00  INFO 529 --- [           main] c.c.v.VirtualThreadsApplication          : Trade List in Redis is 99999

Looks like Executors.newVirtualThreadPerTaskExecutor() returns the fastest and the work is being done async using Virtual threads. I ran the test in a Mac with M1 chip.

