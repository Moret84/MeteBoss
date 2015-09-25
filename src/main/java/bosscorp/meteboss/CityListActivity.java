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

public class CityListActivity extends ListActivity
{
	private ArrayList<City> mCityList;
	private ListView mListView;
	private ArrayAdapter<City> mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mCityList = new ArrayList<City>();

		mCityList.add(new City("Michelville", "France"));
		mCityList.add(new City("Sardouland", "France"));

		mListView = (ListView) findViewById(android.R.id.list);
		mAdapter = new ArrayAdapter<City>(this, R.layout.city_list);

		mListView.setAdapter(mAdapter);
		registerForContextMenu(mListView);
		mAdapter.addAll(mCityList);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
			{
			}
		});
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
		mAdapter.remove(mAdapter.getItem(id));
		Toast.makeText(this, mCityList.get(id).getName() + " has been removed", Toast.LENGTH_SHORT).show();
		mCityList.remove(id);
	}
}
