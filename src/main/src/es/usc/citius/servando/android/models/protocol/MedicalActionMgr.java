package es.usc.citius.servando.android.models.protocol;

import java.util.HashMap;

public class MedicalActionMgr {

	/**
	 * Singleton unique instance
	 */
	private static final MedicalActionMgr instance = new MedicalActionMgr();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private MedicalActionMgr()
	{
		actions = new HashMap<String, MedicalAction>();
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static MedicalActionMgr getInstance()
	{
		return instance;
	}

	private HashMap<String, MedicalAction> actions;

	public void addMedicalAction(MedicalAction a)
	{
		actions.put(a.getId(), a);
	}

	public MedicalAction getMedicalAction(String id)
	{
		return actions.get(id);
	}

	public HashMap<String, MedicalAction> getActions()
	{
		return actions;
	}

}
