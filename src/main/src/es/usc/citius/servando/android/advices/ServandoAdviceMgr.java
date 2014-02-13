package es.usc.citius.servando.android.advices;

import java.util.ArrayList;
import java.util.List;

public class ServandoAdviceMgr {

	public interface HomeAdviceListener {
		public void onHomeAdvice(Advice advice);
	}

	private static final ServandoAdviceMgr instance = new ServandoAdviceMgr();

	private List<HomeAdviceListener> listeners;

	private ServandoAdviceMgr()
	{

		listeners = new ArrayList<ServandoAdviceMgr.HomeAdviceListener>();
	}

	public static ServandoAdviceMgr getInstance()
	{
		return instance;
	}

	private Advice homeAdvice = null;

	public Advice getHomeAdvice()
	{
		return homeAdvice;
	}

	public void setHomeAdvice(Advice homeAdvice)
	{
		this.homeAdvice = homeAdvice;
		for (HomeAdviceListener l : listeners)
		{
			l.onHomeAdvice(homeAdvice);
		}
	}

	public void addAdviceListener(HomeAdviceListener l)
	{
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeAdviceListener(HomeAdviceListener l)
	{
		if (listeners.contains(l))
			listeners.remove(l);
	}

}
