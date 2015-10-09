package bosscorp.meteboss;

import android.app.ProgressDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.util.Log;
import java.lang.InterruptedException;
import java.util.List;
import java.util.ArrayList;

public class CityListActivity extends ListActivity
{
	private ArrayList<City> mCityList = new ArrayList<City>();
	private ListView mListView;
	private ArrayAdapter<City> mAdapter;
	private IWeatherWSClient mWSClient;

	public final static String CITY = "bosscorp.meteboss.city";
	public final static int ADD_CITY = 1;

	private void removeCity(int id)
	{
		String name = mCityList.get(id).getName();

		mCityList.remove(id);
		mAdapter.notifyDataSetChanged();

		Toast.makeText(this, name + getResources().getString(R.string.removeToast), Toast.LENGTH_SHORT).show();
	}

	private void openAdd()
	{
		Intent intent = new Intent(this, AddCityActivity.class);
		startActivityForResult(intent, ADD_CITY);
	}

	private void refresh()
	{
		for(City c : mCityList)
		{
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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mCityList.add(new City("Michelville", "France"));
		mCityList.add(new City("Sardouland", "France"));
		mCityList.add(new City("Lyon", "France"));

		mListView = getListView();
		mAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mCityList);

		//Change this line to change the web service
		mWSClient = new WSXGlobalWeatherClient();

		mListView.setAdapter(mAdapter);
		registerForContextMenu(mListView);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, CityActivity.class);
		City selectedCity = mCityList.get(position);
		intent.putExtra(CITY, selectedCity);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.addCity:
				openAdd();
				return true;
			case R.id.refresh:
				new FetchData().execute();

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == ADD_CITY)
			{
				City city = (City) data.getSerializableExtra(CITY);
				mCityList.add(city);
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Remove");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if(item.getTitle() == "Remove")
			removeCity(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
		else
			return false;
		return true;
	}

	private class FetchData extends AsyncTask<Void, Void, Void>
	{
		private ProgressDialog mProgress;

		protected void onPreExecute()
		{
			mProgress = new ProgressDialog(CityListActivity.this);
			mProgress.setMessage(getString(R.string.refreshing));
			mProgress.show();
		}

		protected Void doInBackground(Void... input)
		{
			refresh();
			return null;
		}

		protected void onPostExecute(Void result)
		{
			if (mProgress.isShowing())
				mProgress.dismiss();
			Toast.makeText(CityListActivity.this, getResources().getString(R.string.refreshed), Toast.LENGTH_SHORT).show();
		}
	}
}
