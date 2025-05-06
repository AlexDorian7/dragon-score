package team.logica_populi.dragonscore.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import team.logica_populi.dragonscore.base.logic.BooleanLogicTreeNode;

import java.util.*;
import java.util.logging.Logger;

/**
 * A view to display a truth table for a given boolean expression
 */
public class TruthTableView extends TableView<TruthTableView.RowData> {

    private static final Logger logger = Logger.getLogger(TruthTableView.class.getName());

    private final BooleanLogicTreeNode expression;
    private List<Character> variables;

    /**
     * Constructs a Truth Table for the provided expression
     * @param expression The boolean expression
     */
    public TruthTableView(BooleanLogicTreeNode expression) {
        this.expression = expression;
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setPrefSize(400, 100);
        this.setMinSize(400, 50);
        VBox.setVgrow(this, Priority.ALWAYS);
        setEditable(true);
        initializeColumns();
        setItems(generateTruthTable());
    }

    private void initializeColumns() {
        Map<Character, Boolean> cache = expression.getCache();
        List<Character> variables = new ArrayList<>(cache.keySet());

        // Add columns for each variable
        for (Character var : variables) {
            TableColumn<RowData, String> col = new TableColumn<>(String.valueOf(var));
            col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue(var)));
            getColumns().add(col);
        }

        // Add editable column for user input
        TableColumn<RowData, Boolean> userInputColumn = new TableColumn<>("User Input");
        userInputColumn.setCellValueFactory(data -> data.getValue().userInput);
        userInputColumn.setCellFactory(CheckBoxTableCell.forTableColumn(userInputColumn));
        userInputColumn.setOnEditCommit(event -> event.getRowValue().userInput.set(event.getNewValue()));

        getColumns().add(userInputColumn);
    }

    private ObservableList<RowData> generateTruthTable() {
        Map<Character, Boolean> cache = expression.getCache();
        variables = new ArrayList<>(cache.keySet());
        int rows = (int) Math.pow(2, variables.size());
        List<RowData> dataList = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            RowData row = new RowData(variables);
            for (int j = 0; j < variables.size(); j++) {
                boolean value = (i & (1 << (variables.size() - j - 1))) != 0;
                row.setValue(variables.get(j), value);
            }
            dataList.add(row);
        }

        return FXCollections.observableArrayList(dataList);
    }

    /**
     * Gets the truth mapping for the current state in {@code 000b: true, 001b: false}.
     * @return the truth mapping
     */
    public Map<Set<Character>, Boolean> getTruthMapping() {
        Map<Set<Character>, Boolean> truthMap = new HashMap<>();

        for (RowData row : getItems()) {
            Set<Character> activeVariables = new HashSet<>();
            for (Character var : row.variables) {
                if (row.getValue(var).equals("1")) { // Also needs to be changed for 1/0 display
                    activeVariables.add(var); // Store only variables that are true
                }
            }

            truthMap.put(activeVariables, row.userInput.get());
        }

        return truthMap;
    }

    /**
     * Gets the expression for this truth table.
     * @return The expression
     */
    public BooleanLogicTreeNode getExpression() {
        return expression;
    }

    public void applyRowColors(Map<Set<Character>, Boolean> truthMap) {
        setRowFactory(tv -> new TableRow<RowData>() {
            @Override
            protected void updateItem(RowData row, boolean empty) {
                super.updateItem(row, empty);
                if (row == null || empty) {
                    setStyle(""); // Reset style if row is empty
                    return;
                }

                // Construct a Set<Character> representing the active variables in this row
                Set<Character> activeVariables = new HashSet<>();
                for (Character var : row.variables) {
                    if (row.getValue(var).equals("1")) { // Also need to be updated for 0/1
                        activeVariables.add(var);
                    }
                }

                // Apply color based on the mapping
                if (truthMap.containsKey(activeVariables)) {
                    boolean isTrue = truthMap.get(activeVariables);
                    setStyle("-fx-background-color: " + (isTrue ? "lightgreen;" : "lightcoral;"));
                } else {
                    setStyle(""); // Reset if no match
                }
            }
        });
    }

    /**
     * Gets the ordered list of constant variables.
     * @return The ordered list of vars
     */
    public List<Character> getVariables() {
        return variables;
    }

    protected static class RowData {
        private final SimpleBooleanProperty userInput = new SimpleBooleanProperty(false);
        private final List<SimpleStringProperty> values = new ArrayList<>();
        private final List<Character> variables;

        public RowData(List<Character> variables) {
            this.variables = variables;
            for (Character var : variables) {
                values.add(new SimpleStringProperty("0"));
            }
        }

        public void setValue(Character var, boolean val) {
            int index = variables.indexOf(var);
            if (index != -1) {
                values.get(index).set(val ? "1" : "0");
            }
        }

        public String getValue(Character var) {
            int index = variables.indexOf(var);
            return index != -1 ? values.get(index).get() : "";
        }
    }
    @FXML
    private void handleHover(MouseEvent ev) {
        Button b = (Button)ev.getSource();
        b.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-size: 20px;" +
                        "-fx-padding: 8 28;"
        );
    }

    @FXML
    private void handleExit(MouseEvent ev) {
        Button b = (Button)ev.getSource();
        b.setStyle(
                "-fx-background-color: #282828;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-size: 20px;" +
                        "-fx-padding: 8 28;"
        );
    }
}