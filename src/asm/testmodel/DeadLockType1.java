package asm.testmodel;

public class DeadLockType1{
	public static Object mutex_1 = new Object();
	public static Object mutex_2 = new Object();
	public static Object mutex_3 = new Object();
	public static Object mutex_4 = new Object();

	
	public static void main(String[] args) {
		new DeadLockType1().run();
	}
	public void run(){
		Thread thd1 = new Thread(new Runnable() {
			@Override
			public void run() {
				
				for(int i=0; i<5; i++){
					System.out.println(Thread.currentThread().getName() + " acq(mutex_1)");
					synchronized(mutex_1){
						System.out.println(Thread.currentThread().getName() + " gets mutex_1");
						DeadLockType1.foo();
						System.out.println("	" + Thread.currentThread().getName() + " acq(mutex_2)");
						synchronized(mutex_2){
							System.out.println("	" + Thread.currentThread().getName() + " gets mutex_2, mutex_1");
							DeadLockType1.foo();
							System.out.println("	" + Thread.currentThread().getName() + " rel(mutex_2)");
						}
						System.out.println("	" + Thread.currentThread().getName() + " released mutex_2");
						DeadLockType1.foo();
						System.out.println(Thread.currentThread().getName() + " rel(mutex_1)");
					}
					System.out.println(Thread.currentThread().getName() + " released mutex_1");
				}
			
			}
		});
	

		
		Thread thd2 = new Thread(new Runnable() {
		
			@Override
			public void run() {
	
				for(int i=0; i<5; i++){
					System.out.println(Thread.currentThread().getName() + " acq(mutex_2)");
					synchronized(mutex_2){
						System.out.println(Thread.currentThread().getName() + " gets mutex_2");
						DeadLockType1.foo();
						System.out.println("	" + Thread.currentThread().getName() + " acq(mutex_3)");
						synchronized(mutex_3){
							System.out.println("	" + Thread.currentThread().getName() + " gets mutex_2, mutex_3");
							DeadLockType1.foo();
							System.out.println("	" + Thread.currentThread().getName() + " rel(mutex_3)");
						}
						System.out.println("	" + Thread.currentThread().getName() + " released mutex_3");
						DeadLockType1.foo();
						System.out.println(Thread.currentThread().getName() + " rel(mutex_2)");
					}
					System.out.println(Thread.currentThread().getName() + " released mutex_2");
				}
		
			}
		});

	
		
		Thread thd3 = new Thread(new Runnable() {
		
			@Override
			public void run() {

			
				for(int i=0; i<5; i++){
					System.out.println(Thread.currentThread().getName() + " acq(mutex_4)");
					synchronized(mutex_4){
						
						System.out.println(Thread.currentThread().getName() + " gets mutex_4");
						DeadLockType1.foo();
						System.out.println("	" + Thread.currentThread().getName() + " acq(mutex_3)");
						synchronized(mutex_3){
							System.out.println("	" + Thread.currentThread().getName() + " gets mutex_4 and mutex_3");
							DeadLockType1.foo();
							System.out.println("	" + Thread.currentThread().getName() + " rel(mutex_3)");
						}
						System.out.println("	" + Thread.currentThread().getName() + " released mutex_3");
						DeadLockType1.foo();
						System.out.println(Thread.currentThread().getName() + " rel(mutex_4)");
					}
					System.out.println(Thread.currentThread().getName() + " released mutex_4");
				}
			
			}
		});
		
		thd1.start();
		thd2.start();
		thd3.start();
	}

	public static void foo(){
		int i=0;
		//System.out.println(Thread.currentThread().getName() + " foo...");
		while(i<200) i++;
		//StaticMethod.printThread2();
	}
}
