package com.android.launcher3.blur;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by puming1 on 12/22/16.
 * 用于缓存高斯模糊效果的Bitmap
 */
public class BlurLruCache extends LruCache<String,Bitmap> {

//    private  BlurLruCache sBlurLruCache;

    public BlurLruCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }

    public void clearCaChe(){
        if (size()>0) {
            evictAll();
        }
    }

   /* public static BlurLruCache getBlurLruCache(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory() / 16;
        if(sBlurLruCache==null){
            synchronized (BlurLruCache.class){
                if (sBlurLruCache==null) {
                    sBlurLruCache=new BlurLruCache(maxMemory);
                }
            }
        }
        return sBlurLruCache;
    }*/
}
