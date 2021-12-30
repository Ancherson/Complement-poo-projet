package projet;

import javafx.scene.paint.Color;

public class Calcul implements Runnable{
    private double Xoffset;
    private double Yoffset;
    private double padding;
    private int firstline;
    private int line_length;
    private int nb_line;

    
    private int iteration;
    private int radius;

    private Complexe com;

    private Control control;


    public Calcul( int firstline,int line_length, int nb_line,double padding, double Xoffset, double Yoffset, int iteration, int radius, Complexe com, Control cont){
        this.firstline = firstline;
        this.line_length = line_length;
        this.nb_line = nb_line;

        this.padding = padding; //coef du zoom
        this.Xoffset = Xoffset; // en pixel brute
        this.Yoffset = Yoffset; // en pixel brute

        this.iteration = iteration;
        this.radius = radius;

        this.com = com;
        this.control = cont;
    }




    @Override
    public void run(){
        Color [] c = new Color [line_length];
        for(int i=firstline; i<nb_line; i += 4){
                for(int j = 0;j<line_length;j++ ){
                    Complexe z = new Complexe((j-400)*padding+Yoffset,(i-250)*padding+Xoffset);
                    int k=0;       
                    while ( k < iteration && z.abs() <= radius){
                        z.multiply(z);
                        z.add(com);
                        k++;
                    }
                    c[j] = Color.hsb(((float)k/iteration)*360, 1, 0.5);
                    
                }
                synchronized (control){
                    control.update(i, c);
            }
        }
        synchronized(control){
            control.notify();
        }
        
    }
}

