package projet;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;



/**
 * This class is the main class of the GUI
 * <br>It contains all the different pages and multiple function of the GUI
 */
public class Graphic_Interface extends Application {
    private int WIDTH = 1150;
    private int HEIGHT = 539;
    public Stage primaryStage;


    private Window window;


    /**
     * Function equivalent to the constructor. It launches the Javafx Thread
     * @param primaryStage The stage that will contain every elements of the GUI
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        window = (Window) primaryStage;
        BorderPane root = new BorderPane();
        Scene first = new Scene(root);
        primaryStage.setScene(first);


    
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(100);

        window.setHeight(HEIGHT);
        window.setWidth(WIDTH);


        

        WritableImage image = new WritableImage(800,500);
        ImageView imageView = new ImageView();
        imageView.setImage(image);



        root.setLeft(imageView);
        
        PixelWriter write = image.getPixelWriter();

        Control control = new Control(write);
        Menu menu = new Menu(root);

        root.setRight(menu);


        primaryStage.show();

       

    }
}

