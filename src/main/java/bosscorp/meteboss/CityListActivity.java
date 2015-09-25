package bosscorp.meteboss;

import android.app.ListActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ListView;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.content.Intent;

public class CityListActivity extends ListActivity
{
	private ArrayList<City> mCityList;
	private ListView mListView;
	private ArrayAdapter<City> mAdapter;

	public final static String CITY = "bosscorp.meteboss.city";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mCityList = new ArrayList<City>();

		mCityList.add(new City("Michelville", "France"));
		mCityList.add(new City("Sardouland", "France"));

		mCityList.get(0).setTemperature("26");
		mCityList.get(0).setPressure("1013");
		mCityList.get(0).setWindSpeed("90");
		mCityList.get(0).setWindDirection("N");
		mCityList.get(0).setLastFetch("Avant hier");

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
		startActivity(intent);
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
			removeCity(item.getItemId());
		else
			return false;
		return true;
	}

	private void removeCity(int id)
	{
		String name = mCityList.get(id).getName();

		mCityList.remove(id);
		mAdapter.notifyDataSetChanged();

		Toast.makeText(this, name + " has been removed", Toast.LENGTH_SHORT).show();
	}
}
