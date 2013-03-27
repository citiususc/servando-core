package es.usc.citius.servando.android.advices.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import es.usc.citius.servando.android.advices.Advice;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class SQLiteAdviceDAO {

	public interface AdviceDAOListener {
		public void onAdviceAdded(Advice advice);

		public void onAdviceSeen(Advice advice);
	}

	private static ILog logger = ServandoLoggerFactory.getLogger(SQLiteAdviceDAO.class);
	private static final String DATA_BASE_NAME = "ADVICES_DB";
	private static final int DATA_BASE_VERSION = 2;
	private static SQLiteAdviceDAO instance;
	private SQLiteAdviceHelper helper;
	private SQLiteDatabase database;
	private boolean initialized = false;
	private List<AdviceDAOListener> listeners;

	private SQLiteAdviceDAO()
	{
		listeners = new ArrayList<SQLiteAdviceDAO.AdviceDAOListener>();

	}

	public static SQLiteAdviceDAO getInstance()
	{

		if (instance == null)
		{
			instance = new SQLiteAdviceDAO();
		}
		return instance;
	}

	/**
	 * This method initialize the instance.
	 * 
	 * @param context The application context
	 */
	public void initialize(Context context)
	{
		if (!initialized)
		{
			logger.debug(this.getClass().getSimpleName() + " initialized.");
			helper = new SQLiteAdviceHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
			// Revisar esta chamada
			database = helper.getWritableDatabase();
			initialized = true;
			// restartDataBase();
		}

	}

	/**
	 * Open database to read
	 */
	private void openToRead()
	{
		try
		{
			if (helper != null && initialized)
			{
				database = this.helper.getReadableDatabase();
			}
		} catch (SQLiteException sqle)
		{
			logger.error("Error openning SQLite database to read.", sqle);
		}
	}

	/**
	 * Open database to write
	 */
	private void openToWrite()
	{
		try
		{
			if (helper != null && initialized)
			{
				database = this.helper.getWritableDatabase();
			}
		} catch (SQLiteException sqle)
		{
			logger.error("Error openning SQLite database to write.", sqle);
		}
	}

	/**
	 * Close database connection
	 */
	private void close()
	{
		try
		{
			if (database != null)
			{
				if (database.isOpen())
				{
					database.close();
				}
			}
		} catch (SQLiteException sqle)
		{
			logger.error("Error closing SQLite database.", sqle);
		}
	}

	/**
	 * This method DROP the tables, and then CREATE them again
	 */
	public void restartDataBase()
	{
		if (helper != null && initialized)
		{
			helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
		}

	}

	/**
	 * This method do a read-query to SQLite database.
	 * 
	 * @param sql
	 * @return
	 */
	private Cursor doQuery(String sql)
	{
		Cursor cursor = null;

		if (initialized && database != null && database.isOpen())
		{
			logger.debug(sql);
			cursor = database.rawQuery(sql, new String[] {});

		}
		return cursor;
	}

	/**
	 * This method allow to execute sql querys wich will modify database content, like delete or update.
	 * 
	 * @param sql
	 */
	private void executeQuery(String sql)
	{
		if (initialized && database != null && database.isOpen())
		{
			logger.debug(sql);
			database.execSQL(sql);
		}
	}

	/**
	 * This method allow insert an advice in the sqlite database
	 * 
	 * @param cv
	 * @return
	 */
	private long insert(ContentValues cv)
	{
		return database.insert(SQLiteAdviceHelper.ADVICES_TABLE_NAME, null, cv);
	}

	/**
	 * This method get all messagges in database
	 * 
	 * @return
	 */
	public synchronized List<Advice> getAll()
	{
		List<Advice> list = new ArrayList<Advice>();
		this.openToRead();
		Cursor cursor = null;
		try
		{
			String sql = "select * from " + SQLiteAdviceHelper.ADVICES_TABLE_NAME;
			cursor = doQuery(sql);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (!cursor.isAfterLast())
				{
					int id = cursor.getInt(SQLiteAdviceHelper.KEY_COLUMN_INDEX);
					String sender = cursor.getString(SQLiteAdviceHelper.SENDER_COLUMN_INDEX);
					String msg = cursor.getString(SQLiteAdviceHelper.MESSAGGE_COLUMN_INDEX);
					String dateString = cursor.getString(SQLiteAdviceHelper.DATE_COLUMN_INDEX);
					Date date = this.dateFromString(dateString);
					boolean seen = false;
					if (cursor.getInt(SQLiteAdviceHelper.SEEN_COLUMN_INDEX) == 1)
					{
						seen = true;
					}
					Advice advice = new Advice(id, sender, msg, date, seen);
					list.add(advice);
					cursor.moveToNext();
				}
			}
		} catch (Exception e)
		{
			logger.error("Error getting not seen advices.", e);

		} finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
			this.close();
		}

		return list;
	}

	/**
	 * This method return all advices that at this moment is marked as not seen
	 * 
	 * @return
	 */
	public synchronized List<Advice> getNotSeen()
	{
		List<Advice> list = new ArrayList<Advice>();
		this.openToRead();
		String sql = "select * from " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " WHERE " + SQLiteAdviceHelper.SEEN_COLUMN + " = 0";
		Cursor cursor = null;
		try
		{
			cursor = doQuery(sql);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (!cursor.isAfterLast())
				{
					int id = cursor.getInt(SQLiteAdviceHelper.KEY_COLUMN_INDEX);
					String sender = cursor.getString(SQLiteAdviceHelper.SENDER_COLUMN_INDEX);
					String msg = cursor.getString(SQLiteAdviceHelper.MESSAGGE_COLUMN_INDEX);
					String dateString = cursor.getString(SQLiteAdviceHelper.DATE_COLUMN_INDEX);
					Date date = this.dateFromString(dateString);
					boolean seen = false;
					if (cursor.getInt(SQLiteAdviceHelper.SEEN_COLUMN_INDEX) == 1)
					{
						seen = true;
					}
					Advice advice = new Advice(id, sender, msg, date, seen);
					list.add(advice);
					cursor.moveToNext();
				}
			}
		} catch (Exception e)
		{
			logger.error("Error getting not seen advices.", e);
		} finally
		{
			if (cursor != null && !cursor.isClosed())
			{
				cursor.close();
			}
			this.close();
		}
		return list;
	}

	/**
	 * This method delete all messagges from database
	 */
	public synchronized void removeAll()
	{
		this.openToWrite();
		try
		{
			if (this.database != null)
			{
				String sql = "DELETE FROM " + SQLiteAdviceHelper.ADVICES_TABLE_NAME;
				executeQuery(sql);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Error removing all advices.", e);
		} finally
		{
			this.close();
		}

	}

	/**
	 * This method inserts a new advice in the database
	 * 
	 * @param advice
	 * @return The rowid for the advice, or -1 if there are some error
	 */
	public synchronized long add(Advice advice)
	{
		long result = -1;
		this.openToWrite();
		try
		{
			ContentValues cv = new ContentValues();
			cv.put(SQLiteAdviceHelper.SENDER_COLUMN, advice.getSender());
			cv.put(SQLiteAdviceHelper.MESSAGGE_COLUMN, advice.getMsg());
			cv.put(SQLiteAdviceHelper.DATE_COLUMN, this.dateToString(advice.getDate()));
			if (advice.isSeen())
			{
				cv.put(SQLiteAdviceHelper.SEEN_COLUMN, 1);
			} else
			{
				cv.put(SQLiteAdviceHelper.SEEN_COLUMN, 0);
			}
			result = insert(cv);
		} catch (Exception e)
		{
			logger.error("Error adding advice in SQLite database", e);
			result = -1;
		} finally
		{
			if (result != -1)
			{
				advice.setId((int) result);
				logger.debug("Se ha insertado: " + advice.toString());
				fireOnAdviceAdded(advice);

			} else
			{
				logger.debug("No se ha podido insertar: " + advice.toString());
			}
			this.close();

		}
		return result;
	}

	/**
	 * This method delete de advice with this id
	 * 
	 * @param id
	 * @return Return false if some there are some problem, true in other case
	 */
	public synchronized boolean remove(int id)
	{
		boolean result = true;
		this.openToWrite();
		String sql = "DELETE FROM " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " WHERE " + SQLiteAdviceHelper.KEY_COLUMN + " = " + id;
		try
		{
			logger.debug(sql);
			executeQuery(sql);
		} catch (SQLiteException ex)
		{
			logger.error("It was not possible delete the advice with id: " + id, ex);
			result = false;
		} finally
		{
			this.close();
		}
		return result;
	}

	/**
	 * This method allow mark an advice as seen
	 * 
	 * @param id
	 * @return
	 */
	private boolean markAsSeen(int id)
	{
		boolean result = true;
		this.openToWrite();
		try
		{
			String sql = "UPDATE " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " SET " + SQLiteAdviceHelper.SEEN_COLUMN + " = 1 " + " WHERE id = " + id;
			executeQuery(sql);
		} catch (SQLiteException ex)
		{
			logger.error("It was not possible set as seen advice with id: " + id, ex);
			result = false;
		} finally
		{
			this.close();
		}
		return result;
	}

	/**
	 * This method mark the advice, or the subadvices as seen, but only in database, the responsable to change the
	 * object value is the user
	 * 
	 * @param adv
	 */
	public synchronized void markAsSeen(Advice adv)
	{
		if (adv.getSubAdvices().size() > 0)
		{
			for (Advice advice : adv.getSubAdvices())
			{
				this.markAsSeen(advice.getId());
			}
		} else
		{
			this.markAsSeen(adv.getId());
		}
		fireOnAdviceSeen(adv);

	}

	/**
	 * This method return the number of advices that was not seen
	 * 
	 * @return -1 if error
	 */
	public synchronized int getNotSeenCount()
	{
		int result = -1;
		Cursor cursor = null;
		if (initialized)
		{
			try
			{
				this.openToRead();
				if (this.database != null)
				{
					String sql = "SELECT COUNT(*) AS result FROM " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " WHERE "
							+ SQLiteAdviceHelper.SEEN_COLUMN + " = 0";
					logger.debug(sql);
					cursor = this.database.rawQuery(sql, new String[] {});
					if (cursor != null)
					{
						if (cursor.moveToFirst())
						{
							result = cursor.getInt(0);
						}
						cursor.close();
					}

				}
			} catch (SQLiteException ex)
			{
				logger.error("Error getting not seen count.", ex);
			} finally
			{
				if (cursor != null)
				{
					cursor.close();
				}
				this.close();
			}

		}
		return result;
	}

	public void fireOnAdviceAdded(Advice a)
	{
		for (AdviceDAOListener l : listeners)
		{
			l.onAdviceAdded(a);
		}
	}

	public void fireOnAdviceSeen(Advice a)
	{
		for (AdviceDAOListener l : listeners)
		{
			l.onAdviceSeen(a);
		}
	}

	public void addAdviceListener(AdviceDAOListener l)
	{
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeAdviceListener(AdviceDAOListener l)
	{
		if (listeners.contains(l))
			listeners.remove(l);
	}

	private String dateToString(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat(Advice.DATE_STRING_FORMAT);
		String dateString = df.format(date);
		return dateString;
	}

	private Date dateFromString(String string) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(Advice.DATE_STRING_FORMAT);
		return df.parse(string);
	}
}
