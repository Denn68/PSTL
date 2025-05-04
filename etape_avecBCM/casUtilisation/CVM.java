package casUtilisation;

import java.util.ArrayList;
import java.util.Arrays;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreComponent;
import reseau.ReseauEndpoint;

/**
 * Classe CVM (Composant de Vie de la Machine) qui étend la classe AbstractCVM.
 * Elle est responsable du déploiement des composants du système.
 */
public class CVM extends AbstractCVM {
	
	// Définition des URI utilisés pour les différents composants
	public final static String RESEAU_COMPONENT_A_RIBP_URI = "reseau-a-ibp-uri";
	public final static String RESEAU_COMPONENT_B_RIBP_URI = "reseau-b-ibp-uri";
	public final static String CONVOYEUR_RIBP_URI = "convoyeur-ibp-uri";
	public final static String FORKLIFT_C_RIBP_URI = "forklift-c-ibp-uri";
	public final static String FORKLIFT_D_RIBP_URI = "forklift-d-ibp-uri";
	public final static String PLACE_COMMUNE_COMPONENT_RIBP_URI = "place-commune-ibp-uri";
	
	// Définition des URI pour les plugins associés aux composants
	public static final String RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
	public static final String RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri";
	public static final String CONVOYEUR_PLUGIN_URI = "convoyeur-plugin-uri";
	public static final String FORKLIFT_C_PLUGIN_URI = "forklift-c-plugin-uri";
	public static final String FORKLIFT_D_PLUGIN_URI = "forklift-d-plugin-uri";
	
	// URI des sémaphores pour la gestion de l'état de disponibilité, de jetons et des mises à jour
	protected final static String SEMAPHORE_AVAILABILITY_URI = "semaphore-availability"; // Disponibilité
	protected final static String SEMAPHORE_JETON_URI = "semaphore-jeton"; // Jeton
	protected final static String SEMAPHORE_UPDATE_URI = "semaphore-update"; // Mise à jour
	
	// URI de l'inbound port pour la gestion de la réflexion
	protected final static String SEMC_URI = "semaphore-inboundPort"; // URI inbound port de réflexion

	/**
	 * Constructeur de la classe CVM.
	 * Appelle le constructeur de la classe parente AbstractCVM.
	 */
	public CVM() throws Exception {
		super();
	}

	/**
	 * Déploie les composants nécessaires pour le fonctionnement du système.
	 * - Création des composants nécessaires (semaphore, place commune, robots, etc.)
	 * - Liaisons entre les composants via des URI et des inbound ports.
	 */
	@Override
	public void deploy() throws Exception {
		// Création des endpoints pour la gestion de la place commune et des réseaux
		ReseauPlaceCommuneEndpoint pc_ep = new ReseauPlaceCommuneEndpoint();
		ReseauEndpoint r_epB = new ReseauEndpoint(RESEAU_B_PLUGIN_URI);
		ReseauEndpoint r_epA = new ReseauEndpoint(RESEAU_A_PLUGIN_URI);
		ReseauEndpoint r_epC = new ReseauEndpoint(CONVOYEUR_PLUGIN_URI);
		ReseauEndpoint r_epFC = new ReseauEndpoint(FORKLIFT_C_PLUGIN_URI);
		ReseauEndpoint r_epFD = new ReseauEndpoint(FORKLIFT_D_PLUGIN_URI);
		
		// Liste des URI pour les sémaphores de jetons
		ArrayList<String> semJetonUriList = new ArrayList<String>();
		
		// Ajout des URI des sémaphores de jetons (pour chaque jeton 1 à 7)
		for(int i = 1; i < 8; i++) {
			String sem = SEMAPHORE_JETON_URI + "-" + i;
			semJetonUriList.add(sem);
		}
		
		// Création du composant SemaphoreComponent avec les URI correspondants
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMC_URI, // URI inbound port de réflexion
						SEMAPHORE_AVAILABILITY_URI,
						SEMAPHORE_UPDATE_URI,
						semJetonUriList // Liste des URI de sémaphores de jetons
				});
		
		// Création du composant ReseauPlaceCommuneComponent et liaison avec les endpoints et autres composants
		AbstractComponent.createComponent(
				ReseauPlaceCommuneComponent.class.getCanonicalName(),
				new Object[]{
						"RPC", // Identifiant du composant
						SEMC_URI, // URI inbound port de réflexion
						SEMAPHORE_AVAILABILITY_URI,
						SEMAPHORE_UPDATE_URI,
						semJetonUriList,
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable()), // Endpoint partagé
						new ArrayList<>(Arrays.asList(
								((ReseauEndpoint) r_epB.copyWithSharable()),
								((ReseauEndpoint) r_epA.copyWithSharable()),
								((ReseauEndpoint) r_epC.copyWithSharable()),
								((ReseauEndpoint) r_epFC.copyWithSharable()),
								((ReseauEndpoint) r_epFD.copyWithSharable())
						)) // Liste des réseaux partagés
				});
		
		// Création des composants robots (MobileRobotB, MobileRobotA, Conveyor, ForkliftC, ForkliftD)
		// Chaque robot est associé à un RIBP URI et à des endpoints correspondants
		AbstractComponent.createComponent(
				MobileRobotB.class.getCanonicalName(),
				new Object[]{
						"R_B",
						RESEAU_COMPONENT_B_RIBP_URI,
						SEMAPHORE_AVAILABILITY_URI,	
						semJetonUriList,
						((ReseauEndpoint) r_epB.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		AbstractComponent.createComponent(
				MobileRobotA.class.getCanonicalName(),
				new Object[]{
						"R_A",
						RESEAU_COMPONENT_A_RIBP_URI,
						SEMAPHORE_AVAILABILITY_URI,	
						semJetonUriList,
						((ReseauEndpoint) r_epA.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		AbstractComponent.createComponent(
				Conveyor.class.getCanonicalName(),
				new Object[]{
						"R_C",
						CONVOYEUR_RIBP_URI,
						SEMAPHORE_AVAILABILITY_URI,	
						semJetonUriList,
						((ReseauEndpoint) r_epC.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		AbstractComponent.createComponent(
				ForkliftC.class.getCanonicalName(),
				new Object[]{
						"R_FC",
						FORKLIFT_C_RIBP_URI,
						SEMAPHORE_AVAILABILITY_URI,	
						semJetonUriList,
						((ReseauEndpoint) r_epFC.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		AbstractComponent.createComponent(
				ForkliftD.class.getCanonicalName(),
				new Object[]{
						"R_FD",
						FORKLIFT_D_RIBP_URI,
						SEMAPHORE_AVAILABILITY_URI,	
						semJetonUriList,
						((ReseauEndpoint) r_epFD.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		// Appel à la méthode super.deploy() pour finaliser le déploiement
		super.deploy();
	}

	/**
	 * Méthode main pour démarrer la CVM.
	 * Initialise la CVM, démarre le cycle de vie standard et attend un certain délai avant de fermer l'application.
	 */
	public static void main(String[] args) {
		try {
			CVM c = new CVM(); // Crée une instance de CVM
			c.startStandardLifeCycle(1000L); // Démarre le cycle de vie standard
			Thread.sleep(1000L); // Attente de 1 seconde
			System.exit(0); // Ferme l'application
		} catch (Exception e) {
			e.printStackTrace(); // Gestion des exceptions
		}
	}
}
