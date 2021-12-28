package projet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.Window;
import java.awt.image.BufferedImage;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;



/**
 * This class is the main class of the GUI
 * <br>It contains all the different pages and multiple function of the GUI
 */
public class Graphic_Interface extends Application {
    private int WIDTH = 1200;
    private int HEIGHT = 800;
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

    
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(100);

        window.setHeight(HEIGHT);
        window.setWidth(WIDTH);


        
        var img=new BufferedImage(800, 500, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i<800;i++){
            for(int y = 0; y<500;y++){
                int r = ((y*355)/500); int g = ((i*355/800)); int b = 208;
                int col = (r << 16) | (g << 8) | b;
                img.setRGB(i,y,col);
            }
        }

        WritableImage image = SwingFXUtils.toFXImage(img, null);
        ImageView imageView = new ImageView();
        imageView.setImage(image);



        BorderPane root = new BorderPane();
        root.setLeft(imageView);

        Menu menu = new Menu();

        root.setRight(menu);

        Scene first = new Scene(root);

        primaryStage.setScene(first);
        primaryStage.show();

    }
}

