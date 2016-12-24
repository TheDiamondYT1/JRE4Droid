package tk.thediamondyt.jre4droid;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import tk.thediamondyt.jre4droid.activities.InstallActivity;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private ProgressDialog dialog;
	
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(Utils.getContext());
        dialog.setMessage("Downloading Java...");
        dialog.setIndeterminate(false);
		dialog.setProgressPercentFormat(null);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... Url) {
        try {
            URL url = new URL(Url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();
			
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(Utils.getExtDir() + "/java.zip");
	
            byte data[] = new byte[1024];
            long total = 0;
            int count;
			
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

			// Am i doing this right?
            Utils.getContext().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialog.setMessage("Installing java...");
				}
			});
			Utils.getContext().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
				}
			});
			
			InstallActivity.instance.install();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        dialog.setProgress(progress[0]);
    }
}
