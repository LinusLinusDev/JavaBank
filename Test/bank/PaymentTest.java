package bank;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static  org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Payment x = new Payment("01.01.2022",1000.0,"description",0.05,0.1);

    @BeforeEach
    void init(){
        x = new Payment("01.01.2022",1000.0,"description",0.05,0.1);
    }

    @Test
    void testConstructor() {
        assertNotNull(x);
        assertEquals(x.getDate(),"01.01.2022");
        assertEquals(x.getAmount(),1000.);
        assertEquals(x.getDescription(),"description");
        assertEquals(x.getIncomingInterest(),0.05);
        assertEquals(x.getOutgoingInterest(),0.1);
    }

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

    @Test
    void testEquals() {
        assertTrue(x.equals(x));

        Payment y = new Payment(x);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));

        y.setDate("02.01.2022");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Payment(x);
        y.setAmount(500);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Payment(x);
        y.setDescription("other Description");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Payment(x);
        y.setIncomingInterest(0);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new Payment(x);
        y.setOutgoingInterest(0);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));
    }

    @Test
    void testToString() {
        String actual = "Datum: "+ x.getDate() + "\nBetrag: " + x.calculate() + " Euro" + "\nBeschreibung: " + x.getDescription() + "\nAnfallende Zinsen bei Einzahlung: " + x.getIncomingInterest()*100 + "%" + "\nAnfallende Zinsen bei Auszahlung: " + x.getOutgoingInterest()*100 + "%";
        assertEquals(x.toString(),actual);
    }
}