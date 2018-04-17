package asm.testmodel.bankmodel;


public class TransferRunnable implements Runnable {
	private Bank bank;
	private int fromAccount;
	private double maxAmount;
	private int DELAY = 10;
	
	public TransferRunnable(Bank bank, int fromAccount, double maxAmount) {
		super();
		this.bank = bank;
		this.fromAccount = fromAccount;
		this.maxAmount = maxAmount;
	}

	@Override
	public void run() {
		
	
		try {
			for(int i=0; i<5; i++){
				
				int toAccount = (int) (bank.size() * Math.random());
			
				while(toAccount == fromAccount){
					toAccount = (int) (bank.size() * Math.random());
				}
				
				double amount = maxAmount * Math.random();
				bank.transfer(fromAccount, toAccount, amount);
				
				Thread.sleep((int)(DELAY * Math.random()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
