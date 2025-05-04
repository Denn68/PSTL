package test;

import java.util.ArrayList;
import java.util.Arrays;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import reseau.ReseauAComponent;
import reseau.ReseauBComponent;
import reseauPlaceCommune.ReseauPlaceCommuneComponent;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreComponent;
import reseau.ReseauEndpoint;

public class CVM extends AbstractCVM {
    // Déclaration des URI pour les différents composants et sémaphores utilisés dans l'application
    public final static String RESEAU_COMPONENT_A_RIBP_URI = "reseau-a-ibp-uri";  // URI pour le composant Reseau A
    public final static String RESEAU_COMPONENT_B_RIBP_URI = "reseau-b-ibp-uri";  // URI pour le composant Reseau B
    public final static String PLACE_COMMUNE_COMPONENT_RIBP_URI = "place-commune-ibp-uri";  // URI pour le composant place commune
    
    public static final String RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri"; // URI pour le plugin Reseau A
    public static final String RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri"; // URI pour le plugin Reseau B
    
    // Définition des URI pour les sémaphores
    protected final static String SEMAPHORE_AVAILABILITY_URI = "semaphore-availability";  // URI pour la disponibilité du sémaphore
    protected final static String SEMAPHORE_JETON_URI = "semaphore-jeton";  // URI pour le sémaphore jeton
    protected final static String SEMAPHORE_UPDATE_URI = "semaphore-update";  // URI pour la mise à jour du sémaphore
    
    // URI pour l'inbound port du sémaphore
    protected final static String SEMC_URI = "semaphore-inboundPort";  // URI du port entrant du sémaphore

    public CVM() throws Exception {
        super();  // Appel au constructeur de la classe parente AbstractCVM
    }

    @Override
    public void deploy() throws Exception {
        // Création des endpoints pour la communication entre composants
        ReseauPlaceCommuneEndpoint pc_ep = new ReseauPlaceCommuneEndpoint();
        ReseauEndpoint r_epB = new ReseauEndpoint(RESEAU_B_PLUGIN_URI);
        ReseauEndpoint r_epA = new ReseauEndpoint(RESEAU_A_PLUGIN_URI);
        
        // Liste pour les URI des sémaphores de type "jeton"
        ArrayList<String> semJetonUriList = new ArrayList<>();
        
        // Ajout de plusieurs URI pour les sémaphores de jeton (1 à 4)
        for (int i = 1; i < 5; i++) {
            String sem = SEMAPHORE_JETON_URI + "-" + i;
            semJetonUriList.add(sem);
        }
        
        // Création du composant SemaphoreComponent
        AbstractComponent.createComponent(SemaphoreComponent.class.getCanonicalName(),
            new Object[] {
                SEMC_URI,  // URI imposée pour l'inbound port
                SEMAPHORE_AVAILABILITY_URI,  // URI pour la disponibilité
                SEMAPHORE_UPDATE_URI,  // URI pour la mise à jour
                semJetonUriList  // Liste des URI de sémaphores
            });

        // Création du composant ReseauPlaceCommuneComponent
        AbstractComponent.createComponent(ReseauPlaceCommuneComponent.class.getCanonicalName(),
            new Object[] {
                "RPC",  // Nom du composant
                SEMC_URI,  // URI du sémaphore
                SEMAPHORE_AVAILABILITY_URI,  // URI pour la disponibilité
                SEMAPHORE_UPDATE_URI,  // URI pour la mise à jour
                semJetonUriList,  // Liste des URI de sémaphores
                ((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable()),  // Endpoint partagé
                new ArrayList<>(Arrays.asList(
                    ((ReseauEndpoint) r_epB.copyWithSharable()),
                    ((ReseauEndpoint) r_epA.copyWithSharable())
                ))  // Liste des endpoints partagés
            });

        // Création du composant ReseauBComponent
        AbstractComponent.createComponent(ReseauBComponent.class.getCanonicalName(),
            new Object[] {
                "R_B",  // Nom du composant
                RESEAU_COMPONENT_B_RIBP_URI,  // URI du composant Reseau B
                SEMAPHORE_AVAILABILITY_URI,  // URI pour la disponibilité
                semJetonUriList,  // Liste des URI de sémaphores
                ((ReseauEndpoint) r_epB.copyWithSharable()),  // Endpoint partagé
                ((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())  // Endpoint partagé
            });

        // Création du composant ReseauAComponent
        AbstractComponent.createComponent(ReseauAComponent.class.getCanonicalName(),
            new Object[] {
                "R_A",  // Nom du composant
                RESEAU_COMPONENT_A_RIBP_URI,  // URI du composant Reseau A
                SEMAPHORE_AVAILABILITY_URI,  // URI pour la disponibilité
                semJetonUriList,  // Liste des URI de sémaphores
                ((ReseauEndpoint) r_epA.copyWithSharable()),  // Endpoint partagé
                ((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())  // Endpoint partagé
            });

        super.deploy();  // Déploiement des composants dans le système
    }

    public static void main(String[] args) {
        try {
            // Initialisation et lancement du cycle de vie standard du CVM
            CVM c = new CVM();
            c.startStandardLifeCycle(800L);  // Démarrage du cycle de vie
            Thread.sleep(1000L);  // Attente de 1 seconde avant de quitter
            System.exit(0);  // Fin du programme
        } catch (Exception e) {
            e.printStackTrace();  // Gestion des exceptions
        }
    }
}
