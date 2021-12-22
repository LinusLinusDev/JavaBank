package ui;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

/**
 * Controllerklasse für die View "Mainview" erbt von der Klasse {@link Controller}
 *
 * @author Linus Palm
 */
public class MainviewController extends Controller {

    /**
     * Button, der das Erstellen eines Kontos ermöglicht
     */
    @FXML
    Button kontoErstellen;

    /**
     * Listview, die alle Konten der Bank anzeigt
     */
    @FXML
    ListView<String> konten = new ListView<>();

    /**
     * Initialisiert beim Start der Scene die Nodes mit entsprechenden Konfigurationen und Anzeigewerten
     */
    @FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(Main.getInstance().getBank().getAllAccounts());
        konten.setItems(items);
    }

    /**
     * Wird durch Drücken des Buttons "Konto erstellen" aufgerufen. Ruft einen Dialog auf, der das Erstellen eines Kontos ermöglicht.
     */
    @FXML
    public void erstelle() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Konto erstellen");
        dialog.setHeaderText("Neues Konto erstellen");
        dialog.setContentText("Name auswählen:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals("")) {
                try {
                    Main.getInstance().getBank().createAccount(result.get());
                    aktualisiere();
                } catch (AccountAlreadyExistsException | IOException e) {
                    fehlermeldung("Konto konnte nicht erstellt werden.", e.getMessage());
                }
            } else {
                fehlermeldung("Konto konnte nicht erstellt werden.", "Der Name darf nicht leer sein.");
            }
        }
    }

    /**
     * Wird durch Drücken der Option "Auswählen" in dem Kontextmenü der Listview aufgerufen. Läd die Accountview des ausgewählten Kontos.
     */
    @FXML
    public void auswaehlen() {
        try {
            Main.getInstance().loadAccountview(konten.getSelectionModel().getSelectedItems().get(0));
        } catch (IOException e) {
            fehlermeldung("Das Konto " + konten.getSelectionModel().getSelectedItems().get(0) + " konnte nicht aufgerufen werden.",e.getMessage());
        }
    }

    /**
     * Wird durch Drücken der Option "Löschen" in dem Kontextmenü der Listview aufgerufen. Löscht nach Bestätigung das ausgewählte Konto.
     */
    @FXML
    public void loeschen() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konto löschen");
        alert.setHeaderText("Sind sie sich sicher, dass sie das folgende Konto löschen wollen?");
        alert.setContentText(konten.getSelectionModel().getSelectedItems().get(0));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            try {
                Main.getInstance().getBank().deleteAccount(konten.getSelectionModel().getSelectedItems().get(0));
                aktualisiere();
            } catch (AccountDoesNotExistException | IOException e) {
                fehlermeldung("Konto konnte nicht gelöscht werden.", e.getMessage());
            }
        }
    }
}