import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import javafx.animation.RotateTransition;

/**
 * Created by zhonge on 3/4/17.
 */

public class Gamepage extends Application{

    private Button[][] tileList = new Button[4][4];
    private Board thisBoard = new Board();
    private String highestScore = "2";
    //If the user have a tile with number 2048, the highestScore will be updated to "2048", and an alert message will occur.

    private Stage stage;
    private Scene scene;
    private BorderPane gridBoard;

    private String curStatus = "Current Score: 0";
    private Text scoreText = new Text(curStatus);

    private String curLevel = "Not Set";
    private Text curLevelDisplay = new Text(this.curLevel);

    private Node boardPane = addBoardPane();


    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        BorderPane root = new BorderPane();
        //gridBoard = (BorderPane) addBoardPane();


        root.setCenter(this.boardPane);
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
        this.curLevel = chosenlevel;
    }

    public String getLevel() {
        return this.curLevel;
    }
    public Board getBoard(){
        return this.thisBoard;
    }

    public Stage getStage() {
        System.out.println("Getstage Called");
        BorderPane root = new BorderPane();

        root.setCenter(this.boardPane);
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
    private Grid[][] copyBoard(){
        Grid[][] virtualGird = new Grid[4][4];
        Grid[][] girdList = getBoard().getGridList();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                virtualGird[i][j] = girdList[i][j].clone();
            }
        }
        return virtualGird;
    }
    private Board getVirtualBoard(){
        Board virtualGame = new Board();
        Grid[][] virtualGird = copyBoard();
        virtualGame.setGridList(virtualGird);
        virtualGame.setScore(this.thisBoard.getScore());
        return virtualGame;
    }
    // If the board is full, we need to test whether this board is playable or not by testing the possiblity of adding 
    // any score. if not, end the game!
    public boolean playable(){
        double scoreLeft, scoreRight, scoreUp, scoreDown;
        Board leftBoard = getVirtualBoard();
        leftBoard.rollLeft();
        scoreLeft = leftBoard.getScore();
        Board rightBoard = getVirtualBoard();
        rightBoard.rollRight();
        scoreRight = rightBoard.getScore();
        Board upBoard = getVirtualBoard();
        upBoard.rollUp();
        scoreUp = upBoard.getScore();
        Board downBoard = getVirtualBoard();
        downBoard.rollDown();
        scoreDown = downBoard.getScore();
        double currentscore = this.thisBoard.getScore();
        if(scoreLeft == currentscore && scoreDown == currentscore && scoreUp == currentscore && scoreRight == currentscore){
            System.out.println(scoreDown);
            System.out.println(scoreUp);
            System.out.println("currentScore = " + currentscore);
            return false;
        }
        return true;
    }
    public void drawInitialBoard() {
        printTileList();
        this.thisBoard.startGame();
        this.thisBoard.generateRandom();
        updateBoard(this.thisBoard);
    }

    public void playLevel1() {
        
        drawInitialBoard(); 
        level1();
        

        // If score is 2048, alert.
    }

    public void level1(){
        thisBoard.resetStatus();
        setLevel("Level 1");
        this.curLevelDisplay.setText(getLevel());

        addKeyHandler();
    }

    public void playLevel2() {
        drawInitialBoard();

        level2();
    }

    public void rotateBoard() {


        RotateTransition rt = new RotateTransition(Duration.millis(3000), this.boardPane);
        rt.setByAngle(-90);
        rt.setAutoReverse(true);
        rt.play();
        //Rotate rotationTransform = new Rotate(90, 600, 260);
        //this.boardPane.getTransforms().add(rotationTransform);
    }

    public void gravity() {
        //rotateBoard();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
            thisBoard.rollLeft1();
            updateBoard(thisBoard);
        }));
        timeline.setCycleCount(3);
        timeline.play();
        Button[][] thisTileList = this.tileList;

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thisBoard.generateRandom();
                updateCurScore();
                updateBoard(thisBoard);

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {

                        Button currentTile = thisTileList[i][j];
                        //Rotate rotationTransform = new Rotate(45);
                        //currentTile.getTransforms().add(rotationTransform);
                        RotateTransition rt = new RotateTransition(Duration.millis(0.01), currentTile);
                        rt.setByAngle(-90);
                        rt.setAutoReverse(true);
                        rt.play();
                    }
                }
            }
        });
    }

    public void level2(){
        thisBoard.resetStatus();
        setLevel("Level 2");
        //rotateBoard();
        gravity();
        this.curLevelDisplay.setText(getLevel());
        addKeyHandler();
    }

    private void addKeyHandler () {
        Board thisBoard = this.thisBoard;
        Board virtualBoard = getVirtualBoard();
        double oldScore = thisBoard.getScore()/1;
        this.thisBoard.setScore(oldScore);
        this.scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                KeyCode keyCode = e.getCode();
                if (keyCode == KeyCode.DOWN){
                    virtualBoard.rollDown();
                    double newScore = virtualBoard.getScore();
                    if(!isMoved(oldScore, newScore)){
                        level1();
                        return;
                    }

                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollDown1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();
                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            thisBoard.generateRandom();
                            updateCurScore();
                            updateBoard(thisBoard);
                        }
                    });

                    if(!thisBoard.isFull()){
                        level1();
                    }else{
                        if (playable()){
                            level1();
                        }
                        else{displayLost();}
                    }
                } else if (keyCode == KeyCode.UP) {
                    virtualBoard.rollUp();
                    double newScore = virtualBoard.getScore();
                    if(!isMoved(oldScore,newScore)){
                        level1();
                        return;
                    }
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollUp1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();
                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            thisBoard.generateRandom();
                            updateCurScore();
                            updateBoard(thisBoard);
                        }
                    });

                    if(!thisBoard.isFull()){
                        level1();
                    }else{
                        if (playable()){
                            level1();
                        }
                        else{displayLost();}
                    }

                } else if (keyCode == KeyCode.LEFT) {
                    virtualBoard.rollLeft();
                    double newScore = virtualBoard.getScore();
                    if(!isMoved(oldScore,newScore)){
                        level1();
                        return;
                    }

                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollLeft1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            thisBoard.generateRandom();
                            updateCurScore();
                            updateBoard(thisBoard);
                        }
                    });


                    if(!thisBoard.isFull()){
                        level1();
                    }else{
                        if (playable()){
                            level1();
                        }
                        else{displayLost();}
                    }

                } else if (keyCode == KeyCode.RIGHT) {
                    virtualBoard.rollRight();
                    double newScore = virtualBoard.getScore();
                    if(!isMoved(oldScore,newScore)){
                        level1();
                        return;
                    }
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
                        thisBoard.rollRight1();
                        updateBoard(thisBoard);
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();

                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            thisBoard.generateRandom();
                            updateCurScore();
                            updateBoard(thisBoard);
                        }
                    });

                    if(!thisBoard.isFull()){
                        level1();
                    }else{
                        if (playable()){
                            level1();
                        }
                        else{displayLost();}
                    }
                }
            }
        });
    }


        public boolean isMoved(double oldScore, double newScore){
            System.out.println("oldScore " + oldScore);
            System.out.println("newScore " + newScore);
            if (oldScore == newScore){
                System.out.println(false);
                return false;
            }
            return true;
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

    private void updateCurScore() {

        int newScore = (int) this.thisBoard.getScore();

        //Get new score from backend, update curStatus
        this.curStatus = "Current Score: " + newScore;

        //Update scoreText to display new status
        this.scoreText.setText(this.curStatus);

        System.out.println(this.scoreText);
        System.out.println("newScore: " + newScore);
        System.out.println("this.curStatus = " + this.curStatus);
    }


    private Node addScoreDisplay() {

        StackPane scorePane = new StackPane();

        this.scoreText.setText(this.curStatus);

        this.scoreText.setId("score");

        scorePane.getChildren().add(this.scoreText);
        scorePane.setAlignment(this.scoreText, Pos.TOP_LEFT);
        scorePane.setAlignment(Pos.TOP_CENTER);
        return scorePane;
    }

    //This method either returns the current score,
    //or notify the user that the 'Gravity' function or 'Substraction' is happening.
    /*
    private String getCurrentCondition() {
        String curCondition = "";
        //if gravity happens, curCondition = "Gravity";
        //else if substraction happens, curCondition = "Substraction";
        //else, return score.
        if (this.thisBoard.gridList != null){
            System.out.println("Gridlist null");
            int currentScore = (int)this.thisBoard.getScore();
            curCondition = "Current Score: " + currentScore;
        } else {
            curCondition = "Current Score: " + "0";
        }
        //A function should return the actual current score.
        return curCondition;
    }
    */


    private Node displayLevel() {

        StackPane levelPane = new StackPane();
        levelPane.setId("levelPane");

        //Rectangle rec = new Rectangle (100, 45, Color.YELLOW);
        //rec.setArcWidth(10);
        //rec.setArcHeight(10);
        //Button level = new Button("Level 1");

        this.curLevelDisplay.setText(this.curLevel);

        this.curLevelDisplay.setId("displayLevel");

        //levelPane.getChildren().add(rec);
        levelPane.getChildren().add(this.curLevelDisplay);
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
