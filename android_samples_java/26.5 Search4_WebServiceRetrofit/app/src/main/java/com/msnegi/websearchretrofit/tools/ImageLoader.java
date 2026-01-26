package com.msnegi.websearchretrofit.tools;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.msnegi.websearchretrofit.R;
import com.squareup.picasso.Target;

import java.io.File;

public class ImageLoader {

    public static void LoadImageCenterInside(Context context, String url, int width, int height, final ImageView imageView){
        if(url != null && !url.isEmpty()){
            if(url.startsWith("http")){
                PicassoCache.getPicassoInstance(context).load(url)
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerInside()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }else {
                PicassoCache.getPicassoInstance(context).load(new File(url))
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerInside()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }
        }else {
            imageView.setImageResource(R.drawable.placeholder);
        }
    }

    public static void LoadImageCenterInside(Context context, String url, int width, int height, final Target target){
        if(url != null && !url.isEmpty()){
            if(url.startsWith("http")){
                PicassoCache.getPicassoInstance(context).load(url)
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerInside()
                        .error(R.drawable.placeholder)
                        .into(target);
            }else {
                PicassoCache.getPicassoInstance(context).load(new File(url))
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerInside()
                        .error(R.drawable.placeholder)
                        .into(target);
            }
        }else {
            target.onBitmapFailed(ContextCompat.getDrawable(context, R.drawable.placeholder));
            //imageView.setImageResource(R.drawable.placeholder);
        }
    }

    public static void LoadImageCenterCrop(Context context, String url, int width, int height, ImageView imageView){
        if(url != null && !url.isEmpty()){
            if(url.startsWith("http")){
                PicassoCache.getPicassoInstance(context).load(url)
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }else {
                PicassoCache.getPicassoInstance(context).load(new File(url))
                        .resize(width, height)
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }
        }else {
            imageView.setImageResource(R.drawable.placeholder);
        }
    }

    public static void LoadImageCenterCrop(Context context, String url, int width, int height, ImageView imageView, int placeholder){
        if(url != null && !url.isEmpty()){
            if(url.startsWith("http")){
                PicassoCache.getPicassoInstance(context).load(url)
                        .resize(width, height)
                        .placeholder(placeholder)
                        .centerCrop()
                        .error(placeholder)
                        .into(imageView);
            }else {
                PicassoCache.getPicassoInstance(context).load(new File(url))
                        .resize(width, height)
                        .placeholder(placeholder)
                        .centerCrop()
                        .error(placeholder)
                        .into(imageView);
            }
        }else {
            imageView.setImageResource(R.drawable.placeholder);
        }
    }

    public static void LoadImageCenterCrop(Context context, int res, int width, int height, ImageView imageView){
        if(res > 0){
            PicassoCache.getPicassoInstance(context).load(res)
                    .resize(width, height)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .error(R.drawable.placeholder)
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.placeholder);
        }
    }

    public static void LoadImageFit(Context context, String url, ImageView imageView){
        if(url != null && !url.isEmpty()){
            if(url.startsWith("http")){
                PicassoCache.getPicassoInstance(context).load(url)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }else {
                PicassoCache.getPicassoInstance(context).load(new File(url))
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }
        }else {
            imageView.setImageResource(R.drawable.placeholder);
        }
    }

}
