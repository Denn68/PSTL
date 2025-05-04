package classes;

import java.util.ArrayList;

import interfaces.PlaceI;

// Classe représentant une place dans un réseau de Petri
public class Place implements PlaceI<Transition> {
    // Nombre de jetons dans la place.
    private int nbJeton;

    // Identifiant unique (URI) de la place
    private String uri;

    // Liste des transitions qui mènent vers cette place (entrées)
    private ArrayList<Transition> transEntrees;

    // Liste des transitions qui partent de cette place (sorties)
    private ArrayList<Transition> transSorties;

    // Constructeur : initialise la place avec une URI, 0 jetons, et des listes vides de transitions
    public Place(String uri) {
        this.transEntrees = new ArrayList<Transition>();
        this.transSorties = new ArrayList<Transition>();
        this.nbJeton = 0;
        this.uri = uri;
    }

    // Renvoie le nombre de jetons actuellement dans la place
    @Override
    public int getNbJeton() {
        return nbJeton;
    }

    // Renvoie l'identifiant URI de la place
    @Override
    public String getUri() {
        return uri;
    }

    // Modifie le nombre de jetons dans la place
    @Override
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    // Renvoie la liste des transitions entrantes
    @Override
    public ArrayList<Transition> getTransEntrees() {
        return transEntrees;
    }

    // Ajoute une transition entrante à cette place
    @Override
    public void addTransEntree(Transition entree) {
        transEntrees.add(entree);
    }

    // Renvoie la liste des transitions sortantes
    @Override
    public ArrayList<Transition> getTransSorties() {
        return transSorties;
    }

    // Ajoute une transition sortante à cette place
    @Override
    public void addTransSortie(Transition sortie) {
        transSorties.add(sortie);
    }

    // Incrémente le nombre de jetons dans la place de 1
    public void addJeton() {
        //System.out.println("Add dans " + uri + " and now " + (this.nbJeton+1));
        this.nbJeton++;
    }

    // Décrémente le nombre de jetons dans la place de 1
    public void retrieveJeton() {
        //System.out.println("Retrieve dans " + uri + " and now " + (this.nbJeton-1));
        this.nbJeton--;
    }
}
