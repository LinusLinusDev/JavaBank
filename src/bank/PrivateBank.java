package bank;

import bank.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Klasse, die eine Bank abstrahiert. Verwaltet und verarbeitet Konten und Transaktionen. Implementiert {@link Bank}.
 * In dieser Klasse ist getAccountBalance mittels Vererbung implementiert.
 *
 * @author Linus Palm
 */
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
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    /**
     * Speicherort der Konten
     */
    private String directoryName = "";

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param name Name der Bank
     * @param incomingInterest Zinsen, die bei einer Einzahlung anfallen
     * @param outgoingInterest Zinsen, die bei einer Auszahlung anfallen
     * @param directoryName Speicherort der Konten
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        setDirectoryName(directoryName);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse PrivateBank
     */
    public PrivateBank(PrivateBank other){
        this(other.getName(), other.getIncomingInterest(), other.getOutgoingInterest(), other.getDirectoryName());
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
     * Gettermethode
     *
     * @return Name des Speicherortes der Konten
     */
    public String getDirectoryName(){
        return directoryName;
    }


    /**
     * Gettermethode
     *
     */
    public String getDirectory(){
        return new File("").getAbsolutePath() + "\\savefiles\\" + directoryName;
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
     * Settermethode, erstellt ein Verzeichnis zum Speichern der persistierten Konten, falls der angegebene Speicherort nicht leer ist und liest persistierte Konten ein, falls das Verzeichnis bereits existiert.
     *
     * @param directoryName Speicherort der Konten
     */
    public void setDirectoryName(String directoryName) {
        if(directoryName!="") {
            this.directoryName = directoryName;
            File dir = new File(getDirectory());
            if (dir.exists()) {
                try {
                    readAccounts();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else dir.mkdir();
        }
    }

    /**
     * Ausgabemethode
     *
     * @return Liste aller Attribute mit ihren Werten als String
     */
    public String toString(){
        String accountsAndTransactions = "\nTransaktionen: \n\n";
        for(var n:accountsToTransactions.entrySet()) {
            accountsAndTransactions += "- Account: " + n.getKey() + "\n";
            for(Transaction t:n.getValue()){
                accountsAndTransactions += t + "\n\n";
            }
        }
        return "Name der Bank: "+ getName() + "\nAnfallende Zinsen bei Einzahlung: " + getIncomingInterest()*100 + " %" + "\nAnfallende Zinsen bei Auszahlung: " + getOutgoingInterest()*100 + " %" + "\nSpeicherort: " + getDirectoryName() + accountsAndTransactions;
    }

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
        return this.getName().equals(that.getName()) && this.getIncomingInterest() == that.getIncomingInterest() && this.getOutgoingInterest() == that.getOutgoingInterest() && this.accountsToTransactions.equals(that.accountsToTransactions) && this.getDirectoryName().equals(that.getDirectoryName());
    }

    /**
     * Liest alle vorhandenen Konten und deren Transaktionen aus dem Dateisystem ein und stellt sie dem PrivateBank-Objekt in accountsToTransactions zur Verfügung.
     * Jeder Account, der Payment-Objekte enthält, wir nach dem Einlesen direkt überschrieben, um unterschiedliche Incoming- bzw. OutgoingInterest-Werte zu vermeiden.
     *
     * @throws IOException wird geworfen, wenn kein entsprechendes Verzeichnis existiert.
     */
    private void readAccounts() throws IOException {
        accountsToTransactions.clear();
        File dir = new File(getDirectory());
        if (!dir.exists()) throw new IOException("Directory not found.");
        File[] fileArray = dir.listFiles();
        for(File file:fileArray){
            if (file.getName().startsWith("Konto ") && file.getName().endsWith(".json")){
                String input = Files.readString(Paths.get(file.getPath()));
                String name = file.getName().substring(6,file.getName().lastIndexOf('.'));

                if(input==""){
                    accountsToTransactions.put(name,new ArrayList<Transaction>());
                }

                else {
                    boolean override = false;

                    GsonBuilder deserializer = new GsonBuilder();
                    deserializer.registerTypeAdapter(Transaction.class, new customSerializer());
                    Gson customDeserializer = deserializer.create();

                    ArrayList<Transaction> transactions = customDeserializer.fromJson(input, new TypeToken<ArrayList<Transaction>>() {}.getType());

                    for(Transaction transaction:transactions) {
                        if(transaction instanceof Payment){
                            ((Payment) transaction).setIncomingInterest(this.getIncomingInterest());
                            ((Payment) transaction).setOutgoingInterest(this.getOutgoingInterest());
                            override = true;
                        }
                    }

                    accountsToTransactions.put(name, transactions);

                    if(override)writeAccount(name);
                }
            }
        }
    }

    /**
     * Persistiert das angegebene Konto im Dateisystem, falls der angegebene Speicherort nicht leer ist.
     *
     * @param account Name des zu persistierenden Kontos
     * @throws IOException wird geworfen, wenn kein entsprechendes Verzeichnis existiert.
     */
    private void writeAccount(String account) throws IOException {
        if(getDirectoryName()=="")return;
        File file = new File(getDirectory());
        if(!file.exists()) throw new IOException("Directory not found.");
        file = new File(getDirectory()+"\\Konto "+account+".json");

        if(!file.exists())file.createNewFile();
        if(accountsToTransactions.get(account).isEmpty())return;

        GsonBuilder serializer = new GsonBuilder();
        serializer.registerTypeHierarchyAdapter(Transaction.class,new customSerializer());
        //serializer.registerTypeAdapter(Payment.class,new customSerializer());
        //serializer.registerTypeAdapter(IncomingTransfer.class,new customSerializer());
        //serializer.registerTypeAdapter(OutgoingTransfer.class,new customSerializer());
        Gson customSerializer = serializer.create();

        /*String output="[";

        for(int i=0;i<accountsToTransactions.get(account).size();i++){
            output += customSerializer.toJson(accountsToTransactions.get(account).get(i));
            if(i<accountsToTransactions.get(account).size()-1)output += ",";
        }

        output += "]";*/

        String output = customSerializer.toJson(accountsToTransactions.get(account));

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(output);
        writer.close();
    }

    /**
     * Fügt ein neues Konto zu der Bank hinzu. Sollte das Konto bereits existieren, wird eine Exception geworfen.
     *
     * @param account das Konto, das hinzugefügt werden soll
     * @throws AccountAlreadyExistsException sollte das Konto bereits existieren
     */
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if(accountsToTransactions.containsKey(account))throw new AccountAlreadyExistsException();
        accountsToTransactions.put(account, new ArrayList<>());
        writeAccount(account);
    }

    /**
     * Fügt ein neues Konto (inklusive einer Liste von Transaktionen) zu der Bank hinzu. Sollte das Konto bereits existieren, wird eine Exception geworfen.
     *
     * @param account das Konto, das hinzugefügt werden soll
     * @throws AccountAlreadyExistsException sollte das Konto bereits existieren
     */
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, IOException {
        if(accountsToTransactions.containsKey(account))throw new AccountAlreadyExistsException();
        accountsToTransactions.put(account, new ArrayList<>());
        for(Transaction n : transactions){
            try {
                this.addTransaction(account,n);
            } catch (TransactionAlreadyExistException | AccountDoesNotExistException e) {
                e.printStackTrace();
            }
        }
        writeAccount(account);
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        if(!accountsToTransactions.containsKey(account))throw new AccountDoesNotExistException();
        if(this.containsTransaction(account,transaction)) throw new TransactionAlreadyExistException();
        if(transaction instanceof Payment){
            Payment other = new Payment((Payment) transaction);
            other.setIncomingInterest(this.getIncomingInterest());
            other.setOutgoingInterest(this.getOutgoingInterest());
            accountsToTransactions.get(account).add(other);
        }
        else accountsToTransactions.get(account).add(transaction);
        writeAccount(account);
    }

    /**
     * Entfernt eine Transaktion von einem Konto. Sollte die Transaktion nicht existieren, wird eine Exception geworfen.
     *
     * @param account     das Konto, aus dem die Transaktion entfernt werden soll
     * @param transaction die Transaktion, die von dem Konto entfernt werden soll
     * @throws TransactionDoesNotExistException sollte die Transaktion nicht existieren
     */
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException, IOException {
        if(transaction instanceof Payment){
            ((Payment) transaction).setIncomingInterest(this.getIncomingInterest());
            ((Payment) transaction).setOutgoingInterest(this.getOutgoingInterest());
        }
        if(!accountsToTransactions.get(account).contains(transaction)) throw new TransactionDoesNotExistException();
        accountsToTransactions.get(account).remove(transaction);
        writeAccount(account);
    }

    /**
     * Prüft, ob die angegebene Transaktion in dem angegebenen Konto vorkommt
     *
     * @param account     das Konto, dass kontrolliert wird
     * @param transaction die Transaktion, die gesucht wird
     * @return Wahrheitswert, ob die Transaktion vorkommt
     */
    public boolean containsTransaction(String account, Transaction transaction) {
        if(transaction instanceof Payment){
            ((Payment) transaction).setIncomingInterest(this.getIncomingInterest());
            ((Payment) transaction).setOutgoingInterest(this.getOutgoingInterest());
        }
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Berechnet den aktuellen Kontostand
     *
     * @param account das ausgewählte Konto
     * @return der aktuelle Kontostand
     */
    public double getAccountBalance(String account) {
        double sum = 0.;
        for(Transaction n:accountsToTransactions.get(account)){
            sum += n.calculate();
        }
        return sum;
    }

    /**
     * Gibt eine Liste mit allen Transaktionen eines Kontos zurück
     *
     * @param account ausgewähltes Konto
     * @return Liste der Transaktionen
     */
    public List<Transaction> getTransactions(String account){
        return accountsToTransactions.get(account);
    }

    /**
     * Gibt eine nach der Geldmenge sortierte Liste aller Transaktionen eines Kontos zurück. Sortiert die Liste entweder auf- oder absteigend
     *
     * @param account ausgewähltes Konto
     * @param asc     Auswahl, ob die Liste aufsteigend oder absteigend zurückgegeben werden soll
     * @return sortierte Liste der Transaktionen
     */
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> list = new ArrayList<>(accountsToTransactions.get(account));
        list.sort((transaction1, transaction2) -> {
            if (asc) {
                return (int) (transaction1.calculate() - transaction2.calculate());
            } else {
                return (int) (transaction2.calculate() - transaction1.calculate());
            }
        });
        return list;
    }

    /**
     * Gibt eine Liste aller positiven oder negativen Transaktionen zurück
     *
     * @param account  ausgewähltes Konto
     * @param positive Auswahl, ob in der Liste positive oder negative Transaktionen vorkommen sollen
     * @return Liste der Transaktionen
     */
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> list = new ArrayList<>();
        if(positive){
            for(Transaction n:accountsToTransactions.get(account)){
                if(n.calculate()>=0)list.add(n);
            }
        }
        else{
            for(Transaction n:accountsToTransactions.get(account)){
                if(n.calculate()<0)list.add(n);
            }
        }
        return list;
    }
}
