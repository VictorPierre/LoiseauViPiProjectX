package Exceptions;

public class OutOfOrderException extends Exception {
	public OutOfOrderException(int i) {
		System.out.println("The slot "+i+" is out of order!");
	}
	public OutOfOrderException() {
		System.out.println("The slot is out of order!");
	}
}
