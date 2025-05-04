package classes_sansBCM;

import java.util.ArrayList;
import interfaces_sansBCM.IPlace;

public class Place implements IPlace {
    
    // Nombre de jetons présents dans la place (à remplacer éventuellement par ArrayList<Jeton>)
    private int nbJeton;

    // Identifiant unique (URI) de la place
    private String uri;

    // Liste des transitions qui aboutissent à cette place (entrées)
    @SuppressWarnings("rawtypes")
	private ArrayList<Transition> transEntrees;

    // Liste des transitions qui partent de cette place (sorties)
    @SuppressWarnings("rawtypes")
	private ArrayList<Transition> transSorties;

    // Constructeur : initialise la place avec une URI et aucune transition
    @SuppressWarnings("rawtypes")
	public Place(String uri) {
    	this.transEntrees = new ArrayList<Transition>();
    	this.transSorties = new ArrayList<Transition>();
    	this.nbJeton = 0;
    	this.uri = uri;
    }
    
    // Retourne le nombre actuel de jetons dans la place
    @Override
    public int getNbJeton() {
        return nbJeton;
    }
    
    // Retourne l'URI de la place
    @Override
    public String getUri() {
        return uri;
    }

    // Définit un nouveau nombre de jetons dans la place
    @Override
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    // Retourne la liste des transitions entrantes
    @SuppressWarnings("rawtypes")
	@Override
    public ArrayList<Transition> getTransEntrees() {
        return transEntrees;
    }
    
    // Ajoute une transition entrante à la liste
    @SuppressWarnings("rawtypes")
	@Override
    public void addTransEntree(Transition entree) {
        transEntrees.add(entree);
    }

    // Retourne la liste des transitions sortantes
    @SuppressWarnings("rawtypes")
	@Override
    public ArrayList<Transition> getTransSorties() {
        return transSorties;
    }
    
    // Ajoute une transition sortante à la liste
    @SuppressWarnings("rawtypes")
	@Override
    public void addTransSortie(Transition sortie) {
        transSorties.add(sortie);
    }
    
    // Incrémente le nombre de jetons dans la place (affiche un message de debug)
    public void addJeton() {
    	System.out.printf("Add dans %s\n", uri);
    	this.nbJeton ++;
    }
    
    // Décrémente le nombre de jetons dans la place (affiche un message de debug)
    public void retrieveJeton() {
    	System.out.printf("Retrieve dans %s\n", uri);
    	this.nbJeton--;
    }
}
