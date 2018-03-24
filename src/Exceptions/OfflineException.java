package Exceptions;

public class OfflineException extends Exception{
	public OfflineException(){
		System.out.println("The station is offline!");
	}
}
