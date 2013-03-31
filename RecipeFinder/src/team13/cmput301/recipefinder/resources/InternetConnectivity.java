/**
 * Responsible for checking whether the user has internet connection.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.resources;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectivity {

	/**
	 * Checks whether an internet connection exists.
	 * @return true if there is a connection, false otherwise
	 */
	public static boolean checkInternetConnection(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = 
				connectivityManager.getActiveNetworkInfo();
		
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
