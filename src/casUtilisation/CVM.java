package casUtilisation;

import java.util.ArrayList;
import java.util.Arrays;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreComponent;
import reseau.ReseauEndpoint;

public class CVM
extends		AbstractCVM {
	public final static String	RESEAU_COMPONENT_A_RIBP_URI = "reseau-a-ibp-uri";
	public final static String	RESEAU_COMPONENT_B_RIBP_URI = "reseau-b-ibp-uri";
	public final static String	CONVOYEUR_RIBP_URI = "convoyeur-ibp-uri";
	public final static String	FORKLIFT_C_RIBP_URI = "forklift-c-ibp-uri";
	public final static String	FORKLIFT_D_RIBP_URI = "forklift-d-ibp-uri";
	public final static String	PLACE_COMMUNE_COMPONENT_RIBP_URI = "place-commune-ibp-uri";
	
	public static final String		RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
	public static final String		RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri";
	public static final String		CONVOYEUR_PLUGIN_URI = "convoyeur-plugin-uri";
	public static final String		FORKLIFT_C_PLUGIN_URI = "forklift-c-plugin-uri";
	public static final String		FORKLIFT_D_PLUGIN_URI = "forklift-d-plugin-uri";
	
	protected final static String	SEMAPHORE_AVAILABILITY_URI = "semaphore-availability"; // availibility
	protected final static String	SEMAPHORE_JETON_URI = "semaphore-jeton"; // jeton
	protected final static String	SEMAPHORE_UPDATE_URI = "semaphore-update"; // availibility
	
	protected final static String	SEMC_URI = "semaphore-inboundPort"; // reflection inbound port URI

	public				CVM() throws Exception
	{
		super();
	}

	@Override
	public void			deploy() throws Exception
	{
		ReseauPlaceCommuneEndpoint pc_ep = new ReseauPlaceCommuneEndpoint();
		ReseauEndpoint r_epB = new ReseauEndpoint(RESEAU_B_PLUGIN_URI);
		ReseauEndpoint r_epA = new ReseauEndpoint(RESEAU_A_PLUGIN_URI);
		ReseauEndpoint r_epC = new ReseauEndpoint(CONVOYEUR_PLUGIN_URI);
		ReseauEndpoint r_epFC = new ReseauEndpoint(FORKLIFT_C_PLUGIN_URI);
		ReseauEndpoint r_epFD = new ReseauEndpoint(FORKLIFT_D_PLUGIN_URI);
		
		ArrayList<String> semJetonUriList = new ArrayList<String>();
		
		for(int i = 1 ; i < 8 ;i++) {
			String sem = SEMAPHORE_JETON_URI + "-" + i;
			semJetonUriList.add(sem);
		}
		
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMC_URI, // imposed reflection inbound port URI
							 SEMAPHORE_AVAILABILITY_URI,
							 SEMAPHORE_UPDATE_URI,
							 semJetonUriList
							});
		
		AbstractComponent.createComponent(
				ReseauPlaceCommuneComponent.class.getCanonicalName(),
				new Object[]{
						"RPC",
						SEMC_URI,
						SEMAPHORE_AVAILABILITY_URI,
						SEMAPHORE_UPDATE_URI,
						semJetonUriList,
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable()),
						new ArrayList<>(Arrays.asList(
								((ReseauEndpoint) r_epB.copyWithSharable()),
								((ReseauEndpoint) r_epA.copyWithSharable()),
								((ReseauEndpoint) r_epC.copyWithSharable()),
								((ReseauEndpoint) r_epFC.copyWithSharable()),
								((ReseauEndpoint) r_epFD.copyWithSharable())
						))
				});
		
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
		
		super.deploy();
	}

	public static void	main(String[] args)
	{
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(1000L);
			Thread.sleep(1000L);
			System.exit(0);
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}
