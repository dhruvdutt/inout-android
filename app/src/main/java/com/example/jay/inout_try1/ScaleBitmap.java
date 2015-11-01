package com.example.jay.inout_try1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by jay on 01/11/2015.
 */
public class ScaleBitmap {
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage){
        int targetWidth=600;
        int targetHeight=600;

        Bitmap targetBitmap=Bitmap.createBitmap(targetWidth,targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas=new Canvas(targetBitmap);
        Path path=new Path();
        path.addCircle(
                ((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW
        );

        canvas.clipPath(path);
        Bitmap sourceBitmap=scaleBitmapImage;
        canvas.drawBitmap(
                sourceBitmap,
                new Rect(0,0,sourceBitmap.getWidth(),sourceBitmap.getHeight()),
                new Rect(0,0,targetWidth,targetHeight),
                null
                );

        return targetBitmap;
    }
}
