package bank;

/**
 * Klasse, die ausgehende Überweisungen abstrahiert. Erbt von der Klasse {@link Transfer}
 *
 * @author Linus Palm
 */
public class OutgoingTransfer extends Transfer {

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     */
    public OutgoingTransfer(String date, double amount, String description) {
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
    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse OutgoingTransfer
     */
    public OutgoingTransfer(Transfer other){
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getSender(), other.getRecipient());
    }

    /**
     * Methode zur Berechnung, des zu verbuchenden Geldbetrages bei einer ausgehenden Überweisung
     *
     * @return Errechneter Betrag
     */
    public double calculate() {
        return -getAmount();
    }
}
