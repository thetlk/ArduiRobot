package boutoille.iut.robot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button button_openConfig = null;
	Button button_openControl = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button_openConfig = (Button) this.findViewById(R.id.open_config);
		button_openControl = (Button) this.findViewById(R.id.open_control);
		
		button_openConfig.setOnClickListener(this.listenerOnClickOpenConfig);
		button_openControl.setOnClickListener(this.listenerOnClickOpenControl);
		
	}
	
	/*
	 * Listener sur button_openConfig
	 * OnClick
	 */
	
	private OnClickListener listenerOnClickOpenConfig =  new View.OnClickListener()
	{

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Open config", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this, ConfigureActivity.class);
			startActivity(intent);
		}
		
	};
	
	/*
	 * Listener sur button_openControl
	 * OnClick
	 */
	
	private OnClickListener listenerOnClickOpenControl = new View.OnClickListener()
	{
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Open control", Toast.LENGTH_SHORT).show();
		}
		
	};

}
