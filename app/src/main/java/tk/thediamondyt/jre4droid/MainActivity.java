package tk.thediamondyt.jre4droid;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;

import tk.thediamondyt.jre4droid.activities.InstallActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
	
	public static MainActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		instance = this;
		Utils.setContext(MainActivity.this);
		
		if(!Utils.getPreferences().contains("install_dir")) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
			dialog.setCancelable(false);
			dialog.setTitle("Cant find Java");
			dialog.setMessage("Java cannot be found. Would you like to install it, or is " +
							  "it already installed?");
			dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.setNeutralButton("Its installed", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//startActivity(new Intent(MainActivity.this, LostInstallActivity.class));
					dialog.dismiss();
				}
			});
			dialog.setPositiveButton("Install", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(MainActivity.this, InstallActivity.class));
					dialog.dismiss();
				}
			});
			dialog.create().show();
			
		}
	}
}
