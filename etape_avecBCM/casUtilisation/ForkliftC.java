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

// Annotations indiquant que cette classe offre et nécessite l'interface ReseauCI
@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ForkliftC<T, P> extends ReseauComponent<T, P> implements ReseauI<P> {
    
    // URI du plugin associé à ce composant
    public static final String FORKLIFT_C_PLUGIN_URI = "forklift-c-plugin-uri";
    
    // URI pour identifier ce composant ForkliftC
    private String uri;

    // Constructeur de la classe ForkliftC
    @SuppressWarnings("unchecked")
    protected ForkliftC(String uri, String reflectionInboundPortURI, 
                        String semaphoreUpdatingAvailibility, ArrayList<String> semJetonUriList,
                        ReseauEndpoint endPointServer, ReseauPlaceCommuneEndpoint endPointClient) throws Exception {
        
        // Appel du constructeur de la classe parente ReseauComponent
        super(uri, reflectionInboundPortURI, FORKLIFT_C_PLUGIN_URI, endPointServer, endPointClient);
        
        // Initialisation de l'URI de ce composant
        this.uri = uri;
        
        // HashMap pour stocker les jetons de mise à jour des places du réseau
        HashMap<String, String> updatingJetons = new HashMap<String, String>();
        
        // Définition des places communes pour les transitions
        String pc6 = "pc6";
        String pc7 = "pc7";
        
        // Lien entre les places communes et les URI des jetons
        updatingJetons.put(pc6, semJetonUriList.get(5));
        updatingJetons.put(pc7, semJetonUriList.get(6));
        
        // Création des places pour le réseau
        String p1 = "p1";
        String p2 = "p2";
        String p3 = "p3";
        String p4 = "p4";
        String p5 = "p5";
        
        // Initialisation des objets Place pour chaque place dans le réseau
        Place P1 = new Place(p1);
        Place P2 = new Place(p2);
        Place P3 = new Place(p3);
        Place P4 = new Place(p4);
        Place P5 = new Place(p5);
        
        // Ajout d'un jeton à la place P1
        P1.addJeton();
        
        // Définition des transitions avec leurs identifiants
        String tC1 = "tC1";
        String tC2 = "tC2";
        String tC3 = "tC3";
        String tC4 = "tC4";
        String tC5 = "tC5";
        
        // Fonctions pour simuler les actions des transitions
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
        
        // Création des transitions avec leurs actions associées
        Transition TC1 = new Transition(tC1, (Function<String, Void>) gotoPosition);
        Transition TC2 = new Transition(tC2, (Function<String, Void>) lift);
        Transition TC3 = new Transition(tC3, (Function<String, Void>) gotoPositionBis);
        Transition TC4 = new Transition(tC4, (Function<String, Void>) drop);
        Transition TC5 = new Transition(tC5, (Function<String, Void>) gotoPosition);
        
        // Ajout des places d'entrée et de sortie pour chaque transition
        TC1.addPlaceEntree(P1, 1);
        TC1.addPlaceSortie(P2);
        TC2.addPlaceEntree(P2, 1);
        TC2.addPlaceSortie(P3);
        TC3.addPlaceEntree(P3, 1);
        TC3.addPlaceSortie(P4);
        TC4.addPlaceEntree(P4, 1);
        TC4.addPlaceSortie(P5);
        TC5.addPlaceEntree(P5, 1);
        TC5.addPlaceSortie(P2);
        
        // Ajout des places et transitions au plugin ForkliftC
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P1);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P2);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P3);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P4);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P5);
        
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC1);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC2);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC3);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC4);
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC5);
        
        // Connexion du ForkliftC au réseau PlaceCommune via des transitions spécifiques
        System.out.println("Connexion entre forklift C et réseau PlaceCommune...");
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tC2", pc6, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc6));
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tC2", pc7, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
        ((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tC3", pc7, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
    }
}
