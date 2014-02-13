package es.usc.citius.servando.android.advices;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class encapsulate the information of an advice
 * 
 * @author pablojose.viqueira
 * 
 */
public class Advice implements Comparable<Advice> {

	public static final String SERVANDO_SENDER_NAME = "Servando";

	private int id;

	private String sender;

	private String msg;

	private Date date;

	private boolean seen;

	private boolean report;

	public static final String DATE_STRING_FORMAT = "dd-MM-yyyy HH:mm:ss";

	public Advice(String sender, String msg, Date date, boolean seen, boolean report)
	{
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
		this.report = report;
	}

	public Advice(int id, String sender, String msg, Date date, boolean seen, boolean report)
	{
		this.id = id;
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
		this.report = report;
	}

	/**
	 * By default this constructor set the atributte seen and report to false
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
		this.report = false;
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

	/**
	 * This method change the atributte seen for advice, or for subadvices
	 * 
	 * @param seen
	 */
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
		SimpleDateFormat df = new SimpleDateFormat(DATE_STRING_FORMAT);
		toReturn = "[" + this.id + "] [" + this.sender + "] [" + this.msg + "] [" + df.format(this.date) + "] [" + this.seen + "] [" + this.report
				+ "]";
		return toReturn;
	}

	@Override
	public int compareTo(Advice another)
	{
		long thismilis = this.getDate().getTime();
		long anotheremilis = another.getDate().getTime();
		if (thismilis < anotheremilis)
		{
			return -1;
		} else if (thismilis == anotheremilis)
		{
			return 0;

		} else
		{
			return 0;
		}
	}

	public boolean isReport()
	{
		return report;
	}

	public void setReport(boolean report)
	{
		this.report = report;
	}

}
