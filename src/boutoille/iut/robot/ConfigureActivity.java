package boutoille.iut.robot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfigureActivity extends Activity {

	EditText mac = null;
	EditText moteur = null;
	Button valider = null;
	
	@Override
	protected void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_configure);
		
		mac = (EditText) findViewById(R.id.config_mac);
		moteur = (EditText) findViewById(R.id.config_moteur);
		valider = (Button) findViewById(R.id.config_valider);
	}
	
}
