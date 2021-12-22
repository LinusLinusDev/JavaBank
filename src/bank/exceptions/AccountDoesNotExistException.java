package bank.exceptions;

/**
 * Exception die von der Klasse {@link bank.PrivateBank} geworfen werden kann.
 */
public class AccountDoesNotExistException extends Exception {
    public AccountDoesNotExistException() {
        super("Konto existiert nicht.");
    }
}