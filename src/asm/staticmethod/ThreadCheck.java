package asm.staticmethod;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import pct.datastructure.Action;

public class ThreadCheck {
	
	public static ArrayList<Thread> threadList = new ArrayList<Thread>();
	public static ArrayList<Action> ActionList = new ArrayList<Action>();
	
	public static Vector<Integer> perm = new Vector<Integer>();
	public static ArrayList<Integer> changePoints_ = new ArrayList<Integer>();
	public static ArrayList<Integer> priorities_ = new ArrayList<Integer>();
	
	public static int step = 0;
	public static int k=40, d=2, n=0;
	
	public static volatile boolean flag = true;	//用于指示是否第一次调用本方法
	
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
	
	
	public synchronized static int findThread(Thread t){
		for(int i=0; i<threadList.size(); i++){
			if(t.equals(threadList.get(i))){
				return i;
			}
		}
		System.out.println("cannot find thread " + t);
		return -1;
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
	
	public static void addThreadToList(Thread t){
		if(!threadList.contains(t)){
			threadList.add(t);
		}
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
	
	public static boolean isMaxPThread(Thread currentThread){
		int it = findThread(currentThread);
		for(int i=0; i<priorities_.size(); i++){
			if(i == it)continue;
			if(priorities_.get(i) > priorities_.get(it)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasEnable(){
		for(Thread thread : threadList){
			if(!thread.getState().equals(Thread.State.TERMINATED))
				return true;
		}
		return false;
	}
	
}
