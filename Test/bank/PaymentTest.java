package bank;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static  org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse, um Methoden der Klasse Payment zu testen.
 *
 * @author Linus Palm
 */
class PaymentTest {
    /**
     * Variable x, wird in allen Tests verwendet, um Funktion der Methoden zu prüfen
     */
    Payment x = new Payment("01.01.2022",1000.0,"description",0.05,0.1);

    /**
     * Initialisiere x vor jedem Test neu mit dem ursprünglichen Objekt
     */
    @BeforeEach
    void init(){
        x = new Payment("01.01.2022",1000.0,"description",0.05,0.1);
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
        assertEquals(x.getIncomingInterest(),0.05);
        assertEquals(x.getOutgoingInterest(),0.1);
    }

    /**
     * Testet, ob der Copy-Konstruktor die Werte wie erwartet kopiert
     */
    @Test
    void testCopyConstructor() {
        Payment y = new Payment(x);
        assertNotNull(y);
        assertEquals(y.getDate(),x.getDate());
        assertEquals(y.getAmount(),x.getAmount());
        assertEquals(y.getDescription(),x.getDescription());
        assertEquals(y.getIncomingInterest(),x.getIncomingInterest());
        assertEquals(y.getOutgoingInterest(),x.getOutgoingInterest());
    }

    /**
     * Testet, ob die calculate-Methode positiven bzw. negativen Amount korrekt verarbeitet
     * @param arg ein-/ausgezahlter Betrag
     */
    @ParameterizedTest
    @ValueSource(doubles = {-1000.,-500.,0.,500.,1000.})
    void testCalculate(double arg) {
        x.setAmount(arg);
        if(arg > 0){
            assertEquals(x.calculate(),arg * (1 - x.getIncomingInterest()));
        }
        else {
            assertEquals(x.calculate(),arg * (1 + x.getOutgoingInterest()));
        }
    }

    /**
     * Testet, ob die equals-Methode kommutativ ist und alle Attribute korrekt vergleicht und verarbeitet
     */
    @Test
    void testEquals() {
        assertEquals(x, x);

        Payment y = new Payment(x);
        assertEquals(x, y);
        assertEquals(y, x);

        y.setDate("02.01.2022");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Payment(x);
        y.setAmount(500);
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Payment(x);
        y.setDescription("other Description");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Payment(x);
        y.setIncomingInterest(0);
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new Payment(x);
        y.setOutgoingInterest(0);
        assertNotEquals(x, y);
        assertNotEquals(y, x);
    }

    /**
     * Testet, ob die toString-Methode den erwarteten Output liefert
     */
    @Test
    void testToString() {
        String actual = "Datum: "+ x.getDate() + "\nBetrag: " + x.calculate() + " Euro" + "\nBeschreibung: " + x.getDescription() + "\nAnfallende Zinsen bei Einzahlung: " + x.getIncomingInterest()*100 + "%" + "\nAnfallende Zinsen bei Auszahlung: " + x.getOutgoingInterest()*100 + "%";
        assertEquals(x.toString(),actual);
    }
}