package basic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CreateThreadDemo {


    @Test
    void testExtendThread() {
        class SubThread extends Thread {
            @Override
            public void run() {
                System.out.println("测试继承Thread创建线程的方式");
            }
        }
        SubThread subThread = new SubThread();
        subThread.start();
    }

    @Test
    void testImplementRunnable() {
        class SubRunnable implements Runnable {
            @Override
            public void run() {
                System.out.println("测试实现Runnable接口的方式");
            }
        }
        new Thread(new SubRunnable()).start();
    }

    @Test
    void testCallable() {
        class SubCallable implements Callable<Integer> {
            @Override
            public Integer call() throws Exception {
                // 测试异常情况
                // int i = 1 / 0;
                TimeUnit.SECONDS.sleep(3);
                System.out.println("测试实现Callable接口的方式");
                return 1;
            }
        }

        FutureTask<Integer> task = new FutureTask<>(new SubCallable());
        new Thread(task).start();

        try {
            // get() 会阻塞，等待线程执行结果
            System.out.println("执行结果：" + task.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("执行异常，message: " + e.getMessage());
        }
    }

    @Test
    void testThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            System.out.println("测试线程创建线程的方式");
            return "执行完毕";
        });

        System.out.println("执行结果：" + future.get());
        executorService.shutdown();
    }

    @Test
    void testVirtualThread() {
        Thread virtualThread = Thread.startVirtualThread(() -> {
            System.out.println("测试虚拟线程");
        });

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorService.submit(() -> {
            System.out.println("线程池方式执行虚拟线程");
        });
        executorService.shutdown();
    }

    @Test
    void testCompletableFuture() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("测试CompletableFuture创建线程");
        });
    }


//    public static void main(String[] args) {
////        new ThreadD().start();
////        new Thread(new RunnableThread()).start();
////        ExecutorService executorService = Executors.newFixedThreadPool(1);
////        Future<String> submit = executorService.submit(new CallableThread() {
////        });
////        System.out.println("submit : " + submit);
////        executorService.shutdown();
//
//
//        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "future";
//            }
//        });
//        futureTask.run();
//
//        try {
//            String o = futureTask.get();
//            System.out.println(o);
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

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
     * Callable和FutureTask
     */
    static class CallableThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("CallableThread Thread");
            return "CallableThread Thread";
        }
    }
}
