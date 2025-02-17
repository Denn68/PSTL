package classes;

import interfaces.IJeton;

public class Jeton implements IJeton {
    private int numid;
    private Place position;

    public Jeton(int num, Place position){
        this.numid = num;
        this.position = position;
    }
    
    @Override
    public int getNumid(){
        return this.numid;
    }
    
    @Override
    public Place getPosition(){
        return this.position;
    }
}
