package bank;

/**
 * Klasse für Ein- bzw. Auszahlungen erbt von der Klasse {@link Transaction}
 *
 * @author Linus Palm
 */
public class Payment extends Transaction {

    /**
     * Zinsen, die bei einer Einzahlung anfallen
     */
    private double incomingInterest = 0.;

    /**
     * Zinsen, die bei einer Auszahlung anfallen
     */
    private double outgoingInterest = 0.;

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     */
    public Payment(String date, double amount, String description) {
        super(date,amount,description);
    }

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     * @param incomingInterest Zinsen, die bei einer Einzahlung anfallen
     * @param outgoingInterest Zinsen, die bei einer Auszahlung anfallen
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest){
        this(date,amount,description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse Payment
     */
    public Payment(Payment other){
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getIncomingInterest(), other.getOutgoingInterest());
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
    public String toString() {
        return super.toString() + "\nAnfallende Zinsen bei Einzahlung: " + getIncomingInterest()*100 + "%" + "\nAnfallende Zinsen bei Auszahlung: " + getOutgoingInterest()*100 + "%";
    }

    /**
     *  Vergleichsmethode
     *
     * @param other zu vergleichendes Objekt der Klasse Payment
     * @return Wahrheitswert, ob die Objekte gleich sind oder nicht
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Payment)) return false;
        Payment that = (Payment) other;
        return super.equals(other) && this.getIncomingInterest() == that.getIncomingInterest() && this.getOutgoingInterest() == that.getOutgoingInterest();
    }

    /**
     * Methode zur Berechnung, des zu verbuchenden Geldbetrages
     *
     * @return Errechneter Betrag
     */
    public double calculate() {
        if(getAmount() > 0.){
            return getAmount() * (1 - getIncomingInterest());
        }
        else
        {
            return getAmount() * (1 + getOutgoingInterest());
        }
    }
}
