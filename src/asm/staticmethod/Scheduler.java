package asm.staticmethod;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import org.omg.CORBA.INTF_REPOS;


public class Scheduler extends ThreadCheck {
	
	private synchronized static void SetUp(int n_, int k_, int d_){
		
		synchronized(Thread.currentThread()){
		System.out.println("Setup :");
		///pi
		for(int i=1; i<=n_; i++){
			perm.addElement(i);
		}
		Collections.shuffle(perm);
		
		System.out.println("Pi: ");
		for (int i = 0; i < n_; i++) {
			System.out.print(perm.get(i) + " ");
		}
		System.out.println();
		
		///thread priorities(p)
		for(int i=0; i<n_; i++){
			priorities_.add(d_ + perm.get(i) - 1);
		}
		
		System.out.println("priotities: ");
		for (int i = 0; i < n_; i++) {
			System.out.print(priorities_.get(i) + " ");
		}
		System.out.println();
		
		///changePoints
		Random random = new Random();
		for(int i=0; i<d_-1; i++){
			changePoints_.add(random.nextInt(k_) + 1);
		}
		}
	}
	
	public synchronized static void initial(){
		if(flag){
			flag = false;
			for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
				Thread thd = stackTrace.getKey();
				
				if( !thd.getName().equals("Attach Listener") && 
					!thd.getName().equals("Reference Handler") &&
					!thd.getName().equals("Finalizer") && 
					!thd.getName().equals("Signal Dispatcher") &&
					!thd.getName().equals("DestroyJavaVM") && 
					!thd.getName().equals("main")){
					addThreadToList(thd);
				}			
			}
			
			n = threadList.size();
			int k_ = k;
			if(ActionList.size() * threadList.size() != 0){
				k_ = ActionList.size() * 2 * threadList.size() * 5 /3;
			}
			
			System.out.println("k = " + k_ + ", n = " + n + ", d = " + d);	
			
			System.out.println("==========initial============");
			SetUp(n, k, d);
			if(!changePoints_.isEmpty()){	
				System.out.println("change points:");
				for(int i : changePoints_){
					System.out.print(i + " ");
				}
			}
			System.out.println();
			printThread();
			System.out.println("===========initial end=============");
		}//if(flag)
	}
	
	//synchronized 会导致除了当前线程以外的线程阻塞
	public static void PCTScheduler(){
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + Thread.currentThread());
		Thread currentThread = Thread.currentThread();
		currentThread.setPriority(Thread.NORM_PRIORITY);
		synchronized(currentThread){
			currentThread.notifyAll();
		}
		
/*		if(flag)
			initial();*/
		//printThread();
		synchronized (currentThread) {
			while(!isMaxPThread(currentThread)){				
				Thread.yield();
			}
			currentThread.setPriority(Thread.MAX_PRIORITY);
		}//synchronized
		
		synchronized (currentThread) {
			
			printThread();
			
			System.out.println("Current execute : " + currentThread.getName());
		
			if(hasEnable() || step > 0){
				++ step;
				System.out.println("step at " + step);
			}
		
			for(int i=1; i<=changePoints_.size(); i++){
				if(step == changePoints_.get(i-1)){
					System.out.println("reach a change point at " + step);
			
					int p = priorities_.get(findThread(currentThread));
					int np = d-i;
			
					System.out.println("change " + currentThread.getName()
						+ " priority " + p + " to " + np);
			
					int it = findThread(currentThread);
					if(it != -1){
						priorities_.set(it, np); //d = 2
					}else{
						System.out.println("cannot find currentthread!");
					}
				
					currentThread.setPriority(Thread.MIN_PRIORITY);
					//Thread.yield();
					try {
						currentThread.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}//if
			}//for
			printThread();
		}//synchronized
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + Thread.currentThread());
		
	}
	
	public static void TheadYield(){
		Thread currentThread = Thread.currentThread();
		synchronized (currentThread) {
			while(!isMaxPThread(currentThread)){
				Thread.yield();
			}
		}
	}
	
	public static void foo(){
		System.out.println(Thread.currentThread().getName());
	}
	
}
