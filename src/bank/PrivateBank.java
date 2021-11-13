package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

import java.util.*;

public class PrivateBank implements Bank {

    /**
     * Name der Bank
     */
    private String name = "";

    /**
     * Zinsen, die bei einer Einzahlung anfallen
     */
    private double incomingInterest = 0.;

    /**
     * Zinsen, die bei einer Auszahlung anfallen
     */
    private double outgoingInterest = 0.;

    /**
     * Verknüpfung von Konten mit Transaktionen
     */
    private Map<String, ArrayList<Transaction>> accountsToTransactions = new HashMap<String, ArrayList<Transaction>>();

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param name Name der Bank
     * @param incomingInterest Zinsen, die bei einer Einzahlung anfallen
     * @param outgoingInterest Zinsen, die bei einer Auszahlung anfallen
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest){
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse PrivateBank
     */
    public PrivateBank(PrivateBank other){
        this(other.getName(), other.getIncomingInterest(), other.getOutgoingInterest());
    }

    /**
     * Gettermethode
     *
     * @return Name der Bank
     */
    public String getName() {
        return name;
    }

    /**
     * Gettermethode
     *
     * @return Zinsen, die bei einer Einzahlung anfallen
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Gettermethode
     *
     * @return Zinsen, die bei einer Auszahlung anfallen
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }


    /**
     * Settermethode
     *
     * @param name Name der Bank
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Settermethode
     *
     * @param incomingInterest Zinsen, die bei einer Einzahlung anfallen
     */
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    /**
     * Settermethode
     *
     * @param outgoingInterest Zinsen, die bei einer Auszahlung anfallen
     */
    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Ausgabemethode
     *
     * @return Liste aller Attribute mit ihren Werten als String
     */
    public String toString(){
        return "Name der Bank: "+ getName() + "\nAnfallende Zinsen bei Einzahlung: " + getIncomingInterest()*100 + " %" + "\nAnfallende Zinsen bei Auszahlung: " + getOutgoingInterest()*100 + " %";
    } // Inhalt der HashMap ebenfalls ausgeben?

    /**
     *  Vergleichsmethode
     *
     * @param other zu vergleichendes Objekt der Klasse PrivateBank
     * @return Wahrheitswert, ob die Objekte gleich sind oder nicht
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PrivateBank)) return false;
        PrivateBank that = (PrivateBank) other;
        return this.getName() == that.getName() && this.getIncomingInterest() == that.getIncomingInterest() && this.getOutgoingInterest() == that.getOutgoingInterest() && this.accountsToTransactions == that.accountsToTransactions;
    }

    /**
     * Fügt einen neues Konto zu der Bank hinzu. Sollte das Konto bereits existieren, wird eine Exception geworfen.
     *
     * @param account das Konto, das hinzugefügt werden soll
     * @throws AccountAlreadyExistsException sollte das Konto bereits existieren
     */
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if(accountsToTransactions.containsKey(account))throw new AccountAlreadyExistsException();
        accountsToTransactions.put(account, new ArrayList<>());
    }

    /**
     * Fügt einen neues Konto (inklusive einer Liste von Transaktionen) zu der Bank hinzu. Sollte das Konto bereits existieren, wird eine Exception geworfen.
     *
     * @param account das Konto, das hinzugefügt werden soll
     * @throws AccountAlreadyExistsException sollte das Konto bereits existieren
     */
    public void createAccount(String account, ArrayList<Transaction> transactions) throws AccountAlreadyExistsException {
        if(accountsToTransactions.containsKey(account))throw new AccountAlreadyExistsException();
        accountsToTransactions.put(account, transactions);
    }

    /**
     * Fügt eine Transaktion zu einem Konto hinzu. Sollte das Konto nicht existieren, wird eine Exception geworfen.
     * Sollte die Transaktion bereits existieren, wird eine Exception geworfen.
     *
     * @param account     das Konto, zu dem die Transaktion hinzugefügt werden soll
     * @param transaction die Transaktion, die zu dem Konto hinzugefügt werden soll
     * @throws TransactionAlreadyExistException sollte die Transaktion bereits existieren
     * @throws AccountDoesNotExistException sollte das Konto nicht existieren
     */
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException {
        if(!accountsToTransactions.containsKey(account))throw new AccountDoesNotExistException();
        if(accountsToTransactions.get(account).contains(transaction)) throw new TransactionAlreadyExistException();
        if(transaction instanceof Payment){
            ((Payment) transaction).setIncomingInterest(this.getIncomingInterest());
            ((Payment) transaction).setOutgoingInterest(this.getOutgoingInterest());
        }
        accountsToTransactions.get(account).add(transaction);
    }

    /**
     * Entfernt eine Transaktion von einem Konto. Sollte die Transaktion nicht existieren, wird eine Exception geworfen.
     *
     * @param account     das Konto, von dem die Transaktion entfernt werden soll
     * @param transaction die Transaktion, die von dem Konto entfernt werden soll
     * @throws TransactionDoesNotExistException sollte die Transaktion nicht existieren
     */
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException {
        if(!accountsToTransactions.get(account).contains(transaction)) throw new TransactionDoesNotExistException();
        accountsToTransactions.get(account).remove(transaction);
    }

    /**
     * Prüft, ob die angegebene Transaktion in dem angegebenen Konto vorkommt
     *
     * @param account     das Konto, dass kontrolliert wird
     * @param transaction die Transaktion, die gesucht wird
     * @return Wahrheitswert, ob die Transaktion vorkommt
     */
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Berechnet den aktuellen Kontostand
     *
     * @param account das ausgewählte Konto
     * @return der aktuelle Kontostand
     */
    public double getAccountBalance(String account) {
        double sum = 0;
        for(Transaction n:accountsToTransactions.get(account)){
            if(n instanceof Transfer && account.equals(((Transfer) n).getSender())){
                sum -= n.calculate();
            }
            else sum += n.calculate();
        }
        return sum;
    }

    /**
     * Gibt eine Liste mit allen Transaktionen eines Kontos zurück
     *
     * @param account ausgewähltes Konto
     * @return Liste der Transaktionen
     */
    public ArrayList<Transaction> getTransactions(String account){
        return accountsToTransactions.get(account);
    }

    /**
     * Gibt eine nach der Geldmenge sortierte Liste aller Transaktionen eines Kontos zurück. Sortiert die Liste entweder auf- oder absteigend
     *
     * @param account ausgewähltes Konto
     * @param asc     Auswahl, ob die Liste aufsteigend oder absteigend zurückgegeben werden soll
     * @return sortierte Liste der Transaktionen
     */
    public ArrayList<Transaction> getTransactionsSorted(String account, boolean asc) {
        return null;
    }

    /**
     * Gibt eine Liste aller positiven oder negativen Transaktionen zurück
     *
     * @param account  ausgewähltes Konto
     * @param positive Auswahl, ob in der Liste positive oder negative Transaktionen vorkommen sollen
     * @return Liste der Transaktionen
     */
    public ArrayList<Transaction> getTransactionsByType(String account, boolean positive) {
        return null;
    }
}
