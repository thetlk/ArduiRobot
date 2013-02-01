package boutoille.iut.robot;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button button_openConfig = null;
	Button button_openControl = null;
	BluetoothAdapter bluetooth = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button_openConfig = (Button) this.findViewById(R.id.open_config);
		button_openControl = (Button) this.findViewById(R.id.open_control);
		
		button_openConfig.setOnClickListener(this.listenerOnClickOpenConfig);
		button_openControl.setOnClickListener(this.listenerOnClickOpenControl);
		
		initBluetooth();
		
	}
	
	private void initBluetooth()
	{
		
		bluetooth = BluetoothAdapter.getDefaultAdapter();
		
		if(bluetooth == null)
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Pas de bluetooth");
			alertDialog.setMessage("Votre appareil ne semble pas supporter le bluetooth. L'application doit quitter.");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			});
			
			alertDialog.show();
		}
		
		if(!bluetooth.isEnabled())
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Activation du bluetooth");
			alertDialog.setMessage("Le bluetooth n'est pas activé. Activation du bluetooth.");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					bluetooth.enable();
				}
			});
			alertDialog.show();
		}
		
	}
	
	/*
	 * Listener sur button_openConfig
	 * OnClick
	 */
	
	private OnClickListener listenerOnClickOpenConfig =  new View.OnClickListener()
	{

		@Override
		public void onClick(View v) {
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
			Intent intent = new Intent(MainActivity.this, ControlActivity.class);
			startActivity(intent);
		}
		
	};

}
