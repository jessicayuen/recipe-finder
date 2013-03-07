package team13.cmput301.recipefinder;

/**
 * Adapter class to display images in gallery
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

public class ImageAdapter extends BaseAdapter implements SpinnerAdapter {


	Context context;	

	public ImageAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		if(convertView == null){
			imageView = new ImageView(context);
			imageView.setLayoutParams(new Gallery.LayoutParams(115, 100));			
		} else {
			imageView = (ImageView)convertView;
		}
		
		imageView.setImageResource(pictureIds[position]);
		return imageView;
	}

	public Integer[] pictureIds = {
			//todo some ids
			// this is where the image ids are held
	};
}
