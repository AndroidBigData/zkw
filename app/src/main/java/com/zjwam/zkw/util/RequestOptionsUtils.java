package com.zjwam.zkw.util;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;

public class RequestOptionsUtils {
    public static RequestOptions roundTransform(int radius) {
        return new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.homepage_background)
                .transform(new GlideRoundTransform(radius));
    }
    public static RequestOptions circleTransform() {
        return new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform());
    }
    public static RequestOptions commonTransform() {
        return new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
