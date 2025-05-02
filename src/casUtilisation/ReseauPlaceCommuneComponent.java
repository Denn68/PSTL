package casUtilisation;

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
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreClientPlugin;

@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<String>{

	protected			ReseauPlaceCommuneComponent(String uri,
			String semaphoreReflectionInboundPortURI,
			String semAvailabilityUri,
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
			System.out.println(ep);
		}
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	String pc6 = "pc6";
    	String pc7 = "pc7";
    	
    	PlaceCommune PC1 = new PlaceCommune(pc1);
    	PlaceCommune PC2 = new PlaceCommune(pc2);
    	PlaceCommune PC3 = new PlaceCommune(pc3);
    	PlaceCommune PC4 = new PlaceCommune(pc4);
    	PlaceCommune PC5 = new PlaceCommune(pc5);
    	PlaceCommune PC6 = new PlaceCommune(pc6);
    	PlaceCommune PC7 = new PlaceCommune(pc7);
    	
    	PC7.addJeton();
    	
    	this.placesCommunes.put(pc1, PC1);
    	this.placesCommunes.put(pc2, PC2);
    	this.placesCommunes.put(pc3, PC3);
    	this.placesCommunes.put(pc4, PC4);
    	this.placesCommunes.put(pc5, PC5);
    	this.placesCommunes.put(pc6, PC6);
    	this.placesCommunes.put(pc7, PC7);
    	
    	this.updatingJetons.put(pc1, this.semJetonUriList.get(0));
    	this.updatingJetons.put(pc2, this.semJetonUriList.get(1));
    	this.updatingJetons.put(pc3, this.semJetonUriList.get(2));
    	this.updatingJetons.put(pc4, this.semJetonUriList.get(3));
    	this.updatingJetons.put(pc5, this.semJetonUriList.get(4));
    	this.updatingJetons.put(pc6, this.semJetonUriList.get(5));
    	this.updatingJetons.put(pc7, this.semJetonUriList.get(6));
		
		this.id = URIGenerator.generateURI();
	}
	
	@Override
	public void execute() throws Exception {
		
		synchronized (this) {
			this.installPlugin(semaphorePlugin);
			System.out.println("Dans ReseauPLaceCommune.java, le plugin " + semaphorePlugin.getPluginURI() + " est installé");
			notifyAll(); 
		}
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	String pc6 = "pc6";
    	String pc7 = "pc7";
    	
		try {
			
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
		
		this.isConnected = true;
		
		this.semaphorePlugin.release(semAvailabilityUri);
		// TODO : MOUFEES gérer la synchro pour toujours avoir les 2 drops des robots mobile avant le convoyeur
		while (true) {

			//System.out.println("Mise à jour des possibilités de transitions");
			for (PlaceCommune pc : this.placesCommunes.values()) {
				
				// System.out.println("Nbjeton :" + pc.getNbJeton());
				boolean transitionsState = (pc.getNbJeton() > 0);
 				for(ReseauEndpoint ep : endPointClients) {
					ep.getClientSideReference().updateTransitionsActivable(pc.getUri(), pc.getTransSorties(), transitionsState);
				}
		        
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
	public boolean isConnected() throws Exception {
		return this.isConnected;
	}
	
	private synchronized void waitUntilPluginIsReady() throws InterruptedException {
		while (this.semaphorePlugin == null || !(this.semaphorePlugin.isInitialised())) {
			//System.out.println("J'ATTEND LA SEMAPHORE " + Thread.currentThread().getName());
			wait();
		}
	}
}
