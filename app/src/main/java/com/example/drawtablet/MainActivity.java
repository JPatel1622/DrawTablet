package com.example.drawtablet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Draw draw;
    AlertDialog.Builder alertDialog;
    AlertDialog dialog;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draw = findViewById(R.id.draw3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.widthID){
            showWidth();
        }

        if(item.getItemId() == R.id.colorID){
            showColor();
        }

        if(item.getItemId() == R.id.deleteID){
            draw.delete();
        }

        if(item.getItemId() == R.id.saveID){
            draw.save();
        }

        if(item.getItemId() == R.id.eraseID){
            int currentColor = draw.pencil.getColor();
            if(currentColor != Color.WHITE){
                draw.pencil.setColor(Color.WHITE);
            } else {
                draw.pencil.setColor(color);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showWidth(){
        alertDialog = new AlertDialog.Builder(this);
        View viewA = getLayoutInflater().inflate(R.layout.width, null);
        Button setWidth = viewA.findViewById(R.id.buttonWidth);
        setWidth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editWidth = viewA.findViewById(R.id.editWidth);
                String widthS = editWidth.getText().toString();
                int width = Integer.parseInt(widthS);
                draw.setR(width);
                dialog.dismiss();
                alertDialog = null;
            }
        });

        alertDialog.setView(viewA);
        dialog = alertDialog.create();
        dialog.show();

    }

    public void showColor(){
        alertDialog = new AlertDialog.Builder(this);
        View viewA = getLayoutInflater().inflate(R.layout.color, null);

        Button setColor = viewA.findViewById(R.id.buttonColor);
        setColor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editA = viewA.findViewById(R.id.editA);
                EditText editR = viewA.findViewById(R.id.editR);
                EditText editG = viewA.findViewById(R.id.editG);
                EditText editB = viewA.findViewById(R.id.editB);
                String aS = editA.getText().toString();
                int a = Integer.parseInt(aS);
                String rS = editR.getText().toString();
                int r = Integer.parseInt(rS);
                String gS = editG.getText().toString();
                int g = Integer.parseInt(gS);
                String bS = editB.getText().toString();
                int b = Integer.parseInt(bS);

                draw.setPencilColor(a, r, b, g);
                color = draw.pencil.getColor();
                dialog.dismiss();
                alertDialog = null;

            }
        });

        Button testColor = viewA.findViewById(R.id.buttonTest);
        testColor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editA = viewA.findViewById(R.id.editA);
                EditText editR = viewA.findViewById(R.id.editR);
                EditText editG = viewA.findViewById(R.id.editG);
                EditText editB = viewA.findViewById(R.id.editB);
                String aS = editA.getText().toString();
                int a = Integer.parseInt(aS);
                String rS = editR.getText().toString();
                int r = Integer.parseInt(rS);
                String gS = editG.getText().toString();
                int g = Integer.parseInt(gS);
                String bS = editB.getText().toString();
                int b = Integer.parseInt(bS);

                TextView testColor = viewA.findViewById(R.id.testColor);
                testColor.setBackgroundColor(Color.argb(a, r, g, b));

            }
        });


        alertDialog.setView(viewA);
        dialog = alertDialog.create();
        dialog.show();


    }

}