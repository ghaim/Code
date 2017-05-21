/**
 * 
 */
package example.com.newnav1.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author android
 * 
 */
public class AppPrefes {

	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public AppPrefes(Context context, String Preferncename) {
		this.appSharedPrefs = context.getSharedPreferences(Preferncename,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/****
	 * 
	 * getdata() get the value from the preference
	 * 
	 * */
	public String getData(String key) {
		return appSharedPrefs.getString(key, "");
	}

	public Integer getIntData(String key) {
		return appSharedPrefs.getInt(key, 0);
	}

	public Long getLocation(String key) {
		return appSharedPrefs.getLong(key, 0);
	}

	public void SaveIntData(String key, int value) {
		prefsEditor.putInt(key, value);
		prefsEditor.commit();
	}

	/****
	 * 
	 * SaveData() save the value to the preference
	 * 
	 * */
	public void saveData(String Tag, String text) {
		prefsEditor.putString(Tag, text);
		prefsEditor.commit();
	}

	public void saveBoolean(String Tag, boolean value) {
		prefsEditor.putBoolean(Tag, value);
		prefsEditor.commit();
	}

	public void saveLocation(String Tag, double location) {
		prefsEditor.putLong(Tag, Double.doubleToRawLongBits(location));
		prefsEditor.commit();
	}

	public boolean getBoolean(String key) {
		return appSharedPrefs.getBoolean(key, false);
	}

}
