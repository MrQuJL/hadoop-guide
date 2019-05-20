package demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TestDistributedLock {

	private static int number = 10;
	private static void getNumber(){
		System.out.println("\n\n******* 开始业务方法   ************");
		System.out.println("当前值：" + number);
		number --;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("******* 结束业务方法   ************\n\n");
	}
	
	public static void main(String[] args) {
		
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
		CuratorFramework cf = CuratorFrameworkFactory.builder()
				              .connectString("192.168.157.111:2181")
				              .retryPolicy(policy)
				              .build();
		
		cf.start();
		
		final InterProcessMutex lock = new InterProcessMutex(cf, "/aaa");
		
		
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				
				public void run() {
				
					try {
						lock.acquire();

						getNumber();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							lock.release();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}

	}

}











