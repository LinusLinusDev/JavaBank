package ui;

import javafx.scene.control.Alert;

/**
 * Abstrakte Controllerklasse. Stellt Grundfunktionen zur Verfügung und gibt Funktionen vor, die in abgeleiteten Klassen implementiert werden müssen.
 */
public abstract class Controller {
    /**
     * Funktion in der Nodes mit initialen Konfigurationen belegt werden sollen.
     */
    public abstract void initialize();

    /**
     * Funktion zur Aktualisierung der Anzeigewerte der Nodes. Entspricht, wenn nicht anders festgelegt, der Initialisierungsfunktion.
     */
    protected void aktualisiere() {
        initialize();
    }

    /**
     * Funktion zum Aufrufen einer Fehlermeldung.
     *
     * @param header Text der im Header dargestellt werden soll
     * @param content Text der im Body dargestellt werden soll
     */
    protected void fehlermeldung(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
