/**
 *
 */
package es.usc.citius.servando.android.settings;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import es.usc.citius.servando.android.models.services.ServiceReflectionInfo;
import es.usc.citius.servando.android.util.ServandoLocaleUtils;

/**
 * This class is a model of the servando settings.xml configuration file, and contains the platfrom config options.
 * 
 * @author Ángel Piñeiro
 */
@Root(name = "ServandoSettings")
public class ServandoSettings {

	@Transient
	private static final String ROLE_PATIENT = "patient";
	private static final String ROLE_DOCTOR = "doctor";

	@Element(name = "serverUrl", required = false)
	private String serverUrl = "http://servando.idisantiago.es/ServandoViewRicardo/objectTransporterWSDLService";

	@Element(name = "language", required = false)
	private String preferredLang;

	@Element(name = "protocolFile", required = false)
	private String protocolFile = "protocol.xml";

	@Element(name = "finishedActionsFile", required = false)
	private String finishedActionsFile = "finishedActions.xml";

	@Element(name = "uncompletedActionsFile", required = false)
	private String uncompletedActionsFile = "uncompletedActions.xml";

	@Element(name = "patientFile", required = false)
	private String patientFile = "patient.xml";

	@Element(name = "logTime", required = false)
	private int logTime = 60;

	@Element(name = "role", required = false)
	private String role = ROLE_PATIENT;

	/**
	 * Indicates whether to enables the httpServer on the device
	 */
	@Element(name = "httpServerEnabled", required = false)
	private boolean httpServerEnabled;

	/**
	 * Indicates whether to enables the httpServer on the device
	 */
	@Element(name = "communicationsModuleEnabled", required = false)
	private boolean communicationsModuleEnabled = true;
	/**
	 * List of available services
	 */
	@ElementList(type = ServiceReflectionInfo.class, name = "services")
	private List<ServiceReflectionInfo> availableServices = null;

	public ServandoSettings()
	{
		setAvailableServices(new ArrayList<ServiceReflectionInfo>());
	}

	/**
	 * Gets a list with the available services listed on the settings.xml file
	 * 
	 * @return the available services
	 */
	public List<ServiceReflectionInfo> getAvailableServices()
	{
		return availableServices;
	}

	/**
	 * Sets the avilable services list
	 * 
	 * @param availableServices the serives to set
	 */
	public void setAvailableServices(List<ServiceReflectionInfo> availableServices)
	{
		this.availableServices = availableServices;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{
		return ServandoLocaleUtils.getValidLocale(preferredLang);
	}

	/**
	 * @return the preferredLang
	 */
	public String getPreferredLang()
	{
		return preferredLang;
	}

	/**
	 * @param preferredLang the preferredLang to set
	 */
	public void setPreferredLang(String preferredLang)
	{
		this.preferredLang = preferredLang;
	}

	/**
	 * @return the httpServerEnabled
	 */
	public boolean isHttpServerEnabled()
	{
		return httpServerEnabled;
	}

	/**
	 * @param httpServerEnabled the httpServerEnabled to set
	 */
	public void setHttpServerEnabled(boolean httpServerEnabled)
	{
		this.httpServerEnabled = httpServerEnabled;
	}

	public boolean isCommunicationsModuleEnabled()
	{
		return communicationsModuleEnabled;
	}

	public void setCommunicationsModuleEnabled(boolean communicationsModuleEnabled)
	{
		this.communicationsModuleEnabled = communicationsModuleEnabled;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl()
	{
		return serverUrl;
	}

	/**
	 * @param serverUrl the serverUrl to set
	 */
	public void setServerUrl(String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	/**
	 * @return the protocolFile
	 */
	public String getProtocolFile()
	{
		return protocolFile;
	}

	/**
	 * @param protocolFile the protocolFile to set
	 */
	public void setProtocolFile(String protocolFile)
	{
		this.protocolFile = protocolFile;
	}

	/**
	 * @return the finishedActionsFile
	 */
	public String getFinishedActionsFile()
	{
		return finishedActionsFile;
	}

	/**
	 * @param finishedActionsFile the finishedActionsFile to set
	 */
	public void setFinishedActionsFile(String finishedActionsFile)
	{
		this.finishedActionsFile = finishedActionsFile;
	}

	/**
	 * @return the uncompletedActionsFile
	 */
	public String getUncompletedActionsFile()
	{
		return uncompletedActionsFile;
	}

	/**
	 * @param uncompletedActionsFile the uncompletedActionsFile to set
	 */
	public void setUncompletedActionsFile(String uncompletedActionsFile)
	{
		this.uncompletedActionsFile = uncompletedActionsFile;
	}

	/**
	 * @return the logTime
	 */
	public int getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(int logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the patientFile
	 */
	public String getPatientFile()
	{
		return patientFile;
	}

	/**
	 * @param patientFile the patientFile to set
	 */
	public void setPatientFile(String patientFile)
	{
		this.patientFile = patientFile;
	}

	/**
	 * @return the role
	 */
	public String getRole()
	{
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role)
	{
		this.role = role;
	}

	public boolean isPatient()
	{
		return ROLE_PATIENT.equalsIgnoreCase(role);
	}

	public boolean isDoctor()
	{
		return ROLE_DOCTOR.equalsIgnoreCase(role);
	}

}
