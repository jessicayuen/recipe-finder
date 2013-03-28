/**
 * PicAdapter updates images and displays them for the 
 * gallery in the provided activity.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.adapters;

import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.model.Photo;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Adapter for images in the gallery
 */
public class PicAdapter extends BaseAdapter {

	private int defaultItemBackground;
	private Context galleryContext;
	private List<Photo> imagePhotos;

	/**
	 * Constructor for PicAdapter
	 * @param c Context
	 * @param photos Photos to be displayed in the gallery
	 */
	public PicAdapter(Context c, List<Photo> photos) {
		galleryContext = c;
		imagePhotos = photos;
		TypedArray styleAttrs = galleryContext.obtainStyledAttributes(
				R.styleable.PicGallery);
		defaultItemBackground = styleAttrs.getResourceId(
				R.styleable.PicGallery_android_galleryItemBackground, 0);
		styleAttrs.recycle();
	}

	/**
	 * Enlarges the photo in the gallery allowing the user to delete it as well.
	 * @param position the position of the photo in the gallery
	 */
	public void enlargePhoto(final int position) {
		final Dialog picDialog = new Dialog(galleryContext);
		picDialog.setContentView(R.layout.custom_dialog_display);
		picDialog.setCancelable(true);
		ImageView imgView = 
				(ImageView) picDialog.findViewById(R.id.enlargedImage);

		/*
		 * if we have image in the list then create a dialog to be
		 * displayed when the user clicks on a certain image.
		 * 
		 * the dialog enlarges the photo clicked
		 */
		if(imagePhotos.size() > 0){
			imgView.setImageBitmap(imagePhotos.get(position).getPhoto());
			Button imageDeleteButton = 
					(Button) picDialog.findViewById(R.id.imgDeleteButton);
			Button imageCancelButton = 
					(Button) picDialog.findViewById(R.id.imgCancelButton);					

			imageDeleteButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					imagePhotos.remove(position);
					notifyDataSetChanged();
					picDialog.dismiss();
				}
			});

			imageCancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					picDialog.dismiss();
				}
			});

			picDialog.show();
		}
	}

	/**
	 * @return number of photos in the gallery
	 */
	@Override
	public int getCount() {
		return imagePhotos.size();
	}

	@Override
	/**
	 * @return Image at @param position
	 */
	public Object getItem(int position) {
		return position;
	}

	@Override
	/**
	 * @return ID of the image at @param position
	 */
	public long getItemId(int position) {
		return position;
	}

	@Override
	/**
	 * Displays the images in the provided gallery
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(galleryContext);
		imageView.setImageBitmap(imagePhotos.get(position).getPhoto());
		imageView.setLayoutParams(new Gallery.LayoutParams(200, 180));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setBackgroundResource(defaultItemBackground);
		return imageView;
	}
}
