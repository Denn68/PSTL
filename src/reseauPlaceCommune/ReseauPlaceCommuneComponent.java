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
import interfaces.ReseauPlaceCommuneI;
import interfaces.ReseauPlaceCommuneCI;
import interfaces.ReseauCI;
import reseau.ReseauEndpoint;
import reseau.ReseauPlugin;
import semaphore.SemaphoreClientPlugin;

@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<Transition>{

	protected			ReseauPlaceCommuneComponent(String uri,
			String semaphoreReflectionInboundPortURI,
			ArrayList<String> semAvailabilityUriList,
			ArrayList<String> semJetonUriList,
			ReseauPlaceCommuneEndpoint endPointServer,
			ArrayList<ReseauEndpoint> endPointClients) throws Exception
	{
		super(1, 0);
		
        this.uri = uri;
        this.placesCommunes = new HashMap<>();
		endPointServer.initialiseServerSide(this);
		this.endPointServer = endPointServer;
		this.endPointClients = endPointClients;
		
		this.updatingAvailability = new HashMap<String, String>();
		this.updatingJetons = new HashMap<String, String>();
		
		this.semAvailabilityUriList = semAvailabilityUriList;
		this.semJetonUriList = semJetonUriList;
		
		this.semaphoreReflectionInboundPortURI =
				semaphoreReflectionInboundPortURI;
		
		this.semaphorePlugin =
				new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_URI,
										  this.semaphoreReflectionInboundPortURI);
		
	}
	
	private ReseauPlaceCommuneEndpoint endPointServer;
	private ArrayList<ReseauEndpoint> endPointClients;
	private Map<String, PlaceCommune> placesCommunes;
    private String uri;
    private String id;
    private SemaphoreClientPlugin semaphorePlugin;
    private Map<String, String> updatingAvailability;
    private Map<String, String> updatingJetons;
    
    private ArrayList<String> semAvailabilityUriList;
    private ArrayList<String> semJetonUriList;
    
    protected String	semaphoreReflectionInboundPortURI;
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
		}
		
		/*try {
			this.installPlugin(semaphorePluginAjout_A);
			this.installPlugin(semaphorePluginRetrait_A);
			this.installPlugin(semaphorePluginAjout_B);
			this.installPlugin(semaphorePluginRetrait_B);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	PlaceCommune PC1 = new PlaceCommune(pc1);
    	PlaceCommune PC2 = new PlaceCommune(pc2);
    	PlaceCommune PC3 = new PlaceCommune(pc3);
    	PlaceCommune PC4 = new PlaceCommune(pc4);
    	
    	this.placesCommunes.put(pc1, PC1);
    	this.placesCommunes.put(pc2, PC2);
    	this.placesCommunes.put(pc3, PC3);
    	this.placesCommunes.put(pc4, PC4);
    	
    	this.updatingAvailability.put(pc1, this.semAvailabilityUriList.get(0));
    	this.updatingAvailability.put(pc2, this.semAvailabilityUriList.get(1));
    	this.updatingAvailability.put(pc3, this.semAvailabilityUriList.get(2));
    	this.updatingAvailability.put(pc4, this.semAvailabilityUriList.get(3));
    	
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
    	
		try {
			
			System.out.println("Connexion en réseau A et réseau PlaceCommune...");
    		endPointClients.get(0).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t1", pc1, this.updatingAvailability.get(pc1), this.updatingJetons.get(pc1));
    		endPointClients.get(0).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t2", pc3, this.updatingAvailability.get(pc3), this.updatingJetons.get(pc3));
    		endPointClients.get(0).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t3", pc4, this.updatingAvailability.get(pc4), this.updatingJetons.get(pc4));
    		endPointClients.get(0).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t4", pc4, this.updatingAvailability.get(pc4), this.updatingJetons.get(pc4));
    		
    		
    		System.out.println("Connexion en réseau B et réseau PlaceCommune...");
    		endPointClients.get(1).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t5", pc2, this.updatingAvailability.get(pc2), this.updatingJetons.get(pc2));
    		endPointClients.get(1).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t6", pc1, this.updatingAvailability.get(pc1), this.updatingJetons.get(pc1));
    		endPointClients.get(1).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t6", pc2, this.updatingAvailability.get(pc2), this.updatingJetons.get(pc2));
    		endPointClients.get(1).getClientSideReference()
    		.linkEntreePlaceCommuneTransition("t8", pc4, this.updatingAvailability.get(pc4), this.updatingJetons.get(pc4));
    		endPointClients.get(1).getClientSideReference()
    		.linkSortiePlaceCommuneTransition("t9", pc4, this.updatingAvailability.get(pc4), this.updatingJetons.get(pc4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Les semaphores :");
		System.out.println(this.updatingAvailability);
		System.out.println(this.updatingJetons);
		

		
		/*System.out.println("Reseau A");
		endPointClients.get(0).getClientSideReference().showReseau();
		
		
		
		System.out.println("Reseau A");
		endPointClients.get(1).getClientSideReference().showReseau();*/
		/*this.runTask(new AbstractComponent.AbstractTask() {
			
			@Override
		    public void run() {
		        for (PlaceCommune pc : ((ReseauPlaceCommuneComponent) this.getTaskOwner()).placesCommunes) {
					if(pc.getNbJeton() == 0) {
						for(Transition t : pc.getTransSorties()) {
							t.updateIsActivable(pc);
						}
					}
				}
				while (true) {
					for (PlaceCommune pc : ((ReseauPlaceCommuneComponent) this.getTaskOwner()).placesCommunes) {
						if (pc.getUpdatingAvailability().tryAcquire()) {
							// Attente ici, pas d'attente active
							System.out.println("Mise à jour des possibilités de transitions: " + uri);

				            for(Transition t: pc.getTransSorties()) {
				            	t.updateIsActivable(pc);
				            }
						}
				        
					}
				}
		    }

		});*/
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
	public ArrayList<Transition> getTransEntrees(String uri) throws Exception {
		for (Map.Entry<String, PlaceCommune> entry : this.placesCommunes.entrySet()) {
		    if (entry.getValue().getUri().equals(uri)) {
		        return entry.getValue().getTransEntrees();
		    }
		}

		return null;
	}

	@Override
	public void addTransEntree(String uri, Transition entree) throws Exception {
		this.placesCommunes.forEach((key, value) -> {
			if(value.getUri().equals(uri)) {
				value.addTransEntree(entree);
			}
        });
	}

	@Override
	public void addTransSortie(String uri, Transition sortie) throws Exception {
		this.placesCommunes.forEach((key, value) -> {
			if(value.getUri().equals(uri)) {
				value.addTransSortie(sortie);
			}
        });
	}

	@Override
	public ArrayList<Transition> getTransSorties(String uri) throws Exception {
		for (Map.Entry<String, PlaceCommune> entry : this.placesCommunes.entrySet()) {
		    if (entry.getValue().getUri().equals(uri)) {
		        return entry.getValue().getTransSorties();
		    }
		}

		return null;
	}

	@Override
	public void addJeton(String uri) throws Exception {
		this.placesCommunes.forEach((key, value) -> {
			if(value.getUri().equals(uri)) {
				value.addJeton();
			}
        });
	}

	@Override
	public void retrieveJeton(String uri) throws Exception {
		this.placesCommunes.forEach((key, value) -> {
			if(value.getUri().equals(uri)) {
				value.retrieveJeton();
			}
        });
	}
	
}
