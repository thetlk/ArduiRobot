package boutoille.iut.robot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	String inBuffer = null;
	int valDroit = 0;
	int valGauche = 0;
	
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
		switch_onOff.setOnCheckedChangeListener(onOffChange);
		inBuffer = "";
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
		lienBluetooth.arret();
		super.onStop();
	}
	
	private Handler cmdHandler = new Handler()
	{
		public void handleMessage(Message msg)
		
		{
			super.handleMessage(msg);
			inBuffer += msg.obj.toString();
			
			if(inBuffer.indexOf('\n') != -1) // \n présent dans le buffer
			{
				inBuffer = "";
			}
			
		}
	};
	
	private OnCheckedChangeListener onOffChange = new OnCheckedChangeListener()
	{

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			if(isChecked == false)
			{
				moteurGauche.setProgressAndThumb(255);
				moteurDroit.setProgressAndThumb(255);
				moteurGauche.setEnabled(false);
				moteurDroit.setEnabled(false);
				lienBluetooth.write("stop;\n");
			} else {
				moteurGauche.setEnabled(true);
				moteurDroit.setEnabled(true);
			}
			
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
			
			int progress = seekBar.getProgress();
			progress = (progress-255); // 255 <=> 0, donc calcule donc notre + et -

			if(seekBar.equals(moteurGauche))
			{
				valGauche = progress;
			} else {
				valDroit = progress;				
			}
			
			String commande = "move;"+valGauche+";"+valDroit+";\n";
			lienBluetooth.write(commande);								
				
		}
		
	};
	
}
