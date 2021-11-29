import bank.*;
import bank.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 *  Klasse Test_P1_bis_P3, welche eine main-Methode liefert, in der die Funktionalität der Implementierungen der ersten drei Praktika getestet werden kann.
 *
 * @author Linus Palm
 */
public class Test_P1_bis_P3 {

    /**
     * main-Methode fungiert als Einstiegspunkt für die folgenden Tests
     */
    public static void main(String[] args){

        /**
         * Testen aller Konstruktoren der Klasse Payment
         */
        Payment a = new Payment("13.10.2021",2.5,"TestTest");
        Payment b = new Payment("08.10.2021",-100,"Bargeldauszahlung am Freitag",0.04,0.03);
        Payment c = new Payment("31.12.2021", 1000, "Bargeldeinzahlung", 0.05, 0.06);
        Payment d = new Payment(c);

        /**
         * Testen der Ausgabe der Klasse Payment, ermöglicht Kontrolle des Ergebnisses der calculate()-Methode
         */
        //System.out.println(a);
        //System.out.println(b);
        //System.out.println(c);
        //System.out.println(d);

        /**
         * Testen aller Konstruktoren der Klasse Transfer
         */
        IncomingTransfer e = new IncomingTransfer("13.10.2021",2.5,"TestTest");
        OutgoingTransfer f = new OutgoingTransfer("24.04.2021", 50.0,"Geburtstagsgeschenk von Opa","Opa","Linus");
        OutgoingTransfer g = new OutgoingTransfer(f);
        Transfer eX = new Transfer("13.10.2021",2.5,"TestTest");
        Transfer fX = new Transfer("24.04.2021", 50.0,"Geburtstagsgeschenk von Opa","Opa","Linus");

        /**
         * Testen der Ausgabe der Klasse Transfer, ermöglicht Kontrolle des Ergebnisses der calculate()-Methode
         */
        //System.out.println(e);
        //System.out.println(f);
        //System.out.println(g);

        /**
         * Testen der Konstruktoren und Ausgabemethode mit leerer Map von PrivateBank
         */
        PrivateBank bank = new PrivateBank("Aachener Bank",0.1,0.2,"");
        PrivateBank bank2 = bank;
        System.out.println(bank);
        System.out.println(bank2);

        /**
         * Testen der createAccount- und addTransactions-Methode inkl. Exceptions und equals-Methode von PrivateBank
         */
        PrivateBank bank3 = new PrivateBank("Aachener Bank",0.1,0.2,"");
        List<Transaction> arrayliste = new ArrayList<Transaction>();
        arrayliste.add(b);
        try {
            bank.createAccount("Linus");
            bank.addTransaction("Linus",b);
            bank3.createAccount("Linus",arrayliste);
            //bank3.createAccount("Linus"); // Account already exists.
            //bank.addTransaction("Zeineb",a); // Account does not exists.
            //bank.addTransaction("Linus",b); // Transaction already exists.
        } catch (AccountAlreadyExistsException ex1) {
            ex1.printStackTrace();
        } catch (AccountDoesNotExistException ex2) {
            ex2.printStackTrace();
        } catch (TransactionAlreadyExistException ex3) {
            ex3.printStackTrace();
        } catch (IOException ex4) {
            ex4.printStackTrace();
        }
        System.out.println("Test mit gleichem Objekt: " + bank.equals(bank2));
        System.out.println("Test mit anderem Objekt, mit gleichen Attributwerten: " + bank.equals(bank3));

        try {
            bank.addTransaction("Linus",c);

        } catch (AccountDoesNotExistException ex1) {
            ex1.printStackTrace();
        } catch (TransactionAlreadyExistException ex2) {
            ex2.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Test mit anderem Objekt, mit anderen Attributwerten: " + bank.equals(bank3));

        /**
         * Testen der containsTransaction-Methode
         */
        System.out.println("Test von containsTransaction ist true: " + bank.containsTransaction("Linus",c));
        System.out.println("Test von containsTransaction ist false: " + bank.containsTransaction("Linus",e));

        /**
         * Testen der removeTransaction Methode inkl. Exception
         */
        try {
            bank.removeTransaction("Linus",c);
            //bank.removeTransaction("Linus",a); // Transaction does not exists.
        } catch (TransactionDoesNotExistException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Nach der remove-Methode sollten die Attributwerte wieder gleich sein: " + bank.equals(bank3));

        /**
         * Testen der getTransactions-Methoden
         */
        try {
            bank.addTransaction("Linus",a);
            bank.addTransaction("Linus",c);
            bank.addTransaction("Linus",e);
            bank.addTransaction("Linus",f);
        } catch (AccountDoesNotExistException ex1) {
            ex1.printStackTrace();
        } catch (TransactionAlreadyExistException ex2) {
            ex2.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        arrayliste = bank.getTransactions("Linus");
        arrayliste = bank.getTransactionsByType("Linus",true);
        arrayliste = bank.getTransactionsByType("Linus",false);
        arrayliste = bank.getTransactionsSorted("Linus",true);
        arrayliste = bank.getTransactionsSorted("Linus",false);

        /**
         * Testen der getAccountBalance-Methoden
         */
        PrivateBankAlt bankAlt = new PrivateBankAlt("Sparkasse",0.1,0.2);
        try {
            bankAlt.createAccount("Opa");
            bankAlt.addTransaction("Opa",a);
            bankAlt.addTransaction("Opa",b);
            bankAlt.addTransaction("Opa",c);
            bankAlt.addTransaction("Opa",eX);
            bankAlt.addTransaction("Opa",fX);

        } catch (AccountAlreadyExistsException | TransactionAlreadyExistException | AccountDoesNotExistException ex) {
            ex.printStackTrace();
        }

        System.out.println("getAccountBalance PrivateBank: " + bank.getAccountBalance("Linus"));
        System.out.println("getAccountBalance PrivateBankAlt: " + bankAlt.getAccountBalance("Opa"));

        /**
         * Testen der Ausgabefunktion mit nicht leerer Map
         */
        try {
            bank.createAccount("Oma", arrayliste);
        } catch (AccountAlreadyExistsException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.print(bank);
    }
}