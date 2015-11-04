package bosscorp.meteboss;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.List;

public class GetData extends IntentService
{
	private IWeatherWSClient mWSClient;

	private static final int WIND = 0;
	private static final int TEMP = 1;
	private static final int PRESSURE = 2;
	private static final int LAST_FETCH = 3;

	public GetData()
	{
		super("GetData");
	}

	protected void onHandleIntent(Intent workIntent)
	{
		//Change this line to change the web service
		mWSClient = new WSXGlobalWeatherClient();

		Uri uri = workIntent.getParcelableExtra("URI");

		List<String> segments = uri.getPathSegments();
		String city = segments.get(ProviderQuiPese.CITY_SEGMENT);
		String country = segments.get(ProviderQuiPese.COUNTRY_SEGMENT);

		List<String> results = mWSClient.refresh(city, country);

		if(results != null && results.size() == 4)
		{
			ContentValues values = new ContentValues(4);
			values.put(HelperQuiPese.WIND, results.get(WIND));
			values.put(HelperQuiPese.TEMPERATURE, results.get(TEMP));
			values.put(HelperQuiPese.PRESSURE, results.get(PRESSURE));
			values.put(HelperQuiPese.LAST_FETCH, results.get(LAST_FETCH));

			getContentResolver().update(uri, values, null, null);
		}
		else
		{
			Log.e("refresh()", "null results for " + city + ", " + country);
		}
	}
}
