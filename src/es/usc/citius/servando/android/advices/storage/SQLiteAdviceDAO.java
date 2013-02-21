package es.usc.citius.servando.android.advices.storage;

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

	ILog logger = ServandoLoggerFactory.getLogger(SQLiteAdviceDAO.class);
	private static final String DATA_BASE_NAME = "ADVICES_DB";
	private static final int DATA_BASE_VERSION = 1;
	private static SQLiteAdviceDAO instance;
	private SQLiteAdviceHelper helper;
	private SQLiteDatabase database;
	private boolean initialized = false;

	private SQLiteAdviceDAO()
	{

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
			logger.debug(this.getClass().getSimpleName() + " initilized.");
			helper = new SQLiteAdviceHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
			database = helper.getWritableDatabase();
			initialized = true;
		}

	}

	/**
	 * Open database to read
	 */
	private void openToRead()
	{
		if (helper != null && initialized)
		{
			database = this.helper.getReadableDatabase();
		}
	}

	/**
	 * Open database to write
	 */
	private void openToWrite()
	{
		if (helper != null && initialized)
		{
			database = this.helper.getWritableDatabase();
		}
	}

	/**
	 * Close database connection
	 */
	private void close()
	{
		if (database != null)
		{
			database.close();
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
	 * This method get all messagges in database
	 * 
	 * @return
	 */
	public List<Advice> getAll()
	{
		List<Advice> list = new ArrayList<Advice>();
		this.openToRead();
		if (database == null || !initialized)
		{
			return list;
		}
		String sql = "select * from " + SQLiteAdviceHelper.ADVICES_TABLE_NAME;
		Cursor cursor = database.rawQuery(sql, new String[] {});
		logger.debug(sql);
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while (!cursor.isAfterLast())
			{
				int id = cursor.getInt(SQLiteAdviceHelper.KEY_COLUMN_INDEX);
				String sender = cursor.getString(SQLiteAdviceHelper.SENDER_COLUMN_INDEX);
				String msg = cursor.getString(SQLiteAdviceHelper.MESSAGGE_COLUMN_INDEX);
				String dateString = cursor.getString(SQLiteAdviceHelper.DATE_COLUMN_INDEX);
				Date date = new Date(dateString);
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
		cursor.close();
		this.close();
		return list;
	}

	/**
	 * This method delete all messagges from database
	 */
	public void removeAll()
	{
		if (!initialized)
		{
			return;
		}
		this.openToWrite();
		if (this.database != null)
		{
			String sql = "DELETE FROM " + SQLiteAdviceHelper.ADVICES_TABLE_NAME;
			logger.debug(sql);
			database.execSQL(sql);
		}
		this.close();
	}

	/**
	 * This method inserts a new advice in the database
	 * 
	 * @param advice
	 * @return The rowid for the advice, or -1 if there are some error
	 */
	public long add(Advice advice)
	{
		if (!initialized)
		{
			return -1;
		}
		this.openToWrite();
		ContentValues cv = new ContentValues();
		cv.put(SQLiteAdviceHelper.SENDER_COLUMN, advice.getSender());
		cv.put(SQLiteAdviceHelper.MESSAGGE_COLUMN, advice.getMsg());
		cv.put(SQLiteAdviceHelper.DATE_COLUMN, advice.getDate().toString());
		if (advice.isSeen())
		{
			cv.put(SQLiteAdviceHelper.SEEN_COLUMN, 1);
		} else
		{
			cv.put(SQLiteAdviceHelper.SEEN_COLUMN, 0);
		}
		long result = database.insert(SQLiteAdviceHelper.ADVICES_TABLE_NAME, null, cv);
		if (result != -1)
		{
			advice.setId((int) result);
			logger.debug("Se ha insertado: " + advice.toString());

		} else
		{
			logger.debug("No se ha podido insertar: " + advice.toString());
		}
		this.close();
		return result;
	}

	/**
	 * This method delete de advice with this id
	 * 
	 * @param id
	 * @return Return false if some there are some problem, true in other case
	 */
	public boolean remove(int id)
	{

		this.openToWrite();
		if (database == null || !initialized)
		{
			return false;
		}
		String sql = "DELETE FROM " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " WHERE " + SQLiteAdviceHelper.KEY_COLUMN + " = " + id;
		try
		{
			logger.debug(sql);
			this.database.execSQL(sql);
		} catch (SQLiteException ex)
		{
			logger.debug("It was not possible delete the advice with id: " + id);
			this.close();
			return false;
		}
		this.close();
		return true;
	}

	/**
	 * This method allow mark an advice as seen
	 * 
	 * @param id
	 * @return
	 */
	public boolean markAsSeen(int id)
	{

		this.openToWrite();
		if (database == null || !initialized)
		{
			return false;
		}
		try
		{
			String sql = "UPDATE " + SQLiteAdviceHelper.ADVICES_TABLE_NAME + " SET " + SQLiteAdviceHelper.SEEN_COLUMN + " = 1 " + " WHERE id = " + id;
			this.database.execSQL(sql);
			logger.debug(sql);
		} catch (SQLiteException ex)
		{
			logger.debug("It was not possible set as seen advice with id: " + id);
			this.close();
			return false;
		}
		this.close();
		return true;
	}

	/**
	 * This method return the number of advices that was not seen
	 * 
	 * @return -1 if error
	 */
	public int getNotSeenCount()
	{
		int result = -1;
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
					Cursor cursor = this.database.rawQuery(sql, new String[] {});
					if (cursor.moveToFirst())
					{
						result = cursor.getInt(0);
					}
					cursor.close();

				}
			} catch (SQLiteException ex)
			{
				ex.printStackTrace();
				logger.debug("SQLiteException: " + ex.getMessage());
			} finally
			{
				this.close();
			}

		}
		return result;
	}
}
