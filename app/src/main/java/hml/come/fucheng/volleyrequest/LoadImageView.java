package hml.come.fucheng.volleyrequest;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import dagger.Module;
import hml.come.fucheng.mvp.component.DaggerAppComponent;

import static com.android.volley.toolbox.ImageLoader.*;

/**
 * Created by tangdi on 8/15/17.
 */
public class LoadImageView {

    private ImageView imageView;
    private String url;
    private ImageLoader imageLoader;
    private ImageListener imageListener;

    public LoadImageView(ImageView imageView, String url){
        this.imageView = imageView;
        this.url = url;
        imageLoader = new ImageLoader(SingleToneRequestQueue.getInstance().requestQueue(), new ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        //imageListener = ImageLoader.getImageListener(imageView, )
    }

}
