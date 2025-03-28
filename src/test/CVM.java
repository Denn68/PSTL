package test;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import placeCommune.PlaceCommuneComponent;
import reseau.ReseauComponent;

public class CVM
extends		AbstractCVM {
	public final static String	RESEAU_COMPONENT_RIBP_URI = "reseau-ibp-uri";
	public final static String	PLACE_COMMUNE_COMPONENT_RIBP_URI = "place-commune-ibp-uri";

	public				CVM() throws Exception
	{
		super();
	}

	@Override
	public void			deploy() throws Exception
	{
		String uri = AbstractComponent.createComponent(
							ReseauComponent.class.getCanonicalName(),
							new Object[]{});
		
		assert	this.isDeployedComponent(uri);
		
		uri = AbstractComponent.createComponent(
							PlaceCommuneComponent.class.getCanonicalName(),
							new Object[]{});
		
		assert	this.isDeployedComponent(uri);

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
			throw new RuntimeException(e);
		}
	}
}
