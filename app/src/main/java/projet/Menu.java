package projet;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox{

    public Menu(){

        this.setMinSize(300, 500);
        this.setAlignment(Pos.CENTER);
        Button a = new Button("salut");
        Button b = new Button("coucou");

        this.getChildren().add(a);
        this.getChildren().add(b);


    }
    
}
