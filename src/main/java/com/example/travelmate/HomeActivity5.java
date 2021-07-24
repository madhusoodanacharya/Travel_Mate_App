package com.example.travelmate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class HomeActivity5 extends AppCompatActivity {

    private CardView fivCardView, fivCardView2, fivCardView3;

    private StorageReference fivStorageReference, fivStorageReference2, fivStorageReference3;

    private TextView fivTextView2;

    DecimalFormat df = new DecimalFormat("#.##");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home5);

        fivCardView = findViewById(R.id.fivCardView);
        fivCardView2 = findViewById(R.id.fivCardView2);
        fivCardView3 = findViewById(R.id.fivCardView3);
        fivTextView2 = findViewById(R.id.fivTextView2);

        String tempUrl = "http://api.openweathermap.org/data/2.5/weather?q=Mumbai,India&APPID=9fc3e491b7e306d955956d37b0daee0e";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            String output = "";
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            output = "Temperature: " + df.format(temp) + "°C";
                            fivTextView2.setText(output);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        fivStorageReference = FirebaseStorage.getInstance().getReference().child("mumbai/marine.jpg");
        fivStorageReference2 = FirebaseStorage.getInstance().getReference().child("mumbai/cave.jpg");
        fivStorageReference3 = FirebaseStorage.getInstance().getReference().child("mumbai/gateway.jpg");

        try {
            final File localFile = File.createTempFile("marine", "jpg");
            final File localFile2 = File.createTempFile("cave", "jpg");
            final File localFile3 = File.createTempFile("gateway", "jpg");

            fivStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fivView)).setImageBitmap(bitmap);
                        }
                    });

            fivStorageReference2.getFile(localFile2)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fivView2)).setImageBitmap(bitmap);
                        }
                    });

            fivStorageReference3.getFile(localFile3)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile3.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fivView3)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        fivCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Queen's+Necklace+-+Marine+Drive/@18.9298297,72.8217287,15z/data=!4m5!3m4!1s0x0:0x67f61f36b0677361!8m2!3d18.9298297!4d72.8217287"));
                startActivity(intent);
            }
        });

        fivCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Elephanta+Caves/@18.9633474,72.9314864,15z/data=!4m5!3m4!1s0x0:0xf4ab181b1aaef53f!8m2!3d18.9633474!4d72.9314864"));
                startActivity(intent);
            }
        });

        fivCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Gateway+Of+India+Mumbai/@18.9219841,72.8346543,15z/data=!4m5!3m4!1s0x0:0xc70a25a7209c733c!8m2!3d18.9219841!4d72.8346543"));
                startActivity(intent);
            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        this.getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        
    }
}