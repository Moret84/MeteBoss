package bosscorp.meteboss;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class CityActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>
{
	private Uri mUri;

	private void fillFields(City city)
	{
		TextView text = (TextView) findViewById(R.id.cityValue);
		text.setText(city.getName());

		text = (TextView) findViewById(R.id.countryValue);
		text.setText(city.getCountry());

		text = (TextView) findViewById(R.id.windValue);
		text.setText(city.getWind());

		text = (TextView) findViewById(R.id.pressureValue);
		text.setText(city.getPressure());

		text = (TextView) findViewById(R.id.temperatureValue);
		text.setText(city.getTemperature());

		text = (TextView) findViewById(R.id.lastUpdateValue);
		text.setText(city.getLastUpdate());
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_view);

		mUri = (Uri) getIntent().getParcelableExtra(CityListActivity.CITY);
		getLoaderManager().initLoader(0, null, this);
	}

	private void refreshCurrent()
	{
		Intent intent = new Intent(this, GetData.class);
		intent.putExtra("URI", mUri);
		startService(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		invalidateOptionsMenu();
		menu.findItem(R.id.addCity).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.refresh:
				refreshCurrent();

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		return new CursorLoader(this, mUri, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		if(data != null && data.moveToFirst())
			fillFields(new City(data));
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
	}
}
