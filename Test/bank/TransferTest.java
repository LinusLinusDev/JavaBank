package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {
    Transfer x = new Transfer("01.01.2022",1000.0,"description","sender","recipient");

    @BeforeEach
    void init(){
        x = new Transfer("01.01.2022",1000.0,"description","sender","recipient");
    }

    @Test
    void testConstructor() {
        assertNotNull(x);
        assertEquals(x.getDate(),"01.01.2022");
        assertEquals(x.getAmount(),1000.);
        assertEquals(x.getDescription(),"description");
        assertEquals(x.getSender(),"sender");
        assertEquals(x.getRecipient(),"recipient");
    }

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

    @Test
    void testCalculate() {
        IncomingTransfer incomingX = new IncomingTransfer(x);
        OutgoingTransfer outgoingX = new OutgoingTransfer(x);
        assertEquals(incomingX.calculate(),incomingX.getAmount());
        assertEquals(outgoingX.calculate(),-outgoingX.getAmount());
    }

    @Test
    void testEquals() {
        assertTrue(x.equals(x));

        Transfer y = new Transfer(x);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));

        y.setDate("02.01.2022");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Transfer(x);
        y.setAmount(500);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Transfer(x);
        y.setDescription("other Description");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Transfer(x);
        y.setSender("other sender");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Transfer(x);
        y.setRecipient("other recipient");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));
    }

    @Test
    void testToString() {
        String actual = "Datum: "+ x.getDate() + "\nBetrag: " + x.calculate() + " Euro" + "\nBeschreibung: " + x.getDescription()  + "\nSender: " + x.getSender() + "\nEmpfänger: " + x.getRecipient();
        assertEquals(x.toString(),actual);
    }
}