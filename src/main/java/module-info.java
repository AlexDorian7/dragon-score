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
//    requires org.json;
    requires com.google.gson;
    requires java.sql;
    requires javafx.web;

    opens team.logica_populi.dragonscore to javafx.fxml;
    opens team.logica_populi.dragonscore.ui to javafx.fxml;
    opens team.logica_populi.dragonscore.ui.controllers to javafx.fxml;
    opens team.logica_populi.dragonscore.base to com.google.gson;
    opens team.logica_populi.dragonscore.base.logic to com.google.gson;
    opens team.logica_populi.dragonscore.base.form to com.google.gson;
    opens team.logica_populi.dragonscore.base.term to com.google.gson;
    opens team.logica_populi.dragonscore.base.json to com.google.gson;
    opens team.logica_populi.dragonscore.base.points to com.google.gson;
    exports team.logica_populi.dragonscore;
}