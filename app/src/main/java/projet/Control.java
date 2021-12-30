package projet;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

/*A control class that have the ability to update the image using it's pixelwriter
This class was created to separate the view and the model*/
public class Control {

    private PixelWriter writer;

    public Control(PixelWriter w){
        this.writer = w;
    }

    public void update(int line, Color [] col){
            int i = 0;
            for (Color c : col) {
                writer.setColor(i, line, c);
                i++;
            }
    }
    public void update_pixel(int line,int y,Color c){
        writer.setColor(y, line, c);
        
    }
}
