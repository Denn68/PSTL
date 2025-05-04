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

// Annotation qui déclare les interfaces offertes et requises par ce composant.
@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauAComponent<T, P> extends ReseauComponent<T, P> implements ReseauI<P> {

    // URI du plugin spécifique pour ce composant.
    public static final String RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
    private String uri;

    // Constructeur de la classe, qui initialise le composant et installe le plugin réseau A
    @SuppressWarnings("unchecked")
    protected ReseauAComponent(String uri,
            String reflectionInboundPortURI,
            String semaphoreUpdatingAvailibility,
            ArrayList<String> semJetonUriList,
            ReseauEndpoint endPointServer,
            ReseauPlaceCommuneEndpoint endPointClient) throws Exception {

        // Appel au constructeur de la classe parente pour initialiser le composant
        super(uri, reflectionInboundPortURI, RESEAU_A_PLUGIN_URI, endPointServer, endPointClient);

        this.uri = uri;

        // Création d'une map pour lier les jetons aux places communes
        HashMap<String, String> updatingJetons = new HashMap<String, String>();

        // Définition des places communes
        String pc1 = "pc1";
        String pc2 = "pc2";
        String pc3 = "pc3";
        String pc4 = "pc4";

        // Ajout des URI des sémaphores aux places communes
        updatingJetons.put(pc1, semJetonUriList.get(0));
        updatingJetons.put(pc2, semJetonUriList.get(1));
        updatingJetons.put(pc3, semJetonUriList.get(2));
        updatingJetons.put(pc4, semJetonUriList.get(3));

        // Définition des places et transitions
        String p1 = "p1";
        String p2 = "p2";
        String p3 = "p3";
        String p4 = "p4";

        String t1 = "t1";
        String t2 = "t2";
        String t3 = "t3";
        String t4 = "t4";

        // Création des objets Place
        Place P1 = new Place(p1);
        Place P2 = new Place(p2);
        Place P3 = new Place(p3);
        Place P4 = new Place(p4);

        // Ajout d'un jeton à la place P1
        P1.addJeton();

        // Définition d'une fonction qui sera activée lors de l'activation des transitions
        Function<String, String> fonction = input -> {
            System.out.println("Fonction activable de la transition: " + input);
            return "Transition activée: " + input;
        };

        // Création des transitions avec la fonction associée
        Transition T1 = new Transition(t1, (Function<String, String>) fonction);
        Transition T2 = new Transition(t2, (Function<String, String>) fonction);
        Transition T3 = new Transition(t3, (Function<String, String>) fonction);
        Transition T4 = new Transition(t4, (Function<String, String>) fonction);

        // Ajout des places d'entrée et de sortie pour chaque transition
        T1.addPlaceEntree(P1, 1);
        T2.addPlaceSortie(P2);
        T3.addPlaceEntree(P2, 1);
        T3.addPlaceSortie(P3);
        T4.addPlaceEntree(P3, 1);
        T4.addPlaceSortie(P4);

        // Ajout des places et transitions au plugin du réseau A
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P1);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P2);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P3);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P4);

        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T1);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T2);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T3);
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T4);

        // Connexion entre le réseau A et le réseau PlaceCommune en utilisant les transitions et places communes
        System.out.println("Connexion entre réseau A et réseau PlaceCommune...");
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t1", pc1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t2", pc3, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t3", pc4, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
        ((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t4", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
    }
}
