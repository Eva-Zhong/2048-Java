import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.Optional;
import java.util.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import javafx.animation.RotateTransition;


/**
 * Created by zhonge and zhaom on 3/4/17.
 */

public class Gamepage extends Application{

    private Button[][] tileList = new Button[4][4];
    private Board thisBoard = new Board();

    //If the user have a tile with number 2048, the highestScore will be updated to "2048", and an alert message will occur.
    private int highestScore = 2;

    //If 2048 occurs for the first time, FIRSTTIME will be changed to true.
    private boolean FIRSTTIME = false;

    private Stage stage;
    private Scene scene;
    private BorderPane gridBoard;

    private String curStatus = "Current Score: 0";
    private Text scoreText = new Text(curStatus);

    private String curLevel = "Not Set";
    private Text curLevelDisplay = new Text(this.curLevel);

    //the board as an instance variable
    private StackPane boardPane = addBoardPane();


    //set the stage for Gamepage;
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        BorderPane root = new BorderPane();

        root.setCenter(this.boardPane);
        root.setTop(addTopPane());

        //printTileList();

        this.scene = new Scene(root,1200,800);

        this.scene.getStylesheets().add
                (Gamepage.class.getResource("styles/Gamepage.css").toExternalForm());
        stage.setTitle("GAME 2048");
        stage.setScene(scene);
        stage.show();
    }

    //Set curLevel as the level chosen by the user
    public void setLevel(String chosenlevel) {
        this.curLevel = chosenlevel;
    }

    //return curLevel
    public String getLevel() {
        return this.curLevel;
    }

    //return the Board object
    public Board getBoard(){
        return this.thisBoard;
    }

    //return the stage of Gamepage
    public Stage getStage() {
        BorderPane root = new BorderPane();
        root.setCenter(this.boardPane);
        root.setTop(addTopPane());
        this.scene = new Scene(root,1200,800);
        this.scene.getStylesheets().add
                (Gamepage.class.getResource("styles/Gamepage.css").toExternalForm());

        stage = new Stage();
        stage.setTitle("GAME 2048");
        stage.setScene(scene);
        stage.show();
        return stage;
    }



    //Layout of Gamepage: this method creates the top part of the game, and updates the current score and the current level.
    //It also displays the game title, and contain the newgame button.
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

    // This method creates the title of the topPane.
    private Node addTitle() {

        StackPane titlePane = new StackPane();
        titlePane.setId("titlePane");
        Button gametitle = new Button("2048");
        gametitle.setMinHeight(60);
        gametitle.setMinWidth(220);
        gametitle.setId("gametitle");

        titlePane.getChildren().add(gametitle);

        titlePane.setAlignment(Pos.CENTER);
        return titlePane;
    }

    // This method keeps the current score up-to-date
    private void updateCurScore() {

        int newScore = (int) this.thisBoard.getScore();

        //Get new score from backend, update curStatus
        this.curStatus = "Current Score: " + newScore;

        //Update scoreText to display new status
        this.scoreText.setText(this.curStatus);
    }

    // This method displays the current score.
    private Node addScoreDisplay() {

        StackPane scorePane = new StackPane();
        this.scoreText.setText(this.curStatus);
        this.scoreText.setId("score");

        scorePane.getChildren().add(this.scoreText);
        scorePane.setAlignment(this.scoreText, Pos.TOP_LEFT);
        scorePane.setAlignment(Pos.TOP_CENTER);
        return scorePane;
    }

    // This method displays the current level of the game
    private Node displayLevel() {

        StackPane levelPane = new StackPane();
        levelPane.setId("levelPane");

        this.curLevelDisplay.setText(this.curLevel);

        this.curLevelDisplay.setId("displayLevel");

        levelPane.getChildren().add(this.curLevelDisplay);
        levelPane.setAlignment(Pos.BOTTOM_LEFT);

        return levelPane;
    }

    // This method creates the newgame button.
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



    //Layout of Gamepage: create the board that contains the background of the board, 16 squares, and 16 buttons.
    //The background is rectangle;
    //The squares are in a gridpane;
    //the 16 buttons are in a separate gridpane;
    public StackPane addBoardPane() {

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
        board.getChildren().add(boardgrid);

        //put the children at the center
        board.setAlignment(rec, Pos.CENTER);
        board.setAlignment(boardgrid, Pos.CENTER);

        board.setAlignment(Pos.BOTTOM_CENTER);
        board.setPadding(new Insets(0,10,20,10));

        //add 16 tiles to the board
        board.getChildren().add(add16Tiles());

        return board;
    }

    //add 16 tiles to the boardPane
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

                //add the tile object into a list for later use;
                this.tileList[row][col] = newtile;
                }
            }

        return tileGrid;
    }


    /* This method gets updated information from the backend,
     and update the view of the board by changing the transparency of each tile.
     By default, each tile is transparent;
     If it has a non-zero value, we set it as not transparent, so the number will be displayed.
    */

    public void updateBoard(Board aBoard) {
        Grid[][] gridList = aBoard.getGridList();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Grid currentGrid = gridList[i][j];

                int currentNum = currentGrid.getNumber();

                Button currentTile = this.tileList[i][j];

                //Set ID, text and color of the tile
                String stringCurrentNum = Integer.toString(currentNum);
                currentTile.setId(stringCurrentNum);
                currentTile.setText(stringCurrentNum);

                //if the value of the tile is non-zero, make it not transparent; otherwise, make it as transparent.
                if (currentGrid.getDisplay() == true) {
                    currentTile.setStyle("-fx-font-size: 40px; -fx-font-family: 'Palatino'; -fx-text-fill: #fbfbfb");

                } else if (currentGrid.getDisplay() == false) {
                    currentTile.setStyle("-fx-background-color: rgba(245, 216, 88, 0.0)");
                }

                // update highestNum
                if (currentNum > this.highestScore) {
                    this.highestScore = currentNum;

                    // If it's level 2, call gravity() when the user gets to 32, 256, 1024
                    if (getLevel()=="Level 2") {
                        if (this.highestScore == 32) {
                            System.out.println("this.highestScore: " + this.highestScore);

                            gravity();
                        } if (this.highestScore == 256) {
                            gravity();
                        } if (this.highestScore == 1024) {
                            gravity();
                        }
                    } else if (getLevel() == "Level 3") {
                        if (this.highestScore == 32) {
                            System.out.println("this.highestScore: " + this.highestScore);

                            gravitySubtract();
                        } if (this.highestScore == 256) {
                            gravitySubtract();
                        } if (this.highestScore == 1024) {
                            gravitySubtract();
                        }
                    }
                }

            }
        }
    }

    //Copy the board as a virtual board, to test what will happen if a move is made
    private Board copyBoard(Board copyBoard){
        Board virtualGame = new Board();
        Grid[][] virtualGird = new Grid[4][4];
        Grid[][] girdList = copyBoard.getGridList();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                virtualGird[i][j] = girdList[i][j].clone();
            }
        }
        virtualGame.setGridList(virtualGird);
        virtualGame.setScore(copyBoard.getScore());
        return virtualGame;
    }

    // If the board is full, we need to test whether this board is playable or not by testing the possiblity of adding 
    // any score. if not, end the game!
    public boolean playable(Board thisBoard){
        double scoreLeft, scoreRight, scoreUp, scoreDown;
        Board leftBoard = copyBoard(thisBoard);
        leftBoard.rollLeft();
        scoreLeft = leftBoard.getScore();
        Board upBoard = copyBoard(thisBoard);
        upBoard.rollUp();
        scoreUp = upBoard.getScore();
        double currentscore = thisBoard.getScore();
        if(scoreLeft == currentscore && scoreUp == currentscore){
            System.out.println(scoreUp);
            System.out.println("currentScore = " + currentscore);
            return false;
        }
        return true;
    }

    // When the game first starts, display the initial board
    public void drawInitialBoard() {
        //printTileList();
        this.thisBoard.startGame();
        this.thisBoard.generateRandom();
        updateBoard(this.thisBoard);
    }

    // A method that plays the game; call different functions depending on the level chosen.
    public void playGame(){
        System.out.println("Current Level: "+getLevel());
        if (getLevel() == "Level 1") {
            level1();
        } else if (getLevel() == "Level 2") {
            level2();
        } else if (getLevel() == "Level 3") {
            //level3();
        }
    }

    // Draw initial board and play level 1
    public void playLevel1() {
        drawInitialBoard(); 
        level1();
    }


    // Play level1
    public void level1(){
        thisBoard.resetStatus();
        setLevel("Level 1");
        this.curLevelDisplay.setText(getLevel());
        addKeyHandler();
    }

    // Draw initial board and play level2
    public void playLevel2() {
        drawInitialBoard();

        level2();
    }
    // This function plays level 2
    public void level2(){
        thisBoard.resetStatus();
        setLevel("Level 2");
        this.curLevelDisplay.setText(getLevel());
        addKeyHandler();
    }

    public void playLevel3() {
        drawInitialBoard();

        level3();
    }

    // This function plays level 3
    public void level3() {
        thisBoard.resetStatus();
        setLevel("Level 3");
        this.curLevelDisplay.setText(getLevel());
        addKeyHandler();
    }
    /*
    Gravity function is called in level 2 and 3.
    When users reach 32, 256 or 1024, the board rotates for 90 degrees, and the tiles drop down, as if they are pulled
    by gravity.
     */
    public void gravity() {

        // The whole board rotates for 90 degrees
        RotateTransition rt1 = new RotateTransition(Duration.millis(2000), this.boardPane);
        rt1.setByAngle(-90);
        rt1.setAutoReverse(false);
        rt1.play();

        StackPane thisBoardPane = this.boardPane;

        Board newBoard = this.thisBoard;

        /* After the board rotates (the view),
            1. Rotate the board back but does not update this change in the view. This is to make sure that the column
            index and row index corresponds to the indices in the backend.
            2. Rotate each tile in the board back.
            3. Call the backend, and update the board, so the values and positions of the tiles are consistent with
            the backend.
        */
        rt1.setOnFinished((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rotate rotationTransform = new Rotate(90, 600, 260);
                thisBoardPane.getTransforms().add(rotationTransform);
                newBoard.rotate();

                updateBoard(newBoard);
                System.out.println("Board after rotate");
                gravityDrop();
            }
        }));
    }


    // This method creates the effect of the tiles dropping down as if they are pulled by gravity.
    // It first calls 'rolldown' method of the board class, and then merge the tiles if necessary.
    public void gravityDrop() {
        Button[][] thisTileList = this.tileList;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), ev -> {
            thisBoard.rollDown1();
            updateBoard(thisBoard);
            System.out.println("BOARD AFTER GRAVITY DROP");
        }));

        timeline.setCycleCount(3);
        timeline.play();

        timeline.setOnFinished(new EventHandler<ActionEvent>()
        {@Override
        public void handle(ActionEvent event) {
            thisBoard.generateRandom();
            updateCurScore();
            updateBoard(thisBoard);
        }
        });
    }

    public void gravitySubtract() {

            // The whole board rotates for 90 degrees
            RotateTransition rt1 = new RotateTransition(Duration.millis(2000), this.boardPane);
            rt1.setByAngle(-90);
            rt1.setAutoReverse(false);
            rt1.play();

            StackPane thisBoardPane = this.boardPane;

            Board newBoard = this.thisBoard;

            rt1.setOnFinished((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Rotate rotationTransform = new Rotate(90, 600, 260);
                    thisBoardPane.getTransforms().add(rotationTransform);
                    newBoard.rotate();

                    updateBoard(newBoard);
                    System.out.println("Board after rotate");
                    gravityDropSubtract();
                }
            }));
    }

    public void gravityDropSubtract() {
        Button[][] thisTileList = this.tileList;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), ev -> {
            thisBoard.rollDownSubtract1();
            updateBoard(thisBoard);
            System.out.println("BOARD AFTER GRAVITY DROP");
        }));

        timeline.setCycleCount(3);
        timeline.play();

        timeline.setOnFinished(new EventHandler<ActionEvent>()
        {@Override
        public void handle(ActionEvent event) {
            thisBoard.generateRandom();
            updateCurScore();
            updateBoard(thisBoard);
        }
        });
    }

    // This method takes in the keyboard input of an arrow key, and move the tiles accordingly.
    private void addKeyHandler () {
        Board thisBoard = this.thisBoard;
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            // this function's API can not be changed, so we have to write codes
            // inside this function.
            public void handle(KeyEvent e) {
                KeyCode keyCode = e.getCode();
                if (keyCode == KeyCode.DOWN) {
                    int score = (int) thisBoard.getScore();
                    double oldScore = score;
                    thisBoard.setScore(oldScore);
                    Board virtualBoard = copyBoard(thisBoard);
                    virtualBoard.rollDown();
                    double newScore = virtualBoard.getScore();

                    // If no tiles can be moved, display a message that the player loses the game.
                    if (!isMoved(oldScore, newScore)) {
                        if (thisBoard.isFull()) {
                            if (!playable(thisBoard)) {
                                displayLost();
                                return;
                            }
                        }
                        playGame();
                        return;
                    }

                    // The timeline is called three times; in each call, the tiles are rolled down for only one
                    // row(or column, depending on the direction), and the view is updated.
                    // We repete this step for three times to create the effect that the tiles are 'moving'.
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollDown1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    // Once we finished rolling the tiles, we check if the highest score has reached 2048.
                    // if it does, display a message that the player has won.
                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    thisBoard.generateRandom();
                                    updateCurScore();
                                    updateBoard(thisBoard);
                                    if (highestScore == 2048 && FIRSTTIME == false) {
                                        displayWin();
                                        FIRSTTIME = true;
                                    }
                                }
                            });
                        }
                    });

                    playGame();

                // Check if the tiles can be rolled up
                } else if (keyCode == KeyCode.UP) {
                    int score = (int) thisBoard.getScore();
                    double oldScore = score;
                    thisBoard.setScore(oldScore);
                    Board virtualBoard = copyBoard(thisBoard);
                    virtualBoard.rollUp();
                    double newScore = virtualBoard.getScore();
                    if (!isMoved(oldScore, newScore)) {
                        if (thisBoard.isFull()) {
                            if (!playable(thisBoard)) {
                                displayLost();
                                return;
                            }
                        }
                        playGame();
                        return;
                    }

                    // If the tiles can be rolled up, roll them up (one row each time and repete this step for three times)
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollUp1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    // Once finished moving the tiles, check highest score.
                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    thisBoard.generateRandom();
                                    updateCurScore();
                                    updateBoard(thisBoard);
                                    if (highestScore == 2048 && FIRSTTIME == false) {
                                        displayWin();
                                        FIRSTTIME = true;
                                    }
                                }
                            });
                        }
                    });
                    playGame();

                // Check if the tiles can be rolled left
                } else if (keyCode == KeyCode.LEFT) {
                    int score = (int) thisBoard.getScore();
                    double oldScore = score;
                    thisBoard.setScore(oldScore);
                    Board virtualBoard = copyBoard(thisBoard);
                    virtualBoard.rollLeft();
                    double newScore = virtualBoard.getScore();
                    if (!isMoved(oldScore, newScore)) {
                        if (thisBoard.isFull()) {
                            if (!playable(thisBoard)) {
                                displayLost();
                                return;
                            }
                        }
                        playGame();
                        return;
                    }

                    // If the tiles can be rolled up, roll them to the left (one column each time and
                    // repete this step for three times)
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollLeft1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    thisBoard.generateRandom();
                                    updateCurScore();
                                    updateBoard(thisBoard);
                                    if (highestScore == 2048 && FIRSTTIME == false) {
                                        displayWin();
                                        FIRSTTIME = true;
                                    }
                                }
                            });
                        }
                    });
                    playGame();

                    // Check if the tiles can be rolled right
                } else if (keyCode == KeyCode.RIGHT) {
                    int score = (int) thisBoard.getScore();
                    double oldScore = score;
                    thisBoard.setScore(oldScore);
                    Board virtualBoard = copyBoard(thisBoard);
                    virtualBoard.rollRight();
                    double newScore = virtualBoard.getScore();
                    if (!isMoved(oldScore, newScore)) {
                        if (thisBoard.isFull()) {
                            if (!playable(thisBoard)) {
                                displayLost();
                                return;
                            }
                        }
                        playGame();
                        return;
                    }

                    // If the tiles can be rolled right, roll them to the right (one column each time and
                    // repete this step for three times)
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollRight1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    thisBoard.generateRandom();
                                    updateCurScore();
                                    updateBoard(thisBoard);
                                    if (highestScore == 2048 && FIRSTTIME == false) {
                                        displayWin();
                                        FIRSTTIME = true;
                                    }
                                }
                            });
                        }
                    });
                    playGame();
                }
            }
        });
    }

        // Check if any tiles are moved;
        // if false is returned, then no tiles will be moved and the view will not be updated
        public boolean isMoved(double oldScore, double newScore){
            System.out.println("oldScore " + oldScore);
            System.out.println("newScore " + newScore);
            if (oldScore == newScore){
                System.out.println(false);
                return false;
            }
            return true;
        }

        // Display a message when the player looses the game.
        public void displayLost() {
            MainPage mainpage = new MainPage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("styles/Alert.css").toExternalForm());
            dialog.getStyleClass().add("alert");
            alert.setTitle("YOU LOSE");
            dialog.setPrefSize(350, 160);
            alert.setHeaderText(null);
            alert.setContentText("TRY HARDER");
            Button newGame = (Button) dialog.lookupButton(ButtonType.OK);
            newGame.setText("New Game");
            //alert.getButtonTypes().setAll(continueGame, newGame);
            Optional<ButtonType> result = alert.showAndWait();

            if (ButtonType.OK.equals(result.get())) {
                Stage newstage = mainpage.getStage();
                newstage.show();
                stage.close();
            }
        }


        // Display a message when the player reaches 2048
        public void displayWin() {
            MainPage mainpage = new MainPage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("styles/Alert.css").toExternalForm());
            dialog.getStyleClass().add("alert");
            alert.setTitle("YOU WIN!");
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


    public static void main(String[] args) {

    }
}
