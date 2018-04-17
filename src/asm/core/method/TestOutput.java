package asm.core.method;

import java.util.Map;

public class TestOutput {

	
	public static void main(String[] args){
		
		TestOutput testOutput = new TestOutput();
		final Object o1 = new Object();
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<3; i++){
					synchronized (o1) {
						
					System.out.println("1----------------------" + i);
					System.out.println("1-------ActiveCount----------" + Thread.activeCount());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0; i<3; i++){
					synchronized (o1) {
					System.out.println("2----------------------" + i);
					System.out.println("2-------ActiveCount----------" + Thread.activeCount());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			}
		});
		
		
		thread2.start();
		thread.start();
		while(!thread.getState().equals(Thread.State.TERMINATED) && 
				!thread2.getState().equals(Thread.State.TERMINATED)){
			testOutput.fun2();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void fun(Thread thread){
		System.out.println(thread + " - " + thread.getState());
	}
	
	public synchronized void fun2(){
		for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
			Thread thread = stackTrace.getKey();
			StackTraceElement[] stack = stackTrace.getValue();
			
			if(thread.equals(Thread.currentThread())){
				continue;
			}
			System.out.println("------------------------------------------------");
			System.out.println("| Threads:" + thread.getName() + " - " + thread.getState() + " | ");
			System.out.println("------------------------------------------------");
			if(stack != null){
				System.out.println("Stack: ");
				for (StackTraceElement stackTraceElement : stack) {
					System.out.println(stackTraceElement.getClassName() + " | " + stackTraceElement.getMethodName() + " | " + stackTraceElement.getFileName() + " | ");
				}
			}
			System.out.println("------------------------------------------------");
			
			
			/*			
			for(StackTraceElement stackTraceElement : stack) {
				System.out.println(stackTraceElement);
			}
			*/
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
