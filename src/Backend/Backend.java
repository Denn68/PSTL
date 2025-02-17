package Backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import classes.Jeton;
import classes.Place;
import classes.Transition;
import classes.TransitionExterne;

public class Backend 
implements IBackend{
	ArrayList<Place> places;
    ArrayList<Transition> transitions;
    ArrayList<TransitionExterne> transitionsExternes;
    ArrayList<Jeton> jetons;

	public Backend() {
		this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
        this.transitionsExternes = new ArrayList<TransitionExterne>();
        this.jetons = new ArrayList<Jeton>();
    }
	
	Backend next;
	
	// Temporaire
	public void setNext(Backend next) {
		this.next = next;
	}
	@Override
	public void CreatePlace(String uri) {
		this.places.add(new Place(uri));
	}

	@Override
	public void LinkPlaces(String placeUri1, String placeUri2, String transitionUri) {
		boolean found = false;
		for (Place p1 : this.places) {
			if(p1.getUri() == placeUri1) {
				for (Place p2 : this.places) {
					if(p2.getUri() == placeUri2) {
						Transition transition = new Transition(transitionUri);
						transition.addPlaceEntree(p1);
						transition.addPlaceSortie(p2);
						this.transitions.add(transition);
						this.addTransSortie(placeUri1, transition);
						this.addTransEntree(placeUri2, transition);
						found = true;
					}
				}
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}

	@Override
	public void LinkPlaceCommuneEtReseau(String placeUri1, String placeUri2, String transitionUri) {
		boolean found = false;
		for (Place p1 : this.places) {
			if(p1.getUri() == placeUri1) {
				for (Place p2 : this.places) {
					if(p2.getUri() == placeUri2) {
						TransitionExterne transitionExterne = new TransitionExterne(transitionUri);
						transitionExterne.addPlaceEntree(p1);
						transitionExterne.addPlaceSortie(p2);
						this.transitionsExternes.add(transitionExterne);
						this.addTransExterneSortie(placeUri1, transitionExterne);
						this.addTransExterneEntree(placeUri2, transitionExterne);
						found = true;
					}
				}
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
		
	}

	@Override
	public void InitializePlace(String placeUri, int nbJeton) {
		boolean found = false;
		for (Place p : this.places) {
			if(p.getUri() == placeUri) {
				p.setNbJeton(nbJeton);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	@Override
	public void addTransSortie(String uri, Transition transition) {
		boolean found = false;
		for (Place p : this.places) {
			if(p.getUri() == uri) {
				p.addTransSortie(transition);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	@Override
	public void addTransEntree(String uri, Transition transition) {
		boolean found = false;
		for (Place p : this.places) {
			if(p.getUri() == uri) {
				p.addTransEntree(transition);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	@Override
	public void addTransExterneSortie(String uri, TransitionExterne transitionExterne) {
		boolean found = false;
		for (Place p : this.places) {
			if(p.getUri() == uri) {
				p.addTransExterneSortie(transitionExterne);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	@Override
	public void addTransExterneEntree(String uri, TransitionExterne transitionExterne) {
		boolean found = false;
		for (Place p : this.places) {
			if(p.getUri() == uri) {
				p.addTransExterneEntree(transitionExterne);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	// PARTIE PLATEAU
	
	@Override
    public void showPlateau() {
        int i = 0;
        System.out.print("(");
        for (Place p : places) {
            if (i != (places.size() - 1)) {
                System.out.print(p.getNbJeton() + ", ");
            } else {
                System.out.print(p.getNbJeton());
            }
            i++;
        }
        System.out.print(")\n");
    }
	
	// Getters
    @Override
    public ArrayList<Place> getPlaces() {
        return places;
    }

    @Override
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public ArrayList<Jeton> getJetons() {
        return jetons;
    }

    // Setters
    @Override
    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public void setJetons(ArrayList<Jeton> jetons) {
        this.jetons = jetons;
    }

    @Override
    public void activateTransition(Transition transition) {
        for (Place place : transition.getPlacesEntrees())
            place.setNbJeton(place.getNbJeton() - 1);
        for (Place place : transition.getPlacesSorties())
            place.setNbJeton(place.getNbJeton() + 1);
    }

    @Override
    public Set<Transition> update() {

        Set<Transition> transitionsPossibles = new HashSet<>();

        for (Place place : places) {

            if (place.getNbJeton() > 0) {
                for (Transition tr_sortie : place.getTransSorties()) {

                    boolean appendTrans = true;
                    for (Place place_entree : tr_sortie.getPlacesEntrees()) {

                        if (place_entree.getNbJeton() == 0) {
                            appendTrans = false;
                            break;
                        }
                    }

                    if (appendTrans)
                        transitionsPossibles.add(tr_sortie);
                }
            }
        }

        return transitionsPossibles;
    }

    @Override
    public void randomTransition(int maxTransitions) {
        Random random = new Random();

        for (int i = 0; i < maxTransitions; i++) {
            Set<Transition> transitionsPossibles = update();
            System.out.print("Transition possible : ");

            for (Transition t : transitionsPossibles)
            	System.out.printf("Transition possible %s:", t.getUri());

            System.out.println();

            if (transitionsPossibles.isEmpty()) {
                System.out.println("Aucune transition possible.");
                break;
            }

            List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);
            Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

            System.out.printf("Transition choisie %s:", transitionChoisie.getUri());

            activateTransition(transitionChoisie);
            showPlateau();
        }
    }

    // Fonction pour afficher les transitions possibles et permettre le choix
    @Override
    public void manualTransition(Scanner scanner) {
        Set<Transition> transitionsPossibles = update();
        if (transitionsPossibles.isEmpty()) {
            System.out.println("Aucune transition possible.");
            return;
        }

        System.out.println("Transitions possibles :");
        List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);

        for (int i = 0; i < listeTransitions.size(); i++)
        	System.out.printf("Transition possible %s:", listeTransitions.get(i).getUri());
        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Transition transitionChoisie = listeTransitions.get(choix - 1);
        activateTransition(transitionChoisie);
        showPlateau();
    }

}
