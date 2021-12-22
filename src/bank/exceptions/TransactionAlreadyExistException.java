package bank.exceptions;

/**
 * Exception die von der Klasse {@link bank.PrivateBank} geworfen werden kann.
 */
public class TransactionAlreadyExistException extends Exception {
    public TransactionAlreadyExistException () {
        super("Transaktion existiert bereits.");
    }
}
