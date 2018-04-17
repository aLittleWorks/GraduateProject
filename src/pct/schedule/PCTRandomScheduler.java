package pct.schedule;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import pct.datastructure.Action;

public class PCTRandomScheduler implements Runnable{
		
	public int n_; //线程数量
	public int k_; //事件数量
	public int d_; //bug深度
	
	public Vector<Integer> perm;
	public ArrayList<Integer> priorities_;
	public ArrayList<Integer> changePoints_;
	
	public Map<Thread, Integer> threadMId;
	public Map<Thread, Integer> threadMP;
	public Map<Action, Thread> ActionMT;
	public ArrayList<Thread> RunnableThread;
	
	public PCTRandomScheduler(int n, int k, int d) {
		super();
		this.n_ = n;
		this.k_ = k;
		this.d_ = d;
		perm = new Vector<Integer>();
		
		priorities_ = new ArrayList<Integer>();
		changePoints_ = new ArrayList<Integer>();
	}

	public void SetUp(){
		///pi
		for(int i=0; i<n_; i++){
			perm.addElement(i);
		}
		Collections.shuffle(perm);
		
		///thread priorities(p)
		for(int i=0; i<n_; i++){
			priorities_.add(d_ + perm.get(i) - 1);
		}
		
		///changePoints
		Random random = new Random();
		for(int i=0; i<d_; i++){
			changePoints_.add(random.nextInt(k_) + 1);
		}
	}
		
	
	public void Explore(State init_state){
		State state = init_state;
			
		int yieldPriority = 0;
			
		int steps = 0;
		
		int maxElement;
			
		while(!state.equals(State.TERMINATED)){
			maxElement = FindMaxPThread();
		}
		
		
			
			
	}
	
	public ArrayList<Thread> getRunnableThread(ArrayList<Thread> ThreadList){
		ArrayList<Thread> RunnableThreadList = new ArrayList<Thread>();
		for(Thread t : ThreadList){
			if(t.getState().equals(Thread.State.RUNNABLE)){
				RunnableThreadList.add(t);
			}
		}
		return RunnableThreadList;
	}
	
	private int FindMaxPThread(){
		int maxElement = 0;
		int p = -1;
		for(int i=0; i<priorities_.size(); i++){
			if(priorities_.get(i) > p){ 
				maxElement = i;
				p = priorities_.get(i);
			}
		}
		
		return maxElement;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
		
}

