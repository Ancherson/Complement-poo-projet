package projet;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;



/*The VBox that will contains all the buttons and the textfield to change the parameter and edit the image
It also contains the method to launch the actualization threads
*/

public class Menu extends VBox{
    private double padding = 0.005;
    private double Xoffset = 0;
    private double Yoffset = 0;
    private TextField client_re = new TextField("-0.8");
    private TextField client_im = new TextField("0.156");
    private TextField iterations = new TextField("500");
    private TextField radius = new TextField("2");
    private CheckBox Julia_set = new CheckBox("Julia set");

    private BorderPane pane;
    private WritableImage image = new WritableImage(800,500);

    public Menu(BorderPane pane){
        this.pane =pane;
        HBox calc_parameters = new HBox();


        calc_parameters.getChildren().addAll(iterations,radius);

        HBox client_complexe = new HBox();

        client_complexe.getChildren().addAll(client_re,client_im);


        HBox client_label = new HBox();
        Label ite_rad = new Label("number of iterations              the radius     ");

        Label cont = new Label("---- Controls ----");
        Label cont2 = new Label("------------------");
        cont.setFont(new Font(20));
        cont2.setFont(new Font(20));

        Julia_set.setSelected(true);


        Label client_complexe_label = new Label("Enter the real part and the imaginary part");

        client_label.getChildren().add(client_complexe_label);
        client_label.setAlignment(Pos.CENTER);
        this.setMinSize(300, 500);
        this.setAlignment(Pos.CENTER);
        Button b = new Button("visualize");

        Button zoom_in = new Button("+");
        Button zoom_out = new Button("-");
        HBox zoom = new HBox(zoom_out,zoom_in);
        zoom.setAlignment(Pos.CENTER);

        Button left = new Button("left");
        Button right = new Button("right");
        Button up = new Button("up");
        Button down = new Button("down");
        HBox deplacement = new HBox(left,down,right);
        deplacement.setAlignment(Pos.CENTER);
        VBox deplacements = new VBox(up,deplacement);
        deplacements.setAlignment(Pos.CENTER);

        Button reset = new Button("reset");

        Button save = new Button("save");
        HBox command = new HBox(b,save);
        command.setAlignment(Pos.CENTER);
        TextField name = new TextField("Image_name");

        this.getChildren().addAll(ite_rad,calc_parameters,cont,zoom,deplacements,cont2,reset,name,command,Julia_set,client_label,client_complexe);
        this.setSpacing(10);

        Julia_set.setOnAction(event -> {
            if(!Julia_set.isSelected()){
                this.getChildren().remove(client_label);
                this.getChildren().remove(client_complexe);
            }else{
                this.getChildren().add(client_label);
                this.getChildren().add(client_complexe);
            }
        });

        save.setOnAction(event -> {
            this.save(name.getText());
        });

        reset.setOnAction(event -> {
            padding = 0.005;
            Xoffset = 0;
            Yoffset = 0;
            Launch_calc();
        });
        left.setOnAction(event -> {
            Xoffset = Xoffset - 10*padding;
            Launch_calc();
        });
        right.setOnAction(event -> {
            Xoffset = Xoffset + 10*padding;
            Launch_calc();
        });

        up.setOnAction(event -> {
            Yoffset = Yoffset - 10*padding;
            Launch_calc();
        });
        down.setOnAction(event -> {
            Yoffset = Yoffset + 10*padding;
            Launch_calc();
        });
        zoom_in.setOnAction(event -> {
            padding = padding*0.5;
            Launch_calc();
        });

        zoom_out.setOnAction(event -> {
            padding = padding*2;
            Launch_calc();
        });

        b.setOnAction(event -> {
            Launch_calc();
        });
    }
/*Function to save the image */
    private void save(String name){
        File file = new File("../"+name+".png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(this.image, null);
        try {
          ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
    }
/*Function to start Thread that will calculate the new image */
    private void Launch_calc(){
        this.image = new WritableImage(800,500);
        PixelWriter write = image.getPixelWriter();
        Control c1 = new Control(write);

        Complexe com = new Complexe(Double.parseDouble(client_re.getText()),Double.parseDouble(client_im.getText()));
        Boolean b = Julia_set.isSelected();
        Calcul calc1 = new Calcul(0, 800, 500, padding,Yoffset,Xoffset, Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1,b);
        Thread t1 = new Thread(calc1);
        Calcul calc2 = new Calcul( 1, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1,b);
        Thread t2 = new Thread(calc2);
        Calcul calc3 = new Calcul(2, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1,b);
        Thread t3 = new Thread(calc3);
        Calcul calc4 = new Calcul(3, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1,b);
        Thread t4 = new Thread(calc4);
        t1.start();
        t2.start();            
        t3.start();
        t4.start();
        synchronized(c1){
            while(t1.getState() != State.TERMINATED || t2.getState() != State.TERMINATED || t3.getState() != State.TERMINATED || t4.getState() != State.TERMINATED){
                try {
                    c1.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        pane.setLeft(imageView);
    }
    
}
