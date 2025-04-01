package reseau;

import java.util.ArrayList;
import java.util.Arrays;

import classes.PlaceCommune;
import classes.URIGenerator;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import test.CVM;

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauPlaceCommuneCI.class })
public class ReseauComponent<T, P>
extends AbstractComponent{
	
	public static final String		RESEAU_PLUGIN_URI = "reseau-plugin-uri";
	private String pluginURI; 

	protected			ReseauComponent(String uri) throws Exception
	{
		super(CVM.RESEAU_COMPONENT_RIBP_URI, 1, 0);

		ReseauPlugin<P> plugin = new ReseauPlugin<P>(uri);
		plugin.setPluginURI(RESEAU_PLUGIN_URI);
		this.installPlugin(plugin);

		assert	this.isInstalled(RESEAU_PLUGIN_URI);
		assert	this.getPlugin(RESEAU_PLUGIN_URI) == plugin;
	}
	
	protected			ReseauComponent(String uri, String pluginURI,
			String semaphorePluginAjoutInboundPortURI,
			String semaphorePluginRetraitInboundPortURI,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super(CVM.RESEAU_COMPONENT_RIBP_URI, 1, 0);
		
		endPointServer.initialiseServerSide(this);
		this.endPointServer = endPointServer;
		this.endPointClient = endPointClient;

		ReseauPlugin<P> plugin = new ReseauPlugin<P>(uri,
				semaphorePluginAjoutInboundPortURI,
				semaphorePluginRetraitInboundPortURI,
				endPointClient);
		plugin.setPluginURI(pluginURI);
		this.installPlugin(plugin);

		assert	this.isInstalled(pluginURI);
		assert	this.getPlugin(pluginURI) == plugin;
		
		this.pluginURI = pluginURI;
	}
	
	private ReseauPlaceCommuneEndpoint endPointClient;
	private ReseauEndpoint endPointServer;
	
	@Override
	public void start() {
		try {
			super.start();
		} catch (ComponentStartException e) {
			e.printStackTrace();
		}
		if(!endPointClient.clientSideInitialised()) {
			try {
				endPointClient.initialiseClientSide(this);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void execute() throws Exception {
		this.runTask(new AbstractComponent.AbstractTask() {
			
			@SuppressWarnings("unchecked")
			@Override
		    public void run() {
		    	System.out.println("------- DEBUT -------");
		    	try {
		    		((ReseauPlugin<P>) ((ReseauComponent<T, P>) this.getTaskOwner()).getPlugin(pluginURI)).showReseau();
					((ReseauPlugin<P>) ((ReseauComponent<T, P>) this.getTaskOwner()).getPlugin(pluginURI)).randomTransition();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }

		});
	}

	
}
