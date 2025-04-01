package reseauPlaceCommune;

import java.util.ArrayList;
import java.util.Arrays;

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
import semaphore.SemaphoreClientPlugin;

@OfferedInterfaces(offered = { ReseauPlaceCommuneCI.class })
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauPlaceCommuneComponent
extends AbstractComponent
implements ReseauPlaceCommuneI<Transition>{
	
	protected final static String	SEMAPHORE_PLUGIN_AJOUT_A_URI = "semaphore-client-plugin-ajout-a";
	protected final static String	SEMAPHORE_PLUGIN_RETRAIT_A_URI = "semaphore-client-plugin-retrait-a";
	protected final static String	SEMAPHORE_PLUGIN_AJOUT_B_URI = "semaphore-client-plugin-ajout-b";
	protected final static String	SEMAPHORE_PLUGIN_RETRAIT_B_URI = "semaphore-client-plugin-retrait-b";

	protected			ReseauPlaceCommuneComponent(String uri,
			String semaphorePluginAjout_A_InboundPortURI,
			String semaphorePluginRetrait_A_InboundPortURI,
			String semaphorePluginAjout_B_InboundPortURI,
			String semaphorePluginRetrait_B_InboundPortURI,
			ReseauPlaceCommuneEndpoint endPointServer,
			ArrayList<ReseauEndpoint> endPointClients) throws Exception
	{
		super(1, 0);
		
        this.uri = uri;
        this.placesCommunes = new ArrayList<PlaceCommune>();
		endPointServer.initialiseServerSide(this);
		this.endPointServer = endPointServer;
		this.endPointClients = endPointClients;
		
		this.semaphorePluginAjout_A = new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_AJOUT_A_URI,
				semaphorePluginAjout_A_InboundPortURI);
		this.semaphorePluginRetrait_A = new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_RETRAIT_A_URI,
				semaphorePluginRetrait_A_InboundPortURI);
		
		this.semaphorePluginAjout_B = new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_AJOUT_B_URI,
				semaphorePluginAjout_B_InboundPortURI);
		this.semaphorePluginRetrait_B = new SemaphoreClientPlugin(SEMAPHORE_PLUGIN_RETRAIT_B_URI,
				semaphorePluginRetrait_B_InboundPortURI);
	}
	
	private ReseauPlaceCommuneEndpoint endPointServer;
	private ArrayList<ReseauEndpoint> endPointClients;
	private ArrayList<PlaceCommune> placesCommunes;
    private String uri;
    private String id;
    private SemaphoreClientPlugin semaphorePluginAjout_A;
    private SemaphoreClientPlugin semaphorePluginRetrait_A;
    private SemaphoreClientPlugin semaphorePluginAjout_B;
    private SemaphoreClientPlugin semaphorePluginRetrait_B;
	
	@Override
	public void start() {
		try {
			super.start();
			try {
				this.installPlugin(semaphorePluginAjout_A);
				this.installPlugin(semaphorePluginRetrait_A);
				this.installPlugin(semaphorePluginAjout_B);
				this.installPlugin(semaphorePluginRetrait_B);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	String t5 = "t5";
    	String t6 = "t6";
    	String t7 = "t7";
    	String t8 = "t8";
    	String t9 = "t9";
    	
    	PlaceCommune PC1 = new PlaceCommune(pc1);
    	PlaceCommune PC2 = new PlaceCommune(pc2);
    	PlaceCommune PC3 = new PlaceCommune(pc3);
    	PlaceCommune PC4 = new PlaceCommune(pc4);
    	
    	ReseauEndpoint ep1 = endPointClients.get(0);
    	ReseauEndpoint ep2 = endPointClients.get(1);
    	
    	try {
			ep1.getClientSideReference().linkPlacesTransition(null, t1, new ArrayList<>(Arrays.asList(PC1)));
			ep1.getClientSideReference().linkPlacesTransition(new ArrayList<>(Arrays.asList(PC3)), t2, null);
			ep1.getClientSideReference().linkPlacesTransition(new ArrayList<>(Arrays.asList(PC4)), t3, null);
			ep1.getClientSideReference().linkPlacesTransition(null, t4, new ArrayList<>(Arrays.asList(PC4)));
			
			ep2.getClientSideReference().linkPlacesTransition(null, t5, new ArrayList<>(Arrays.asList(PC2)));
			ep2.getClientSideReference().linkPlacesTransition(new ArrayList<>(Arrays.asList(PC2)), t6, new ArrayList<>(Arrays.asList(PC3)));
			ep2.getClientSideReference().linkPlacesTransition(new ArrayList<>(Arrays.asList(PC4)), t8, null);
			ep2.getClientSideReference().linkPlacesTransition(null, t9, new ArrayList<>(Arrays.asList(PC4)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.id = URIGenerator.generateURI();
	}
	
	@Override
	public void execute() throws Exception {
		this.runTask(new AbstractComponent.AbstractTask() {
			
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

		});
	}

	@Override
	public int getNbJeton(String uri) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				return p.getNbJeton();
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
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				p.setNbJeton(nbJeton);
			}
        }
	}

	@Override
	public ArrayList<Transition> getTransEntrees(String uri) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				return p.getTransEntrees();
			}
        }
		return null;
	}

	@Override
	public void addTransEntree(String uri, Transition entree) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				p.addTransEntree(entree);
			}
        }
	}

	@Override
	public void addTransSortie(String uri, Transition sortie) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				p.addTransSortie(sortie);
			}
        }
	}

	@Override
	public ArrayList<Transition> getTransSorties(String uri) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				return p.getTransSorties();
			}
        }
		return null;
	}

	@Override
	public void addJeton(String uri) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				p.addJeton();
			}
        }
	}

	@Override
	public void retrieveJeton(String uri) throws Exception {
		for(PlaceCommune p: this.placesCommunes) {
			if(p.getUri().equals(uri)) {
				p.retrieveJeton();
			}
        }
	}
	
}
