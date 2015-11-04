package bosscorp.meteboss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperQuiPese extends SQLiteOpenHelper
{
	private String[] allColumns = { ID, NAME, COUNTRY, TEMPERATURE, WIND, PRESSURE, LAST_FETCH };

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "meteoManager";

	public static final String CITIES_TABLE = "cities";

	public static final String ID = "_id";
	public static final String NAME = "name";
	public static final String COUNTRY = "country";
	public static final String TEMPERATURE = "temperature";
	public static final String WIND = "wind";
	public static final String PRESSURE = "pressure";
	public static final String LAST_FETCH = "last_fetch";

	public HelperQuiPese(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_CITIES_TABLE = "CREATE TABLE IF NOT EXISTS " + CITIES_TABLE +  " ("
			+ ID + " INTEGER PRIMARY KEY,"
			+ NAME + " TEXT,"
			+ COUNTRY + " TEXT,"
			+ TEMPERATURE + " TEXT,"
			+ WIND + " TEXT,"
			+ PRESSURE + " TEXT,"
			+ LAST_FETCH + " TEXT,"
			+ "UNIQUE(" + NAME + "," + COUNTRY + ")"
			+ ")";

		db.execSQL(CREATE_CITIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE);
		onCreate(db);
	}

	public Cursor getAllCities()
	{
		return getReadableDatabase().query(CITIES_TABLE, allColumns,
				null, null, null, null, null);
	}

	public Cursor getCityWeather(String city, String country)
	{
		return getReadableDatabase().query(CITIES_TABLE, allColumns,
			NAME + " =? AND " + COUNTRY + " =?", new String[]{city, country},
			null, null, null);
	}

	public long addCity(String city, String country)
	{
		ContentValues pair = new ContentValues(2);
		pair.put(COUNTRY, country);
		pair.put(NAME, city);

		return getWritableDatabase().insert(CITIES_TABLE, null, pair);
	}

	public int deleteCity(String city, String country)
	{
		return getWritableDatabase().delete(CITIES_TABLE,
				NAME + "=? AND " + COUNTRY + "=?",
				new String[]{city, country});
	}

	public int updateCity(String city, String country, ContentValues values)
	{
		return getWritableDatabase().update(CITIES_TABLE,
				values,
				NAME + "=? AND " + COUNTRY + "=?",
				new String[]{city, country});
	}
}
