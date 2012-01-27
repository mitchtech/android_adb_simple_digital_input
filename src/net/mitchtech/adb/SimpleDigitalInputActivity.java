package net.mitchtech.adb;

import java.io.IOException;

import net.mitchtech.adb.simpledigitalinput.R;

import org.microbridge.server.Server;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SimpleDigitalInputActivity extends Activity {
	
	private final static String TAG = SimpleDigitalInputActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		Server server = null;
		try
		{
			server = new Server(4567);
			server.start();
			
			ButtonView buttonView = (ButtonView) findViewById(R.id.inputView);
			buttonView.setServer(server);
		} catch (IOException e)
		{
			Log.e(TAG, "Unable to start TCP server", e);
			System.exit(-1);
		}
	}
}