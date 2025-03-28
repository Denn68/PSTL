package placeCommune;

import fr.sorbonne_u.components.AbstractComponent;
import test.CVM;

public class PlaceCommuneComponent<I, R>
extends AbstractComponent{
	
	public static final String		PLACE_COMMUNE_PLUGIN_URI = "place-commune-plugin-uri";

	protected			PlaceCommuneComponent(String uri) throws Exception
	{
		super(CVM.PLACE_COMMUNE_COMPONENT_RIBP_URI, 1, 0);

		PlaceCommunePlugin<I, R> plugin = new PlaceCommunePlugin<I, R>(uri);
		plugin.setPluginURI(PLACE_COMMUNE_PLUGIN_URI);
		this.installPlugin(plugin);

		assert	this.isInstalled(PLACE_COMMUNE_PLUGIN_URI);
		assert	this.getPlugin(PLACE_COMMUNE_PLUGIN_URI) == plugin;
	}
}
