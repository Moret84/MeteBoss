package bosscorp.meteboss;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import java.util.List;

public class ProviderQuiPese extends ContentProvider
{
	static final String AUTHORITY = "bosscorp.meteboss";
	static final String URL = "content://" + AUTHORITY + "/" + HelperQuiPese.CITIES_TABLE;
	static final Uri CONTENT_URI = Uri.parse(URL);

	static final int WEATHER = 1;
	static final int BY_CITY_WEATHER = 2;

	static final int COUNTRY_SEGMENT = 1;
	static final int CITY_SEGMENT = 2;

	static final UriMatcher mUriMatcher;
	static
	{
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "cities", WEATHER);
		mUriMatcher.addURI(AUTHORITY, "cities/*/*", BY_CITY_WEATHER);
	}

	private HelperQuiPese mDB;

	public static Uri getAllCitiesUri()
	{
		return CONTENT_URI;
	}

	public static Uri buildCityUri(String city, String country)
	{
		return CONTENT_URI.buildUpon().appendPath(country).appendPath(city).build();
	}

	@Override
	public boolean onCreate()
	{
		mDB = new HelperQuiPese(getContext());

		return (mDB == null)? false:true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		if(mUriMatcher.match(uri) != BY_CITY_WEATHER)
			return null;

		List<String> segments = uri.getPathSegments();
		String city = segments.get(CITY_SEGMENT);
		String country = segments.get(COUNTRY_SEGMENT);

		getContext().getContentResolver().notifyChange(uri, null);
		return (mDB.addCity(city, country) == -1) ? null : buildCityUri(city, country);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder)
	{
		Cursor result = null;

		switch (mUriMatcher.match(uri))
		{
			case WEATHER:
				result = mDB.getAllCities();
				break;

			case BY_CITY_WEATHER:
				List<String> segments = uri.getPathSegments();
				result = mDB.getCityWeather(segments.get(CITY_SEGMENT), segments.get(COUNTRY_SEGMENT));
				break;
		}


		result.setNotificationUri(getContext().getContentResolver(), uri);

		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		if(mUriMatcher.match(uri) == BY_CITY_WEATHER)
		{
			List<String> segments = uri.getPathSegments();
			getContext().getContentResolver().notifyChange(uri, null);
			return mDB.deleteCity(segments.get(CITY_SEGMENT), segments.get(COUNTRY_SEGMENT));
		}

		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		if(mUriMatcher.match(uri) == BY_CITY_WEATHER)
		{
			List<String> segments = uri.getPathSegments();
			getContext().getContentResolver().notifyChange(uri, null);
			return mDB.updateCity(segments.get(CITY_SEGMENT), segments.get(COUNTRY_SEGMENT), values);
		}

		return 0;
	}

	@Override
	public String getType(Uri uri)
	{
		String type;
		String suffix = "/vnd." + AUTHORITY + ".cities";

		switch (mUriMatcher.match(uri))
		{
			case WEATHER:
				type = ContentResolver.CURSOR_DIR_BASE_TYPE + suffix;
			case BY_CITY_WEATHER:
				type = ContentResolver.CURSOR_ITEM_BASE_TYPE + suffix;
			default:
				type = null;
		}

		return type;
	}
}
