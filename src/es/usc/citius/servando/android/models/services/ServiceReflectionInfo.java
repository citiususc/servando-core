package es.usc.citius.servando.android.models.services;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Groups information needed to load new services at runtime from assemblies.
 * 
 * @author angel
 * 
 */
@Root(name = "serviceReflectionInfo")
public class ServiceReflectionInfo {
	public static final String APK = "apk";
	public static final String JAR = "jar";
	public static final String COMPILED = "compiled";
	public static final String LOCAL = "local";
	public static final String REMOTE = "local";

	public ServiceReflectionInfo()
	{
		neededAssemblys = new ArrayList<String>();
	}

	/**
	 * The service ID
	 */
	@Element(name = "serviceId")
	public String serviceID;

	/**
	 * The class name of the class that implements the service.
	 */
	@Element(name = "serviceClassName")
	public String serviceClassName;

	/**
	 * The service package name
	 */
	@Element(name = "servicePackageName")
	public String servicePackageName;

	/**
	 * The service assembly name, that is, the name of the .apk or .jar that contains the service
	 */
	@Element(name = "serviceAssemblyName")
	public String serviceAssemblyName;

	/**
	 * Type of the assembly that contains the service. Possible values are 'apk', 'jar' or 'compiled'
	 */
	@Element(name = "serviceAssemblyType")
	public String serviceAssemblyType;

	/**
	 * Origin of the service. Possible values are 'local' or 'remote'
	 */
	@Element(name = "serviceAssemblyOrigin")
	public String serviceAssemblyOrigin;

	@ElementList(type = String.class, name = "neededAssemblys")
	public List<String> neededAssemblys;

	public String getCannonicalClassName()
	{
		return servicePackageName.concat(".").concat(serviceClassName);
	}

	public String getFullAssemblyName()
	{
		return serviceAssemblyName.concat(".").concat(serviceAssemblyType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ServiceReflectionInfo [serviceID=" + serviceID + ", serviceClassName=" + serviceClassName + ", servicePackageName="
				+ servicePackageName + ", serviceAssemblyName=" + serviceAssemblyName + ", serviceAssemblyType=" + serviceAssemblyType + "]";
	}

}
