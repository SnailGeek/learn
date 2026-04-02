package basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CreateThreadDemo {

    public static void main(String[] args) {
        new ThreadD().start();
        new Thread(new RunnableThread()).start();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> submit = executorService.submit(new CallableThread() {
        });
        System.out.println("submit : " + submit);
        executorService.shutdown();
    }

    /**
     * 使用Thread来创建线程
     */
    static class ThreadD extends Thread {
        @Override
        public void run() {
            System.out.println("继承方式运行Thread");
        }
    }

    /**
     * 实现Runnable接口的方式创建线程
     */
    static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable Thread");
        }
    }

    /**
     * Callable和Future
     */
    static class CallableThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("CallableThread Thread");
            return "CallableThread Thread";
        }
    }
}
