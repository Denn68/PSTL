package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import interfaces.ReseauI;

@RequiredInterfaces(required = { ReseauPlaceCommuneCI.class})
public class ReseauPlugin<P>
extends 	AbstractPlugin
implements ReseauI<P>{

	private static final long serialVersionUID = 1L;

	// Port entrant pour la gestion du réseau
	protected ReseauInboundPortForPlugin<P>	rip;

	// Cartes pour stocker les places et les transitions
	protected Map<String, P> 		places;
	protected Map<String, Transition> 	transitions;
	protected String 					uri;
	
	// Points de terminaison pour la communication avec les places communes et le serveur
	private ReseauPlaceCommuneEndpoint endPointClient;
	private ReseauEndpoint endPointServer;
	private Transition oldTransition;  // Transition précédente utilisée pour éviter les redondances

	// Constructeur principal pour initialiser avec un URI
	public				ReseauPlugin(String uri) throws Exception
	{
		super();
		this.uri = uri;
	}
	
	// Constructeur avec les points de terminaison serveur et client
	public				ReseauPlugin(String uri,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super();
		this.uri = uri;

		this.endPointServer = endPointServer;
		this.endPointClient = endPointClient;
	}

	// Méthode appelée lors de l'installation du plugin sur le composant
	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);
		
		// Ajout des interfaces requises
		this.addRequiredInterface(ReseauPlaceCommuneCI.class);
	}

	// Initialisation du plugin, incluant la création du port entrant et l'initialisation des points de terminaison
	@Override
	public void			initialise() throws Exception
	{
		super.initialise();
		endPointServer.initialiseServerSide(this.getOwner());
		
		if(!this.endPointClient.clientSideInitialised()) {
			try {
				this.endPointClient.initialiseClientSide(this.getOwner());
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}

		// Initialisation des cartes pour stocker les places et transitions
		this.places = new HashMap<>();
		this.transitions = new HashMap<>();

		// Création et publication du port entrant
		this.rip = new ReseauInboundPortForPlugin<P>(this.getOwner(),
													this.getPluginURI());
		this.rip.publishPort();
	}

	// Finalisation du plugin, nettoyage des cartes de places et transitions
	@Override
	public void			finalise() throws Exception
	{
		this.places.clear();
		this.places = null;
		this.transitions.clear();
		this.transitions = null;
		super.finalise();
	}

	// Désinstallation du plugin, suppression du port entrant
	@Override
	public void			uninstall() throws Exception
	{
		this.rip.unpublishPort();
		this.rip.destroyPort();					// optionnel
		this.removeOfferedInterface(ReseauCI.class);
	}

	// Récupère l'URI du plugin
	@Override
	public String getUri() throws Exception {
		return this.uri;
	}

	// Récupère toutes les places du réseau
	@Override
	public Collection<P> getPlaces() {
        return places.values();
    }

	// Récupère toutes les transitions du réseau
	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return transitions.values();
	}

	// Ajoute une place au réseau en la mettant dans la carte des places
	@Override
	public void addPlace(P place) throws Exception {
		this.places.put(((Place) place).getUri(), place);		
	}

	// Ajoute une transition au réseau en la mettant dans la carte des transitions
	@Override
	public void addTransition(Transition transition) throws Exception {
		this.transitions.put(transition.getUri(), transition);
	}

	// Met à jour la liste des transitions activables en fonction des jetons dans les places
	@Override
	public Set<Transition> update() throws Exception {
	    Set<Transition> transitionsPossibles = new HashSet<>();

	    // Vérification de chaque transition pour savoir si elle peut être activée
	    for (Transition transition : this.transitions.values()) {
	        boolean appendTrans = (oldTransition != transition);

	        for (String place : transition.getPlacesEntrees().keySet()) {
	            
	            if (((Place) this.places.get(place)).getNbJeton() == 0) {
	            	appendTrans = false;
	            }
	        }
	        if (appendTrans && transition.isActivable())
	            transitionsPossibles.add(transition);
	    }

	    return transitionsPossibles;
	}

	// Affiche l'état actuel du réseau, y compris les jetons dans les places
	@Override
	public void showReseau() throws Exception {
		StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");

        // Itération sur les places et ajout de leurs jetons à l'affichage
        Iterator<P> it = places.values().iterator();
        while (it.hasNext()) {
            P p = it.next();
            sb.append(((Place) p).getNbJeton());
            if (it.hasNext()) sb.append(", ");
        }

        sb.append(")");
        System.out.println(sb.toString());		
	}

	// Choisit une transition aléatoire parmi celles qui sont activables et l'active
	@Override
	public void randomTransition() throws Exception {
		Random random = new Random();
        while(true) {
        	StringBuilder sb = new StringBuilder();
            Set<Transition> transitionsPossibles = update();
            sb.append("Transitions possible : ");
            String message = new String();
            
            // Affichage des transitions possibles
            for (Transition t : transitionsPossibles) {
            	message = String.format("%s,", t.getUri());
            	sb.append(message);
            }
            
            sb.append("\n");

            // Si aucune transition n'est possible, attendre un peu et recommencer
            if (transitionsPossibles.isEmpty()) {
            	sb.append("Aucune transition possible.\n");
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            } else {
            	// Choisir une transition aléatoire et l'activer
            	List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);
            	Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message).append("\n");

                activeTransition(transitionChoisie);
                showReseau();
            }
        }
	}

	// Permet à l'utilisateur de choisir manuellement une transition et de l'activer
	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		Set<Transition> transitionsPossibles = update();
        if (transitionsPossibles.isEmpty()) {
            System.out.println("Aucune transition possible.");
            return;
        }

        // Affichage des transitions possibles
        System.out.println("Transitions possibles :");
        List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);

        for (int i = 0; i < listeTransitions.size(); i++)
        	System.out.printf("%s,\n", listeTransitions.get(i).getUri());
        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        // Vérification de la validité du choix de l'utilisateur
        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        // Activation de la transition choisie
        Transition transitionChoisie = listeTransitions.get(choix - 1);
        activeTransition(transitionChoisie);
        showReseau();		
	}

	// Active une transition, en prenant les jetons des places d'entrée et en ajoutant des jetons dans les places de sortie
	@SuppressWarnings("unchecked")
	@Override
	public void activeTransition(Transition tr) throws Exception {

		if(tr.isActivable()) {
			
    		boolean notSkip = true;
    		
    		// Essayer de prendre les jetons des places communes d'entrée
    		for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
    			notSkip = endPointClient.getClientSideReference().tryAcquireJeton(placeCommune);
    			if (!notSkip) {
    				break;
    			}
	        }
    		
    		// Si la transition est activable
    		if(notSkip) {
    			// Prendre les jetons des places d'entrée et ajouter des jetons dans les places de sortie
				for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
					endPointClient.getClientSideReference().retrieveJeton(placeCommune);
					endPointClient.getClientSideReference().releaseAvailability();
					endPointClient.getClientSideReference().releaseJeton(placeCommune);
		        }
				
		        for (String place : tr.getPlacesEntrees().keySet())
		        	((Place) places.get(place)).retrieveJeton();
		        
		        for (String place : tr.getPlacesSorties()) {
		        	((Place) places.get(place)).addJeton();}

		        for (String placeCommune : tr.getPlacesCommuneSorties()) {
		        	endPointClient.getClientSideReference().addJeton(placeCommune);
		        	endPointClient.getClientSideReference().releaseAvailability();
	        	}
		        
		        // Appliquer la fonction activable associée à la transition
		        tr.getActivableFunction().apply(tr.getUri());
		        oldTransition = tr;
    		} 
    	}
	}

	// Lier une place commune d'entrée à une transition avec les informations de seuil et mise à jour
	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, int seuil, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneEntree(placeCommune, seuil, updatingAvailability, updatingJetons);
	}

	// Lier une place commune de sortie à une transition avec les informations de mise à jour
	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneSortie(placeCommune, updatingAvailability);
	}

	// Mettre à jour les transitions activables en fonction des jetons disponibles
	@Override
	public void updateTransitionsActivable(String uri, ArrayList<String> transSorties, int nbJeton)
			throws Exception {
		for(String t : transSorties) {
			if(this.transitions.containsKey(t)) {
				this.transitions.get(t).updateIsActivable(uri, nbJeton);
			}
		}
	}
	
}
