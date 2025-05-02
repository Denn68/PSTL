package reseauPlaceCommune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.Place;
import classes.PlaceCommune;
import classes.Transition;
import classes.URIGenerator;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import interfaces.ReseauPlaceCommuneI;
import reseau.ReseauEndpoint;
import reseau.ReseauPlugin;
import semaphore.SemaphoreClientPlugin;

@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<String>{

	protected			ReseauPlaceCommuneComponent(String uri,
			String semaphoreReflectionInboundPortURI,
			String semAvailabilityUri,
			String semInitialisationUri,
			ArrayList<String> semJetonUriList,
			ReseauPlaceCommuneEndpoint endPointServer,
			ArrayList<ReseauEndpoint> endPointClients) throws Exception
	{
		super(2, 0);
		
        this.uri = uri;
        this.placesCommunes = new HashMap<>();
		endPointServer.initialiseServerSide(this);
		this.endPointServer = endPointServer;
		this.endPointClients = endPointClients;
		
		this.updatingJetons = new HashMap<String, String>();
		
		this.semAvailabilityUri = semAvailabilityUri;
		this.semInitialisationUri = semInitialisationUri;
		this.semJetonUriList = semJetonUriList;
		
		this.semaphoreReflectionInboundPortURI =
				semaphoreReflectionInboundPortURI;
		
		this.semaphorePlugin =
				new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_URI,
										  this.semaphoreReflectionInboundPortURI);
		
		this.isConnected = false;
		
	}
	
	private ReseauPlaceCommuneEndpoint endPointServer;
	private ArrayList<ReseauEndpoint> endPointClients;
	private Map<String, PlaceCommune> placesCommunes;
    private String uri;
    private String id;
    private SemaphoreClientPlugin semaphorePlugin;
    private Map<String, String> updatingJetons;
    
    private String semAvailabilityUri;
    private String semInitialisationUri;
    private ArrayList<String> semJetonUriList;
    
    protected String	semaphoreReflectionInboundPortURI;
	private boolean isConnected;
    protected final static String	SEMAPHORE_PLUGIN_URI =
			"semaphore-client-plugin";
	
	@Override
	public void start() {
		try {
			super.start();
		} catch (ComponentStartException e) {
			e.printStackTrace();
		}
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	PlaceCommune PC1 = new PlaceCommune(pc1);
    	PlaceCommune PC2 = new PlaceCommune(pc2);
    	PlaceCommune PC3 = new PlaceCommune(pc3);
    	PlaceCommune PC4 = new PlaceCommune(pc4);
    	
    	PC4.addJeton();
    	
    	this.placesCommunes.put(pc1, PC1);
    	this.placesCommunes.put(pc2, PC2);
    	this.placesCommunes.put(pc3, PC3);
    	this.placesCommunes.put(pc4, PC4);
    	
    	this.updatingJetons.put(pc1, this.semJetonUriList.get(0));
    	this.updatingJetons.put(pc2, this.semJetonUriList.get(1));
    	this.updatingJetons.put(pc3, this.semJetonUriList.get(2));
    	this.updatingJetons.put(pc4, this.semJetonUriList.get(3));
		
		this.id = URIGenerator.generateURI();
	}
	
	@Override
	public void execute() throws Exception {
		
		this.installPlugin(semaphorePlugin);

		System.out.println("Dans ReseauPLaceCommune.java, le plugin " + semaphorePlugin.getPluginURI() + " est installé");
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	Thread.sleep(5000); // On attend le initialise server side
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
    	
		try {
			System.out.println("Connexion entre réseau A et réseau PlaceCommune...");
    		endPointClients.get(1).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t1", pc1, semAvailabilityUri, this.updatingJetons.get(pc1));
    		this.placesCommunes.get(pc1).addTransEntree("t1");
    		
    		endPointClients.get(1).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t2", pc3, semAvailabilityUri, this.updatingJetons.get(pc3));
    		this.placesCommunes.get(pc3).addTransSortie("t2");
    		
    		endPointClients.get(1).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t3", pc4, semAvailabilityUri, this.updatingJetons.get(pc4));
    		this.placesCommunes.get(pc4).addTransSortie("t3");
    		
    		endPointClients.get(1).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t4", pc4, semAvailabilityUri, this.updatingJetons.get(pc4));
    		this.placesCommunes.get(pc4).addTransEntree("t4");
    		
    		
    		System.out.println("Connexion entre réseau B et réseau PlaceCommune...");
    		endPointClients.get(0).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t5", pc2, semAvailabilityUri, this.updatingJetons.get(pc2));
    		this.placesCommunes.get(pc2).addTransEntree("t5");
    		
    		endPointClients.get(0).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t6", pc1, semAvailabilityUri, this.updatingJetons.get(pc1));
    		this.placesCommunes.get(pc1).addTransSortie("t6");
    		
    		endPointClients.get(0).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t6", pc2, semAvailabilityUri, this.updatingJetons.get(pc2));
    		this.placesCommunes.get(pc2).addTransSortie("t6");
    		
    		endPointClients.get(0).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t6", pc3, semAvailabilityUri, this.updatingJetons.get(pc3));
    		this.placesCommunes.get(pc3).addTransEntree("t6");
    		
    		endPointClients.get(0).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t8", pc4, semAvailabilityUri, this.updatingJetons.get(pc4));
    		this.placesCommunes.get(pc4).addTransSortie("t8");
    		
    		endPointClients.get(0).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t9", pc4, semAvailabilityUri, this.updatingJetons.get(pc4));
    		this.placesCommunes.get(pc4).addTransEntree("t9");
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.isConnected = true;
		
		boolean flag = true;
		while (true) {
			for (PlaceCommune pc : this.placesCommunes.values()) {
				
				System.out.println("Mise à jour des possibilités de transitions : " + pc.getUri());
				/*
				boolean transitionsState = (pc.getNbJeton() > 0);
 				for(ReseauEndpoint ep : endPointClients) {
					ep.getClientSideReference().updateTransitionsActivable(pc.getUri(), pc.getTransSorties(), transitionsState);
				}
		        */
			}
			
			if (flag) {
				this.semaphorePlugin.release(semInitialisationUri);
				flag = false;
			}
			this.semaphorePlugin.acquire(semAvailabilityUri);
		}
	}
	
	@Override
	public void finalise() throws Exception {
		
		System.out.println("Finalise de " + this.uri);
	}

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
			if(value.getUri().equals(uri)) {
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
		this.semaphorePlugin.acquire(this.updatingJetons.get(placeCommune));
	}

	@Override
	public boolean tryAcquireJeton(String placeCommune) throws Exception {
		return this.semaphorePlugin.tryAcquire(this.updatingJetons.get(placeCommune));
	}

	@Override
	public void releaseJeton(String placeCommune) throws Exception {
		this.semaphorePlugin.release(this.updatingJetons.get(placeCommune));
	}

	@Override
	public void acquireAvailability() throws Exception {
		System.out.println("acquireAvailability Availability");
		this.semaphorePlugin.acquire(semAvailabilityUri);
	}

	@Override
	public boolean tryAcquireAvailability() throws Exception {
		System.out.println("tryAcquireAvailability Availability");
		return this.semaphorePlugin.tryAcquire(semAvailabilityUri);
	}

	@Override
	public void releaseAvailability() throws Exception {
		System.out.println("Release Availability");
		this.semaphorePlugin.release(semAvailabilityUri);
	}
	
	@Override
	public void acquireInitialisation() throws Exception {
		System.out.println("acquireInitialisation");
		this.semaphorePlugin.acquire(semInitialisationUri);
	}

	@Override
	public boolean isConnected() throws Exception {
		return this.isConnected;
	}
	
}
