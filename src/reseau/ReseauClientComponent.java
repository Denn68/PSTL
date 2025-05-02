package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import test.CVM;


public class ReseauClientComponent<T, P>
extends AbstractComponent
implements ReseauI<P>{
	
	private String pluginURI; 
	private ReseauCI<P> serverRef;

	protected			ReseauClientComponent(String uri) throws Exception
	{
		super(1, 0);
	}
	
	protected			ReseauClientComponent(String uri, String pluginURI, 
			String reflectionInboundPortURI, ReseauPlaceCommuneEndpoint endPointClient,
			ReseauEndpoint endPointServer) throws Exception
	{
		super(reflectionInboundPortURI, 1, 0);
		this.pluginURI = pluginURI;
		this.endPointClient = endPointClient;
		this.endPointServer = endPointServer;
		this.addRequiredInterface(ReseauPlaceCommuneCI.class);
	}
	
	private ReseauPlaceCommuneEndpoint endPointClient;
	private ReseauEndpoint endPointServer;
	
	@Override
	public void start() {
		System.out.println("Start de " + this.pluginURI);
		try {
			super.start();
		} catch (ComponentStartException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!this.endPointClient.clientSideInitialised()) {
			try {
				System.out.println(this.endPointClient.getClientSideInterface());
				this.endPointClient.initialiseClientSide(this);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void execute() throws Exception {
		super.execute();
		
		System.out.println("Execute de " + this.pluginURI);
		
		// Install the plugin
		ReseauClientPlugin<P> plugin = new ReseauClientPlugin<P>(this.pluginURI);
		plugin.setPluginURI(this.pluginURI);
		this.installPlugin(plugin);
		this.serverRef = plugin.getReseauServicesReference();
		this.init();
		
		this.endPointServer.initialiseServerSide(this);
				
		StringBuilder sb = new StringBuilder();
		System.out.println("------- ATTENTE DE L'INITIALISATION -------\n");

		
		this.endPointClient.getClientSideReference().acquireInitialisation();
		System.out.println("------- AFFICHAGE DU RESEAU -------\n");

	    for (P p : this.getPlaces()) {
	        sb.append("URI : ").append(((Place) p).getUri()).append("\n");

	        sb.append("Transitions entrantes : ");
	        ArrayList<Transition> entrees = ((Place) p).getTransEntrees();
	        if (entrees != null && !entrees.isEmpty()) {
	            for (Transition t : entrees) {
	                sb.append(t.getUri()).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n");

	        sb.append("Transitions sortantes : ");
	        ArrayList<Transition> sorties = ((Place) p).getTransSorties();
	        if (sorties != null && !sorties.isEmpty()) {
	            for (Transition t : sorties) {
	                sb.append(t.getUri()).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n\n");
	    }

	    for (Transition t : this.getTransitions()) {
	        sb.append("URI : ").append(t.getUri()).append("\n");

	        sb.append("Places entrantes : ");
	        ArrayList<String> placesEntrees = t.getPlacesEntrees();
	        if (placesEntrees != null && !placesEntrees.isEmpty()) {
	            for (String p : placesEntrees) {
	                sb.append(p).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n");

	        sb.append("Places Communes entrantes : ");
	        ArrayList<String> entreesCommune = t.getPlacesCommuneEntrees();
	        if (entreesCommune != null && !entreesCommune.isEmpty()) {
	            for (String p : entreesCommune) {
	                sb.append(p).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n");

	        sb.append("Places sortantes : ");
	        ArrayList<String> sorties = t.getPlacesSorties();
	        if (sorties != null && !sorties.isEmpty()) {
	            for (String p : sorties) {
	                sb.append(p).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n");

	        sb.append("Places Communes sortantes : ");
	        ArrayList<String> sortiesCommune = t.getPlacesCommuneSorties();
	        if (sortiesCommune != null && !sortiesCommune.isEmpty()) {
	            for (String p : sortiesCommune) {
	                sb.append(p).append(" ");
	            }
	        } else {
	            sb.append("Aucune");
	        }
	        sb.append("\n\n");
	    }

	    System.out.println(sb.toString());
		
		System.out.println("------- DEBUT -------");
    	try {
    		showReseau();
			randomTransition();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		System.out.println("------- Initialisation du réseau -------");
	}
	
	public ReseauCI<P> getServerRef() {
		return this.serverRef;
	}
	
	
	@Override
	public void finalise() throws Exception {
		
		System.out.println("Finalise de " + this.pluginURI);
		
		
	}
	
	
	

	@Override
	public String getUri() throws Exception {
		return ((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).getUri();
	}

	@Override
	public Collection<P> getPlaces() throws Exception {
		return ((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).getPlaces();
	}

	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return ((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).getTransitions();
	}

	@Override
	public void addPlace(P place) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).addPlace(place);
	}

	@Override
	public void addTransition(Transition transition) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).addTransition(transition);
	}

	@Override
	public Set<Transition> update() throws Exception {
		return ((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).update();
	}

	@Override
	public void showReseau() throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).showReseau();
	}

	@Override
	public void randomTransition() throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).randomTransition();
	}

	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).manualTransition(scanner);
	}

	@Override
	public void activeTransition(Transition tr) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).activeTransition(tr);
	}

	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
		.linkEntreePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
		.linkSortiePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	@Override
	public void updateTransitionsActivable(String uri, ArrayList<String> transSorties, boolean transitionsState)
			throws Exception {
		((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
		.updateTransitionsActivable(uri, transSorties, transitionsState);
	}

}
