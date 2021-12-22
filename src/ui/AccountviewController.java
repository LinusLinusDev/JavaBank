package ui;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.Transaction;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controllerklasse für die View "Accountview" erbt von der Klasse {@link Controller}
 *
 * @author Linus Palm
 */
public class AccountviewController extends Controller{

    /**
     * Name das aktuell ausgewählten Kontos
     */
    String account = Main.getInstance().getSelectedAccount();

    /**
     * Liste der Transaktionen des aktuell ausgewählten Kontos
     */
    List<Transaction> list = new ArrayList<>();

    /**
     * Textfeld, das den Namen des aktuell gewählten Kontos anzeigt
     */
    @FXML
    TextField accountName;

    /**
     * Textfeld, das den Kontostand des aktuell gewählten Kontos anzeigt
     */
    @FXML
    TextField accountBalance;

    /**
     * Button, der das Zurückgehen zur Mainview ermöglicht
     */
    @FXML
    Button zurueck;

    /**
     * Listview, die alle Transaktionen des gewählten Kontos zeigt
     */
    @FXML
    ListView<String> transaktionen = new ListView<>();

    /**
     * Button, der das Sortieren nach Betrag aufsteigend ermöglicht
     */
    @FXML
    Button sort1;

    /**
     * Button, der das Sortieren nach Betrag absteigend ermöglicht
     */
    @FXML
    Button sort2;

    /**
     * Button, der das Sortieren nach Betrag ermöglicht, bei dem nur positive Beträge angezeigt werden
     */
    @FXML
    Button sort3;

    /**
     * Button, der das Sortieren nach Betrag ermöglicht, bei dem nur negative Beträge angezeigt werden
     */
    @FXML
    Button sort4;

    /**
     * Button, der das Erstellen einer Transaktion ermöglicht
     */
    @FXML
    Button transaktionErstellen;


    /**
     * Initialisiert beim Start der Scene die Nodes mit entsprechenden Konfigurationen und Anzeigewerten
     */
    public void initialize() {
        accountName.setEditable(false);
        accountName.setText(account);

        accountBalance.setEditable(false);
        accountBalance.setText(String.valueOf(Main.getInstance().getBank().getAccountBalance(account)));

        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,0));
        transaktionen.setItems(items);
    }

    /**
     * Aktualisiert die Anzeigewerte des Kontostandes und der Transaktionen, wird nach dem Löschen eines Eintrags aufgerufen.
     */
    protected void aktualisiere() {
        accountBalance.setText(String.valueOf(Main.getInstance().getBank().getAccountBalance(account)));

        List<String> result = new ArrayList<>();

        for(Transaction n : list) {
            result.add(n.toString());
        }

        ObservableList<String> items = FXCollections.observableArrayList(result);
        transaktionen.setItems(items);
    }

    /**
     * Aktualisiert die Anzeigewerte des Kontostandes und der Transaktionen, wird nach dem Erstellen eines Eintrags aufgerufen.
     */
    private void aktualisiereNeuerEintrag() {
        accountBalance.setText(String.valueOf(Main.getInstance().getBank().getAccountBalance(account)));
        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,0));
        transaktionen.setItems(items);
    }

    /**
     * Erstelle eine Liste von Transaktionen eines Kontos, in der die Transaktionen als Strings vorkommen. Dabei kann ein Sortierkriterium gewählt werden.
     *
     * @param account Ausgewähltes Konto
     * @param mode Wahl des Sortierkriteriums (0 = Nach Erstellungsreihenfolge, 1 = Nach Betrag aufsteigend, 2 = Nach Betrag absteigent, 3 = Nur positive Beträge, 4 = Nur negative Beträge)
     * @return Liste der Transaktionen
     */
    public List<String> getTransactionsString(String account, int mode){
        switch (mode) {
            case 0 -> list = Main.getInstance().getBank().getTransactions(account);
            case 1 -> list = Main.getInstance().getBank().getTransactionsSorted(account, true);
            case 2 -> list = Main.getInstance().getBank().getTransactionsSorted(account, false);
            case 3 -> list = Main.getInstance().getBank().getTransactionsByType(account, true);
            case 4 -> list = Main.getInstance().getBank().getTransactionsByType(account, false);
        }

        List<String> result = new ArrayList<>();

        for(Transaction n : list) {
            result.add(n.toString());
        }

        return result;
    }

    /**
     * Wird durch Drücken des Buttons "Aufsteigend" aufgerufen. Sortiert die Liste der Transaktionen entsprechend und aktualisiert die Anzeige.
     */
    @FXML
    public void sort1() {
        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,1));
        transaktionen.setItems(items);
    }

    /**
     * Wird durch Drücken des Buttons "Absteigend" aufgerufen. Sortiert die Liste der Transaktionen entsprechend und aktualisiert die Anzeige.
     */
    @FXML
    public void sort2() {
        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,2));
        transaktionen.setItems(items);
    }

    /**
     * Wird durch Drücken des Buttons "Nur positive Beträge" aufgerufen. Sortiert die Liste der Transaktionen entsprechend und aktualisiert die Anzeige.
     */
    @FXML
    public void sort3() {
        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,3));
        transaktionen.setItems(items);
    }

    /**
     * Wird durch Drücken des Buttons "Nur negative Beträge" aufgerufen. Sortiert die Liste der Transaktionen entsprechend und aktualisiert die Anzeige.
     */
    @FXML
    public void sort4() {
        ObservableList<String> items = FXCollections.observableArrayList(getTransactionsString(account,4));
        transaktionen.setItems(items);
    }

    /**
     * Wird durch Drücken des Buttons "Zurück" aufgerufen. Wechselt zurück in die Mainview.
     */
    @FXML
    public void zurueck() {
        try {
            Main.getInstance().loadMainview();
        } catch (IOException e) {
            fehlermeldung("Die Startseite konnte nicht aufgerufen werden.",e.getMessage());
        }
    }

    /**
     * Wird durch Drücken der Option "Löschen" im Kontextmenü der Listview aufgerufen. Löscht nach Bestätigung die ausgewählte Transaktion und aktualisiert die Anzeige.
     */
    @FXML
    public void loeschen() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaktion löschen");
        alert.setHeaderText("Sind sie sich sicher, dass sie die folgende Transaktion löschen wollen?");
        alert.setContentText(transaktionen.getSelectionModel().getSelectedItems().get(0));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            try {
                Main.getInstance().getBank().removeTransaction(account,list.get(transaktionen.getSelectionModel().getSelectedIndex()));
                aktualisiere();
            } catch (IOException | TransactionDoesNotExistException e) {
                fehlermeldung("Transaktion konnte nicht gelöscht werden.", e.getMessage());
            }
        }
    }

    /**
     * Wird durch Drücken der Option "Transaktion erstellen" im Kontextmenü der Listview aufgerufen. Ruft einen Dialog auf, in dem der Typ der Transaktion gewählt werden kann.
     */
    @FXML
    public void erstelle() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaktion erstellen");
        alert.setHeaderText("Neue Transaktion erstellen");
        alert.setContentText("Was für eine Transaktion soll erstellt werden?");

        ButtonType buttonTypeOne = new ButtonType("Überweisung");
        ButtonType buttonTypeTwo = new ButtonType("Ein-/Auszahlung");
        ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOne){
            newTransfer();
        } else if (result.isPresent() && result.get() == buttonTypeTwo) {
            newPayment();
        }
    }

    /**
     * Wird für die Erstellung einer neuen Überweisung aufgerufen. Öffnet ein Dialogfeld und verarbeitet die Eingaben entsprechend.
     */
    private void newTransfer() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Überweisung erstellen");
        dialog.setHeaderText("Neue Überweisung erstellen");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField date = new TextField();
        TextField amount = new TextField();
        TextField descr = new TextField();
        TextField sender = new TextField();
        TextField recipient = new TextField();

        grid.add(new Label("Datum:"), 0, 0);
        grid.add(date, 1, 0);
        grid.add(new Label("Betrag in Euro:"), 0, 1);
        grid.add(amount, 1, 1);
        grid.add(new Label("Beschreibung:"), 0, 2);
        grid.add(descr, 1, 2);
        grid.add(new Label("Sender:"), 0, 3);
        grid.add(sender, 1, 3);
        grid.add(new Label("Empfänger:"), 0, 4);
        grid.add(recipient, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get().getText().equals("OK")) {
            if(date.getText().equals("")) {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Es muss ein Datum angegeben werden.");
                return;
            }

            double amountValue;
            try {
                amountValue = Double.parseDouble(amount.getText());
            } catch (NullPointerException | NumberFormatException e) {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Als Betrag muss eine Zahl angegeben werden.");
                return;
            }
            if(Double.parseDouble(amount.getText())<=0.){
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Betrag muss größer als 0 sein.");
                return;
            }

            if(sender.getText().equals("")) {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Es muss ein Sender angegeben werden.");
                return;
            }

            if(recipient.getText().equals("")) {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Es muss ein Empfänger angegeben werden.");
                return;
            }
            if(sender.getText().equals(account) && recipient.getText().equals(account)) {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Sender und Empfänger müssen unterschiedlich sein.");
            }
            else if(sender.getText().equals(account)){
                try {
                    Main.getInstance().getBank().addTransaction(account,new OutgoingTransfer(date.getText(),amountValue,descr.getText(),account,recipient.getText()));
                    aktualisiereNeuerEintrag();
                } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
                    fehlermeldung("Überweisung konnte nicht erstellt werden.",e.getMessage());
                }
            }
            else if(recipient.getText().equals(account)){
                try {
                    Main.getInstance().getBank().addTransaction(account,new IncomingTransfer(date.getText(),amountValue,descr.getText(),sender.getText(),account));
                    aktualisiereNeuerEintrag();
                } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
                    fehlermeldung("Überweisung konnte nicht erstellt werden.",e.getMessage());
                }
            }
            else {
                fehlermeldung("Überweisung konnte nicht erstellt werden.","Das Konto " + account + " muss entweder Sender oder Empfänger der Überweisung sein.");
            }
        }
    }

    /**
     * Wird für die Erstellung einer neuen Ein-/Auszahlung aufgerufen. Öffnet ein Dialogfeld und verarbeitet die Eingaben entsprechend.
     */
    private void newPayment() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ein-/Auszahlung erstellen");
        dialog.setHeaderText("Neue Ein-/Auszahlung erstellen");
        ButtonType einzahlen = new ButtonType("Einzahlen", ButtonBar.ButtonData.OK_DONE);
        ButtonType auszahlen = new ButtonType("Auszahlen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(einzahlen, auszahlen, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField date = new TextField();
        TextField amount = new TextField();
        TextField descr = new TextField();

        grid.add(new Label("Datum:"), 0, 0);
        grid.add(date, 1, 0);
        grid.add(new Label("Betrag in Euro:"), 0, 1);
        grid.add(amount, 1, 1);
        grid.add(new Label("Beschreibung:"), 0, 2);
        grid.add(descr, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && (result.get().getText().equals("Einzahlen")) || result.get().getText().equals("Auszahlen")) {
            if(date.getText().equals("")) {
                fehlermeldung("Ein-/Auszahlung konnte nicht erstellt werden.","Es muss ein Datum angegeben werden.");
                return;
            }

            double amountValue;
            try {
                amountValue = Double.parseDouble(amount.getText());
            } catch (NullPointerException | NumberFormatException e) {
                fehlermeldung("Ein-/Auszahlung konnte nicht erstellt werden.","Als Betrag muss eine Zahl angegeben werden.");
                return;
            }
            if(Double.parseDouble(amount.getText())<=0.){
                fehlermeldung("Ein-/Auszahlung konnte nicht erstellt werden.","Betrag muss größer als 0 sein.");
                return;
            }

            if(result.get().getText().equals("Einzahlen")) {
                try {
                    Main.getInstance().getBank().addTransaction(account, new Payment(date.getText(), amountValue, descr.getText()));
                    aktualisiereNeuerEintrag();
                } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
                    fehlermeldung("Ein-/Auszahlung konnte nicht erstellt werden.", e.getMessage());
                }
            }
            else {
                try {
                    Main.getInstance().getBank().addTransaction(account, new Payment(date.getText(), -amountValue, descr.getText()));
                    aktualisiereNeuerEintrag();
                } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
                    fehlermeldung("Ein-/Auszahlung konnte nicht erstellt werden.", e.getMessage());
                }
            }
        }
    }
}
