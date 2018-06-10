import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * The Class Assignment2, used to launch an application and handle the information from the user accordingly.
 * @author Jeffery Ceja
 */
public class Assignment2 extends Application {

    // Colors used for button color changes
    private static final Color black = Color.web("#000000"); // ...
    private static final Color white = Color.web("#ffffff"); // ...
    private static final BlendMode green = BlendMode.GREEN;

    // Table Instantiations
    private final TableView<TennisMatch> table = new TableView<>(); // ...
    private final TableView<TennisPlayer> table2 = new TableView<>();

    // Allow the data to be read as observable array lists
    private final ObservableList<TennisMatch> data = FXCollections.observableArrayList();
    private final ObservableList<TennisPlayer> data2 = FXCollections.observableArrayList();

    //button sections and file chooser
    private final HBox addMatchSection = new HBox(); // ...
    private final HBox addPlayerSection = new HBox();
    private final HBox deletePlayerSection = new HBox();
    private final FileChooser fileChooser = new FileChooser();

    // Labels
    private final Label myName = new Label("Jeffery Ceja");
    private final Label matchesLabel = new Label("TennisMatches");
    private final Label playersLabel = new Label("TennisPlayers");

    private final VBox vbox = new VBox();

    // Buttons
    private final Button clearDatabase = new Button("Clear Database");
    private final Button changeToMatchesButton = new Button("Change to Matches");
    private final Button changeToPlayersButton = new Button("Change To Players");
    private final Button importButton = new Button("Select a file to import from.");
    private final Button exportButton = new Button("Select a file to export to.");
    private final Button deletePlayerButton = new Button("Delete Player");
    private final Button addPlayerButton = new Button("AddPlayer");
    private final Button addMatchButton = new Button("AddMatch");

    // Player column information
    private TableColumn<TennisPlayer, String> playerIdCol = new TableColumn<>("Player ID");
    private TableColumn<TennisPlayer, String> firstNameCol = new TableColumn<>("First Name");
    private TableColumn<TennisPlayer, String> lastNameCol = new TableColumn<>("Last Name");
    private TableColumn<TennisPlayer, String> birthYearCol = new TableColumn<>("Birth Year");
    private TableColumn<TennisPlayer, String> countryCol = new TableColumn<>("Country");

    // Add Player Text Fields
    private final TextField addPlayerID = new TextField();
    private final TextField addFirstName = new TextField();
    private final TextField addLastName = new TextField();
    private final TextField addYear = new TextField();
    private final TextField addCountry = new TextField();

    // Match column information
    private TableColumn<TennisMatch, String> firstIdCol = new TableColumn<>("Player 1 Name");
    private TableColumn<TennisMatch, String> secondIdCol = new TableColumn<>("Player 2 Name");
    private TableColumn<TennisMatch, String> matchDateCol = new TableColumn<>("Date");
    private TableColumn<TennisMatch, String> matchLocationCol = new TableColumn<>("Location");
    private TableColumn<TennisMatch, String> matchScoreCol = new TableColumn<>("Winner");

    // Add Match Text Fields
    private final TextField addFirstID = new TextField();
    private final TextField addSecondId = new TextField();
    private final TextField addTennisMatchDate = new TextField();
    private final TextField addMatchLocation = new TextField();
    private final TextField addMatchScore = new TextField();

    // Remove Player Text Fields
    private final TextField idToDelete = new TextField();

    private TennisDatabase database = new TennisDatabase();
    private Scene scene = new Scene(new Group());

    /**
     * Main method used to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method used to launch the GUI of the program.
     *
     * Alters the stage, tables, and calls for the setup of the table, buttons, stage, box and scene to be
     * displayed to the stage.
     * @param stage Screen launched when program is started
     */
    @Override
    public void start(Stage stage) {
        String[] args = new String[10];

        setMyNameInfo();
        setMatchesLabelInfo();
        setPlayersLabelInfo();

        table.setEditable(false);
        table2.setEditable(false);

        setPlayerColumnInfo();
        setPlayerTextFieldsInfo();

        setMatchesColumnInfo();
        setMatchTextFieldInfo();

        table.getColumns().addAll(firstIdCol, secondIdCol, matchDateCol, matchLocationCol, matchScoreCol);

        setClearDatabaseButtonInfo();
        setChangeToMatchesButtonInfo();
        setChangeToPlayerButtonInfo();
        setImportButtonInfo(stage, args);
        setExportButtonInfo(stage, args);
        setDeletePlayerButtonInfo();
        setAddPlayerButtonInfo();
        setAddMatchButtonInfo();

        table.setMinWidth(550);
        table2.setMinWidth(550);
        addMatchSection.getChildren().addAll(addFirstID, addSecondId,
                addTennisMatchDate, addMatchLocation, addMatchScore, addMatchButton);
        addMatchSection.setSpacing(3);
        addMatchSection.setAlignment(Pos.CENTER);
        addPlayerSection.getChildren().addAll(addPlayerID, addFirstName,
                addLastName, addYear, addCountry, addPlayerButton);
        addPlayerSection.setSpacing(3);
        addPlayerSection.setAlignment(Pos.CENTER);
        deletePlayerSection.getChildren().addAll(idToDelete, deletePlayerButton);
        deletePlayerSection.setSpacing(3);
        deletePlayerSection.setAlignment(Pos.CENTER_RIGHT);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(clearDatabase, changeToPlayersButton,
                importButton, exportButton, matchesLabel, myName, table, addMatchSection);
        setSceneInfo();

        stage.setTitle("CS 102 Assignment 2");
        stage.setHeight(700);
        stage.setWidth(650);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to set the effects on my name display.
     */
    private void setMyNameInfo() {
        myName.setFont(new Font("Arial", 20));
        myName.setTextFill(white);
        myName.setOnMouseEntered(
                (MouseEvent e) -> {
                    myName.setTextFill(Color.BLUE);
                }
        );
        myName.setOnMouseExited(
                (MouseEvent e) -> {
                    myName.setTextFill(white);
                }
        );
    }

    /**
     * Method used to set the effects for leaving the application screen with the mouse.
     */
    private void setSceneInfo() {
        scene.setFill(black);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        (scene.getRoot()).setBlendMode(BlendMode.LIGHTEN);
        (scene.getRoot()).setOnMouseEntered(
                (MouseEvent e) -> {
                    scene.setFill(black);
                }
        );
        (scene.getRoot()).setOnMouseExited(
                (MouseEvent e) -> {
                    scene.setFill(white);
                }
        );
    }

    /**
     * Method used to set the effects on the match label display.
     */
    private void setMatchesLabelInfo() {
        matchesLabel.setFont(new Font("Arial", 20));
        matchesLabel.setTextFill(white);
        matchesLabel.setOnMouseEntered(
                (MouseEvent e) -> {
                    matchesLabel.setTextFill(Color.AQUAMARINE);
                }
        );
        matchesLabel.setOnMouseExited(
                (MouseEvent e) -> {
                    matchesLabel.setTextFill(white);
                }
        );
    }

    /**
     * Method used to set the effects on the players label display.
     */
    private void setPlayersLabelInfo() {
        playersLabel.setFont(new Font("Arial", 20));
        playersLabel.setTextFill(white);
        playersLabel.setOnMouseEntered(
                (MouseEvent e) -> {
                    playersLabel.setTextFill(Color.HOTPINK);
                }
        );
        playersLabel.setOnMouseExited(
                (MouseEvent e) -> {
                    playersLabel.setTextFill(white);
                }
        );
    }

    /**
     * Method used to set the column information for TennisPlayers
     */
    private void setPlayerColumnInfo() {
        playerIdCol.setMinWidth(playerIdCol.getPrefWidth());
        playerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        playerIdCol.setCellFactory(TextFieldTableCell.forTableColumn());

        firstNameCol.setMinWidth(firstNameCol.getPrefWidth());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        lastNameCol.setMinWidth(lastNameCol.getPrefWidth());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        birthYearCol.setMinWidth(100);
        birthYearCol.setCellValueFactory(new PropertyValueFactory<>("StringBirthYear"));
        birthYearCol.setCellFactory(TextFieldTableCell.forTableColumn());

        countryCol.setMinWidth(200);
        countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Method used to set the text shown inside the text box's of the add player button.
     */
    private void setPlayerTextFieldsInfo() {
        idToDelete.setPromptText("ID to Delete");
        idToDelete.setMaxWidth(100);
        idToDelete.setAlignment(Pos.CENTER);

        addPlayerID.setPromptText("Player ID");
        addPlayerID.setMaxWidth(100);
        addPlayerID.setAlignment(Pos.CENTER);

        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(100);
        addFirstName.setAlignment(Pos.CENTER);

        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(100);
        addLastName.setAlignment(Pos.CENTER);

        addYear.setPromptText("Year");
        addYear.setMaxWidth(100);
        addYear.setAlignment(Pos.CENTER);

        addCountry.setPromptText("Country");
        addCountry.setMaxWidth(100);
        addCountry.setAlignment(Pos.CENTER);
    }

    /**
     * Method used to set the column information for the add Match button.
     */
    private void setMatchesColumnInfo() {

        firstIdCol.setMinWidth(120);
        firstIdCol.setCellValueFactory(new PropertyValueFactory<>("Player1Name"));
        firstIdCol.setCellFactory(TextFieldTableCell.forTableColumn());

        secondIdCol.setMinWidth(120);
        secondIdCol.setCellValueFactory(new PropertyValueFactory<>("Player2Name"));
        secondIdCol.setCellFactory(TextFieldTableCell.forTableColumn());

        matchDateCol.setMinWidth(100);
        matchDateCol.setCellValueFactory(new PropertyValueFactory<>("TennisMatchDate"));
        matchDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        matchLocationCol.setMinWidth(150);
        matchLocationCol.setCellValueFactory(new PropertyValueFactory<>("Tournament"));
        matchLocationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        matchScoreCol.setMinWidth(100);
        matchScoreCol.setCellValueFactory(new PropertyValueFactory<>("winner"));
        matchScoreCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Method used to set the text shown inside the text box's of the add match button.
     */
    private void setMatchTextFieldInfo() {
        addFirstID.setPromptText("Player 1 ID");
        addFirstID.setMaxWidth(100);
        addFirstID.setAlignment(Pos.CENTER);

        addSecondId.setMaxWidth(100);
        addSecondId.setPromptText("Player 2 ID");
        addSecondId.setAlignment(Pos.CENTER);

        addTennisMatchDate.setMaxWidth(100);
        addTennisMatchDate.setPromptText("Date");
        addTennisMatchDate.setAlignment(Pos.CENTER);

        addMatchLocation.setMaxWidth(100);
        addMatchLocation.setPromptText("Location");
        addMatchLocation.setAlignment(Pos.CENTER);

        addMatchScore.setMaxWidth(100);
        addMatchScore.setPromptText("Scores");
        addMatchScore.setAlignment(Pos.CENTER);
    }

    /**
     * Method used to set the effects for the clear database button and the actions for when the button is pressed.
     */
    private void setClearDatabaseButtonInfo() {
        clearDatabase.setTextFill(black);
        clearDatabase.setBlendMode(BlendMode.LIGHTEN);
        clearDatabase.setOnMouseEntered(
                (MouseEvent e) -> {
                    clearDatabase.setBlendMode(green);
                }
        );
        clearDatabase.setOnMouseExited(
                (MouseEvent e) -> {
                    clearDatabase.setBlendMode(BlendMode.LIGHTEN);
                }
        );

        clearDatabase.setOnAction(
                (ActionEvent e) -> {
                    database.reset();
                    data.setAll(database.getTmc().getMatches());
                    table.setItems(data);
                    data2.setAll(database.getBST().getPlayersArray());
                    table2.setItems(data2);
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(clearDatabase, changeToPlayersButton,
                            importButton, exportButton, matchesLabel, myName, table, addMatchSection);
                    ((Group) scene.getRoot()).getChildren().clear();
                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                }
        );
    }

    /**
     * Method used to set the effects for the change to matches button and the actions for when the button is pressed.
     */
    private void setChangeToMatchesButtonInfo() {
        changeToMatchesButton.setTextFill(black);
        changeToMatchesButton.setBlendMode(BlendMode.LIGHTEN);
        changeToMatchesButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    changeToMatchesButton.setBlendMode(green);
                }
        );
        changeToMatchesButton.setOnMouseExited(
                (MouseEvent e) -> {
                    changeToMatchesButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        changeToMatchesButton.setOnAction(
                (ActionEvent e) -> {
                    data.setAll(database.getTmc().getMatches());
                    table.setItems(data);
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(clearDatabase, changeToPlayersButton,
                            importButton, exportButton, matchesLabel, myName, table, addMatchSection);
                    ((Group) scene.getRoot()).getChildren().clear();
                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                }
        );
    }

    /**
     * Method used to set the effects for the change to players button and the actions for when the button is pressed.
     */
    private void setChangeToPlayerButtonInfo() {
        changeToPlayersButton.setTextFill(black);
        changeToPlayersButton.setBlendMode(BlendMode.LIGHTEN);
        changeToPlayersButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    changeToPlayersButton.setBlendMode(green);
                }
        );
        changeToPlayersButton.setOnMouseExited(
                (MouseEvent e) -> {
                    changeToPlayersButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        changeToPlayersButton.setOnAction(
                (ActionEvent e) -> {
                    database.getBST().getPlayersArray().clear();
                    database.getBST().putInArrayList();
                    database.getTmc().goOverMatches(database.getBST());
                    data2.setAll(database.getBST().getPlayersArray());
                    table2.setItems(data2);
                    table2.getColumns().clear();
                    table2.getColumns().addAll(playerIdCol, firstNameCol, lastNameCol, birthYearCol, countryCol);
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(clearDatabase, changeToMatchesButton,
                            importButton, exportButton, playersLabel, myName, table2,
                            addPlayerSection, deletePlayerSection);
                    ((Group) scene.getRoot()).getChildren().clear();
                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                }
        );
    }

    /**
     * Method used to set the effects for the import button and the actions for when the button is pressed.
     */
    private void setImportButtonInfo(Stage stage, String[] args) {
        importButton.setTextFill(black);
        importButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    importButton.setBlendMode(green);
                }
        );
        importButton.setOnMouseExited(
                (MouseEvent e) -> {
                    importButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        importButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null && !file.getAbsolutePath().isEmpty()) {
                        args[0] = file.getAbsolutePath();
                        database.loadFromFile(args);
                        database.getBST().getPlayersArray().clear();
                        database.getBST().putInArrayList();
                        database.getTmc().goOverMatches(database.getBST());
                        data2.setAll(database.getBST().getPlayersArray());
                        table2.setItems(data2);
                        table2.getColumns().clear();
                        table2.getColumns().addAll(playerIdCol, firstNameCol,
                                lastNameCol, birthYearCol, countryCol);
                        data.setAll(database.getTmc().getMatches());
                        table.setItems(data);

                        vbox.getChildren().clear();
                        vbox.getChildren().addAll(clearDatabase, changeToPlayersButton,
                                importButton, exportButton, matchesLabel, myName, table, addMatchSection);
                        ((Group) scene.getRoot()).getChildren().clear();
                        ((Group) scene.getRoot()).getChildren().addAll(vbox);
                    }
                }
        );
    }

    /**
     * Method used to set the effects for the export button and the actions for when the button is pressed.
     */
    private void setExportButtonInfo(Stage stage, String[] args) {
        exportButton.setTextFill(black);
        exportButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    exportButton.setBlendMode(green);
                }
        );
        exportButton.setOnMouseExited(
                (MouseEvent e) -> {
                    exportButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        exportButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null && !file.getAbsolutePath().isEmpty()) {
                        args[0] = file.getAbsolutePath();
                        database.exportToFile(args);
                    }
                }
        );
    }

    /**
     * Method used to set the effects for the delete player button and the actions for when the button is pressed.
     */
    private void setDeletePlayerButtonInfo() {
        deletePlayerButton.setTextFill(black);
        deletePlayerButton.setBlendMode(BlendMode.LIGHTEN);
        deletePlayerButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    deletePlayerButton.setBlendMode(BlendMode.RED);
                }
        );
        deletePlayerButton.setOnMouseExited(
                (MouseEvent e) -> {
                    deletePlayerButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        deletePlayerButton.setOnAction(
                (ActionEvent e) -> {
                    database.getBST().deleteKey(idToDelete.getText());
                    database.getBST().getPlayersArray().clear();
                    database.getBST().putInArrayList();
                    database.getTmc().goOverMatches(database.getBST());
                    idToDelete.clear();
                    data2.setAll(database.getBST().getPlayersArray());
                    table2.setItems(data2);
                }
        );
    }

    /**
     * Method used to set the effects for the add player button and the actions for when the button is pressed.
     */
    private void setAddPlayerButtonInfo() {
        addPlayerButton.setBlendMode(BlendMode.LIGHTEN);
        addPlayerButton.setTextFill(black);
        addPlayerButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    addPlayerButton.setBlendMode(green);
                }
        );
        addPlayerButton.setOnMouseExited(
                (MouseEvent e) -> {
                    addPlayerButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        addPlayerButton.setOnAction(
                (ActionEvent e) -> {
                    TennisPlayer player = new TennisPlayer(addPlayerID.getText(),
                            addFirstName.getText(), addLastName.getText(), parseInt(addYear.getText()), addCountry.getText());
                    database.getBST().insertPlayer(new TennisPlayerNode(player));

                    addPlayerID.clear();
                    addFirstName.clear();
                    addLastName.clear();
                    addYear.clear();
                    addCountry.clear();
                    database.getBST().getPlayersArray().clear();
                    database.getBST().putInArrayList();
                    database.getTmc().goOverMatches(database.getBST());
                    data2.setAll(database.getBST().getPlayersArray());
                    table2.setItems(data2);
                }
        );
    }

    /**
     * Method used to set the effects for the add match button and the actions for when the button is pressed.
     */
    private void setAddMatchButtonInfo() {
        addMatchButton.setBlendMode(BlendMode.LIGHTEN);
        addMatchButton.setTextFill(black);
        addMatchButton.setOnMouseEntered(
                (MouseEvent e) -> {
                    addMatchButton.setBlendMode(green);
                }
        );
        addMatchButton.setOnMouseExited(
                (MouseEvent e) -> {
                    addMatchButton.setBlendMode(BlendMode.LIGHTEN);
                }
        );
        addMatchButton.setOnAction(
                (ActionEvent e) -> {
                    try {
                        database.getTmc().insertMatch(new TennisMatch(addFirstID.getText(), addSecondId.getText(),
                                addTennisMatchDate.getText(), addMatchLocation.getText(),
                                addMatchScore.getText(), database.getBST()));
                    } catch (TennisDatabaseException ex) {
                        Logger.getLogger(Assignment2.class.getName()).log(Level.WARNING,
                                "Trouble inserting match", ex);
                    }
                    addFirstID.clear();
                    addSecondId.clear();
                    addTennisMatchDate.clear();
                    addMatchLocation.clear();
                    addMatchScore.clear();
                    data.setAll(database.getTmc().getMatches());
                    table.setItems(data);
                });
    }

    /**
     * Method used to set display information for popup window when looking for a file.
     * @param fileChooser file chooser
     */
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select File to Import or Export");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
    }
}