import bank.Payment;
import bank.Transfer;

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
    }
}
