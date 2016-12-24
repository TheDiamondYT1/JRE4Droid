package tk.thediamondyt.jre4droid.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Environment;
import android.content.DialogInterface;
import android.widget.Button;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.stericson.RootTools.RootTools;

import tk.thediamondyt.jre4droid.Utils;
import tk.thediamondyt.jre4droid.DownloadTask;
import tk.thediamondyt.jre4droid.R;

import java.io.File;

public class InstallActivity extends AppCompatActivity {
	
	public static InstallActivity instance;
	
	private RadioGroup radioGroup = null;
	private boolean systemInstall;
	private String dir;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
		
		instance = this;
		Utils.setContext(InstallActivity.this);
		
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		
		Button button = (Button) findViewById(R.id.install_button);
        button.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				systemInstall = true; // TODO: Change
				dir = "/system/java";
				Utils.setPreference("install_dir", "/system/java");
				download(); // TODO: Custom install directory
			}
		});
    }
	
	private void download() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(systemInstall ? "System" : "Custom" + " install");
        dialog.setMessage("Java will be installed in the " + dir + " directory. " +
						  "Press continue to request root access.");
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
        dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (new File(dir).exists()) {
					Utils.showErrorDialog("Java is already installed in the " + dir + " directory.");
					return;
				}
				if (!RootTools.isAccessGiven()) {
					Utils.showErrorDialog("Root access not available. Please give access.");
					return;
				}
				if(!new File(Utils.getExtDir()).exists()) new File(Utils.getExtDir()).mkdir();				
				new DownloadTask().execute("https://github.com/TheDiamondYT1/JRE4Droid/blob/download/java.zip?raw=true");
				dialog.dismiss();
			}
		});
        dialog.create().show();
	}
	
	public void install() {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Installing Java...");
		dialog.setIndeterminate(true);
		dialog.setProgressNumberFormat(null);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setCancelable(false);
		dialog.show();
		
		new Thread(new Runnable() {
			public void run() {
		        try {      
				    Utils.installBusybox();
			        Utils.installScript();			
			        Runtime.getRuntime().exec("su -c sh " + Utils.getDir() + "/install.sh").waitFor();
					dialog.dismiss();
		        } catch (Exception e) {
			        e.printStackTrace();
				}
			}
		}).start();
	}
}
