package bank.exceptions;

public class TransactionDoesNotExistException extends Exception {
    public TransactionDoesNotExistException () {
        super("Transaktion existiert nicht.");
    }
}
