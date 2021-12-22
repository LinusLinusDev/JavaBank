package bank;


/**
 * Klasse, die eingehende Überweisungen abstrahiert. Erbt von der Klasse {@link Transfer}
 *
 * @author Linus Palm
 */
public class IncomingTransfer extends Transfer {

    /**
     * Konstruktor, initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param date Datum der Transaktion im Format DD.MM.YYYY
     * @param amount Geldmenge die bei der Transaktion verbucht wird
     * @param description Zusätzliche Beschreibung des Vorgangs
     * @param sender Sender des Geldes
     * @param recipient Empfänger des Geldes
     */
    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor, initialisiert alle Attribute mit den Werten des entsprechenden Attributes des übergebenen Objekts
     *
     * @param other zu kopierendes Objekt der Klasse IncomingTransfer
     */
    public IncomingTransfer(Transfer other){
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getSender(), other.getRecipient());
    }
}
