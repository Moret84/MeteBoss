package bosscorp.meteboss;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;

public class HelperQuiPese extends SQLiteOpenHelper
{
	private SQLiteDatabase mDB;
	private String[] allColumns = { KEY_ID, KEY_NAME, KEY_COUNTRY, KEY_TEMPERATURE, KEY_WIND, KEY_PRESSURE, KEY_LAST_FETCH };

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "meteoManager";

	private static final String CITIES_TABLE = "cities";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_COUNTRY = "country";
	private static final String KEY_TEMPERATURE = "temperature";
	private static final String KEY_WIND = "wind";
	private static final String KEY_PRESSURE = "pressure";
	private static final String KEY_LAST_FETCH = "last_fetch";

	public HelperQuiPese(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		mDB = db;

		String CREATE_CITIES_TABLE = "CREATE TABLE " + CITIES_TABLE+ "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT UNIQUE,"
			+ KEY_COUNTRY + " TEXT," + KEY_TEMPERATURE + " TEXT,"
			+ KEY_WIND + " TEXT," + KEY_PRESSURE + " TEXT,"
			+ KEY_LAST_FETCH + " TEXT" + ")";
		db.execSQL(CREATE_CITIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE);

		onCreate(db);
	}

	public List<City> getAllCities()
	{
		List<City> cities = new ArrayList<City>();

		Cursor cursor = mDB.query(CITIES_TABLE,
				new String[] { KEY_ID, KEY_NAME, KEY_COUNTRY, KEY_TEMPERATURE, KEY_WIND, KEY_PRESSURE, KEY_LAST_FETCH },
				null, null, null, null, null);

		cursor.moveToFirst();
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			City city = new City(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
					cursor.getString(cursor.getColumnIndex(KEY_COUNTRY)));
			city.setPressure(cursor.getString(cursor.getColumnIndex(KEY_PRESSURE)));
			city.setTemperature(cursor.getString(cursor.getColumnIndex(KEY_TEMPERATURE)));
			city.setWind(cursor.getString(cursor.getColumnIndex(KEY_WIND)));
			city.setLastFetch(cursor.getString(cursor.getColumnIndex(KEY_LAST_FETCH)));

			cities.add(city);
		}
		cursor.close();

		return cities;
	}

	public void add()
	{
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, "Michelville");
		values.put(KEY_COUNTRY, "France");
		values.put(KEY_TEMPERATURE, "pas chaud");
		values.put(KEY_PRESSURE, "y a la pression");
		values.put(KEY_WIND, "con de vent");
		values.put(KEY_LAST_FETCH, "jamais");

		long insertId = mDB.insert(CITIES_TABLE, null, values);
		Cursor cursor = mDB.query(CITIES_TABLE, allColumns, KEY_ID + " = " + insertId, null, null, null, null);
		cursor.close();
	}
}
