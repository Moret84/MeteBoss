package bosscorp.meteboss;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;

public class AddCityActivity extends Activity
{
	private void addCity(String cityName, String countryName)
	{
		City city = new City(cityName, countryName);
		Intent intent = new Intent(this, CityListActivity.class);
		intent.putExtra(CityListActivity.CITY, city);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void emptyFieldDialog()
	{
		new AlertDialog.Builder(this).setTitle(getString(R.string.emptyFieldTitle))
			.setMessage(getString(R.string.emptyFieldMessage))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
						}
					}).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_city);

		((Button) findViewById(R.id.addButton))
			.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String city = ((EditText) findViewById(R.id.cityField)).getText().toString();
					String country = ((EditText) findViewById(R.id.countryField)).getText().toString();

					if(city.isEmpty() || country.isEmpty())
						emptyFieldDialog();
					else
						addCity(city, country);
				}
			});
	}
}
