package es.usc.citius.servando.android.ui;

public class Notification implements Comparable<Notification> {

	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_NORMAL = 2;
	public static final int PRIORITY_HIGHT = 3;

	private String title;
	private String description;
	private int drawableId;
	private String medicalActionId;
	private int priority = PRIORITY_LOW;

	public Notification()
	{
	}

	public Notification(String title)
	{
		setTitle(title);
	}

	public Notification(String actionId, String title)
	{
		setTitle(title);
		setMedicalActionId(actionId);
	}

	public Notification(String actionId, String title, String description)
	{
		this(actionId, title);
		setDescription(description);
	}

	public Notification(String title, String description, int drawableId)
	{
		this(title, description);
		setDrawableId(drawableId);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getDrawableId()
	{
		return drawableId;
	}

	public void setDrawableId(int drawableId)
	{
		this.drawableId = drawableId;
	}

	public String getMedicalActionId()
	{
		return medicalActionId;
	}

	public void setMedicalActionId(String medicalActionId)
	{
		this.medicalActionId = medicalActionId;
	}

	/**
	 * @return the priority
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	@Override
	public int compareTo(Notification another)
	{

		if (this.priority == another.getPriority())
			return 0;

		return (this.priority < another.getPriority()) ? -1 : 1;

	}

}
