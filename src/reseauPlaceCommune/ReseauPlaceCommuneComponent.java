package reseauPlaceCommune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.PlaceCommune;
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
import semaphore.SemaphoreClientPlugin;

@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<String>{

	protected			ReseauPlaceCommuneComponent(String uri,
			String semaphoreReflectionInboundPortURI,
			String semAvailabilityUri,
			String semUpdateUri,
			ArrayList<String> semJetonUriList,
			ReseauPlaceCommuneEndpoint endPointServer,
			ArrayList<ReseauEndpoint> endPointClients) throws Exception
	{
		super(2, 0);
		
        this.uri = uri;
        this.placesCommunes = new HashMap<>();
		endPointServer.initialiseServerSide(this);
		//this.endPointServer = endPointServer;
		this.endPointClients = endPointClients;
		
		this.updatingJetons = new HashMap<String, String>();
		
		this.semAvailabilityUri = semAvailabilityUri;
		this.semUpdateUri = semUpdateUri;
		this.semJetonUriList = semJetonUriList;
		
		this.semaphoreReflectionInboundPortURI =
				semaphoreReflectionInboundPortURI;
		
		this.semaphorePlugin =
				new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_URI,
										  this.semaphoreReflectionInboundPortURI);
		
		this.isConnected = false;
		
	}
	
	//private ReseauPlaceCommuneEndpoint endPointServer;
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
	
	@Override
	public void start() {
		try {
			super.start();
		} catch (ComponentStartException e) {
			e.printStackTrace();
		}
		for (ReseauEndpoint ep : endPointClients) {
			if(!ep.clientSideInitialised()) {
				try {
					ep.initialiseClientSide(this);
				} catch (ConnectionException e) {
					e.printStackTrace();
				}
			}
			//System.out.println(ep);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		
		synchronized (this) {
			this.installPlugin(semaphorePlugin);
			//System.out.println("Dans ReseauPLaceCommune.java, le plugin " + semaphorePlugin.getPluginURI() + " est installé");
			notifyAll(); 
		}
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
		try {
			
    		this.placesCommunes.get(pc1).addTransEntree("t1");
    		this.placesCommunes.get(pc3).addTransSortie("t2");
    		this.placesCommunes.get(pc4).addTransSortie("t3");
    		this.placesCommunes.get(pc4).addTransEntree("t4");
    		
    		this.placesCommunes.get(pc2).addTransEntree("t5");
    		this.placesCommunes.get(pc1).addTransSortie("t6");
    		this.placesCommunes.get(pc2).addTransSortie("t6");
    		this.placesCommunes.get(pc3).addTransEntree("t6");
    		this.placesCommunes.get(pc4).addTransSortie("t8");
    		this.placesCommunes.get(pc4).addTransEntree("t9");
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.isConnected = true;
		
		this.semaphorePlugin.release(semAvailabilityUri);
		
		while (true) {
			/*while(this.semaphorePlugin.availablePermits(semUpdateUri) > 1) {
				this.semaphorePlugin.tryAcquire(semUpdateUri);
			}*/
			for (PlaceCommune pc : this.placesCommunes.values()) {
				
				//System.out.println("Mise à jour des possibilités de transitions : " + pc.getUri());
				//System.out.println("Nombre de permits : " + this.semaphorePlugin.availablePermits(semAvailabilityUri));
				
				int nbJeton = pc.getNbJeton();
 				for(ReseauEndpoint ep : endPointClients) {
					ep.getClientSideReference().updateTransitionsActivable(pc.getUri(), pc.getTransSorties(), nbJeton);
				}
		        
			}

			/*if(this.semaphorePlugin.availablePermits(semUpdateUri) == 0) {
				for(ReseauEndpoint ep : endPointClients) {
					this.semaphorePlugin.release(semUpdateUri);
				}
			}*/
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
		System.out.println("acquireAvailability Availability");
		waitUntilPluginIsReady();
		this.semaphorePlugin.acquire(semAvailabilityUri);
	}

	@Override
	public boolean tryAcquireAvailability() throws Exception {
		System.out.println("tryAcquireAvailability Availability");
		waitUntilPluginIsReady();
		return this.semaphorePlugin.tryAcquire(semAvailabilityUri);
	}

	@Override
	public void releaseAvailability() throws Exception {
		waitUntilPluginIsReady();
		System.out.println("Release Availability " + Thread.currentThread().getName());
		this.semaphorePlugin.release(semAvailabilityUri);
	}
	
	@Override
	public void acquireUpdate() throws Exception {
		System.out.println("acquireUpdate");
		waitUntilPluginIsReady();
		this.semaphorePlugin.acquire(semUpdateUri);
	}

	@Override
	public boolean tryAcquireUpdate() throws Exception {
		System.out.println("tryAcquireUpdate");
		waitUntilPluginIsReady();
		return this.semaphorePlugin.tryAcquire(semUpdateUri);
	}

	@Override
	public void releaseUpdate() throws Exception {
		waitUntilPluginIsReady();
		System.out.println("Release Update " + Thread.currentThread().getName());
		this.semaphorePlugin.release(semUpdateUri);
	}

	@Override
	public boolean isConnected() throws Exception {
		return this.isConnected;
	}
	
	private synchronized void waitUntilPluginIsReady() throws InterruptedException {
		while (this.semaphorePlugin == null || !(this.semaphorePlugin.isInitialised())) {
			System.out.println("J'ATTEND LA SEMAPHORE " + Thread.currentThread().getName());
			wait();
		}
	}
}
