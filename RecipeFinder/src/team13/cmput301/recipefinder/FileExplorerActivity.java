/**
 * Activity that allows user to navigate the phone directories
 * to retrieve images.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class FileExplorerActivity extends Activity {

	// Stores names of traversed directories
	ArrayList<String> str = new ArrayList<String>();

	// Check if the first level of the directory structure is the one showing
	private Boolean firstLvl = true;

	private static final String TAG = "F_PATH";

	private ArrayList<Item> fileList;
	private File path = 
			new File(Environment.getExternalStorageDirectory() + "");
	private String chosenFile;
	private static final int DIALOG_LOAD_FILE = 1000;

	ListAdapter adapter;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadFileList();
		showDialog(DIALOG_LOAD_FILE);
		Log.d(TAG, path.getAbsolutePath());
	}

	/**
	 * Loads the list of files
	 */
	private void loadFileList() {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card ");
		}

		// Checks whether path exists
		if (path.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					// Filters based on whether the file is hidden or not
					return (sel.isFile() || sel.isDirectory())
							&& !sel.isHidden();

				}
			};

			String[] fList = path.list(filter);
			fileList = new ArrayList<Item>();
			for (int i = 0; i < fList.length; i++) {
				Item temp = new Item(fList[i], R.drawable.file_icon);
				fileList.add(temp);
				// Convert into file path
				File sel = new File(path, fList[i]);

				if(sel.isFile()){
					if(BitmapFactory.decodeFile(sel.getPath()) == null){
						fileList.remove(temp);
					}
				}				
				// Set drawables
				else if (sel.isDirectory()) {
					int tempIndex = fileList.indexOf(temp);
					fileList.get(tempIndex).icon = R.drawable.directory_icon;
					Log.d("DIRECTORY", fileList.get(tempIndex).file);
				} else {
					Log.d("FILE", fileList.get(i).file);
				}
			}

			if (!firstLvl) {
				ArrayList<Item> temp = new ArrayList<Item>();
				for (int i = 0; i < fileList.size(); i++) {
					temp.add(i, fileList.get(i));
				}
				temp.add(0, new Item("Up", R.drawable.directory_up));
				fileList = temp;
			}
		} else {
			Log.e(TAG, "path does not exist");
		}

		adapter = new ArrayAdapter<Item>(this,
				android.R.layout.select_dialog_item, android.R.id.text1, fileList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// creates view
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				// put the image on the text view
				textView.setCompoundDrawablesWithIntrinsicBounds(
						fileList.get(position).icon, 0, 0, 0);

				// add margin between image and text (support various screen densities)
				int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
				textView.setCompoundDrawablePadding(dp5);

				return view;
			}
		};

	}

	/**
	 * Inner class to store items include the file path as a string and whether
	 * its a folder or file the icon is set respectively
	 */
	private class Item {
		public String file;
		public int icon;

		public Item(String file, Integer icon) {
			this.file = file;
			this.icon = icon;
		}

		@Override
		public String toString() {
			return file;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);

		if (fileList == null) {
			Log.e(TAG, "No files loaded");
			dialog = builder.create();
			return dialog;
		}

		switch (id) {
		case DIALOG_LOAD_FILE:
			builder.setTitle("Choose your file");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					chosenFile = fileList.get(which).file;
					File sel = new File(path + "/" + chosenFile);
					if (sel.isDirectory()) {
						firstLvl = false;

						// Adds chosen directory to list
						str.add(chosenFile);
						fileList = null;
						path = new File(sel + "");

						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}

					// Checks if 'up' was clicked
					else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {

						// present directory removed from list
						String s = str.remove(str.size() - 1);

						// path modified to exclude present directory
						path = new File(path.toString().substring(0,
								path.toString().lastIndexOf(s)));
						fileList = null;

						// if there are no more directories in the list, then
						// its the first level
						if (str.isEmpty()) {
							firstLvl = true;
						}
						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}
					// File picked
					else {
						//return the file path to the caller acivity
						Intent resultData = new Intent();
						String filePath = sel.getPath();
						resultData.putExtra("path", filePath);
						setResult(Activity.RESULT_OK, resultData);
						finish();
					}

				}
			});
			break;
		}
		dialog = builder.show();
		return dialog;
	}
}

