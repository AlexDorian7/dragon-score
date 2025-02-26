module team.logica_populi.dragonscore {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;

    opens team.logica_populi.dragonscore to javafx.fxml;
    opens team.logica_populi.dragonscore.ui to javafx.fxml;
    opens team.logica_populi.dragonscore.ui.controllers to javafx.fxml;
    exports team.logica_populi.dragonscore;
}