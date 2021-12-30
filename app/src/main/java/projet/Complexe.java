package projet;
public class Complexe{
    private double re;
    private double im;

    public Complexe (double re, double im){
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm(){
        return im;
    }

    public void add(Complexe com){
        this.re += com.re;
        this.im += com.im;
    }

    public void multiply(Complexe com){
        double re = this.re*com.getRe() - this.im * com.getIm();
        this.im = this.re*com.getIm() + this.im * com.getRe();
        this.re = re;
    }

    public double abs(){
        return (Math.sqrt(this.re*this.re+this.im*this.im));
    }

}