package bosscorp.meteboss;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class CityActivity extends Activity
{

	private City mCity;

	private void fillFields()
	{
		TextView text = (TextView) findViewById(R.id.cityValue);
		text.setText(mCity.getName());

		text = (TextView) findViewById(R.id.countryValue);
		text.setText(mCity.getCountry());

		text = (TextView) findViewById(R.id.windValue);
		text.setText(mCity.getWind());

		text = (TextView) findViewById(R.id.pressureValue);
		text.setText(mCity.getPressure());

		text = (TextView) findViewById(R.id.temperatureValue);
		text.setText(mCity.getTemperature());

		text = (TextView) findViewById(R.id.lastUpdateValue);
		text.setText(mCity.getLastUpdate());
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_view);

		mCity = (City) getIntent().getSerializableExtra(CityListActivity.CITY);
		fillFields();
	}

	private void refreshCurrent()
	{
		/*final GetData geeet = new GetData(this);
		geeet.execute(mCity);

			new Thread()
			{
				public void run()
				{
					while(geeet.getStatus() != AsyncTask.Status.FINISHED);
					runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							fillFields();
						}
					});
				}
			}.start();

		fillFields();
		Intent intent = new Intent();
		intent.putExtra(CityListActivity.CITY, mCity);
		setResult(RESULT_OK, intent);
		*/
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
}
