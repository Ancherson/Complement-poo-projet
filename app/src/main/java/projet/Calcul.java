package projet;

public class Calcul implements Runnable{
    private int line;
    private int padding;
    private int firstpixel;
    private int length;
    
    private int iteration;
    private int radius;
    private Complexe com;

    public Calcul(int line, int iteration, int padding, int firstpixel, Complexe com){
        this.line = line;
        this.padding = padding;
        this.iteration = iteration;
        this.firstpixel = firstpixel;
        this.com = com;
    }




    @Override
    public void run(){

    }
}
