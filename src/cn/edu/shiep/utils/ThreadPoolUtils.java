package cn.edu.shiep.utils;



import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtils {

	private ThreadPoolUtils(){
		
	}
	//�̳߳غ����߳���
	private static int CORE_POOL_SIZE = 5;
	//�̳߳�����߳���
	private static int MAX_POOL_SIZE = 100;
	//�����߳̿�״̬����ʱ��
	private static int KEEP_ALIVE_TIME = 10000;
	//�������С��������̶߳���ռ�ã���������������������£��ŻῪ�������̡߳�
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);
	//�̹߳���
	private  static ThreadFactory threadFactory = new ThreadFactory() {
		private final AtomicInteger integer = new AtomicInteger();
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r,"myThreadPool thread:" + integer.getAndIncrement());
		}
	};
	//�̳߳�
    private static ThreadPoolExecutor threadPool;
    
    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue,
                threadFactory);
    }
    /**
     * ���̳߳��г�ȡ�̣߳�ִ��ָ����Runnable����
     * @param runnable
     */
    public static void execute(Runnable runnable){
        threadPool.execute(runnable);
    }	
}

