package team13.cmput301.recipefinder;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Adapter for images in the gallery
 */
class PicAdapter extends BaseAdapter {

	private int defaultItemBackground;
	private Context galleryContext;
	private List<Photo> imagePhotos;
	
	public PicAdapter(Context c, List<Photo> photos) {
		galleryContext = c;
		imagePhotos = photos;
		TypedArray styleAttrs = galleryContext.obtainStyledAttributes(
				R.styleable.PicGallery);
		defaultItemBackground = styleAttrs.getResourceId(
				R.styleable.PicGallery_android_galleryItemBackground, 0);
		styleAttrs.recycle();
	}
	
	@Override
	public int getCount() {
		return imagePhotos.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(galleryContext);
		imageView.setImageBitmap(imagePhotos.get(position).getPhoto());
		imageView.setLayoutParams(new Gallery.LayoutParams(120, 100));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setBackgroundResource(defaultItemBackground);
		return imageView;
	}
}
