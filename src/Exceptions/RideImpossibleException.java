package Exceptions;

public class RideImpossibleException extends Exception {
	public RideImpossibleException(){
		System.out.println("Ride generation impossible : check is there are enough available stations");
	}
}
