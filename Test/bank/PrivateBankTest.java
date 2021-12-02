package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import org.junit.jupiter.api.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse, um Methoden der Klasse PrivateBank zu testen.
 *
 * @author Linus Palm
 */
import static org.junit.jupiter.api.Assertions.*;

class PrivateBankTest {
    /**
     * Variable x, wird in allen Tests verwendet, um Funktion der Methoden zu prüfen
     */
    PrivateBank x = new PrivateBank("TestBank",0.2,0.3,"Test");

    /**
     * Initialisiere x vor jedem Test neu mit dem ursprünglichen Objekt
     */
    @BeforeEach
    void init(){
        x = new PrivateBank("TestBank",0.2,0.3,"Test");
    }

    /**
     * Lösche neue .json bzw. neues Verzeichnis, falls im letzten Test erstellt. Setze Konto Peter.json zurück.
     */
    @AfterEach
    void cleanUp() {
        File x = new File(new File("").getAbsolutePath()+ File.separator +"savefiles" + File.separator + "Test" + File.separator + "Konto Linus.json");
        if(x.exists())x.delete();

        x = new File(new File("").getAbsolutePath()+ File.separator + "savefiles" + File.separator + "otherDirectory");
        if(x.exists())x.delete();

        x = new File(new File("").getAbsolutePath()+ File.separator + "savefiles" + File.separator + "Test" + File.separator + "Konto Peter.json");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(x));
            writer.write("[{\"CLASSNAME\":\"Payment\",\"INSTANCE\":{\"incomingInterest\":0.2,\"outgoingInterest\":0.3,\"date\":\"01.01.2022\",\"amount\":1000.0,\"description\":\"description\"}}]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testet, ob der Konstruktor die Werte wie erwartet zuweist und die .json korrekt einließt
     */
    @Test
    void testConstructor() {
        assertNotNull(x);
        assertEquals(x.getName(),"TestBank");
        assertEquals(x.getIncomingInterest(),0.2);
        assertEquals(x.getOutgoingInterest(),0.3);
        assertEquals(x.getDirectoryName(),"Test");
        assertEquals(x.getDirectory(),new File("").getAbsolutePath() + File.separator +"savefiles" + File.separator + "Test");
        assertEquals(x.getTransactions("Peter").get(0),new Payment("01.01.2022",1000.,"description",0.2,0.3));
    }

    /**
     * Testet, ob der Copy-Konstruktor die Werte wie erwartet kopiert
     */
    @Test
    void testCopyConstructor() {
        PrivateBank y = new PrivateBank(x);
        assertNotNull(y);
        assertEquals(y.getName(),x.getName());
        assertEquals(y.getIncomingInterest(),x.getIncomingInterest());
        assertEquals(y.getOutgoingInterest(),x.getOutgoingInterest());
        assertEquals(y.getDirectoryName(),x.getDirectoryName());
        assertEquals(y.getDirectory(),x.getDirectory());
        assertEquals(y.getTransactions("Peter").get(0),new Payment("01.01.2022",1000.,"description",y.getIncomingInterest(),y.getOutgoingInterest()));
    }

    /**
     * Testet, ob das Anlegen eines Kontos, der Hinzufügen von Transaktionen und die Getter-Methode der Map erwartungsgemäß funktionieren.
     * Testet, ob in den gewünschten Fällen auch wirklich die richtigen Exceptions geworfen werden.
     */
    @Test
    void createAccountAddTransactionGetTransactions() {
        assertDoesNotThrow(() -> {
            x.createAccount("Linus");
            x.addTransaction("Linus",new Payment("01.01.2022",1000.,"description",0,0));
        });

        List<Transaction> actual = new ArrayList<>();
        actual.add(new Payment("01.01.2022",1000.,"description",x.getIncomingInterest(),x.getOutgoingInterest()));
        assertNotNull(x.getTransactions("Linus"));
        assertEquals(actual,x.getTransactions("Linus"));

        Exception testException1 = assertThrows(AccountAlreadyExistsException.class,() -> x.createAccount("Linus"));
        assertEquals("Account already exists.",testException1.getMessage());

        Exception testException2 = assertThrows(TransactionAlreadyExistException.class,() -> x.addTransaction("Linus",new Payment("01.01.2022",1000.,"description",0,0)));
        assertEquals("Transaction already exist.",testException2.getMessage());

        Exception testException3 = assertThrows(AccountDoesNotExistException.class,() -> x.addTransaction("Test",new Payment("01.01.2022",1000.,"description",0,0)));
        assertEquals("Account does not exist.",testException3.getMessage());

        Exception testException4 = assertThrows(IOException.class,() -> {
            PrivateBank y = new PrivateBank("Test2",1,1,"Temp");
            File file = new File(new File("").getAbsolutePath()+ File.separator + "savefiles" + File.separator + "Temp");
            if(file.exists())file.delete();
            y.createAccount("Temp");
        });
        assertEquals("Directory not found.",testException4.getMessage());
    }

    /**
     * Testet, ob die createAccount-Methode auch in dem Fall korrekt arbeitet, wenn direkt eine Liste mit Transaktionen übergeben wird
     */
    @Test
    void createAccount() {
        List<Transaction> actual = new ArrayList<>();
        actual.add(new Payment("01.01.2022",1000.,"description",x.getIncomingInterest(),x.getOutgoingInterest()));
        assertDoesNotThrow(() -> x.createAccount("Linus",actual));
        assertNotNull(x.getTransactions("Linus"));
        assertEquals(actual,x.getTransactions("Linus"));
    }

    /**
     * Testet, ob die containsTransaction-Methode unabhängig von Klasse und Attributen korrekt arbeitet
     */
    @Test
    void containsTransaction() {
        assertTrue(x.containsTransaction("Peter",new Payment("01.01.2022",1000.,"description",0.2,0.3)));
        assertFalse(x.containsTransaction("Peter",new IncomingTransfer("01.01.2022",1000.,"description","0.2","0.3")));
        assertFalse(x.containsTransaction("Peter",new OutgoingTransfer("01.01.2022",1000.,"description","0.2","0.3")));
        assertFalse(x.containsTransaction("Peter",new Payment("02.01.2022",1000.,"description",0.2,0.3)));
        assertFalse(x.containsTransaction("Peter",new Payment("01.01.2022",100.,"description",0.2,0.3)));
        assertFalse(x.containsTransaction("Peter",new Payment("01.01.2022",1000.,"description!",0.2,0.3)));
        assertFalse(x.containsTransaction("hallo",new Payment("01.01.2022",1000.,"description",0.2,0.3)));
        assertTrue(x.containsTransaction("Peter",new Payment("01.01.2022",1000.,"description",0.5,0.3)));
        assertTrue(x.containsTransaction("Peter",new Payment("01.01.2022",1000.,"description",0.2,0.5)));
    }

    /**
     * Testet, ob die Transaktionen erwartungsgemäß entfernt werden können und die Exceptions wie gewünscht geworfen werden
     */
    @Test
    void removeTransaction() {
        List<Transaction> temp = new ArrayList<>();
        temp.add(new Payment("01.01.2022",1000.,"description",x.getIncomingInterest(),x.getOutgoingInterest()));
        temp.add(new OutgoingTransfer("24.12.2021",20,"delete this","X","Y"));
        assertDoesNotThrow(() -> x.createAccount("Linus",temp));
        assertDoesNotThrow(() ->x.removeTransaction("Linus",new Payment("01.01.2022",1000.,"description",x.getIncomingInterest(),x.getOutgoingInterest())));
        List<Transaction> actual = new ArrayList<>();
        actual.add(new OutgoingTransfer("24.12.2021",20,"delete this","X","Y"));

        assertEquals(actual,x.getTransactions("Linus"));

        Exception testException = assertThrows(TransactionDoesNotExistException.class,() -> x.removeTransaction("Linus",new OutgoingTransfer("123",123,"Throws exception","X","Y")));
        assertEquals("Transaction does not exist.",testException.getMessage());
    }

    /**
     * Testet, ob die getAccountBalance-Methode, unabhängig von den Klassen der Objekte in der Liste, den Kontostand korrekt errechnet
     */
    @Test
    void getAccountBalance() {
        assertDoesNotThrow(() -> {
            x.addTransaction("Peter",new OutgoingTransfer("temp",100,"temp","temp","temp"));
            x.addTransaction("Peter",new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));
            x.addTransaction("Peter",new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
            x.addTransaction("Peter",new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));
            x.addTransaction("Peter",new Payment("temp4",-100,"temp4",1,1));
        });
        assertEquals(x.getAccountBalance("Peter"),1120.);
    }

    /**
     * Testet, ob die getTransactionsSorted-Methode den übergebenen Parameter korrekt interpretiert und die erwartete Liste zurückgibt
     */
    @Test
    void getTransactionsSorted() {
        assertDoesNotThrow(() -> {
            x.addTransaction("Peter",new OutgoingTransfer("temp",100,"temp","temp","temp"));
            x.addTransaction("Peter",new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));
            x.addTransaction("Peter",new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
            x.addTransaction("Peter",new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));
            x.addTransaction("Peter",new Payment("temp4",-100,"temp4",1,1));
        });
        List<Transaction> actual = new ArrayList<>();
        actual.add(new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));
        actual.add(new Payment("temp4",-100,"temp4",0.2,0.3));
        actual.add(new OutgoingTransfer("temp",100,"temp","temp","temp"));
        actual.add(new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
        actual.add(new Payment("01.01.2022",1000,"description",0.2,0.3));
        actual.add(new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));

        assertEquals(actual,x.getTransactionsSorted("Peter",true));

        List<Transaction> actual2 = new ArrayList<>();
        actual2.add(new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));
        actual2.add(new Payment("01.01.2022",1000,"description",0.2,0.3));
        actual2.add(new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
        actual2.add(new OutgoingTransfer("temp",100,"temp","temp","temp"));
        actual2.add(new Payment("temp4",-100,"temp4",0.2,0.3));
        actual2.add(new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));

        assertEquals(actual2,x.getTransactionsSorted("Peter",false));
    }

    /**
     * Testet, ob die getTransactionsByType-Methode den übergebenen Parameter korrekt interpretiert und die erwartete Liste zurückgibt
     */
    @Test
    void getTransactionsByType() {
        assertDoesNotThrow(() -> {
            x.addTransaction("Peter",new OutgoingTransfer("temp",100,"temp","temp","temp"));
            x.addTransaction("Peter",new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));
            x.addTransaction("Peter",new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
            x.addTransaction("Peter",new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));
            x.addTransaction("Peter",new Payment("temp4",-100,"temp4",1,1));
        });
        List<Transaction> actual = new ArrayList<>();
        actual.add(new Payment("01.01.2022",1000,"description",0.2,0.3));
        actual.add(new IncomingTransfer("temp3",50,"temp3","temp3","temp3"));
        actual.add(new IncomingTransfer("temp4",1000,"temp4","temp4","temp4"));

        assertEquals(actual,x.getTransactionsByType("Peter",true));

        List<Transaction> actual2 = new ArrayList<>();
        actual2.add(new OutgoingTransfer("temp",100,"temp","temp","temp"));
        actual2.add(new OutgoingTransfer("temp2",500,"temp2","temp2","temp2"));
        actual2.add(new Payment("temp4",-100,"temp4",0.2,0.3));

        assertEquals(actual2,x.getTransactionsByType("Peter",false));
    }

    /**
     * Testet, ob die equals-Methode kommutativ ist und alle Attribute korrekt vergleicht und verarbeitet
     */
    @Test
    void testEquals() {
        assertEquals(x, x);

        PrivateBank y = new PrivateBank(x);
        assertEquals(x, y);
        assertEquals(y, x);

        y = new PrivateBank(x);
        y.setName("other Name");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new PrivateBank(x);
        y.setIncomingInterest(0);
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new PrivateBank(x);
        y.setOutgoingInterest(0);
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new PrivateBank(x);
        y.setDirectoryName("otherDirectory");
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        y = new PrivateBank(x);
        assertDoesNotThrow(() -> x.addTransaction("Peter",new IncomingTransfer("24.04.1998",100,"Test","Test","Test")));
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        x = new PrivateBank(y);
        assertDoesNotThrow(() -> x.createAccount("Linus"));
        assertNotEquals(x, y);
        assertNotEquals(y, x);
    }
}