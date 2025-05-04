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

@OfferedInterfaces(offered = { ReseauCI.class}) // Indique que cette classe offre l'interface ReseauCI
@RequiredInterfaces(required = { ReseauCI.class}) // Indique que cette classe nécessite l'interface ReseauCI
public class Conveyor<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P> {
	
    public static final String CONVOYEUR_PLUGIN_URI = "convoyeur-plugin-uri";  // URI du plugin du convoyeur
    private String uri;  // URI unique du convoyeur

    @SuppressWarnings("unchecked")
    protected Conveyor(String uri,
                       String reflectionInboundPortURI,
                       String semaphoreUpdatingAvailibility,
                       ArrayList<String> semJetonUriList,
                       ReseauEndpoint endPointServer,
                       ReseauPlaceCommuneEndpoint endPointClient) throws Exception {
        
        // Appel au constructeur de la classe parente (ReseauComponent)
        super(uri,
              reflectionInboundPortURI,
              CONVOYEUR_PLUGIN_URI,
              endPointServer,
              endPointClient);
        
        this.uri = uri;  // Initialisation de l'URI du convoyeur
        
        // Création d'un HashMap pour associer des places communes aux sémaphores de jetons
        HashMap<String, String> updatingJetons = new HashMap<String, String>();
        
        String pc5 = "pc5";
        String pc6 = "pc6";
        
        // Association des places communes aux sémaphores
        updatingJetons.put(pc5, semJetonUriList.get(4));
        updatingJetons.put(pc6, semJetonUriList.get(5));
        
        // Initialisation des places P1 et P2 du convoyeur
        String p1 = "p1";
        String p2 = "p2";
        
        Place P1 = new Place(p1);
        Place P2 = new Place(p2);
        
        P1.addJeton();  // Ajout d'un jeton à la place P1
        
        // Création de la transition du convoyeur
        String tCONV = "tCONV";
        
        // Définition de l'action associée à la transition (ici, un simple print)
        Function<String, Void> move = input -> {
            System.out.println("move");  // Action à réaliser lors du mouvement
            return null;
        };
        
        // Création de la transition TCONV associée à la fonction move
        Transition TCONV = new Transition(tCONV, (Function<String, Void>) move);
        
        // Définition des entrées et sorties pour la transition
        TCONV.addPlaceEntree(P1, 1);  // La transition prend 1 jeton de P1
        TCONV.addPlaceSortie(P2);     // La transition déplace le jeton vers P2

        // Ajout des places et de la transition au plugin du convoyeur
        ((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addPlace((P) P1);
        ((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addPlace((P) P2);
        ((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addTransition(TCONV);
        
        // Connexion du convoyeur au réseau PlaceCommune
        System.out.println("Connexion entre convoyeur et réseau PlaceCommune...");
        
        // Association de la transition aux places communes avec les sémaphores
        ((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tCONV", pc5, 2, semaphoreUpdatingAvailibility, updatingJetons.get(pc5));
        ((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tCONV", pc6, semaphoreUpdatingAvailibility, updatingJetons.get(pc6));
    }
}
