/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.usc.citius.servando.android.models.protocol;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Transient;

import es.usc.citius.servando.android.models.services.IPlatformService;
import es.usc.citius.servando.android.ui.Iconnable;

/**
 * Clase que representa una determinada actuación médica susceptible de ser ejecutada. La actuación médica tendrá un
 * nombre, una categoría, un servicio proveedor, y un conjunto de recursos necesarios. Esta clase será susceptible de
 * ser serializada en un documento XML, pero dicho documento sólo incluirá información relativa al identificador de la
 * actuación. El resto de atributos son demasiado complejos para ser serializados de modo eficiente, por lo que la
 * instancia completa de la actuación médica se deberá obtener a partir del identificador obtenido. La fachada de la
 * plataforma ya proporciona un método para obtener dicha instancia.
 * 
 * @author tomas
 */
@Default(DefaultType.FIELD)
@Order(elements = { "id", "displayname", "description" })
public class MedicalAction extends Iconnable {

	/**
	 * Identificador único de la actuación médica, que contendrá su nombre.
	 */
	@Element(name = "id")
	private String id;
	/**
	 * Breve descripción de la actuación médica, que será mostrada al paciente.
	 */
	@Element(name = "description")
	private String description;
	/**
	 * Nombre de la actuación médica que se mostrará al usuario. Si la actuación médica se crea con el constructor que
	 * recibe el identificador como argumento, esta propiedad tendrá el mismo valor que la propiedad {@link Id}, aunque
	 * podrá ser modificado posteriormente.
	 */
	@Element(name = "displayname")
	private String displayName;
	@Transient
	@Deprecated
	private MedicalActionCategory category;

	/**
	 * Servicio que provee esta actuación médica. Los servicios se serializarán como su String identificador.
	 */
	@Transient
	private IPlatformService provider;

	public MedicalAction()
	{
	}

	public MedicalAction(String id)
	{
		this();
		this.id = id;
		this.displayName = id;
	}

	/**
	 * @return the Id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param Id the Id to set
	 */
	public void setId(String Id)
	{
		this.id = Id;
	}

	/**
	 * @return the Category
	 */
	@Deprecated
	public MedicalActionCategory getCategory()
	{
		return category;
	}

	/**
	 * @param Category the Category to set
	 */
	@Deprecated
	public void setCategory(MedicalActionCategory Category)
	{
		this.category = Category;
		if (!category.getActions().contains(this))
		{
			category.getActions().add(this);
		}
	}

	/**
	 * @return the Provider
	 */
	public IPlatformService getProvider()
	{
		return provider;
	}

	/**
	 * @param Provider the Provider to set
	 */
	public void setProvider(IPlatformService Provider)
	{
		this.provider = Provider;
	}

	/**
	 * Obtiene el nombre de la actuación normalizado, en minúsculas, sin tildes ni espacios en blanco. Útil para, por
	 * ejemplo, crear archivos o directorios con el nombre de la actuación.
	 * 
	 * @return
	 */
	public String getSafeName()
	{

		return getId().toLowerCase().replace('á', 'á').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u').replace(' ', '_');

	}

	/**
	 * Breve descripción de la actuación médica, que será mostrada al paciente.
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Breve descripción de la actuación médica, que será mostrada al paciente.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Nombre de la actuación médica que se mostrará al usuario. Si la actuación médica se crea con el constructor que
	 * recibe el identificador como argumento, esta propiedad tendrá el mismo valor que la propiedad {@link Id}, aunque
	 * podrá ser modificado posteriormente.
	 * 
	 * @return the displayName
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Nombre de la actuación médica que se mostrará al usuario. Si la actuación médica se crea con el constructor que
	 * recibe el identificador como argumento, esta propiedad tendrá el mismo valor que la propiedad {@link Id}, aunque
	 * podrá ser modificado posteriormente.
	 * 
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
}
