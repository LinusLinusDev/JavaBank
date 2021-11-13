package bank;

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
    private HashMap<String, List<Transaction>> accountsToTransactions = new HashMap<String, List<Transaction>>();

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
}
