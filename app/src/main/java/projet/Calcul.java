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

    private Boolean julia;

    /*Class that calculate the color of each pixel in a line then update this color using the controleur
    after that it calcultes the color of each pixel of the next line+4 (because there is always 4 Thread that do the calcul) until reaching the end */
    public Calcul( int firstline,int line_length, int nb_line,double padding, double Xoffset, double Yoffset, int iteration, int radius, Complexe com, Control cont,Boolean julia){
        this.firstline = firstline;
        this.line_length = line_length; // number of colons of the image (width in pixel)
        this.nb_line = nb_line; // number of line of the image (height in pixel)

        this.padding = padding; //coef of the zoom
        this.Xoffset = Xoffset; // offset for the deplacement 
        this.Yoffset = Yoffset; // offset for the deplacement

        this.iteration = iteration; // number of iteration
        this.radius = radius; // coef we try to reach

        this.com = com; // complex number for the julia set
        this.control = cont; // controleur that modify the image 
        this.julia = julia; // boulean to chose which type of calcul to do
    }




    @Override
    public void run(){
        Color [] c = new Color [line_length];
        for(int i=firstline; i<nb_line; i += 4){
                for(int j = 0;j<line_length;j++ ){
                    int k=0;       
                    if(julia){
                        Complexe z = new Complexe((j-400)*padding+Yoffset,(i-250)*padding+Xoffset);
                        while ( k < iteration && z.abs() <= radius){
                            z.multiply(z);
                            z.add(com);
                            k++;
                        }
                    }else{
                        Complexe c1 = new Complexe((j-400)*padding+Yoffset,(i-250)*padding+Xoffset);
                        Complexe z =new Complexe(0,0);
                        while ( k < iteration && z.abs() <= radius){
                            z.multiply(z);
                            z.add(c1);
                            k++;
                        }
                    }
                    c[j] = Color.hsb(((float)k/iteration)*360, 1, 0.5);
                    
                }
                /*draw the line in the color */
                synchronized (control){
                    control.update(i, c);
            }
        }
        /* wakes up the main javafx Thread to avoid a concurence of Thread ( javafx doesn't allow anonther Thread to modify an element attached to a visible node) */
        synchronized(control){
            control.notify();
        }
        
    }
}

