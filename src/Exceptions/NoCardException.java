package Exceptions;

public class NoCardException extends Exception {
	public NoCardException() {
		System.out.println("A card is required for this operation !");
	}
}
