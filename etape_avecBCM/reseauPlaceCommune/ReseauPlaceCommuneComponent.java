package reseauPlaceCommune;

// Import des différentes classes nécessaires
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.URIGenerator;
import classes.PlaceCommune;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import interfaces.ReseauPlaceCommuneI;
import reseau.ReseauEndpoint;
import semaphore.SemaphoreClientPlugin;

// Le composant offre l'interface ReseauPlaceCommuneCI et nécessite l'interface ReseauCI
@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class })
public class ReseauPlaceCommuneComponent extends AbstractComponent implements ReseauPlaceCommuneI<String> {

    // Constructeur initialisant les différents paramètres et plugins du composant
    protected ReseauPlaceCommuneComponent(String uri,
                                          String semaphoreReflectionInboundPortURI,
                                          String semAvailabilityUri,
                                          String semUpdateUri,
                                          ArrayList<String> semJetonUriList,
                                          ReseauPlaceCommuneEndpoint endPointServer,
                                          ArrayList<ReseauEndpoint> endPointClients) throws Exception {
        super(2, 0); // Nombre de threads et de services

        // Initialisation des variables locales
        this.uri = uri;
        this.placesCommunes = new HashMap<>();
        endPointServer.initialiseServerSide(this); // Initialisation côté serveur du point d'entrée
        this.endPointClients = endPointClients;

        this.updatingJetons = new HashMap<String, String>();

        // Paramètres des sémaphores pour la synchronisation
        this.semAvailabilityUri = semAvailabilityUri;
        this.semUpdateUri = semUpdateUri;
        this.semJetonUriList = semJetonUriList;

        // URI pour la reflection des sémaphores
        this.semaphoreReflectionInboundPortURI = semaphoreReflectionInboundPortURI;

        // Création du plugin pour la gestion des sémaphores
        this.semaphorePlugin = new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_URI,
                                                         this.semaphoreReflectionInboundPortURI);

        // Indicateur de connexion
        this.isConnected = false;
    }

    // Variables d'état du composant
    private ArrayList<ReseauEndpoint> endPointClients;
    private Map<String, PlaceCommune> placesCommunes;
    private String uri;
    private String id;
    private SemaphoreClientPlugin semaphorePlugin;
    private Map<String, String> updatingJetons;
    private String semAvailabilityUri;
    private String semUpdateUri;
    private ArrayList<String> semJetonUriList;
    protected String semaphoreReflectionInboundPortURI;
    private boolean isConnected;

    // URI pour le plugin de sémaphore
    protected final static String SEMAPHORE_PLUGIN_URI = "semaphore-client-plugin";

    // Méthode start() appelée lors du démarrage du composant
    @Override
    public void start() {
        try {
            super.start(); // Démarre le composant
        } catch (ComponentStartException e) {
            e.printStackTrace();
        }

        // Initialisation côté client pour chaque endpoint
        for (ReseauEndpoint ep : endPointClients) {
            if (!ep.clientSideInitialised()) {
                try {
                    ep.initialiseClientSide(this); // Initialisation du côté client
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }
        }

        // Initialisation des places communes
        String pc1 = "pc1", pc2 = "pc2", pc3 = "pc3", pc4 = "pc4";
        PlaceCommune PC1 = new PlaceCommune(pc1);
        PlaceCommune PC2 = new PlaceCommune(pc2);
        PlaceCommune PC3 = new PlaceCommune(pc3);
        PlaceCommune PC4 = new PlaceCommune(pc4);

        PC4.addJeton(); // Ajout d'un jeton à la place pc4

        // Ajout des places communes à la map
        this.placesCommunes.put(pc1, PC1);
        this.placesCommunes.put(pc2, PC2);
        this.placesCommunes.put(pc3, PC3);
        this.placesCommunes.put(pc4, PC4);

        // Assignation des URIs de sémaphores pour la gestion des jetons
        this.updatingJetons.put(pc1, this.semJetonUriList.get(0));
        this.updatingJetons.put(pc2, this.semJetonUriList.get(1));
        this.updatingJetons.put(pc3, this.semJetonUriList.get(2));
        this.updatingJetons.put(pc4, this.semJetonUriList.get(3));

        // Génération de l'ID unique pour ce composant
        this.id = URIGenerator.generateURI();
    }

    // Méthode exécutée en boucle pour la gestion des transitions et des jetons
    @Override
    public void execute() throws Exception {
        synchronized (this) {
            this.installPlugin(semaphorePlugin); // Installation du plugin de sémaphore
            notifyAll(); // Notifie tous les threads en attente
        }

        // Mise à jour des transitions pour chaque place commune
        try {
            // Ajout des transitions aux places communes
            this.placesCommunes.get("pc1").addTransEntree("t1");
            this.placesCommunes.get("pc3").addTransSortie("t2");
            this.placesCommunes.get("pc4").addTransSortie("t3");
            this.placesCommunes.get("pc4").addTransEntree("t4");
            this.placesCommunes.get("pc2").addTransEntree("t5");
            this.placesCommunes.get("pc1").addTransSortie("t6");
            this.placesCommunes.get("pc2").addTransSortie("t6");
            this.placesCommunes.get("pc3").addTransEntree("t6");
            this.placesCommunes.get("pc4").addTransSortie("t8");
            this.placesCommunes.get("pc4").addTransEntree("t9");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Le composant est maintenant connecté
        this.isConnected = true;

        // Libération du sémaphore d'accessibilité
        this.semaphorePlugin.release(semAvailabilityUri);

        // Boucle infinie pour la mise à jour continue des transitions activables
        while (true) {
            for (PlaceCommune pc : this.placesCommunes.values()) {
                int nbJeton = pc.getNbJeton();
                // Mise à jour des transitions activables pour chaque client
                for (ReseauEndpoint ep : endPointClients) {
                    ep.getClientSideReference().updateTransitionsActivable(pc.getUri(), pc.getTransSorties(), nbJeton);
                }
            }

            // Acquisition du sémaphore d'accessibilité avant de répéter
            this.semaphorePlugin.acquire(semAvailabilityUri);
        }
    }

    // Méthode appelée lors de la finalisation du composant
    @Override
    public void finalise() throws Exception {
        System.out.println("Finalise de " + this.uri);
    }

    // Implémentation des méthodes d'accès aux jetons et transitions
    @Override
    public int getNbJeton(String uri) throws Exception {
        for (Map.Entry<String, PlaceCommune> entry : this.placesCommunes.entrySet()) {
            if (entry.getValue().getUri().equals(uri)) {
                return entry.getValue().getNbJeton();
            }
        }
        return 0;
    }

    @Override
    public String getUri() throws Exception {
        return uri;
    }

    @Override
    public void setNbJeton(String uri, int nbJeton) throws Exception {
        this.placesCommunes.forEach((key, value) -> {
            if (value.getUri().equals(uri)) {
                value.setNbJeton(nbJeton);
            }
        });
    }

    @Override
    public ArrayList<String> getTransEntrees(String uri) throws Exception {
        return this.placesCommunes.get(uri).getTransEntrees();
    }

    @Override
    public void addTransEntree(String uri, String entree) throws Exception {
        this.placesCommunes.get(uri).addTransEntree(entree);
    }

    @Override
    public void addTransSortie(String uri, String sortie) throws Exception {
        this.placesCommunes.get(uri).addTransSortie(sortie);
    }

    @Override
    public ArrayList<String> getTransSorties(String uri) throws Exception {
        return this.placesCommunes.get(uri).getTransSorties();
    }

    @Override
    public void addJeton(String uri) throws Exception {
        this.placesCommunes.get(uri).addJeton();
    }

    @Override
    public void retrieveJeton(String uri) throws Exception {
        this.placesCommunes.get(uri).retrieveJeton();
    }

    @Override
    public void acquireJeton(String placeCommune) throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.acquire(this.updatingJetons.get(placeCommune));
    }

    @Override
    public boolean tryAcquireJeton(String placeCommune) throws Exception {
        waitUntilPluginIsReady();
        return this.semaphorePlugin.tryAcquire(this.updatingJetons.get(placeCommune));
    }

    @Override
    public void releaseJeton(String placeCommune) throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.release(this.updatingJetons.get(placeCommune));
    }

    @Override
    public void acquireAvailability() throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.acquire(semAvailabilityUri);
    }

    @Override
    public boolean tryAcquireAvailability() throws Exception {
        waitUntilPluginIsReady();
        return this.semaphorePlugin.tryAcquire(semAvailabilityUri);
    }

    @Override
    public void releaseAvailability() throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.release(semAvailabilityUri);
    }

    @Override
    public void acquireUpdate() throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.acquire(semUpdateUri);
    }

    @Override
    public boolean tryAcquireUpdate() throws Exception {
        waitUntilPluginIsReady();
        return this.semaphorePlugin.tryAcquire(semUpdateUri);
    }

    @Override
    public void releaseUpdate() throws Exception {
        waitUntilPluginIsReady();
        this.semaphorePlugin.release(semUpdateUri);
    }

    @Override
    public boolean isConnected() throws Exception {
        return this.isConnected;
    }

    // Méthode privée pour attendre que le plugin de sémaphore soit prêt
    private synchronized void waitUntilPluginIsReady() throws InterruptedException {
        while (this.semaphorePlugin == null || !(this.semaphorePlugin.isInitialised())) {
            System.out.println("J'ATTEND LA SEMAPHORE " + Thread.currentThread().getName());
            wait();
        }
    }
}
