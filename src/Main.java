import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

import java.util.ArrayList;

/**
 *  Klasse Main, welche die main-Methode liefert
 *
 * @author Linus Palm
 */
public class Main {

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
        Transfer e = new Transfer("13.10.2021",2.5,"TestTest");
        Transfer f = new Transfer("24.04.2021", 50.0,"Geburtstagsgeschenk von Opa","Opa","Linus");
        Transfer g = new Transfer(f);

        /**
         * Testen der Ausgabe der Klasse Transfer, ermöglicht Kontrolle des Ergebnisses der calculate()-Methode
         */
        //System.out.println(e);
        //System.out.println(f);
        //System.out.println(g);

        /**
         * Testen der Konstruktoren und Ausgabemethode von PrivateBank
         */
        PrivateBank bank = new PrivateBank("Aachener Bank",0.1,0.2);
        PrivateBank bank2 = bank;
        System.out.println(bank);
        System.out.println(bank2);

        /**
         * Testen der createAccount- und addTransactions-Methode inkl. Exceptions und equals-Methode von PrivateBank
         */
        PrivateBank bank3 = new PrivateBank("Aachener Bank",0.1,0.2);
        ArrayList<Transaction> arrayliste = new ArrayList<Transaction>();
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
        }
        System.out.println("Test mit gleichem Objekt: " + bank.equals(bank2));
        System.out.println("Test mit anderem Objekt, mit gleichen Attributwerten: " + bank.equals(bank3));

        try {
            bank.addTransaction("Linus",e);

        } catch (AccountDoesNotExistException ex1) {
            ex1.printStackTrace();
        } catch (TransactionAlreadyExistException ex2) {
            ex2.printStackTrace();
        }

        System.out.println("Test mit anderem Objekt, mit anderen Attributwerten: " + bank.equals(bank3));

        /**
         * Testen der containsTransaction-Methode
         */
        System.out.println("Test von containsTransaction ist true: " + bank.containsTransaction("Linus",e));
        System.out.println("Test von containsTransaction ist false: " + bank.containsTransaction("Linus",d));

        /**
         * Testen der removeTransaction Methode inkl. Exception
         */
        try {
            bank.removeTransaction("Linus",e);
            //bank.removeTransaction("Linus",a); // Transaction does not exists.
        } catch (TransactionDoesNotExistException ex) {
            ex.printStackTrace();
        }

        System.out.println("Nach der remove-Methode sollte es wieder true sein: " + bank.equals(bank3));
    }
}
