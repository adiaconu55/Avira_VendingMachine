package Avira_VendingMachine;

import java.util.ArrayList;

public class AviraPrime {

	private int number;	
	
	public AviraPrime(int number) {
		this.number = number;
	}
	
	public void add(int number) {
		if(this.number+number>10)
			this.number=10;
		else
			this.number+=number;
	}
	
	public void takeOne() {
		number--;
	}
	
	public int getQuantity() {
		return number;
	}
	
	public void show() {
		System.out.print(number+" ");
	}
	
}
