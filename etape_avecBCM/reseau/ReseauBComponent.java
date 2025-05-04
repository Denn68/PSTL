package reseau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

// Déclaration des interfaces proposées et requises pour le composant
@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauBComponent<T, P> extends ReseauComponent<T, P> implements ReseauI<P> {

    // URI du plugin associé à ce composant
    public static final String RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri";
    private String uri;

    // Constructeur du composant, qui initialise les paramètres et les objets nécessaires
    @SuppressWarnings("unchecked")
    protected ReseauBComponent(String uri,
                               String reflectionInboundPortURI,
                               String semaphoreUpdatingAvailibility,
                               ArrayList<String> semJetonUriList,
                               ReseauEndpoint endPointServer,
                               ReseauPlaceCommuneEndpoint endPointClient) throws Exception {
        
        // Appel du constructeur de la classe parente (ReseauComponent)
        super(uri,
              reflectionInboundPortURI,
              RESEAU_B_PLUGIN_URI,
              endPointServer,
              endPointClient);
        
        this.uri = uri;  // Assignation de l'URI

        // HashMap pour associer chaque place avec un jeton de sémaphore
        HashMap<String, String> updatingJetons = new HashMap<String, String>();
        
        // Définition des places communes
        String pc1 = "pc1";
        String pc2 = "pc2";
        String pc3 = "pc3";
        String pc4 = "pc4";
        
        // Ajout des URI des sémaphores dans la HashMap
        updatingJetons.put(pc1, semJetonUriList.get(0));
        updatingJetons.put(pc2, semJetonUriList.get(1));
        updatingJetons.put(pc3, semJetonUriList.get(2));
        updatingJetons.put(pc4, semJetonUriList.get(3));
        
        // Définition des places (P5 à P9)
        String p5 = "p5";
        String p6 = "p6";
        String p7 = "p7";
        String p8 = "p8";
        String p9 = "p9";
        
        // Définition des transitions (T5 à T9)
        String t5 = "t5";
        String t6 = "t6";
        String t7 = "t7";
        String t8 = "t8";
        String t9 = "t9";
        
        // Création des objets Place
        Place P5 = new Place(p5);
        Place P6 = new Place(p6);
        Place P7 = new Place(p7);
        Place P8 = new Place(p8);
        Place P9 = new Place(p9);
        
        // Initialisation d'une place avec un jeton
        P5.addJeton();
        
        // Définition d'une fonction qui sera activée lors de l'exécution de la transition
        Function<String, String> fonction = input -> {
            System.out.println("Fonction activable de la transition: " + input);
            return "Transition activée: " + input;
        };
        
        // Création des objets Transition
        Transition T5 = new Transition(t5, (Function<String, String>) fonction);
        Transition T6 = new Transition(t6, (Function<String, String>) fonction);
        Transition T7 = new Transition(t7, (Function<String, String>) fonction);
        Transition T8 = new Transition(t8, (Function<String, String>) fonction);
        Transition T9 = new Transition(t9, (Function<String, String>) fonction);
        
        // Définition des entrées et sorties des transitions avec les places correspondantes
        T5.addPlaceEntree(P5, 1);  // Transition T5 entre en P5
        T6.addPlaceSortie(P6);  // Transition T6 sort de P6
        T7.addPlaceEntree(P6, 1);  // Transition T7 entre en P6
        T7.addPlaceSortie(P7);  // Transition T7 sort de P7
        T8.addPlaceEntree(P7, 1);  // Transition T8 entre en P7
        T8.addPlaceSortie(P8);  // Transition T8 sort de P8
        T9.addPlaceEntree(P8, 1);  // Transition T9 entre en P8
        T9.addPlaceSortie(P9);  // Transition T9 sort de P9

        // Ajout des places et transitions au plugin du réseau
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P5);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P6);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P7);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P8);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P9);

        // Ajout des transitions au plugin du réseau
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T5);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T6);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T7);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T8);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T9);

        // Connexion entre le réseau B et le réseau PlaceCommune avec les transitions et les jetons
        System.out.println("Connexion entre réseau B et réseau PlaceCommune...");
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t5", pc2, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t6", pc1, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t6", pc2, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t6", pc3, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t8", pc4, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t9", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
    }
}
