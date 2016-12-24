package tk.thediamondyt.jre4droid.activities;

import android.support.annotation.Nullable;
import android.os.Environment;

import com.nononsenseapps.filepicker.*;

import tk.thediamondyt.jre4droid.fragments.FileChooserFragment;

import java.io.File;

public class FileChooserActivity extends AbstractFilePickerActivity {
	
	private AbstractFilePickerFragment<File> fragment;
	
    public FileChooserActivity() {
        super();
    }

    @Override
    protected AbstractFilePickerFragment<File> getFragment(
		@Nullable final String startPath, final int mode, final boolean allowMultiple,
		final boolean allowCreateDir, final boolean allowExistingFile,
		final boolean singleClick) {
        fragment = new FileChooserFragment();
        
        fragment.setArgs(startPath != null ? startPath : Environment.getExternalStorageDirectory().getPath(),
						 mode, allowMultiple, allowCreateDir, allowExistingFile, singleClick);
        return fragment;
    }
}
