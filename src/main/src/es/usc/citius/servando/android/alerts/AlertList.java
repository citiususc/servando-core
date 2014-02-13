package es.usc.citius.servando.android.alerts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AlertList implements List<AlertMsg> {

	private List<AlertMsg> alerts;

	public boolean add(AlertMsg object)
	{
		return alerts.add(object);
	}

	public void add(int location, AlertMsg object)
	{
		alerts.add(location, object);
	}

	public boolean addAll(Collection<? extends AlertMsg> arg0)
	{
		return alerts.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends AlertMsg> arg1)
	{
		return alerts.addAll(arg0, arg1);
	}

	public void clear()
	{
		alerts.clear();
	}

	public boolean contains(Object object)
	{
		return alerts.contains(object);
	}

	public boolean containsAll(Collection<?> arg0)
	{
		return alerts.containsAll(arg0);
	}

	public boolean equals(Object object)
	{
		return alerts.equals(object);
	}

	public AlertMsg get(int location)
	{
		return alerts.get(location);
	}

	public int hashCode()
	{
		return alerts.hashCode();
	}

	public int indexOf(Object object)
	{
		return alerts.indexOf(object);
	}

	public boolean isEmpty()
	{
		return alerts.isEmpty();
	}

	public Iterator<AlertMsg> iterator()
	{
		return alerts.iterator();
	}

	public int lastIndexOf(Object object)
	{
		return alerts.lastIndexOf(object);
	}

	public ListIterator<AlertMsg> listIterator()
	{
		return alerts.listIterator();
	}

	public ListIterator<AlertMsg> listIterator(int location)
	{
		return alerts.listIterator(location);
	}

	public AlertMsg remove(int location)
	{
		return alerts.remove(location);
	}

	public boolean remove(Object object)
	{
		return alerts.remove(object);
	}

	public boolean removeAll(Collection<?> arg0)
	{
		return alerts.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0)
	{
		return alerts.retainAll(arg0);
	}

	public AlertMsg set(int location, AlertMsg object)
	{
		return alerts.set(location, object);
	}

	public int size()
	{
		return alerts.size();
	}

	public List<AlertMsg> subList(int start, int end)
	{
		return alerts.subList(start, end);
	}

	public Object[] toArray()
	{
		return alerts.toArray();
	}

	public <T> T[] toArray(T[] array)
	{
		return alerts.toArray(array);
	}

	public AlertList()
	{
		alerts = new ArrayList<AlertMsg>();
	}

	public AlertList(List<AlertMsg> alerts)
	{
		this.alerts = alerts;
	}


}
