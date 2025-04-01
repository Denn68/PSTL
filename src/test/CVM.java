package test;

import java.util.ArrayList;
import java.util.Arrays;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import reseau.ReseauAComponent;
import reseau.ReseauBComponent;
import reseauPlaceCommune.ReseauPlaceCommuneComponent;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import semaphore.SemaphoreComponent;
import reseau.ReseauEndpoint;

public class CVM
extends		AbstractCVM {
	public final static String	RESEAU_COMPONENT_RIBP_URI = "reseau-ibp-uri";
	public final static String	PLACE_COMMUNE_COMPONENT_RIBP_URI = "place-commune-ibp-uri";
	
	protected final static String	SEMAPHORE_AJOUT_A_URI = "semaphore-client-ajout-a";
	protected final static String	SEMAPHORE_RETRAIT_A_URI = "semaphore-client-retrait-a";
	protected final static String	SEMAPHORE_AJOUT_B_URI = "semaphore-client-ajout-b";
	protected final static String	SEMAPHORE_RETRAIT_B_URI = "semaphore-client-retrait-b";

	public				CVM() throws Exception
	{
		super();
	}

	@Override
	public void			deploy() throws Exception
	{
		ReseauPlaceCommuneEndpoint pc_ep = new ReseauPlaceCommuneEndpoint();
		ReseauEndpoint r_epA = new ReseauEndpoint();
		ReseauEndpoint r_epB = new ReseauEndpoint();
		
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMAPHORE_AJOUT_A_URI,	// imposed reflection inbound port URI
							 1			// number of permits of the semaphore
							});
		
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMAPHORE_RETRAIT_A_URI,	// imposed reflection inbound port URI
							 1			// number of permits of the semaphore
							});
		
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMAPHORE_AJOUT_B_URI,	// imposed reflection inbound port URI
							 1			// number of permits of the semaphore
							});
		
		AbstractComponent.createComponent(
				SemaphoreComponent.class.getCanonicalName(),
				new Object[]{SEMAPHORE_RETRAIT_B_URI,	// imposed reflection inbound port URI
							 1			// number of permits of the semaphore
							});
		
		AbstractComponent.createComponent(
				ReseauAComponent.class.getCanonicalName(),
				new Object[]{
						"R_A",
						SEMAPHORE_AJOUT_A_URI,
						SEMAPHORE_RETRAIT_A_URI,
						((ReseauEndpoint) r_epA.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
		AbstractComponent.createComponent(
				ReseauBComponent.class.getCanonicalName(),
				new Object[]{
						"R_B",
						SEMAPHORE_AJOUT_B_URI,
						SEMAPHORE_RETRAIT_B_URI,
						((ReseauEndpoint) r_epB.copyWithSharable()),
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable())
				});
		
	
		AbstractComponent.createComponent(
				ReseauPlaceCommuneComponent.class.getCanonicalName(),
				new Object[]{
						"RPC",
						SEMAPHORE_AJOUT_A_URI,
						SEMAPHORE_RETRAIT_A_URI,
						SEMAPHORE_AJOUT_B_URI,
						SEMAPHORE_RETRAIT_B_URI,
						((ReseauPlaceCommuneEndpoint) pc_ep.copyWithSharable()),
						new ArrayList<>(Arrays.asList(
								((ReseauEndpoint) r_epA.copyWithSharable()),
								((ReseauEndpoint) r_epB.copyWithSharable())))
				});

		super.deploy();
	}

	public static void	main(String[] args)
	{
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(30000L);
			Thread.sleep(1000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
