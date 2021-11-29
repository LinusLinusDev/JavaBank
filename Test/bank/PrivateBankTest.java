package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PrivateBankTest {
    PrivateBank x = new PrivateBank("TestBank",0.2,0.3,"Test");

    @BeforeEach
    void init(){
        x = new PrivateBank("TestBank",0.2,0.3,"Test");
    }

    @AfterEach
    void cleanUp() {
        File x = new File("D:\\Linus\\Projekte\\OOS\\savefiles\\Test\\Konto Linus.json");
        if(x.exists())x.delete();

        x = new File("D:\\Linus\\Projekte\\OOS\\savefiles\\otherDirectory");
        if(x.exists())x.delete();

        x = new File("D:\\Linus\\Projekte\\OOS\\savefiles\\Test\\Konto Peter.json");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(x));
            writer.write("[{\"CLASSNAME\":\"Payment\",\"INSTANCE\":{\"incomingInterest\":0.2,\"outgoingInterest\":0.3,\"date\":\"01.01.2022\",\"amount\":1000.0,\"description\":\"description\"}}]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor() {
        assertNotNull(x);
        assertEquals(x.getName(),"TestBank");
        assertEquals(x.getIncomingInterest(),0.2);
        assertEquals(x.getOutgoingInterest(),0.3);
        assertEquals(x.getDirectoryName(),"Test");
        assertEquals(x.getDirectory(),"D:\\Linus\\Projekte\\OOS\\savefiles\\Test");
        assertEquals(x.getTransactions("Peter").get(0),new Payment("01.01.2022",1000.,"description",0.2,0.3));
    }

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

    @Test
    void createAccount1() {

    }

    @Test
    void createAccount2() {
    }

    @Test
    void addTransaction() {
    }

    @Test
    void removeTransaction() {
    }

    @Test
    void containsTransaction() {
    }

    @Test
    void getAccountBalance() {
    }

    @Test
    void getTransactions() {
    }

    @Test
    void getTransactionsSorted() {
    }

    @Test
    void getTransactionsByType() {
    }

    @Test
    void testEquals() {
        assertTrue(x.equals(x));

        PrivateBank y = new PrivateBank(x);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));

        y = new PrivateBank(x);
        y.setName("other Name");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new PrivateBank(x);
        y.setIncomingInterest(0);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new PrivateBank(x);
        y.setOutgoingInterest(0);
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new PrivateBank(x);
        y.setDirectoryName("otherDirectory");
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new PrivateBank(x);
        try {
            y.addTransaction("Peter",new IncomingTransfer("24.04.1998",100,"Test","Test","Test"));
        } catch (TransactionAlreadyExistException e) {
            e.printStackTrace();
        } catch (AccountDoesNotExistException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        y = new PrivateBank(x);
        try {
            y.createAccount("Linus");
        } catch (AccountAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));
    }
}