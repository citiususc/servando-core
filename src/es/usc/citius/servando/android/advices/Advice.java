package es.usc.citius.servando.android.advices;


import java.util.Date;

/**
 * This class encapsulate the information of an advice
 * 
 * @author pablojose.viqueira
 * 
 */
public class Advice {

	private int id;

	private String sender;

	private String msg;

	private Date date;

	private boolean seen;

	public Advice(String sender, String msg, Date date, boolean seen)
	{

		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
	}

	public Advice(int id, String sender, String msg, Date date, boolean seen)
	{
		this.id = id;
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
	}

	/**
	 * By default this constructor set the atributte seen to 'false'
	 * 
	 * @param sender
	 * @param msg
	 */
	public Advice(String sender, String msg, Date date)
	{
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = false;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public boolean isSeen()
	{
		return seen;
	}

	public void setSeen(boolean seen)
	{
		this.seen = seen;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		String toReturn;
		toReturn = "[" + this.id + "] [" + this.sender + "] [" + this.msg + "] [" + this.date.toString() + "] [" + this.seen + "]";
		return toReturn;
	}

}
