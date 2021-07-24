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

public class HomeActivity2 extends AppCompatActivity {

    private CardView secCardView, secCardView2, secCardView3;

    private StorageReference secStorageReference, secStorageReference2, secStorageReference3;

    private TextView secTextView2;

    DecimalFormat df = new DecimalFormat("#.##");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        secCardView = findViewById(R.id.secCardView);
        secCardView2 = findViewById(R.id.secCardView2);
        secCardView3 = findViewById(R.id.secCardView3);
        secTextView2 = findViewById(R.id.secTextView2);

        String tempUrl = "http://api.openweathermap.org/data/2.5/weather?q=Bangalore,India&APPID=9fc3e491b7e306d955956d37b0daee0e";

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
                            secTextView2.setText(output);
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

        secStorageReference = FirebaseStorage.getInstance().getReference().child("bangalore/lalbhag.png");
        secStorageReference2 = FirebaseStorage.getInstance().getReference().child("bangalore/bannerghatta.jpg");
        secStorageReference3 = FirebaseStorage.getInstance().getReference().child("bangalore/palace.jpg");

        try {
            final File localFile = File.createTempFile("lalbhag", "png");
            final File localFile2 = File.createTempFile("bannerghatta", "jpg");
            final File localFile3 = File.createTempFile("palace", "jpg");

            secStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView) findViewById(R.id.secView)).setImageBitmap(bitmap);
                        }
                    });

            secStorageReference2.getFile(localFile2)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                            ((ImageView) findViewById(R.id.secView2)).setImageBitmap(bitmap);
                        }
                    });

            secStorageReference3.getFile(localFile3)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile3.getAbsolutePath());
                            ((ImageView) findViewById(R.id.secView3)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        secCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Lalbagh+Botanical+Garden/@12.9507484,77.5825886,17z/data=!3m1!4b1!4m5!3m4!1s0x3bae15c191f2d31d:0x8e110b99df2fbe22!8m2!3d12.9507432!4d77.5847773"));
                startActivity(intent);
            }
        });

        secCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Bannerghatta+Biological+Park/@12.7985882,77.567835,15z/data=!4m5!3m4!1s0x0:0x4f6f2e78ffa13a5f!8m2!3d12.7985882!4d77.567835?hl=en-IN"));
                startActivity(intent);
            }
        });

        secCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Bangalore+Palace/@12.9987712,77.5899184,17z/data=!3m1!4b1!4m5!3m4!1s0x3bae1649294a5637:0xb1f8b77e331512cf!8m2!3d12.998766!4d77.5921071?hl=en-IN"));
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
