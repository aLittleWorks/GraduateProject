package asm.testmodel.bankmodel;

public class Main_Entry {

	
	public static final int NACCOUNT = 5;
	public static final double INITIAL_BALANCE = 500;

	public static void main(String[] args) {
		new Main_Entry().run();
		//StaticMethod.printThread();
	}

	public void run(){
		Bank bank = new Bank(NACCOUNT, INITIAL_BALANCE);
		for(int i=0; i<NACCOUNT; i++){
			TransferRunnable r = new TransferRunnable(bank, i, INITIAL_BALANCE);
			Thread t = new Thread(r);
			t.start();
		}

	}
}
