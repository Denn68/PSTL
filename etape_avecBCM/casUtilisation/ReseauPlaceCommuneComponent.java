package casUtilisation;

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
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreClientPlugin;

// Le composant ReseauPlaceCommuneComponent est déclaré ici, offrant l'interface ReseauPlaceCommuneCI
// et nécessitant l'interface ReseauCI pour interagir avec d'autres composants
@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<String>{

	// Constructeur qui initialise le composant en définissant les paramètres nécessaires
	protected ReseauPlaceCommuneComponent(String uri,
			String semaphoreReflectionInboundPortURI,
			String semAvailabilityUri,
			String semUpdateUri,
			ArrayList<String> semJetonUriList,
			ReseauPlaceCommuneEndpoint endPointServer,
			ArrayList<ReseauEndpoint> endPointClients) throws Exception
	{
		// Appel au constructeur de la classe parent AbstractComponent avec des paramètres spécifiques
		super(2, 0);
		
		// Initialisation des variables d'instance avec les paramètres passés
		this.uri = uri;
		this.placesCommunes = new HashMap<>();
		endPointServer.initialiseServerSide(this);
		this.endPointClients = endPointClients;
		
		// Création d'un HashMap pour suivre les jetons mis à jour
		this.updatingJetons = new HashMap<String, String>();
		
		// Initialisation des URIs des sémaphores pour la gestion de la synchronisation
		this.semAvailabilityUri = semAvailabilityUri;
		this.semUpdateUri = semUpdateUri;
		this.semJetonUriList = semJetonUriList;
		
		// Initialisation du port entrant pour la gestion des sémaphores
		this.semaphoreReflectionInboundPortURI =
				semaphoreReflectionInboundPortURI;
		
		// Création du plugin de gestion des sémaphores
		this.semaphorePlugin =
				new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_URI,
										  this.semaphoreReflectionInboundPortURI);
		
		// Initialisation de la variable de connexion
		this.isConnected = false;
	}
	
	// Déclaration des variables nécessaires à la gestion des places communes et de la synchronisation
	private ArrayList<ReseauEndpoint> endPointClients;
	private Map<String, PlaceCommune> placesCommunes;
    private String uri;
    private String id;
    private SemaphoreClientPlugin semaphorePlugin;
    private Map<String, String> updatingJetons;
    
    private String semAvailabilityUri;
    private String semUpdateUri;
    private ArrayList<String> semJetonUriList;
    
    protected String	semaphoreReflectionInboundPortURI;
	private boolean isConnected;
    protected final static String	SEMAPHORE_PLUGIN_URI =
			"semaphore-client-plugin";
	
	// Méthode d'initialisation du composant
	@Override
	public void start() {
		try {
			super.start();
		} catch (ComponentStartException e) {
			e.printStackTrace();
		}
		
		// Initialisation des points de terminaison côté client
		for (ReseauEndpoint ep : endPointClients) {
			if(!ep.clientSideInitialised()) {
				try {
					ep.initialiseClientSide(this);
				} catch (ConnectionException e) {
					e.printStackTrace();
				}
			}
			System.out.println(ep);
		}
		
		// Initialisation des places communes
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	String pc6 = "pc6";
    	String pc7 = "pc7";
    	
    	// Création des objets PlaceCommune
    	PlaceCommune PC1 = new PlaceCommune(pc1);
    	PlaceCommune PC2 = new PlaceCommune(pc2);
    	PlaceCommune PC3 = new PlaceCommune(pc3);
    	PlaceCommune PC4 = new PlaceCommune(pc4);
    	PlaceCommune PC5 = new PlaceCommune(pc5);
    	PlaceCommune PC6 = new PlaceCommune(pc6);
    	PlaceCommune PC7 = new PlaceCommune(pc7);
    	
    	// Ajout d'un jeton à la place commune PC7
    	PC7.addJeton();
    	
    	// Ajout des places communes à la carte
    	this.placesCommunes.put(pc1, PC1);
    	this.placesCommunes.put(pc2, PC2);
    	this.placesCommunes.put(pc3, PC3);
    	this.placesCommunes.put(pc4, PC4);
    	this.placesCommunes.put(pc5, PC5);
    	this.placesCommunes.put(pc6, PC6);
    	this.placesCommunes.put(pc7, PC7);
    	
    	// Initialisation des jetons à mettre à jour pour chaque place commune
    	this.updatingJetons.put(pc1, this.semJetonUriList.get(0));
    	this.updatingJetons.put(pc2, this.semJetonUriList.get(1));
    	this.updatingJetons.put(pc3, this.semJetonUriList.get(2));
    	this.updatingJetons.put(pc4, this.semJetonUriList.get(3));
    	this.updatingJetons.put(pc5, this.semJetonUriList.get(4));
    	this.updatingJetons.put(pc6, this.semJetonUriList.get(5));
    	this.updatingJetons.put(pc7, this.semJetonUriList.get(6));
		
		// Génération de l'ID unique pour ce composant
		this.id = URIGenerator.generateURI();
	}
	
	// Méthode d'exécution principale du composant
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		
		// Synchronisation pour garantir que le plugin de sémaphore est bien installé avant de notifier
		synchronized (this) {
			this.installPlugin(semaphorePlugin);
			notifyAll(); 
		}
		
		// Initialisation des transitions pour les places communes
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	String pc6 = "pc6";
    	String pc7 = "pc7";
    	
		try {
			// Définition des transitions d'entrée et de sortie pour chaque place commune
    		this.placesCommunes.get(pc1).addTransEntree("tA1");
    		this.placesCommunes.get(pc1).addTransEntree("tB1");
    		this.placesCommunes.get(pc1).addTransSortie("tA2");
    		this.placesCommunes.get(pc1).addTransSortie("tB2");
    		
    		this.placesCommunes.get(pc2).addTransEntree("tA2");
    		this.placesCommunes.get(pc2).addTransEntree("tB2");
    		this.placesCommunes.get(pc2).addTransSortie("tA3");
    		this.placesCommunes.get(pc2).addTransSortie("tB3");
    		
    		this.placesCommunes.get(pc3).addTransEntree("tA3");
    		this.placesCommunes.get(pc3).addTransEntree("tB3");
    		this.placesCommunes.get(pc3).addTransSortie("tA4");
    		this.placesCommunes.get(pc3).addTransSortie("tB4");
    		
    		this.placesCommunes.get(pc4).addTransEntree("tA4");
    		this.placesCommunes.get(pc4).addTransEntree("tB4");
    		this.placesCommunes.get(pc4).addTransSortie("tA5");
    		this.placesCommunes.get(pc4).addTransSortie("tB5");
    		
    		this.placesCommunes.get(pc5).addTransEntree("tA5");
    		this.placesCommunes.get(pc5).addTransEntree("tB5");
    		this.placesCommunes.get(pc5).addTransSortie("tCONV");
    		this.placesCommunes.get(pc5).addTransSortie("tCONV");
    		
    		this.placesCommunes.get(pc6).addTransEntree("tCONV");
    		this.placesCommunes.get(pc6).addTransSortie("tC2");
    		this.placesCommunes.get(pc6).addTransSortie("tD2");
    		
    		this.placesCommunes.get(pc7).addTransEntree("tC3");
    		this.placesCommunes.get(pc7).addTransEntree("tD3");
    		this.placesCommunes.get(pc7).addTransSortie("tC2");
    		this.placesCommunes.get(pc7).addTransSortie("tD2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Libération du sémaphore de disponibilité pour signaler que le composant est prêt
		this.semaphorePlugin.release(semAvailabilityUri);
		
		// Boucle principale pour mettre à jour les transitions et gérer les jetons
		while (true) {
			// Parcours de toutes les places communes pour mettre à jour les transitions activables
			for (PlaceCommune pc : this.placesCommunes.values()) {
				int nbJeton = pc.getNbJeton();  // Récupération du nombre de jetons dans la place commune
				// Mise à jour des transitions activables pour chaque place commune sur les points de terminaison clients
				for(ReseauEndpoint ep : endPointClients) {
					ep.getClientSideReference().updateTransitionsActivable(pc.getUri(), pc.getTransSorties(), nbJeton);
				}
			}
			
			// Acquisition du sémaphore de disponibilité avant chaque itération de mise à jour
			this.semaphorePlugin.acquire(semAvailabilityUri);
		}
	}
	
	// Méthode finalise qui est appelée à la fin du cycle de vie de l'objet, typiquement lors de sa destruction ou nettoyage.
	@Override
	public void finalise() throws Exception {
	    System.out.println("Finalise de " + this.uri);  // Affiche l'URI de l'objet lorsque la méthode finalise est appelée.
	}

	// Méthode qui permet de récupérer le nombre de jetons dans une place spécifique, identifiée par son URI.
	@Override
	public int getNbJeton(String uri) throws Exception {
	    // Parcours de la map des places communes pour trouver celle correspondant à l'URI donné.
	    for (Map.Entry<String, PlaceCommune> entry : this.placesCommunes.entrySet()) {
	        // Si l'URI de la place commune correspond à l'URI passé en argument, on retourne le nombre de jetons.
	        if (entry.getValue().getUri().equals(uri)) {
	            return entry.getValue().getNbJeton();
	        }
	    }

	    return 0;  // Si l'URI n'est pas trouvé, on retourne 0.
	}

	// Méthode qui retourne l'URI de l'objet courant.
	@Override
	public String getUri() throws Exception {
	    return uri;  // Retourne simplement l'URI de l'objet.
	}

	// Méthode qui permet de définir le nombre de jetons pour une place spécifique, identifiée par son URI.
	@Override
	public void setNbJeton(String uri, int nbJeton) throws Exception {
	    // Parcours de la map des places communes et met à jour le nombre de jetons si l'URI correspond.
	    this.placesCommunes.forEach((key, value) -> {
	        if(value.getUri().equals(uri)) {
	            value.setNbJeton(nbJeton);  // On met à jour le nombre de jetons de la place.
	        }
	    });
	}

	// Méthode qui retourne la liste des transitions entrantes d'une place identifiée par son URI.
	@Override
	public ArrayList<String> getTransEntrees(String uri) throws Exception {
	    return this.placesCommunes.get(uri).getTransEntrees();  // Retourne les transitions entrantes pour l'URI donné.
	}

	// Méthode qui permet d'ajouter une transition entrante à une place spécifique, identifiée par son URI.
	@Override
	public void addTransEntree(String uri, String entree) throws Exception {
	    this.placesCommunes.get(uri).addTransEntree(entree);  // Ajoute une transition entrante à la place spécifiée.
	}

	// Méthode qui permet d'ajouter une transition sortante à une place spécifique, identifiée par son URI.
	@Override
	public void addTransSortie(String uri, String sortie) throws Exception {
	    this.placesCommunes.get(uri).addTransSortie(sortie);  // Ajoute une transition sortante à la place spécifiée.
	}

	// Méthode qui retourne la liste des transitions sortantes d'une place identifiée par son URI.
	@Override
	public ArrayList<String> getTransSorties(String uri) throws Exception {
	    return this.placesCommunes.get(uri).getTransSorties();  // Retourne les transitions sortantes pour l'URI donné.
	}

	// Méthode qui permet d'ajouter un jeton à une place spécifique, identifiée par son URI.
	@Override
	public void addJeton(String uri) throws Exception {
	    this.placesCommunes.get(uri).addJeton();  // Ajoute un jeton à la place spécifiée.
	}

	// Méthode qui permet de retirer un jeton d'une place spécifique, identifiée par son URI.
	@Override
	public void retrieveJeton(String uri) throws Exception {
	    this.placesCommunes.get(uri).retrieveJeton();  // Retire un jeton de la place spécifiée.
	}

	// Méthode pour acquérir un jeton d'une place spécifique, en attendant que le plugin soit prêt.
	@Override
	public void acquireJeton(String placeCommune) throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.acquire(this.updatingJetons.get(placeCommune));  // Acquisition du jeton via un sémaphore.
	}

	// Méthode pour tenter d'acquérir un jeton d'une place spécifique sans bloquer si ce n'est pas possible.
	@Override
	public boolean tryAcquireJeton(String placeCommune) throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    return this.semaphorePlugin.tryAcquire(this.updatingJetons.get(placeCommune));  // Tentative d'acquisition du jeton sans blocage.
	}

	// Méthode pour libérer un jeton d'une place spécifique.
	@Override
	public void releaseJeton(String placeCommune) throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.release(this.updatingJetons.get(placeCommune));  // Libération du jeton via un sémaphore.
	}

	// Méthode pour acquérir la disponibilité du plugin, en attendant que le plugin soit prêt.
	@Override
	public void acquireAvailability() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.acquire(semAvailabilityUri);  // Acquisition du sémaphore de disponibilité.
	}

	// Méthode pour tenter d'acquérir la disponibilité du plugin sans blocage.
	@Override
	public boolean tryAcquireAvailability() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    return this.semaphorePlugin.tryAcquire(semAvailabilityUri);  // Tentative d'acquisition du sémaphore de disponibilité sans blocage.
	}

	// Méthode pour libérer la disponibilité du plugin.
	@Override
	public void releaseAvailability() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.release(semAvailabilityUri);  // Libération du sémaphore de disponibilité.
	}

	// Méthode pour acquérir l'update du plugin, en attendant que le plugin soit prêt.
	@Override
	public void acquireUpdate() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.acquire(semUpdateUri);  // Acquisition du sémaphore d'update.
	}

	// Méthode pour tenter d'acquérir l'update du plugin sans blocage.
	@Override
	public boolean tryAcquireUpdate() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    return this.semaphorePlugin.tryAcquire(semUpdateUri);  // Tentative d'acquisition du sémaphore d'update sans blocage.
	}

	// Méthode pour libérer l'update du plugin.
	@Override
	public void releaseUpdate() throws Exception {
	    waitUntilPluginIsReady();  // Attente que le plugin soit prêt avant de procéder.
	    this.semaphorePlugin.release(semUpdateUri);  // Libération du sémaphore d'update.
	}

	// Méthode qui retourne l'état de la connexion, vrai si connecté, faux sinon.
	@Override
	public boolean isConnected() throws Exception {
	    return this.isConnected;  // Retourne l'état de la connexion.
	}

	// Méthode privée, synchronisée, pour attendre que le plugin soit prêt avant de procéder à une opération.
	private synchronized void waitUntilPluginIsReady() throws InterruptedException {
	    // Tant que le plugin n'est pas initialisé, on attend.
	    while (this.semaphorePlugin == null || !(this.semaphorePlugin.isInitialised())) {
	        wait();  // Attente active en mode synchrone.
	    }
	}
}