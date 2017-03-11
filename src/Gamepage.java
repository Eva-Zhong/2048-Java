import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Created by zhonge on 3/4/17.
 */

public class Gamepage extends Application{

    private Button[][] tileList = new Button[4][4];
    private Board thisBoard = new Board();
    private String highestScore = "2";
    //If the user have a tile with number 2048, the highestScore will be updated to "2048", and an alert message will occur.

    private Stage stage;
    private String level;
    private Scene scene;


    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        BorderPane root = new BorderPane();

        root.setCenter(addBoardPane());
        root.setTop(addTopPane());

        printTileList();

        this.scene = new Scene(root,1200,800);

        this.scene.getStylesheets().add
                (Gamepage.class.getResource("Gamepage.css").toExternalForm());
        stage.setTitle("GAME 2048");
        stage.setScene(scene);
        stage.show();

        if (this.highestScore == "2048") {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("Alert.css").toExternalForm());
            dialog.getStyleClass().add("alert");
            alert.setTitle("CONGRATULATIONS!");
            dialog.setPrefSize(350,160);
            alert.setHeaderText(null);
            alert.setContentText("YOU WIN!");
            Button continueGame = (Button) dialog.lookupButton(ButtonType.CLOSE);
            Button newGame = (Button) dialog.lookupButton(ButtonType.OK);
            continueGame.setText("Continue");
            newGame.setText("New Game");

            //alert.getButtonTypes().setAll(continueGame, newGame);
            Optional<ButtonType> result = alert.showAndWait();

            if (ButtonType.CLOSE.equals(result.get())){
                alert.close();
            } else if (ButtonType.OK.equals(result.get())){
                MainPage mainpage = new MainPage();
                Stage newstage = mainpage.getStage();
                newstage.show();
                stage.close();
            }
        }
    }

    public void setLevel(String chosenlevel) {
        this.level = chosenlevel;
    }

    public String getLevel() {
        return this.level;
    }

    public Stage getStage() {
        BorderPane root = new BorderPane();

        root.setCenter(addBoardPane());
        root.setTop(addTopPane());

        this.scene = new Scene(root,1200,800);

        this.scene.getStylesheets().add
                (Gamepage.class.getResource("Gamepage.css").toExternalForm());
        stage = new Stage();
        stage.setTitle("GAME 2048");
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public Node addBoardPane() {

        StackPane board = new StackPane();
        //Create the background of the grid
        Rectangle rec = new Rectangle();
        rec.setWidth(500);
        rec.setHeight(520);
        rec.setFill(Color.rgb(52,30,105,0.4));

        //Create the grid with 16 squares
        GridPane boardgrid = new GridPane();
        boardgrid.setAlignment(Pos.CENTER);
        boardgrid.setHgap(10);
        boardgrid.setVgap(10);

        int rowNum = 4;
        int colNum = 4;
        for (int row = 0; row < rowNum; row++) {
            for (int col=0; col < colNum; col++) {
                Rectangle newtile = new Rectangle();

                newtile.setFill(Color.rgb(180,130,180,0.2));

                newtile.setWidth(110);
                newtile.setHeight(110);
                newtile.setX(50);
                newtile.setY(50);
                boardgrid.add(newtile, col, row);
            }
            boardgrid.setGridLinesVisible(true);
        }

        //add both the background and the grid to the board
        board.getChildren().add(rec);

        //Need a function that initialize the tiles at the beginning;
        board.getChildren().add(boardgrid);

        //put the children at the center
        board.setAlignment(rec, Pos.CENTER);
        board.setAlignment(boardgrid, Pos.CENTER);

        board.setAlignment(Pos.BOTTOM_CENTER);
        board.setPadding(new Insets(0,10,20,10));

        board.getChildren().add(add16Tiles());

        //addTiles(grid, "2", 1, 1 );

        //Need a separate function to add tiles


        return board;
    }

    private Node add16Tiles() {
        GridPane tileGrid = new GridPane();
        tileGrid.setAlignment(Pos.CENTER);
        tileGrid.setHgap(10);
        tileGrid.setVgap(10);

        int rowNum = 4;
        int colNum = 4;
        for (int row = 0; row < rowNum; row++) {
            for (int col=0; col < colNum; col++) {

                Button newtile = new Button("button"+row+col);

                newtile.setMinWidth(110);
                newtile.setMinHeight(110);
                newtile.setStyle("-fx-background-color: rgba(245,216,88,0.0)");


                tileGrid.add(newtile, col, row);

                int[] keyList = {row, col};

                //Print array: system.out.println(Arrays.toString(keyList));

                //Put the 16 buttons in a hashmap; each button is associated with a position
                //this.tileMap.put(keyList, newtile);

                this.tileList[row][col] = newtile;
                if (this.tileList[row][col] == null){
                System.out.println("null"+row+col);
                }
                //int[] thisKey = {1,2};

                //if (Arrays.equals(keyList, thisKey))  {
                //    System.out.println("true");
                }
            }
        return tileGrid;
    }

    public void updateBoard(Board aBoard) {
        Grid[][] gridList = aBoard.getGridList();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Grid currentGrid = gridList[i][j];

                int currentNum = currentGrid.getNumber();

                Button currentTile = this.tileList[i][j];
                
                String stringCurrentNum = Integer.toString(currentNum);
                currentTile.setId(stringCurrentNum);
                currentTile.setText(stringCurrentNum);
                
                System.out.println(currentTile.getText());

                if (currentGrid.getDisplay() == true) {
                    currentTile.setStyle("-fx-font-size: 40px; -fx-font-family: 'Palatino'; -fx-text-fill: #fbfbfb");

                } if (currentGrid.getDisplay() == false) {
                    currentTile.setStyle("-fx-background-color: rgba(245, 216, 88, 0.0)");
                }
            }
        }
    }
    public void printTileList(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(this.tileList[i][j].getText()); 
            }
        }
    }
    public void drawInitialBoard() {
        printTileList();
        this.thisBoard.startGame();
        this.thisBoard.generateRandom();
        updateBoard(this.thisBoard);
    }

    public void playLevel1() {
        
        drawInitialBoard();

        System.out.println("playLevel1");
        
        level1();
        


        // If score is 2048, alert.
    }
    public void level1(){
        addKeyHandler();
    }
    private void addKeyHandler () {
        Board thisBoard = this.thisBoard;

        this.scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                KeyCode keyCode = e.getCode();
                if (keyCode == KeyCode.DOWN){
                    thisBoard.rollDown();
                    thisBoard.generateRandom();
                    updateBoard(thisBoard);
                    thisBoard.printBoard();
                    if(!thisBoard.isFull()){
                        level1();
                    }else{
                        displayLost();
                    }
                } else if (keyCode == KeyCode.UP) {
                    thisBoard.rollUp();
                    thisBoard.generateRandom();
                    updateBoard(thisBoard);
                    thisBoard.printBoard();
                    if(!thisBoard.isFull()){
                        level1();
                    }
                    else{
                        displayLost();
                    }

                } else if (keyCode == KeyCode.LEFT) {
                    thisBoard.rollLeft();
                    thisBoard.generateRandom();
                    updateBoard(thisBoard);
                    thisBoard.printBoard();
                    if(!thisBoard.isFull()){
                        level1();
                    }
                     else{
                        displayLost();
                    }

                } else if (keyCode == KeyCode.RIGHT) {
                    thisBoard.rollRight();
                    thisBoard.generateRandom();
                    updateBoard(thisBoard);
                    thisBoard.printBoard();
                    if(!thisBoard.isFull()){
                        level1();
                    }
                    else{
                        displayLost();
                    }
                }
            }
        });
    }
    
    
        public void displayLost(){
            MainPage mainpage = new MainPage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("Alert.css").toExternalForm());
            dialog.getStyleClass().add("alert");
            alert.setTitle("YOU LOST!");
            dialog.setPrefSize(350,160);
            alert.setHeaderText(null);
            alert.setContentText("GOOD GAME!");
            Button newGame = (Button) dialog.lookupButton(ButtonType.OK);
            newGame.setText("New Game");
            //alert.getButtonTypes().setAll(continueGame, newGame);
            Optional<ButtonType> result = alert.showAndWait();

            if (ButtonType.OK.equals(result.get())){
                        Stage newstage = mainpage.getStage();
                        newstage.show();
                        stage.close();
            }
        }

    //Get a keyboard input from the user; return the input;

    /*
    private Node addTiles(GridPane grid, String num, int col, int row) {
        GridPane newGrid = grid;
        Button atile = new Button(num);
        atile.setId("tileLable");
        atile.setMinWidth(110);
        atile.setMinHeight(110);
        newGrid.add(atile, col, row);


        return newGrid;
    }
    */




    //This function creates the top part of the game interface, including the game title,
    //the current level, and the current score.

    public Node addTopPane() {

        GridPane topPane = new GridPane();

        topPane.setPadding(new Insets(100,10,10,10));

        topPane.setPrefSize(400, 200);

        topPane.setHgap(10);
        topPane.setVgap(10);

        //Set constraints to the size of the columns
        ColumnConstraints col1 = new ColumnConstraints(240);
        ColumnConstraints col2 = new ColumnConstraints(240);
        topPane.getColumnConstraints().add(0, col1);
        topPane.getColumnConstraints().add(1, col2);

        //Add the elemets into the topPane.
        topPane.add(addTitle(), 0, 0);
        topPane.add(addScoreDisplay(),1 , 0);
        topPane.add(displayLevel(), 1, 0);
        topPane.add(newGameButton(), 1, 0);

        //Put the topPane at the bottom of the center of the Border Pane
        topPane.setAlignment(Pos.BOTTOM_CENTER);

        return topPane;
    }


    private Node addTitle() {

            StackPane titlePane = new StackPane();
            titlePane.setId("titlePane");
            Button gametitle = new Button("2048");
            gametitle.setMinHeight(60);
            gametitle.setMinWidth(220);
            //Text gametitle = new Text("2048");
            gametitle.setId("gametitle");
            //Rectangle rec = new Rectangle (220,100, Color.YELLOW);
            //rec.setArcWidth(10);
            //rec.setArcHeight(10);

            //titlePane.getChildren().add(rec);
            titlePane.getChildren().add(gametitle);

            titlePane.setAlignment(Pos.CENTER);

        return titlePane;
    }

    private Node addScoreDisplay() {

        StackPane scorePane = new StackPane();

        Text score = new Text (getCurrentCondition());
        score.setId("score");
        scorePane.getChildren().add(score);
        scorePane.setAlignment(score, Pos.TOP_LEFT);
        scorePane.setAlignment(Pos.TOP_CENTER);

        return scorePane;
    }


    //This method either returns the current score,
    //or notify the user that the 'Gravity' function or 'Substraction' is happening.
    private String getCurrentCondition() {
        String curCondition = "";
        //if gravity happens, curCondition = "Gravity";
        //else if substraction happens, curCondition = "Substraction";
        //else, return score.

        curCondition = "Current Score: " + "10000";
        //A function should return the actual current score.

        return curCondition;
    }


    private Node displayLevel() {

        StackPane levelPane = new StackPane();
        levelPane.setId("levelPane");

        //Rectangle rec = new Rectangle (100, 45, Color.YELLOW);
        //rec.setArcWidth(10);
        //rec.setArcHeight(10);
        //Button level = new Button("Level 1");
        Text level = new Text (getLevel());
        System.out.println(getLevel());
        level.setId("displayLevel");

        //levelPane.getChildren().add(rec);
        levelPane.getChildren().add(level);
        levelPane.setAlignment(Pos.BOTTOM_LEFT);

        return levelPane;
    }

    private String getCurrentLevel() {
        String curLevel = "level 2";
        return curLevel;
    }


    private Node newGameButton() {

        MainPage mainpage = new MainPage();

        StackPane newGamePane = new StackPane();
        newGamePane.setId("newGamePane");

        Button newGameButton = new Button("New Game");
        newGameButton.setId("newGameButton");

        newGamePane.getChildren().add(newGameButton);

        newGamePane.setAlignment(Pos.BOTTOM_RIGHT);

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage newstage = mainpage.getStage();
                newstage.show();
                stage.close();

            }
        });

        return newGamePane;

    }

    public static void main(String[] args) {


    }
}
