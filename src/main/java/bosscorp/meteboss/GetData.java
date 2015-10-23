package bosscorp.meteboss;

import android.app.ProgressDialog;
import android.app.IntentService;
import android.app.ListActivity;
import android.content.Intent;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

public class GetData extends IntentService
{
	private ProgressDialog mProgress;
	private IWeatherWSClient mWSClient;

	public GetData()
	{
		super("GetData");
	}

	protected void onHandleIntent(Intent workIntent)
	{
		//Change this line to change the web service
		mWSClient = new WSXGlobalWeatherClient();

		List<City> cities = (List<City>) workIntent.getExtras().getSerializable("cities");
		for(City c : cities)
		{
			Log.e("refreshing", c.getName() + ", " + c.getCountry());
			List<String> results = mWSClient.refresh(c.getName(), c.getCountry());
			if(results != null && results.size() == 4)
			{
				c.setWind(results.get(0));
				c.setTemperature(results.get(1));
				c.setPressure(results.get(2));
				c.setLastFetch(results.get(3));
			}
			else
			{
				Log.e("refresh()", "null results for " + c.getName() + ", " + c.getCountry());
			}
		}
	}
}
