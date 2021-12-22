package bank;

/**
 * Klasse, die Überweisungen abstrahiert. Erbt von der Klasse {@link Transaction}
 *
 * @author Linus Palm
 */
public class Transfer extends Transaction {

    /**
     * Sender des Geldes
     */
    protected String sender = "";

    /**
     * Empfänger des Geldes
     */
    protected String recipient = "";

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     */
    public Transfer(String date, double amount, String description) {
        super(date,amount,description);
    }

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     * @param sender Sender des Geldes
     * @param recipient Empfänger des Geldes
     */
    public Transfer(String date, double amount, String description, String sender, String recipient){
        this(date,amount,description);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse Transfer
     */
    public Transfer(Transfer other){
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getSender(), other.getRecipient());
    }

    /**
     * Gettermethode
     *
     * @return Sender des Geldes
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gettermethode
     *
     * @return Empfänger des Geldes
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Settermethode
     *
     * @param sender Sender des Geldes
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Settermethode
     *
     * @param recipient Empfänger des Geldes
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Ausgabemethode
     *
     * @return Liste aller Attribute mit ihren Werten als String
     */
    public String toString() {

        return super.toString() + "\nSender: " + getSender() + "\nEmpfänger: " + getRecipient();
    }

    /**
     *  Vergleichsmethode
     *
     * @param other zu vergleichendes Objekt der Klasse Transfer
     * @return Wahrheitswert, ob die Objekte gleich sind oder nicht
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Transfer)) return false;
        Transfer that = (Transfer) other;
        return super.equals(other) && this.getSender().equals(that.getSender()) && this.getRecipient().equals(that.getRecipient());
    }
}
