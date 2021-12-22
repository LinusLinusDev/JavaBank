package ui;

import bank.PrivateBank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Klasse die den Einstiegspunkt des Programms und die "globalen" Variablen liefert.
 */
public class Main extends Application {

    /**
     * Diese Instanz der Klasse Main
     */
    private static Main instance;

    /**
     * Hauptfenster/Stage der Anwendung
     */
    private static Stage thisStage;

    /**
     * Ausgewählter Account
     */
    private String selectedAccount;

    /**
     * Bank die in der Anwendung genutzt wird
     */
    PrivateBank bank = new PrivateBank("FXBank",0.02,0.03,"FXBank");

    /**
     * Initialisiert stage und instance und ruft Mainview auf
     */
    public void start(Stage stage) throws IOException {
        thisStage = stage;
        thisStage.setResizable(false);
        instance = this;
        loadMainview();
    }

    /**
     * Einstiegsfunktion der Anwendung
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Getter-Methode ermöglicht den Controllern Zugriff
     *
     * @return Diese Instanz der Klasse Main
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Getter-Methode ermöglicht den Controllern Zugriff
     *
     * @return Bank die in der Anwendung genutzt wird
     */
    public PrivateBank getBank() {
        return bank;
    }

    /**
     * Getter-Methode ermöglicht Kommunikation von Mainview zu Accountview
     *
     * @return ausgewähltes Konto
     */
    public String getSelectedAccount() {
        return selectedAccount;
    }

    /**
     * Ruft die Mainview auf
     */
    public void loadMainview() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mainview.fxml"));
        Scene scene = new Scene(root);
        thisStage.setTitle("Konten");
        thisStage.setScene(scene);
        thisStage.show();
    }

    /**
     * Ruft die Accountview eines ausgewählten Kontos auf
     *
     * @param account ausgewähltes Konto
     */
    public void loadAccountview(String account) throws IOException {
        selectedAccount = account;
        Parent root = FXMLLoader.load(getClass().getResource("Accountview.fxml"));
        Scene scene = new Scene(root);
        thisStage.setTitle(selectedAccount);
        thisStage.setScene(scene);
        thisStage.show();
    }
}