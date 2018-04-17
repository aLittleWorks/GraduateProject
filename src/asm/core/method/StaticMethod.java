package asm.core.method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import pct.datastructure.Action;

public class StaticMethod {
	
	public static volatile ArrayList<Thread> threadList = new ArrayList<Thread>();
	public static volatile ArrayList<Action> ActionList = new ArrayList<Action>();
	public static Vector<Integer> perm = new Vector<Integer>();
	public static ArrayList<Integer> priorities_ = new ArrayList<Integer>();
	public static ArrayList<Integer> changePoints_ = new ArrayList<Integer>();
	public static int step = 0;
	public static int k = 40;
	public static int d = 2;
	public static int n = 0;
	public static boolean flag = true; //用于指示是否第一次调用本方法
	
	public synchronized static void SetUp(int n_, int k_, int d_){
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
	
	public static void acqmsg(){
		System.out.println("acquire a sychronize lock ...");
	}
	
	public static void relmsg(){
		System.out.println("release a sychronize lock ...");
	}
	
	public static void checkMethod(){
		System.out.println("check ...");
	}
	
	public static void lockaction(){
		System.out.println("Here is a lock or an unlock action...");
	}
	
	public static Thread getMaxPThread(){
		Thread thread = threadList.get(0);
		for(Thread t : threadList){
			if(t.getPriority() > thread.getPriority()){
				thread = t;
			}
		}
		return thread;
	}
	
	public static boolean hasEnable(){
		for(Thread thread : threadList){
			if(!thread.getState().equals(Thread.State.TERMINATED))
				return true;
		}
		return false;
	}
	
	public static int findThread(Thread t){
		for(int i=0; i<threadList.size(); i++){
			if(t.equals(threadList.get(i))){
				return i;
			}
		}
		return -1;
	}
	
	//插入到事件之前 可能需要锁
	public synchronized static void letThreadYield(){
		
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Thread currentThread = Thread.currentThread();
		//synchronized(currentThread){
		synchronized(currentThread){
			currentThread.notifyAll();
		}
		if(flag){
			flag = false;
			
			/*
			 * 在这里插入获取线程信息？
			 */
			for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
				Thread thd = stackTrace.getKey();
				
				if( !thd.getName().equals("Attach Listener") && 
					!thd.getName().equals("Reference Handler") &&
					!thd.getName().equals("Finalizer") && 
					!thd.getName().equals("Signal Dispatcher") &&
					!thd.getName().equals("DestroyJavaVM") && 
					!thd.getName().equals("main")){
					StaticMethod.addThreadToList(thd);
				}			
			}
			
			n = threadList.size();

			if(ActionList.size() * threadList.size() != 0){
				k = ActionList.size() * threadList.size();
			}
			
			System.out.println("k = " + k + ", n = " + n + ", d = " + d);	
			
			SetUp(n, k, d);
			
			System.out.println("==========initial============");
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
		

		//}//synchronized
		
		//synchronized(currentThread){
		printThread();
			//System.out.println("Before check the p");
			/*while(!isMaxPThread(currentThread)){
				//保证当前执行的是优先权最高的线程
				//System.out.println("current thread yield");
				//Thread.yield();
				try {
					System.out.println("current thread wait");
					currentThread.notifyAll();
					currentThread.wait();
					currentThread.notifyAll();
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
			}
			*/
			
			//synchronized(currentThread){
			while(!isMaxPThread(currentThread)){
				//synchronized(currentThread){
					currentThread.notifyAll();
				//}
				try {
					System.out.println(currentThread.getName() +" sleep");
					//currentThread.wait(500);
					//System.out.println("current thread wait");
					//currentThread.notifyAll();
					Thread.sleep(500);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				//}
			}
			
			//}
			System.out.println("Current execute : " + currentThread.getName());
		
			if(hasEnable() || step > 0){
				++ step;
				System.out.println("step at " + step);
			}
		
		//synchronized (currentThread) {
			
		
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
					try {
						currentThread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			//currentThread.notifyAll();
	
		printThread();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public static boolean isMaxPThread(Thread currentThread){
		int it = findThread(currentThread);
		for(int i=0; i<priorities_.size(); i++){
			if(i == it)continue;
			//System.out.println(currentThread.getName() + "-" + priorities_.get(it) + "mp: " + priorities_.get(i));
			if(priorities_.get(i) > priorities_.get(it)){
				return false;
			}
		}
		return true;
	}
	
	public static void addThreadToList(Thread t){
		if(!threadList.contains(t)){
			threadList.add(t);
		}
	}
	
	public synchronized static void addThreadToList() throws InterruptedException{
		
		Thread t = Thread.currentThread();
		
		if(!threadList.contains(t)){
			System.out.println("[add thread]:");
			threadList.add(t);
			//Thread.yield();
			printThread();
		}else{
			//cannnot run to here
			System.out.println("Cannot run to here unless this method execute twice");
			//printThread();
		}
		
	}
	
	public synchronized static void printThread(){
		if(threadList.isEmpty()){
			//System.out.println("ThreadList is Empty！");
			System.out.println("|---------------------------------");
			for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
				Thread thd = stackTrace.getKey();
				
				if( !thd.getName().equals("Attach Listener") && 
					!thd.getName().equals("Reference Handler") &&
					!thd.getName().equals("Finalizer") && 
					!thd.getName().equals("Signal Dispatcher") &&
					!thd.getName().equals("DestroyJavaVM") && 
					!thd.getName().equals("main")){
					System.out.println("|" + thd.getName() + " - " +  thd.getState() +"|");
				}			
			}
			System.out.println("---------------------------------|");
			return;
		}
		System.out.println("---------------------------------");
		for(Thread thread : threadList){
			System.out.println("|" + thread.getName() + " - " + priorities_.get(findThread(thread)) + "-" + thread.getState()  +  "|");
		}
		System.out.println("---------------------------------");
	}
	
	public synchronized static void printThread2(){
		if(threadList.isEmpty()){
			//System.out.println("ThreadList is Empty！");
			System.out.println("|---------------------------------|");
			for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
				Thread thd = stackTrace.getKey();
				
				if( !thd.getName().equals("Attach Listener") && 
					!thd.getName().equals("Reference Handler") &&
					!thd.getName().equals("Finalizer") && 
					!thd.getName().equals("Signal Dispatcher") &&
					!thd.getName().equals("DestroyJavaVM") && 
					!thd.getName().equals("main")){
					System.out.println("|" + thd.getName() + " - " +  thd.getState() +"|");
				}			
			}
			System.out.println("|---------------------------------|");
			return;
		}
		System.out.println("|---------------------------------|");
		for(Thread thread : threadList){
			System.out.println("|" + thread.getName() + " - " + priorities_.get(findThread(thread)) + "-" + thread.getState()  +  "|");
		}
		System.out.println("|---------------------------------|");
	}
	
	public synchronized static void clearThreadList(){
		for(int i=0; i<threadList.size(); i++){
			threadList.remove(i);
		}
	}
	
	public synchronized static void printActionList(){
		
		if(ActionList.isEmpty()){
			System.out.println("ActionList is Empty！");
			return;
		}
		for(Action a : ActionList){
			System.out.print("[" + a.ActionId + ";" + a.Opcode_ + "] ");
		}
		System.out.println();
		
	}
	
	public synchronized static void clearActionList(){
		for(int i=0; i<ActionList.size(); i++){
			ActionList.remove(i);
		}
	}
	
	public synchronized static void checkThreadState(){
		Thread t = Thread.currentThread();
		System.out.println(t + " : " + t.getState());
		if(t.getState().equals(Thread.State.WAITING)){
			System.out.println("Current Thread is WAITING! Let's yield!");
			//t.yield();
		}
	}
	
	public synchronized static void printCallStack() {
		//Throwable ex = new Throwable();
        //StackTraceElement[] stackElements = ex.getStackTrace();
        StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
        
        System.out.println("===current thread: " + Thread.currentThread().getName() + "========");
        System.out.println("================print Stack=================");
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                System.out.print(stackElements[i].getClassName()+" | ");
                System.out.print(stackElements[i].getFileName()+" | ");
                System.out.print(stackElements[i].getLineNumber()+" | ");
                System.out.println(stackElements[i].getMethodName());
               // System.out.println("-----------------------------------");
            }
        }
        System.out.println("===================end Stack==================");
	}
	
}
