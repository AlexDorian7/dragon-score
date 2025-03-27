/**
 * The main Dragon Score module.
 * This is required for JavaFX.
 */
module team.logica_populi.dragonscore {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;
    requires org.jetbrains.annotations;
    requires org.json;

    opens team.logica_populi.dragonscore to javafx.fxml;
    opens team.logica_populi.dragonscore.ui to javafx.fxml;
    opens team.logica_populi.dragonscore.ui.controllers to javafx.fxml;
    exports team.logica_populi.dragonscore;
}