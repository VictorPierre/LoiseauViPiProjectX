package Exceptions;

public class EmptyStationException extends Exception {
	public EmptyStationException() {
		System.out.println("The station is empty");
	}
}
