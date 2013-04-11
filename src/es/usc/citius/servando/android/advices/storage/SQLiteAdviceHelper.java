package es.usc.citius.servando.android.advices.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import es.usc.citius.servando.android.logging.ILog;
import es.usc.citius.servando.android.logging.ServandoLoggerFactory;

public class SQLiteAdviceHelper extends SQLiteOpenHelper {

	ILog logger = ServandoLoggerFactory.getLogger(SQLiteAdviceHelper.class);

	public static final String ADVICES_TABLE_NAME = "ADVICE";
	public static final String KEY_COLUMN = "id";
	public static final int KEY_COLUMN_INDEX = 0;
	public static final String SENDER_COLUMN = "sender";
	public static final int SENDER_COLUMN_INDEX = 1;
	public static final String MESSAGGE_COLUMN = "messagge";
	public static final int MESSAGGE_COLUMN_INDEX = 2;
	public static final String DATE_COLUMN = "date";
	public static final int DATE_COLUMN_INDEX = 3;
	public static final String DATE_FORMAT = "yyyy-MM-dd kk:mm:ss";
	public static final String SEEN_COLUMN = "seen";
	public static final int SEEN_COLUMN_INDEX = 4;
	public static final String DAILY_REPORT_COLUMN = "report";
	public static final int DAILY_REPORT_COLUMN_INDEX = 5;

	private static final String CREATE_DATA_BASE_SCRIPT = "CREATE TABLE " + ADVICES_TABLE_NAME + "(" + KEY_COLUMN
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + SENDER_COLUMN + " varchar(50), " + MESSAGGE_COLUMN + " varchar(300), " + DATE_COLUMN
			+ " varchar(50)," + SEEN_COLUMN + " INTEGER," + DAILY_REPORT_COLUMN + " INTEGER)";

	private static final String DELETE_DATABASE_SCRIPT = "DROP TABLE " + ADVICES_TABLE_NAME;

	public SQLiteAdviceHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		logger.debug(CREATE_DATA_BASE_SCRIPT);
		db.execSQL(CREATE_DATA_BASE_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
	{
		logger.debug(DELETE_DATABASE_SCRIPT);
		db.execSQL(DELETE_DATABASE_SCRIPT);
		this.onCreate(db);
	}

}
