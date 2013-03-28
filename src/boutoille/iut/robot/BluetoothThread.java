package boutoille.iut.robot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public class BluetoothThread extends Thread {

	BluetoothSocket socket = null;
	BluetoothAdapter adapter = null;
	BluetoothDevice device = null;
	ProgressDialog dialog = null;
	String macAddress = null;
	Handler handler = null;
	InputStream istream;
	OutputStream ostream;
	
	public BluetoothThread(BluetoothAdapter adapter, ProgressDialog dialog, String macAddress, Handler handler)
	{
		this.adapter = adapter;
		this.dialog = dialog;
		this.macAddress = macAddress;
		this.handler = handler;
	}
	
	public void run()
	{
		
		if (adapter == null)
		{
			return;
		}

		device = adapter.getRemoteDevice(macAddress);
		
		try {
			socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (socket == null)
		{
			return;
		}
		
		try {
			istream = socket.getInputStream();
			ostream = socket.getOutputStream();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if(dialog != null && dialog.isShowing())
		{
			dialog.dismiss();
		}
		
		byte[] buffer = new byte[1024];
		int bytes;
		while(true)
		{
			try {
				bytes = istream.read(buffer);
				String dataRead = new String(buffer, 0, bytes);
				handler.obtainMessage(0x2a, dataRead).sendToTarget();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void write(String cmd)
	{
		
		
		try {
			ostream.write(cmd.getBytes());
		} catch (Exception e)
		{
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
