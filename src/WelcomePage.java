/**
 * Created by zhonge on 3/1/17.
 */

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomePage extends Application{
    public static final double MIN_BUTTON_HEIGHT = 100;
    public static final double MIN_BUTTON_WIDTH = 100;



    @Override
    //This method initiates the primary stage, and call the methods that create the buttons
    //and the text. It also calls the css file to add styles to the page.
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();

        root.setCenter(addGridpane());

        Scene scene = new Scene(root,1200,800);
        scene.getStylesheets().add
                (WelcomePage.class.getResource("WelcomePage.css").toExternalForm());
        primaryStage.setTitle("GAME 2048");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node addGridpane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(addButtons(),0,11);
        gridPane.add(addText(),0,6);
        gridPane.add(addHelp(),0,30);

        gridPane.setGridLinesVisible(false);
        return gridPane;
    }

    private Node addButtons(){
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(5);
        buttonPane.setVgap(5);
        buttonPane.setPadding(new Insets(0,10,0,10));

        Button level1 = new Button("Level 1");
        level1.setMinHeight(MIN_BUTTON_HEIGHT);
        level1.setMinWidth(MIN_BUTTON_WIDTH);
        level1.setId("level1");
        Button level2 = new Button("Level 2");
        level2.setMinHeight(MIN_BUTTON_HEIGHT);
        level2.setMinWidth(MIN_BUTTON_WIDTH);
        level2.setId("level2");
        Button level3 = new Button("Level 3");
        level3.setMinHeight(MIN_BUTTON_HEIGHT);
        level3.setMinWidth(MIN_BUTTON_WIDTH);
        level3.setId("level3");
        buttonPane.add(level1,0,12);
        buttonPane.add(level2,1,12);
        buttonPane.add(level3,2,12);
        buttonPane.setGridLinesVisible(false);
        return buttonPane;

    }

    private Node addText(){
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        Text welcometext = new Text("2048");
        welcometext.setId("welcome-text");
        flowPane.getChildren().add(welcometext);
        return flowPane;

    }

    private Node addHelp(){
        GridPane helpPane = new GridPane();
        helpPane.setAlignment(Pos.BOTTOM_RIGHT);
        Button help = new Button("Help");
        help.setId("helpButton");
        help.setMinHeight(40);
        help.setMinWidth(60);
        helpPane.add(help, 0, 0);
        return helpPane;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
