/**
 * Created by zhaozhaoxia on 3/5/17.
 */

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Optional;

//create 2 scenes, one for welcome page and the other for help page
public class MainPage extends Application{
    public static final double MIN_BUTTON_HEIGHT = 100;
    public static final double MIN_BUTTON_WIDTH = 100;
    private Stage stage;
    private Scene sceneWelcome;
    private Scene sceneHelp;


    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        BorderPane welcomeRoot = new BorderPane();
        welcomeRoot.setCenter(welcomeAddGridpane());
        BorderPane helpRoot = new BorderPane();
        helpRoot.setCenter(helpAddGridPane());
        sceneWelcome = new Scene(welcomeRoot,1200,800);
        sceneWelcome.getStylesheets().add(MainPage.class.getResource("styles/MainPage.css").toExternalForm());
        sceneHelp = new Scene(helpRoot,1200,800);
        sceneHelp.getStylesheets().add(MainPage.class.getResource("styles/Help.css").toExternalForm());
        stage.setTitle("GAME 2048");
        stage.setScene(sceneWelcome);
        stage.show();

    }

    //This method returns the stage of MainPage
    public Stage getStage() {
        BorderPane welcomeRoot = new BorderPane();
        welcomeRoot.setCenter(welcomeAddGridpane());
        BorderPane helpRoot = new BorderPane();
        helpRoot.setCenter(helpAddGridPane());
        sceneWelcome = new Scene(welcomeRoot,1200,800);
        sceneWelcome.getStylesheets().add(MainPage.class.getResource("styles/MainPage.css").toExternalForm());
        sceneHelp = new Scene(helpRoot,1200,800);
        sceneHelp.getStylesheets().add(MainPage.class.getResource("styles/Help.css").toExternalForm());
        stage = new Stage();
        stage.setTitle("GAME 2048");
        stage.setScene(sceneWelcome);
        stage.show();
        return stage;
    }

    //create a gridpane for the welcome page
    private Node welcomeAddGridpane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(welcomeAddButtons(),0,11);
        gridPane.add(welcomeAddText(),0,6);
        gridPane.add(welcomeAddHelp(),0,30);

        gridPane.setGridLinesVisible(false);
        return gridPane;
    }


	//add level buttons to gridpane
    private Node welcomeAddButtons(){
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(5);
        buttonPane.setVgap(5);
        buttonPane.setPadding(new Insets(0,10,0,10));

        buttonPane.add(createLevelButton("Level1"),0,12);
        buttonPane.add(createLevelButton("Level2"),1,12);
        buttonPane.add(createLevelButton("Level3"),2,12);
        buttonPane.setGridLinesVisible(false);
        return buttonPane;
    }

    //add Level buttons
    private Button createLevelButton(String level){
        Gamepage gamepage = new Gamepage();
        Button button = new Button(level);
        button.setMinHeight(MIN_BUTTON_HEIGHT);
        button.setMinWidth(MIN_BUTTON_WIDTH);
        button.setId(level);

        //if users choose a level, go to the gamepage with that level
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage newstage = gamepage.getStage();
                gamepage.setLevel(level);
                if (level == "Level1") {
                    gamepage.playLevel1();
                } else if (level == "Level2"){
                    gamepage.playLevel2();
                } else if (level == "Level3") {
                    gamepage.playLevel3();
                }
                stage.close();
            }
        });
        return button;
    }


	//show title of welcome page
    private Node welcomeAddText(){
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        Text welcometext = new Text("2048");
        welcometext.setId("welcome-text");
        flowPane.getChildren().add(welcometext);
        return flowPane;
    }


	//help button that sets the scene to the help page
    private Node welcomeAddHelp(){
        GridPane helpPane = new GridPane();
        helpPane.setAlignment(Pos.BOTTOM_RIGHT);
        Button help = new Button("Help");

        //if users click help, go to help page
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(sceneHelp);
            }
        });
        help.setId("helpButton");
        help.setMinHeight(40);
        help.setMinWidth(60);
        helpPane.add(help, 0, 0);
        return helpPane;
    }


    //Layout of the help page scene;

    public Node helpAddGridPane(){
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(2);
        grid.add(helpAddFlowPaneTop(),0,2);
        grid.add(helpAddFlowPaneLevel1(),0,9);
        grid.add(helpAddFlowPaneLevel2(), 0, 16);
        grid.add(helpAddHBoxGravityDemo(),0, 18);
        grid.add(helpAddFlowPaneLevel3(),0,26);
        grid.add(helpAddHBoxSubtractionDemo(),0,28);
        grid.setGridLinesVisible(false);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    //Layout of the help page scene;
    public Node helpAddFlowPaneTop(){
        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(500);
        flow.setHgap(24);
        flow.getChildren().addAll(helpAddStackPane(), helpAddHBox1(),helpAddHBox2());
        return flow;
    }

    //the return icon button, when pressed, the scene switches back to the welcome page
    public Node helpAddStackPane(){
        StackPane stack = new StackPane();
        Button button = new Button("⏎");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(sceneWelcome);
            }
        });
        stack.getChildren().addAll(button);
        return stack;
    }

    //Layout of the help page scene; this part explains the goal of the game
    public HBox helpAddHBox1(){
        HBox hbox = new HBox();
        hbox.setSpacing(2);
        Text text1 = new Text("Goal:");
        text1.setId("text");
        ImageView image1 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/2048.png")));
        image1.setFitHeight(55);
        image1.setFitWidth(55);
        hbox.getChildren().addAll(text1,image1);
        return hbox;
    }
    
    //Layout of the help page scene; this part explains Gameover with image
    public HBox helpAddHBox2(){
        HBox hbox = new HBox();
        hbox.setSpacing(2);
        Text text2 = new Text("Game Over:");
        text2.setId("text");
        ImageView image2 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/gameover.jpg")));
        image2.setFitHeight(55);
        image2.setFitWidth(55);
        hbox.getChildren().addAll(text2,image2);
        return hbox;
    }
    
    //level1 explanation paragraph
    public Node helpAddFlowPaneLevel1(){
        FlowPane flow = new FlowPane();
        Label label1 = new Label("Level 1: Press ");
        Label label2 = new Label(", all numbered tiles slide\n");
        Label label3 = new Label("in that direction. When tiles with the same number touch: ");
        Label label4 = new Label(" ⇒ ");
        Label label5 = new Label(" = ");
        Label label6 = new Label(", they merge into a tile of the numbers");
        Label label7 = new Label("combined.");
        ImageView image1 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/left.png" )));
        image1.setFitHeight(30);
        image1.setFitWidth(30);
        ImageView image2 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/up.png" )));
        image2.setFitHeight(30);
        image2.setFitWidth(30);
        ImageView image3 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/down.png" )));
        image3.setFitHeight(30);
        image3.setFitWidth(30);
        ImageView image4 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/right.png" )));
        image4.setFitHeight(30);
        image4.setFitWidth(30);
        ImageView image5 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/2.png")));
        image5.setFitHeight(30);
        image5.setFitWidth(30);
        ImageView image6 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/2.png")));
        image6.setFitHeight(30);
        image6.setFitWidth(30);
        ImageView image7 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/4.png")));
        image7.setFitHeight(30);
        image7.setFitWidth(30);
        flow.getChildren().addAll(label1,image1,image2,image3,image4,label2,label3,image5,label4, image6,label5,image7,label6,label7);
        return flow;
    }

	//level2 explanation paragraph
    public Node helpAddFlowPaneLevel2(){
        FlowPane flow = new FlowPane();
        Label label1 = new Label("Level 2 (Gravity): When the 512 tile ");
        Label label2 = new Label(" appears, the");
        Label label3 = new Label("grid rotates 90 degrees to the left and all tiles fall down.");
        ImageView image1 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/512.png")));
        image1.setFitHeight(30);
        image1.setFitWidth(30);
        flow.getChildren().addAll(label1,image1,label2,label3);
        return flow;
    }

	//level2 image demonstration paragraph
    public Node helpAddHBoxGravityDemo(){
        HBox hbox = new HBox();
        Label label = new Label(" ⇒ ");
        label.setId("labelarrow");
        ImageView image1 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/gravity1.png")));
        image1.setFitHeight(80);
        image1.setFitWidth(80);
        ImageView image2 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/gravity2.png")));
        image2.setFitHeight(80);
        image2.setFitWidth(80);
        hbox.getChildren().addAll(image1,label,image2);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

	//level3 explanation
    public Node helpAddFlowPaneLevel3(){
        FlowPane flow = new FlowPane();
        Label label = new Label("Level 3 (Subtraction): When gravity happens and tiles with\n" + "the same number touch, both tiles disappear.");
        flow.getChildren().add(label);
        return flow;
    }

	//level3 image demonstration paragraph
    public Node helpAddHBoxSubtractionDemo(){
        HBox hbox = new HBox();
        Label label = new Label(" ⇒ ");
        label.setId("labelarrow");
        ImageView image1 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/subtraction1.png")));
        image1.setFitHeight(80);
        image1.setFitWidth(80);
        ImageView image2 = new ImageView(new Image(MainPage.class.getResourceAsStream("/images/subtraction2.png")));
        image2.setFitHeight(80);
        image2.setFitWidth(80);
        hbox.getChildren().addAll(image1,label,image2);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }

}




