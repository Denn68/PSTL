package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

public class ReseauComponent<T, P>
extends AbstractComponent
implements ReseauI<P>{

    // URI pour le plugin reseau
    public static final String RESEAU_PLUGIN_URI = "reseau-plugin-uri";
    private String pluginURI;

    // Constructeur pour initialiser le composant avec un URI
    protected ReseauComponent(String uri) throws Exception {
        super("URI", 2, 0);

        // Initialisation du plugin
        ReseauPlugin<P> plugin = new ReseauPlugin<P>(uri);
        plugin.setPluginURI(RESEAU_PLUGIN_URI);
        this.installPlugin(plugin);

        // Vérification de l'installation du plugin
        assert this.isInstalled(RESEAU_PLUGIN_URI);
        assert this.getPlugin(RESEAU_PLUGIN_URI) == plugin;
    }

    // Constructeur avec plus d'arguments pour initialiser un composant avec un plugin et des endpoints spécifiques
    protected ReseauComponent(String uri,
                              String reflectionInboundPortURI,
                              String pluginURI,
                              ReseauEndpoint endPointServer,
                              ReseauPlaceCommuneEndpoint endPointClient) throws Exception {
        super(reflectionInboundPortURI, 2, 0);

        // Initialisation du plugin avec des endpoints spécifiques
        ReseauPlugin<P> plugin = new ReseauPlugin<P>(pluginURI,
                                                    endPointServer,
                                                    endPointClient);
        plugin.setPluginURI(pluginURI);
        this.installPlugin(plugin);

        // Vérification de l'installation du plugin
        assert this.isInstalled(pluginURI);
        assert this.getPlugin(pluginURI) == plugin;

        // Sauvegarde de l'URI du plugin
        this.pluginURI = pluginURI;
        this.endPointClient = endPointClient;
    }

    // EndPoint client pour la communication
    private ReseauPlaceCommuneEndpoint endPointClient;

    // Méthode start() pour démarrer le composant
    @Override
    public void start() {
        System.out.println("Start de " + this.pluginURI);
        try {
            super.start();  // Démarre le composant de base
        } catch (ComponentStartException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialisation côté client si nécessaire
        if (!endPointClient.clientSideInitialised()) {
            try {
                endPointClient.initialiseClientSide(this);
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode execute() pour afficher et manipuler le réseau
    @Override
    public void execute() throws Exception {
        super.execute();  // Exécution de la logique de base
        System.out.println("Execute de " + this.pluginURI);

        StringBuilder sb = new StringBuilder();
        sb.append("------- AFFICHAGE RESEAU -------\n");

        // Affichage des lieux (places) et des transitions associées
        for (P p : this.getPlaces()) {
            sb.append("URI : ").append(((Place) p).getUri()).append("\n");

            // Affichage des transitions entrantes
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

            // Affichage des transitions sortantes
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

        // Affichage des transitions avec leurs places associées
        for (Transition t : this.getTransitions()) {
            sb.append("URI : ").append(t.getUri()).append("\n");

            sb.append("Places entrantes : ");
            Set<String> placesEntrees = t.getPlacesEntrees().keySet();
            if (placesEntrees != null && !placesEntrees.isEmpty()) {
                for (String p : placesEntrees) {
                    sb.append(p).append(" ");
                }
            } else {
                sb.append("Aucune");
            }
            sb.append("\n");

            sb.append("Places Communes entrantes : ");
            Set<String> entreesCommune = t.getPlacesCommuneEntrees().keySet();
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

        // Affichage final des informations réseau
        System.out.println(sb.toString());

        System.out.println("------- DEBUT -------");
        try {
            showReseau();  // Affichage du réseau
            randomTransition();  // Transition aléatoire
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Finalisation du composant (nettoyage)
    @Override
    public void finalise() throws Exception {
        System.out.println("Finalise de " + this.pluginURI);
        ((ReseauPlugin<P>) this.getPlugin(this.pluginURI)).showReseau();  // Affichage réseau à la fin
    }

    // Méthodes implémentées de l'interface ReseauI<P> pour manipuler le réseau

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
    public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, int seuil, String updatingAvailability,
                                                 String updatingJetons) throws Exception {
        ((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
        .linkEntreePlaceCommuneTransition(transition, placeCommune, seuil, updatingAvailability, updatingJetons);
    }

    @Override
    public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
                                                 String updatingJetons) throws Exception {
        ((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
        .linkSortiePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
    }

    @Override
    public void updateTransitionsActivable(String uri, ArrayList<String> transSorties, int nbJeton)
            throws Exception {
        ((ReseauPlugin<P>) this.getPlugin(this.pluginURI))
        .updateTransitionsActivable(uri, transSorties, nbJeton);
    }
}
