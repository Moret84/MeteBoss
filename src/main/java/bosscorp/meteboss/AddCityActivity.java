package bosscorp.meteboss;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class AddCityActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_city);
		((Button) findViewById(R.add_city.addButton))
			/*.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void OnClick(View v)
				{
					System.out.println("test");
				}
			});*/
	}
}
