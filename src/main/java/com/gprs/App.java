package com.gprs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gprs.gateway.TextMessageGateway;
import com.gprs.gateway.TextMessageSubscriber;


/**
 * Starts the gateway and configures the disruptor to handle messages.
 * 
 * Message can be sent to the gateway using Netcat.
 * 
 * <pre>
 * Examples of sending messages:
 * 
 * cat SAMPLE-DATA.txt | nc localhost 7000
 * echo 'C|STOP' | nc 127.0.0.1 7000
 * </pre>
 * 
 * See: README.md for more details about how to start and interact with with the
 * server.
 */
public class App implements ShutdownListener {
    private static final Logger l = LoggerFactory.getLogger(TextMessageGateway.class);

    /** This is the number of event processors + 1 thread for the gateway */
    private static final int THREAD_POOL_SIZE = 4;

    private static final int RINGBUFFER_SIZE = 16;

    /**
     * Thread pool for disruptor threads.
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    Future<?>[] tasks = new Future<?>[THREAD_POOL_SIZE];


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    private void run(String[] args) throws InterruptedException {

        // Netty Event Publisher
        TextMessageGateway gateway = createGatewayEventPublisher();
        gateway.run();
        // The producer can't move past this barrier.

        l.info("Shutting down the app.");
    }

    /**
     * G* in the README.md
     * 
     * @param ringBuffer
     * @return
     */
    private TextMessageGateway createGatewayEventPublisher() {
        TextMessageGateway gateway = new TextMessageGateway(this);
        return gateway;
    }

    

    public void notifyShutdown() {
        shutdownThreadPool();

    }

    private void shutdownThreadPool() {
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore as we are shutting down anyway.
        }
    }
}
