package Exceptions;

public class FullSlotException extends Exception{
	public FullSlotException(int i){
		System.out.println("The slot "+i+" is already full!");
	}
	public FullSlotException(){
		System.out.println("The slot is already full!");
	}
}
