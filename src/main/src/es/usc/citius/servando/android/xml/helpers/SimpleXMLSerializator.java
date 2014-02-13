package es.usc.citius.servando.android.xml.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

public class SimpleXMLSerializator {

	private Strategy strategy;
	private Serializer serializer;

	public SimpleXMLSerializator()
	{
		strategy = new AnnotationStrategy();
		serializer = new Persister(strategy);
	}

	public void serialize(Object obj, String fullpath) throws Exception
	{
		serialize(obj, new File(fullpath));
	}

	public void serialize(Object obj, File file) throws Exception
	{
		serializer.write(obj, file);
	}

	public void serialize(Object obj, String path, String filename) throws Exception
	{
		path += (path.endsWith("/")) ? "" : "/";
		serialize(obj, path + filename);
	}

	public Object deserialize(File file, Class<?> clazz) throws Exception
	{
		FileInputStream fis;
		fis = new FileInputStream(file);
		Reader reader = new InputStreamReader(fis);
		return serializer.read(clazz, reader, false);
	}

	public Object deserializeString(String source, Class<?> clazz) throws Exception
	{
		return serializer.read(clazz, source);
	}

	public Object deserialize(String fullpath, Class<?> clazz) throws Exception
	{
		return deserialize(new File(fullpath), clazz);
	}

	public Serializer getSerializer()
	{
		return serializer;
	}

	public void setSerializer(Serializer serializer)
	{
		this.serializer = serializer;
	}

}
