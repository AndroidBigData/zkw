package com.zjwam.zkw.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.security.AllPermission;

public class GlideImageUtil {

    public static void setImageView(Context context, String url, ImageView view,RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(view);
    }

    public static RequestOptions roundTransform(int radius) {
        return new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(radius));
    }

    public static RequestOptions circleTransform() {
        return new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform());
    }

}
