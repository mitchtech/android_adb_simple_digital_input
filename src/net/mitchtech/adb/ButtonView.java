package net.mitchtech.adb;

import net.mitchtech.adb.simpledigitalinput.R;

import org.microbridge.server.AbstractServerListener;
import org.microbridge.server.Server;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ButtonView extends FrameLayout {
	private static final String TAG = ButtonView.class.getSimpleName();

	private final View mButtonView;

	private Server mServer;

	private final int BUTTON1 = 2;
	private final int BUTTON2 = 3;

	public ButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mButtonView = inflater.inflate(R.layout.input, this);
	}

	public void setServer(Server server) {
		mServer = server;

		mServer.addListener(new AbstractServerListener() {

			@Override
			public void onReceive(org.microbridge.server.Client client, byte[] data) {
				if (data.length < 2)
					return;

				final int pinNumber = data[0];
				final int pinState = data[1];
				Log.i(TAG, "data[0]:" + pinNumber + "  data[1]:" + pinState);

				final TextView positionText = (TextView) findViewById(R.id.activeText);

				class InputAction implements Runnable {

					public void run() {

						switch (pinNumber) {
						case BUTTON1:
							if (pinState == 1) {
								positionText.setText("Button 1 Active");
							} else {
								positionText.setText("");
							}
							break;
						case BUTTON2:
							if (pinState == 1) {
								positionText.setText("Button 2 Active");
							} else {
								positionText.setText("");
							}
							break;

						default:
							break;
						}
					}
				};

				InputAction action = new InputAction();
				post(action);
			}
		});
	}
}
