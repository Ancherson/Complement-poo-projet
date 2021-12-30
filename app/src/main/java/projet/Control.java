package projet;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

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
