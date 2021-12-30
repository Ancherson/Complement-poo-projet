package projet;


import java.lang.Thread.State;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends VBox{
    private double padding = 0.005;
    private double Xoffset = 0;
    private double Yoffset = 0;
    private TextField client_re = new TextField("-0.8");
    private TextField client_im = new TextField("0.156");
    private TextField iterations = new TextField("500");
    private TextField radius = new TextField("2");
    private BorderPane pane;
    public Menu(BorderPane pane){
        this.pane =pane;
        HBox calc_parameters = new HBox();


        calc_parameters.getChildren().addAll(iterations,radius);

        HBox client_complexe = new HBox();

        client_complexe.getChildren().addAll(client_re,client_im);


        HBox client_label = new HBox();

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


        //this.getChildren().add(a);
        this.getChildren().add(b);
        this.getChildren().addAll(calc_parameters,client_complexe,client_label,zoom,deplacements,reset);

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

    private void Launch_calc(){
        WritableImage image = new WritableImage(800,500);
        PixelWriter write = image.getPixelWriter();
        Control c1 = new Control(write);

        Complexe com = new Complexe(Double.parseDouble(client_re.getText()),Double.parseDouble(client_im.getText()));
        Calcul calc1 = new Calcul(0, 800, 500, padding,Yoffset,Xoffset, Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1);
        Thread t1 = new Thread(calc1);
        Calcul calc2 = new Calcul( 1, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1);
        Thread t2 = new Thread(calc2);
        Calcul calc3 = new Calcul(2, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1);
        Thread t3 = new Thread(calc3);
        Calcul calc4 = new Calcul(3, 800, 500,padding,Yoffset,Xoffset,  Integer.parseInt(iterations.getText()), Integer.parseInt(radius.getText()), com, c1);
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
