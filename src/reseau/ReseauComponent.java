package reseau;

import fr.sorbonne_u.components.AbstractComponent;
import test.CVM;

public class ReseauComponent<T, I, R, P>
extends AbstractComponent{
	
	public static final String		RESEAU_PLUGIN_URI = "reseau-plugin-uri";

	protected			ReseauComponent(String uri) throws Exception
	{
		super(CVM.RESEAU_COMPONENT_RIBP_URI, 1, 0);

		ReseauPlugin<I, R, P> plugin = new ReseauPlugin<I, R, P>(uri);
		plugin.setPluginURI(RESEAU_PLUGIN_URI);
		this.installPlugin(plugin);

		assert	this.isInstalled(RESEAU_PLUGIN_URI);
		assert	this.getPlugin(RESEAU_PLUGIN_URI) == plugin;
	}

	
}
