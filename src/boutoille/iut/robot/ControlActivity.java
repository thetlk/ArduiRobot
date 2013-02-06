package boutoille.iut.robot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ControlActivity extends Activity {
	
	VerticalSeekBar moteurGauche = null;
	VerticalSeekBar moteurDroit = null;
	TextView debug_moteurGauche = null;
	TextView debug_moteurDroit = null;
	ProgressBar bar_batterie = null;
	ToggleButton switch_onOff = null;
	ProgressDialog progressBluetooth = null;
	
	BluetoothThread lienBluetooth = null;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		moteurGauche = (VerticalSeekBar) findViewById(R.id.seekbar_moteurGauche);
		moteurDroit = (VerticalSeekBar) findViewById(R.id.seekbar_moteurDroit);
		debug_moteurGauche = (TextView) findViewById(R.id.text_valueMoteurGauche);
		debug_moteurDroit = (TextView) findViewById(R.id.text_valueMoteurDroit);
		bar_batterie = (ProgressBar) findViewById(R.id.bar_batterie);
		switch_onOff = (ToggleButton) findViewById(R.id.switch_onOff);
		moteurGauche.setOnSeekBarChangeListener(listenerSeekbar);
		moteurDroit.setOnSeekBarChangeListener(listenerSeekbar);
	}
	
	protected void onStart()
	{
		super.onStart();
		progressBluetooth = ProgressDialog.show(this, "Connexion", "Recherche du robot ...", true, true);
		lienBluetooth = new BluetoothThread(BluetoothAdapter.getDefaultAdapter(), progressBluetooth, "00:07:80:49:7F:3C", cmdHandler);
		lienBluetooth.start();
		
	}
	
	protected void onStop()
	{
		super.onStop();
		lienBluetooth.arret();
	}
	
	private Handler cmdHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Log.i("rcv", msg.obj.toString());
		}
	};
	
	private OnSeekBarChangeListener listenerSeekbar = new OnSeekBarChangeListener()
	{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			
			if(seekBar.equals(moteurGauche))
			{
				debug_moteurGauche.setText(""+progress);
			} else {
				debug_moteurDroit.setText(""+progress);
			}
			
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}
		
	};
	
}
