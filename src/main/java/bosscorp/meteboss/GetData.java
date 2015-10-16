package bosscorp.meteboss;

import android.app.ProgressDialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

public class GetData extends AsyncTask<City, Void, Void>
{
	private Context mContext;
	private ProgressDialog mProgress;
	private IWeatherWSClient mWSClient;

	public GetData(Context c)
	{
		mContext = c;
	}

	protected void onPreExecute()
	{
		//Change this line to change the web service
		mWSClient = new WSXGlobalWeatherClient();

		mProgress = new ProgressDialog(mContext);
		mProgress.setMessage(mContext.getString(R.string.refreshing));
		mProgress.show();
	}

	protected Void doInBackground(City... cities)
	{
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
		return null;
	}

	protected void onPostExecute(Void result)
	{
		if (mProgress.isShowing())
			mProgress.dismiss();
		Toast.makeText(mContext, mContext.getString(R.string.refreshed), Toast.LENGTH_SHORT).show();
	}
}
