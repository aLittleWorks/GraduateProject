package asm.testmodel.bankmodel2;

public class Bank2 {
	
	private final double[] accounts;
	public static final int NACCOUNT = 5;
	public static final double INITIAL_BALANCE = 100;
	
	public static void main(String[] args) {
		new Bank2(NACCOUNT, INITIAL_BALANCE).run();
	}
	

	public void run(){
		Bank2 bank = new Bank2(NACCOUNT, INITIAL_BALANCE);
		for(int i=0; i<NACCOUNT; i++){
			TransferRunnable r = new TransferRunnable(bank, i, INITIAL_BALANCE);
			Thread t = new Thread(r);
			t.start();
		}
	
	}

	public Bank2(int n, double initialBalance) {
		super();
		accounts = new double[n];
		for(int i=0; i<n; i++){
			accounts[i] = initialBalance;
		}
	}
	
	public synchronized void transfer(int from, int to, double amount) throws InterruptedException{
		while(accounts[from] < amount){
			wait();
		}
		System.out.println(Thread.currentThread());
		accounts[from] -= amount;
		System.out.printf(" %10.2f from %d to %d \n", amount, from, to);
		accounts[to] += amount;
		System.out.printf(" Total Balance:%10.2f%n \n", getTotalBalance());
		notifyAll();
	}
	

	private synchronized double getTotalBalance() {
		double sum = 0;
		for( double a : accounts){
			sum += a;
		}
		return sum;
	}
	
	public int size(){
		return accounts.length;
	}

}

class TransferRunnable implements Runnable {
	private Bank2 bank2;
	private int fromAccount;
	private double maxAmount;
	private int DELAY = 10;
	
	public TransferRunnable(Bank2 bank2, int fromAccount, double maxAmount) {
		super();
		this.bank2 = bank2;
		this.fromAccount = fromAccount;
		this.maxAmount = maxAmount;
	}
	 
	@Override
	public void run() {

		try {
			for(int i=0; i<3; i++){
				
				int toAccount = (int) (bank2.size() * Math.random());
				
				while(toAccount == fromAccount){
					toAccount = (int) (bank2.size() * Math.random());
				}
				
				double amount = maxAmount * Math.random();
				bank2.transfer(fromAccount, toAccount, amount);
				
				Thread.sleep((int)(DELAY * Math.random()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
