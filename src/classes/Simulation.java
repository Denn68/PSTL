package classes;
import java.util.ArrayList;

import interfaces.ISimulation;

// compile with : javac --release 17 *.java
//execute with : java Simulation

public class Simulation implements ISimulation {
	
	public Simulation() {}
	
	/*@Override
	public Plateau createNetwork(ArrayList<Place> places, ArrayList<Transition> transitions, ArrayList<Jeton> jetons) {
		return new Plateau(places,transitions,jetons);
	}
	
    public static void main(String[] args){

        System.out.println("in the main");
        ArrayList<Place> places = new ArrayList<Place>();
        ArrayList<Transition> transitions = new ArrayList<Transition>();
        int i = 0, j=0;

        while(i<4){
            Place p = new Place(10, 0, i);
            if(i == 1) p.setNbJeton(1);
            places.add(p);
            i++;
        }
        while(j<3){
            Transition t = new Transition(1, j);
            transitions.add(t);
            j++;
        }*/

        /* MatriceEntree matEntree = new MatriceEntree();
        MatriceSortie matSortie = new MatriceSortie();*/

        //next part will be handle throught the terminal after
        /*int[][] matE = new int[places.size()][transitions.size()];
        int[][] matS = new int[places.size()][transitions.size()];
        for(int a=0; a<places.size(); a++){
            for(int b=0; b<transitions.size(); b++ ){
                if(a == 0 && b==0){
                    matE[a][b] = 1;
                    matS[a][b] = 0;
                }
                else if(a==0 && b == 2){
                    matE[a][b] = 0;
                    matS[a][b] = 1;
                }
                else if(a==1 && b == 1){
                    matE[a][b] = 1;
                    matS[a][b] = 0;
                }
                else if(a==1 && b == 0){
                    matE[a][b] = 0;
                    matS[a][b] = 1;
                }
                else if(a==2 && b == 1){
                    matE[a][b] = 0;
                    matS[a][b] = 1;
                }
                else if(a==3 && b == 2){
                    matE[a][b] = 1;
                    matS[a][b] = 0;
                }
                else if(a==3 && b == 1){
                    matE[a][b] = 0;
                    matS[a][b] = 1;
                }
                else{
                    matE[a][b] = 0;
                    matS[a][b] = 0;
                }
            }
        }

        Plateau petriNetwork = new Plateau(places, transitions, null, matE, matS);

        petriNetwork.showMatSortie();
        petriNetwork.showPlateau();

        int tour = 0;
        while(tour < 5){
            
            petriNetwork.update();
            petriNetwork.showPlateau();
            tour++;
        }
    }*/
}
