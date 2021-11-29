package bank;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse, um Methoden der Klasse Transfer zu testen.
 *
 * @author Linus Palm
 */
class TransferTest {
    /**
     * Variable x, wird in allen Tests verwendet, um Funktion der Methoden zu prüfen
     */
    Transfer x = new Transfer("01.01.2022",1000.0,"description","sender","recipient");

    /**
     * Initialisiere x vor jedem Test neu mit dem ursprünglichen Objekt
     */
    @BeforeEach
    void init(){
        x = new Transfer("01.01.2022",1000.0,"description","sender","recipient");
    }

    /**
     * Testet, ob der Konstruktor die Werte wie erwartet zuweist.
     */
    @Test
    void testConstructor() {
        assertNotNull(x);
        assertEquals(x.getDate(),"01.01.2022");
        assertEquals(x.getAmount(),1000.);
        assertEquals(x.getDescription(),"description");
        assertEquals(x.getSender(),"sender");
        assertEquals(x.getRecipient(),"recipient");
    }

    /**
     * Testet, ob der Copy-Konstruktor die Werte wie erwartet kopiert
     */
    @Test
    void testCopyConstructor() {
        Transfer y = new Transfer(x);
        assertNotNull(y);
        assertEquals(y.getDate(),x.getDate());
        assertEquals(y.getAmount(),x.getAmount());
        assertEquals(y.getDescription(),x.getDescription());
        assertEquals(y.getSender(),x.getSender());
        assertEquals(y.getRecipient(),x.getRecipient());
    }

    /**
     * Testet, ob die calculate-Methode die korrekten Werte bei ein- bzw. ausgehenden Überweisungen liefert
     */
    @Test
    void testCalculate() {
        IncomingTransfer incomingX = new IncomingTransfer(x);
        OutgoingTransfer outgoingX = new OutgoingTransfer(x);
        assertEquals(incomingX.calculate(),incomingX.getAmount());
        assertEquals(outgoingX.calculate(),-outgoingX.getAmount());
    }

    /**
     * Testet, ob die equals-Methode kommutativ ist und alle Attribute korrekt vergleicht und verarbeitet
     */
    @Test
    void testEquals() {
        assertEquals(x, x);

        Transfer y = new Transfer(x);
        assertEquals(x, y);
        assertEquals(y, x);

        y.setDate("02.01.2022");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Transfer(x);
        y.setAmount(500);
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Transfer(x);
        y.setDescription("other Description");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Transfer(x);
        y.setSender("other sender");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Transfer(x);
        y.setRecipient("other recipient");
        assertNotEquals(x, y);
        assertNotEquals(y, x);
    }

    /**
     * Testet, ob die toString-Methode den erwarteten Output liefert
     */
    @Test
    void testToString() {
        String actual = "Datum: "+ x.getDate() + "\nBetrag: " + x.calculate() + " Euro" + "\nBeschreibung: " + x.getDescription()  + "\nSender: " + x.getSender() + "\nEmpfänger: " + x.getRecipient();
        assertEquals(x.toString(),actual);
    }
}