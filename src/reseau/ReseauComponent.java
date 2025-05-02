package reseau;

import fr.sorbonne_u.components.AbstractComponent;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

public class ReseauComponent<T, P>
extends AbstractComponent{
	

	protected			ReseauComponent(String uri, String pluginUri, ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super(pluginUri, 1, 0);

		System.out.println(pluginUri);
		ReseauPlugin<P> plugin = new ReseauPlugin<P>(uri, endPointClient);
		plugin.setPluginURI(pluginUri);
		this.installPlugin(plugin);

		assert	this.isInstalled(pluginUri);
		assert	this.getPlugin(pluginUri) == plugin;
	}

}