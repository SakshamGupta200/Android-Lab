package com.example.graphicalprimitiveapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Create a Bitmap
        Bitmap bg = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);

        // Set the Bitmap as the background for the ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setBackgroundDrawable(new BitmapDrawable(bg));

        // Create a Canvas object
        Canvas canvas = new Canvas(bg);

        // Create a Paint object and set its color & textSize
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);

        // Draw a Circle
        canvas.drawText("Circle", 120, 150, paint);
        canvas.drawCircle(200, 350, 150, paint);

        // Draw a Rectangle
        canvas.drawText("Rectangle", 420, 150, paint);
        canvas.drawRect(400, 200, 650, 700, paint);

        // Draw a Line
        canvas.drawText("Line", 120, 800, paint);
        canvas.drawLine(520, 850, 520, 1150, paint);

        // Draw a Square
        canvas.drawText("Square", 480, 800, paint);
        canvas.drawRect(50, 850, 350, 1150, paint);
    }
}