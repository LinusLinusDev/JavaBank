package bank.exceptions;

/**
 * Exception die von der Klasse {@link bank.PrivateBank} geworfen werden kann.
 */
public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException () {
        super("Konto existiert bereits.");
    }
}
