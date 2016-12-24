package tk.thediamondyt.jre4droid;

import android.support.v7.app.AlertDialog;

import android.app.Activity;
import android.os.Environment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.DialogInterface;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import tk.thediamondyt.jre4droid.activities.InstallActivity;

public class Utils {
	
	private static Activity context;
	private static SharedPreferences prefs;
	
	public static void setContext(Activity context) {
		context = context;
		prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
	}
	
	public static Activity getContext() {
		return context;
	}
	
	public static String getDir() {
		return context.getApplicationInfo().dataDir;
	}
	
	public static String getExtDir() {
		return Environment.getExternalStorageDirectory() + "/.JRE4Droid";
	}
	
	public static void setPreference(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }  

    public static SharedPreferences getPreferences() {
        return prefs;
    }
	
	public static void installBusybox() throws Exception {
		File busybox = new File(getDir() + "/busybox");
		if(busybox.exists()) return;
	
		copyAsset("busybox", busybox);
		busybox.setExecutable(true, true);
	}
	
	public static void installScript() throws Exception {
		File script = new File(getDir() + "/install.sh");
		if(script.exists()) return;
		
		copyAsset("install.sh", script);
		script.setExecutable(true, true);
	}
	
	public static void copyAsset(String name, File target) throws Exception {
		target.delete();
		OutputStream os = new FileOutputStream(target);
		InputStream is = context.getAssets().open(name);
		int cou = 0;
		byte[] buffer = new byte[8192];
		
		while((cou = is.read(buffer)) != -1) os.write(buffer, 0, cou);
	
		is.close();
		os.close();
	}
	
	public static void showErrorDialog(String text) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(text);
        dialog.setNegativeButton("Alright", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
        dialog.create().show();
    }
}
