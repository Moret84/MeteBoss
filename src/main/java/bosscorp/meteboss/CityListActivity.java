package bosscorp.meteboss;

import android.app.ProgressDialog;
import android.app.ListActivity;
import android.os.Bundle;
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
import java.util.ListIterator;
import java.util.ArrayList;

public class CityListActivity extends ListActivity
{
	private ArrayList<City> mCityList = new ArrayList<City>();
	private ListView mListView;
	private ArrayAdapter<City> mAdapter;

	public final static String CITY = "bosscorp.meteboss.city";
	public final static int ADD_CITY = 1;
	public final static int REFRESH_CURRENT_CITY = 2;

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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mCityList.add(new City("Brest", "France"));
		mCityList.add(new City("Marseille", "France"));
		mCityList.add(new City("Montreal", "Canada"));
		mCityList.add(new City("Istanbul", "Turkey"));
		mCityList.add(new City("Seoul", "Korea"));
		mCityList.add(new City("Lyon", "France"));

		mListView = getListView();
		mAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mCityList);

		mListView.setAdapter(mAdapter);
		registerForContextMenu(mListView);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, CityActivity.class);
		City selectedCity = mCityList.get(position);
		intent.putExtra(CITY, selectedCity);
		startActivityForResult(intent, REFRESH_CURRENT_CITY);
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
				Bundle bundle = new Bundle();
				bundle.putSerializable("cities", mCityList);
				startService(new Intent(this, GetData.class).putExtras(bundle));

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			City city = (City) data.getSerializableExtra(CITY);

			switch(requestCode)
			{
				case ADD_CITY:
					mCityList.add(city);
					mAdapter.notifyDataSetChanged();
				case REFRESH_CURRENT_CITY:
					if(city != null)
					{
						ListIterator<City> it = mCityList.listIterator();
						City c;
						while (it.hasNext())
						{
							c = it.next();
							if(c.getName().equals(city.getName()) && c.getCountry().equals(city.getCountry()))
							{
								System.out.println("Ça pèse");
								it.set(city);
								mAdapter.notifyDataSetChanged();
							}
						}
					}
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

}
