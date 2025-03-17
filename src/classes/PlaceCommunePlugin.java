package classes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.PlaceCI;
import interfaces.PlaceI;

public class PlaceCommunePlugin<I, R>
extends 	AbstractPlugin
implements PlaceI<Transition<I, R>>{
	private static final long serialVersionUID = 1L;

	protected PlaceCommuneInboundPortForPlugin<I, R>	pcip;

	private int nbJeton;
    private String uri;
    private ArrayList<Transition<I, R>> transEntrees;
    private ArrayList<Transition<I, R>> transSorties;
    private Semaphore updatingAvailability;
    private Semaphore updatingJetons;

	public				PlaceCommunePlugin(String uri)
	{
		super();
		this.uri = uri;
        this.nbJeton = 0;
	}


	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);

		// Add the interface
		this.addOfferedInterface(PlaceCI.class);
	}

	@Override
	public void			initialise() throws Exception
	{
		super.initialise();

		this.transEntrees = new ArrayList<>();
        this.transSorties = new ArrayList<>();
        this.updatingAvailability = new Semaphore(0);
        this.updatingJetons = new Semaphore(1);

		// Create the inbound port
		this.pcip = new PlaceCommuneInboundPortForPlugin<I, R>(this.getOwner(),
													this.getPluginURI());
		this.pcip.publishPort();
	}


	@Override
	public void			finalise() throws Exception
	{
		this.transEntrees.clear();
		this.transEntrees = null;
		this.transSorties.clear();
		this.transSorties = null;
		super.finalise();
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractPlugin#uninstall()
	 */
	@Override
	public void			uninstall() throws Exception
	{
		this.pcip.unpublishPort();
		this.pcip.destroyPort();					// optional
		this.removeOfferedInterface(PlaceCI.class);
	}


	@Override
	public int getNbJeton() throws Exception {
        return nbJeton;
	}


	@Override
	public String getUri() throws Exception {
        return uri;
	}


	@Override
	public void setNbJeton(int nbJeton) throws Exception {
        this.nbJeton = nbJeton;
		
	}


	@Override
	public ArrayList<Transition<I, R>> getTransEntrees() throws Exception {
        return transEntrees;
	}


	@Override
	public void addTransEntree(Transition<I, R> entree) throws Exception {
        transEntrees.add(entree);
	}


	@Override
	public void addTransSortie(Transition<I, R> sortie) throws Exception {
        transSorties.add(sortie);
	}


	@Override
	public ArrayList<Transition<I, R>> getTransSorties() throws Exception {
        return transSorties;
	}


	@Override
	public void addJeton() throws Exception {
    	System.out.printf("Add dans %s\n", uri);
    	this.nbJeton ++;
	}


	@Override
	public void retrieveJeton() throws Exception {
    	System.out.printf("Retrieve dans %s\n", uri);
    	this.nbJeton--;
	}
}
