package boutoille.iut.robot;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BluetoothThread extends Thread {

	BluetoothSocket socket = null;
	BluetoothAdapter adapter = null;
	BluetoothDevice device = null;
	ProgressDialog dialog = null;
	String macAddress = null;
	
	public BluetoothThread(BluetoothAdapter adapter, ProgressDialog dialog, String macAddress)
	{
		this.adapter = adapter;
		this.dialog = dialog;
		this.macAddress = macAddress;
	}
	
	public void run()
	{
		if (adapter == null)
		{
			return;
		}

		device = adapter.getRemoteDevice(macAddress);
		try {
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void arret()
	{
		try {
			if (socket != null)
			{
				socket.close();
			}
		} catch (IOException e)
		{
			
		}
	}
	
}
