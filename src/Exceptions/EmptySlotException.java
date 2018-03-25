package Exceptions;

public class EmptySlotException extends Exception{
	public EmptySlotException(int i){
		System.out.println("The slot "+i+" is empty");
	}
	public EmptySlotException(){
		System.out.println("The slot is empty");
	}
}
