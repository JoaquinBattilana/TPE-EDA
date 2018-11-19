package TPE.Model;

public class Point {
    int x;
    int y;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    @Override
    public int hashCode(){
        return 3*x+5*y;
    }
    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;
        if(!(o instanceof Point))
            return false;
        Point aux = (Point) o;
        return (aux.x==this.x && aux.y==this.y);
    }
    @Override
    public String toString(){
        return "" + x + "," + y;
    }
}
