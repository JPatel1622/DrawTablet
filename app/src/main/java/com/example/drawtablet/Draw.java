package com.example.drawtablet;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;

import static android.graphics.Paint.DITHER_FLAG;

public class Draw extends View {

    Paint pencil;
    float x, y;
    Path path;
    Bitmap bitmap;
    Paint bitmapPaint;
    Canvas bmCanvas;



    public Draw(Context context) {
        super(context);
    }

    public Draw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setColor(Color.BLACK);
        pencil.setStrokeWidth(7);
        pencil.setStrokeJoin(Paint.Join.ROUND);
        pencil.setStrokeCap(Paint.Cap.ROUND);
        pencil.setStyle(Paint.Style.STROKE);
        bitmapPaint = new Paint(DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, pencil);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        x = event.getX();
        y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    path.lineTo(x, y);
                    bmCanvas.drawPath(path, pencil);
                    path.reset();
                    break;
                default:
                    return false;
            }

        this.invalidate();
        return true;

    }


    public void delete(){
        path.reset();
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setPencilColor(int a, int r, int b, int g){
        pencil.setARGB(a, r, g, b);
    }

    public void setR(int r1){
        pencil.setStrokeWidth(r1);
    }

    public void save(){
        String fName = "drawing";

        ContentValues value = new ContentValues();
        value.put(MediaStore.Images.Media.TITLE, fName);
        value.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());

        Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);


        OutputStream outputStream;

        try {
            outputStream = getContext().getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

            Toast msg = Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG);
            msg.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}


