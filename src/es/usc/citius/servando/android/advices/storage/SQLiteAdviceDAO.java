package es.usc.citius.servando.android.advices.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.usc.citius.servando.android.advices.Advice;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class SQLiteAdviceDAO {

	ILog logger = ServandoLoggerFactory.getLogger(SQLiteAdviceDAO.class);
	
	private static final String DATA_BASE_NAME = "ADVICES_DB";

	private static final int DATA_BASE_VERSION = 1;

	private static SQLiteAdviceDAO instance;

	private SQLiteAdviceHelper helper;

	private SQLiteDatabase database;

	private SQLiteAdviceDAO(Context context)
	{
		helper = new SQLiteAdviceHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
		database = helper.getWritableDatabase();
	}

	public static SQLiteAdviceDAO getInstance(Context context)
	{

		if (instance == null)
		{
			instance = new SQLiteAdviceDAO(context);
		}
		return instance;
	}

	private void openToRead()
	{
		if (helper != null)
		{
			database = this.helper.getReadableDatabase();
		}
	}

	private void openToWrite()
	{
		if (helper != null)
		{
			database = this.helper.getWritableDatabase();
		}
	}

	private void close()
	{
		if (database != null)
		{
			database.close();
		}
	}

	/**
	 * This method allow the user to restart the database
	 */
	public void restartDataBase()
	{
		if (helper != null)
		{
			helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
		}

	}

	public List<Advice> readAll()
	{
		List<Advice> list = new ArrayList<Advice>();
		if (database == null)
		{
			return list;
		}
		this.openToRead();
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
		this.close();
		return list;
	}

	/**
	 * This method delete all messagges from database
	 */
	public void clearMessagges()
	{
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
	public long insert(Advice advice)
	{
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
	public boolean deleteAdvice(int id)
	{
		if (database == null)
		{
			return false;
		}
		this.openToWrite();
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
		if (database == null)
		{
			return false;
		}
		this.openToWrite();
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
}
