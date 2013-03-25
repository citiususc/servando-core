package es.usc.citius.servando.android.advices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class encapsulate the information of an advice
 * 
 * @author pablojose.viqueira
 * 
 */
public class Advice implements Comparable<Advice> {

	public static final String SERVANDO_SENDER_NAME = "system";

	private int id;

	private String sender;

	private String msg;

	private Date date;

	private boolean seen;

	public static final String DATE_STRING_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/**
	 * This attributte is only used to group advices by sender, but in the SQLite database this component doesnt exist
	 */
	private List<Advice> subAdvices;

	public Advice(String sender, String msg, Date date, boolean seen)
	{
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
		subAdvices = new ArrayList<Advice>();
	}

	public Advice(int id, String sender, String msg, Date date, boolean seen)
	{
		this.id = id;
		this.sender = sender;
		this.msg = msg;
		this.date = date;
		this.seen = seen;
		subAdvices = new ArrayList<Advice>();
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
		subAdvices = new ArrayList<Advice>();
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
		if (subAdvices.size() > 0)
		{
			for (Advice adv : subAdvices)
			{
				if (!adv.isSeen())
				{
					return false;
				}
			}
			return true;
		} else
		{
			return seen;
		}
	}

	/**
	 * This method change the atributte seen for advice, or for subadvices
	 * 
	 * @param seen
	 */
	public void setSeen(boolean seen)
	{
		if (subAdvices.size() > 0)
		{
			for (Advice adv : subAdvices)
			{
				adv.setSeen(seen);
			}
		} else
		{
			this.seen = seen;
		}
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
		toReturn = "[" + this.id + "] [" + this.sender + "] [" + this.msg + "] [" + df.format(this.date) + "] [" + this.seen + "]";
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

	public void addSubAdvice(Advice advice)
	{
		this.subAdvices.add(advice);
	}

	public List<Advice> getSubAdvices()
	{
		return this.subAdvices;
	}

	/**
	 * This method return true if the advices is composed by subadvices, false if not.
	 * 
	 * @return
	 */
	public boolean isReport()
	{
		if (this.subAdvices.size() > 0)
		{
			return true;
		}
		return false;

	}

}
