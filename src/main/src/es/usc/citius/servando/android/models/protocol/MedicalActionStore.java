package es.usc.citius.servando.android.models.protocol;

import java.io.File;
import java.util.Collection;

import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;
import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.settings.StorageModule;
import es.usc.citius.servando.android.xml.helpers.SimpleXMLSerializator;

/**
 * 
 * @author Ángel Piñeiro
 * 
 */
public class MedicalActionStore {

	private static final ILog log = ServandoLoggerFactory.getLogger(MedicalActionStore.class);
	private static final String CATEGORIES_RELATIVE_PATH = "actionCategories.xml";

	/**
	 * Medical actions categories
	 */
	private ActionCategories medicalActionCategories = null;

	/**
	 *
	 */
	public MedicalActionStore()
	{
		medicalActionCategories = loadMedicalActionCategories();
	}

	/**
	 * 
	 * @param services
	 * @throws Exception
	 */
	public void addMedicalActions(Collection<IPlatformService> services) throws Exception
	{
		for (IPlatformService service : services)
		{
			addServiceActions(service);
		}
	}

	/**
	 * 
	 * @param service
	 * @throws Exception
	 */
	public void addServiceActions(IPlatformService service) throws Exception
	{
		for (MedicalAction action : service.getProvidedActions())
		{
			addAction(action);
		}
	}

	/**
	 * 
	 * @param action
	 * @throws Exception
	 */
	public void addAction(MedicalAction action) throws Exception
	{
		if (action.getCategory() != null)
		{
			// Buscamos la categoría
			MedicalActionCategory category = medicalActionCategories.getCategoryWithName(action.getCategory().getName());
			// Si la encontramos, actualizamos la referencia de la actuación médica, por seguridad.
			// Añadimos también la actuación a la categoría.
			if (category != null)
			{
				action.setCategory(category);
				// Si hay otra actuación con el mismo nombre en la misma categoría, lanzamos una excepción
				for (MedicalAction categoryAction : action.getCategory().getActions())
				{
					if (categoryAction.getId().equals(action.getId()))
					{
						Exception ex = new Exception("Hay varias actuaciones con el identificador '" + action.getId() + "' en la categoría '"
								+ action.getCategory().getName() + "'");

						log.error(ex.getMessage());
					}
				}
				// Añadimos la actuación
				action.getCategory().getActions().add(action);
			}
			// En otro caso, agregamos la categoría al árbol de categorías
			else
			{
				// Buscamos en el árbol hasta encontrar el primer elemento que ya esté en nuestra colección, o hasta
				// llegar a la raíz.
				category = action.getCategory();
				while (category.getParentCategory() != null
						&& medicalActionCategories.getCategoryWithName(category.getParentCategory().getName()) == null)
				{
					category = category.getParentCategory();
				}
				// Ahora añadimos la categoría a la colección, ya sea como categoría raíz o como subcategoría de
				// otra categoría.
				medicalActionCategories.insertCategory(category);
				// Añadimos la actuación
				action.getCategory().getActions().add(action);
			}
		}
	}

	/**
	 * Loads categories from file
	 */
	public synchronized ActionCategories loadMedicalActionCategories()
	{
		// if action categories is null
		if (medicalActionCategories == null)
		{
			// create an xml serializator
			SimpleXMLSerializator serializator = new SimpleXMLSerializator();
			// Open categories file
			File categoriesFile = new File(StorageModule.getInstance().getBasePath() + "/" + CATEGORIES_RELATIVE_PATH);
			try
			{
				// load medicalactions categories
				medicalActionCategories = (ActionCategories) serializator.deserialize(categoriesFile, ActionCategories.class);
			} catch (Exception e)
			{
				log.error("An error ocurred while reading categories file.", e);
				medicalActionCategories = new ActionCategories();
			}
		}
		return medicalActionCategories;
	}

	/**
	 * 
	 * @return
	 */
	public ActionCategories getCategories()
	{
		return medicalActionCategories;
	}

	/**
	 * 
	 * @param medicalActionCategories
	 */
	public void setCategories(ActionCategories medicalActionCategories)
	{
		this.medicalActionCategories = medicalActionCategories;
	}
}
