package casUtilisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseau.ReseauComponent;
import reseau.ReseauEndpoint;
import reseau.ReseauPlugin;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

// Annotation pour déclarer que cette classe offre l'interface ReseauCI
@OfferedInterfaces(offered = { ReseauCI.class})
// Annotation pour déclarer que cette classe nécessite l'interface ReseauCI
@RequiredInterfaces(required = { ReseauCI.class})
public class ForkliftD<T, P>
extends ReseauComponent<T, P> // Extends ReseauComponent pour utiliser ses fonctionnalités
implements ReseauI<P> { // Implémente ReseauI pour respecter l'interface

    // URI du plugin associé à ForkliftD
    public static final String FORKLIFT_D_PLUGIN_URI = "forklift-d-plugin-uri";
    private String uri;

    // Constructeur de la classe ForkliftD
    @SuppressWarnings("unchecked")
    protected ForkliftD(String uri,
                        String reflectionInboundPortURI,
                        String semaphoreUpdatingAvailibility,
                        ArrayList<String> semJetonUriList,
                        ReseauEndpoint endPointServer,
                        ReseauPlaceCommuneEndpoint endPointClient) throws Exception {
        
        // Appel au constructeur parent pour initialiser la classe ReseauComponent
        super(uri,
              reflectionInboundPortURI,
              FORKLIFT_D_PLUGIN_URI,
              endPointServer,
              endPointClient);
        
        this.uri = uri;
        
        // HashMap pour gérer les jetons de mise à jour
        HashMap<String, String> updatingJetons = new HashMap<String, String>();

        // Définition des places communes avec leurs URI
        String pc6 = "pc6";
        String pc7 = "pc7";
        
        // Associe les URI de sémaphores aux places communes
        updatingJetons.put(pc6, semJetonUriList.get(5));
        updatingJetons.put(pc7, semJetonUriList.get(6));
        
        // Définition des places
        String p1 = "p1";
        String p2 = "p2";
        String p3 = "p3";
        String p4 = "p4";
        String p5 = "p5";
        
        // Création des objets Place
        Place P1 = new Place(p1);
        Place P2 = new Place(p2);
        Place P3 = new Place(p3);
        Place P4 = new Place(p4);
        Place P5 = new Place(p5);
        
        // Ajout d'un jeton à la place P1
        P1.addJeton();
        
        // Définition des transitions
        String tD1 = "tD1";
        String tD2 = "tD2";
        String tD3 = "tD3";
        String tD4 = "tD4";
        String tD5 = "tD5";
        
        // Fonctions associées aux transitions (comportements des transitions)
        Function<String, Void> gotoPosition = input -> {
            System.out.println("gotoPosition pb ");
            return null;
        };
        
        Function<String, Void> lift = input -> {
            System.out.println("lift");
            return null;
        };
        
        Function<String, Void> gotoPositionBis = input -> {
            System.out.println("gotoPosition pc");
            return null;
        };
        
        Function<String, Void> drop = input -> {
            System.out.println("drop");
            return null;
        };
        
        // Création des transitions avec les actions associées
        Transition TD1 = new Transition(tD1, (Function<String, Void>) gotoPosition);
        Transition TD2 = new Transition(tD2, (Function<String, Void>) lift);
        Transition TD3 = new Transition(tD3, (Function<String, Void>) gotoPositionBis);
        Transition TD4 = new Transition(tD4, (Function<String, Void>) drop);
        Transition TD5 = new Transition(tD5, (Function<String, Void>) gotoPosition);
        
        // Ajout des places d'entrée et de sortie pour chaque transition
        TD1.addPlaceEntree(P1, 1);
        TD1.addPlaceSortie(P2);
        TD2.addPlaceEntree(P2, 1);
        TD2.addPlaceSortie(P3);
        TD3.addPlaceEntree(P3, 1);
        TD3.addPlaceSortie(P4);
        TD4.addPlaceEntree(P4, 1);
        TD4.addPlaceSortie(P5);
        TD5.addPlaceEntree(P5, 1);
        TD5.addPlaceSortie(P2);

        // Ajout des places et transitions au plugin du réseau
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addPlace((P) P1);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addPlace((P) P2);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addPlace((P) P3);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addPlace((P) P4);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addPlace((P) P5);
        
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addTransition(TD1);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addTransition(TD2);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addTransition(TD3);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addTransition(TD4);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).addTransition(TD5);
        
        // Connexion entre forklift D et réseau PlaceCommune
        System.out.println("Connexion entre forklift D et réseau PlaceCommune...");
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tD2", pc6, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc6));
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tD2", pc7, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_D_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tD3", pc7, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
    }
}
