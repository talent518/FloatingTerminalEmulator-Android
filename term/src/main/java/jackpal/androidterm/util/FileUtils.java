package jackpal.androidterm.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	private static final String TAG = "FileUtils";

	public static void copyAssetDirToFiles(Context context, String dirname) throws IOException {
		File dir = new File(context.getFilesDir() + "/" + dirname);
		if (!dir.isDirectory()) dir.mkdir();

		AssetManager assetManager = context.getAssets();
		String[] children = assetManager.list(dirname);
		for (String child : children) {
			child = dirname + '/' + child;
			Log.i(TAG, child);
			String[] grandChildren = assetManager.list(child);
			if (0 == grandChildren.length) copyAssetFileToFiles(context, child);
			else copyAssetDirToFiles(context, child);
		}
	}

	public static void copyAssetFileToFiles(Context context, String filename) throws IOException {
		File of = new File(context.getFilesDir() + "/" + filename);
		if(of.exists()) {
			return;
		}

		of.createNewFile();
		InputStream is = context.getAssets().open(filename);
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();

		FileOutputStream os = new FileOutputStream(of);
		os.write(buffer);
		os.close();
	}
}
