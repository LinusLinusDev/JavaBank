package bank;

/**
 * abstrakte Klasse für Transaktionen implementiert das Interface {@link CalculateBill}
 *
 * @author Linus Palm
 */
public abstract class Transaction implements CalculateBill{

    /**
     * Datum der Transaktion im Format DD.MM.YYYY
     */
    protected String date;

    /**
     *  Geldmenge die bei der Transaktion verbucht wird
     */
    protected double amount;

    /**
     *  Zusätzliche Beschreibung des Vorgangs
     */
    protected String description;

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     */
    public Transaction(String date, double amount, String description){
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    /**
     * Gettermethode
     *
     * @return Datum der Transaktion im Format DD.MM.YYYY
     */
    public String getDate(){
        return date;
    }

    /**
     * Gettermethode
     *
     * @return Geldmenge die bei der Transaktion verbucht wird
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gettermethode
     *
     * @return Zusätzliche Beschreibung des Vorgangs
     */
    public String getDescription() {
        return description;
    }

    /**
     * Settermethode
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Settermethode
     *
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Settermethode
     *
     * @param description Zusätzliche Beschreibung des Vorgangs
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Ausgabemethode
     *
     * @return Liste aller Attribute mit ihren Werten als String
     */
    public String toString(){
        return "Datum: "+ getDate() + "\nBetrag: " + calculate() + " Euro" + "\nBeschreibung: " + getDescription();
    }

    /**
     *  Vergleichsmethode
     *
     * @param other zu vergleichendes Objekt der Klasse Transaktion
     * @return Wahrheitswert, ob die Objekte gleich sind oder nicht
     */
    public boolean equals(Object other) {
        Transaction that = (Transaction) other;
        return this.getDate() == that.getDate() && this.getAmount() == that.getAmount() && this.getDescription() == that.getDescription();
    }

    /**
     * Methode zur Berechnung, des zu verbuchenden Geldbetrages
     *
     * @return Errechneter Betrag
     */
    public double calculate() {
        return getAmount();
    }
}
