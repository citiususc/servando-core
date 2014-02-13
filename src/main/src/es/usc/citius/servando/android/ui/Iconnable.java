package es.usc.citius.servando.android.ui;

import org.simpleframework.xml.Transient;

import es.usc.citius.servando.R;

public class Iconnable {

	// @Transient
	// private ServiceFragmentView view;

	@Transient
	private ActionExecutionViewFactory viewFactory;

	@Transient
	private int iconResourceId;

	/**
	 * Identificador del recurso del icono
	 * 
	 * @return
	 */
	public int getIconResourceId()
	{
		return iconResourceId != 0 ? iconResourceId : R.drawable.icon;
	}

	/**
	 * @param iconResourceId the iconResourceId to set
	 */
	public void setIconResourceId(int iconResourceId)
	{
		this.iconResourceId = iconResourceId;
	}

	/**
	 * Texto del icono
	 * 
	 * @return
	 */
	public String getIconText()
	{
		return "Launcher";
	}

	/**
	 * @return the viewFactory
	 */
	public ActionExecutionViewFactory getViewFactory()
	{
		return viewFactory;
	}

	/**
	 * @param viewFactory the viewFactory to set
	 */
	public void setViewFactory(ActionExecutionViewFactory viewFactory)
	{
		this.viewFactory = viewFactory;
	}

	/**
	 * Vista que se debería mostrar al pulsar el icono, en caso de que la haya
	 * 
	 * @return
	 */
	// public ServiceFragmentView getViewFragment()
	// {
	// return view;
	// }
	//
	// public void setViewFragment(ServiceFragmentView fragment)
	// {
	// view = fragment;
	// }

}
