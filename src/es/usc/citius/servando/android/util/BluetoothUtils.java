package es.usc.citius.servando.android.util;

import android.bluetooth.BluetoothAdapter;

/**
 * Singleton que permite o acceso ao adaptador Bluetooth dende aquelas clases
 * que non o poden facer a través do contexto. Hai que ter en conta que
 * previammente, algunha clase con acceso ó contexto deberá pasar unha
 * referencia a dito adaptador mediante o método
 * {@link #setAdapter(BluetoothAdapter)}
 * 
 * @author Ángel Piñeiro
 * 
 */
public class BluetoothUtils {

	/**
	 * Singleton unique instance
	 */
	private static BluetoothUtils instance = null;
	/**
	 * Private constructor to avoid multiple instances
	 */
	private BluetoothUtils() {
	}
	/**
	 * Static member to obtain the unique instance
	 */
	public static BluetoothUtils getInstance() {
		if (instance == null) {
			instance = new BluetoothUtils();
		}
		return instance;
	}

	private BluetoothAdapter adapter = null;

	public BluetoothAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BluetoothAdapter adapter) {
		this.adapter = adapter;
	}

}
