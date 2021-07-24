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

public class HomeActivity6 extends AppCompatActivity {

    private CardView sixCardView, sixCardView2, sixCardView3;

    private StorageReference sixStorageReference, sixStorageReference2, sixStorageReference3;

    private TextView sixTextView2;

    DecimalFormat df = new DecimalFormat("#.##");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home6);

        sixCardView = findViewById(R.id.sixCardView);
        sixCardView2 = findViewById(R.id.sixCardView2);
        sixCardView3 = findViewById(R.id.sixCardView3);
        sixTextView2 = findViewById(R.id.sixTextView2);

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
                            sixTextView2.setText(output);
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

        sixStorageReference = FirebaseStorage.getInstance().getReference().child("mysore/palace.jpg");
        sixStorageReference2 = FirebaseStorage.getInstance().getReference().child("mysore/church.jpg");
        sixStorageReference3 = FirebaseStorage.getInstance().getReference().child("mysore/lake.jpg");

        try {
            final File localFile = File.createTempFile("palace", "jpg");
            final File localFile2 = File.createTempFile("church", "jpg");
            final File localFile3 = File.createTempFile("lake", "jpg");

            sixStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView) findViewById(R.id.sixView)).setImageBitmap(bitmap);
                        }
                    });

            sixStorageReference2.getFile(localFile2)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                            ((ImageView) findViewById(R.id.sixView2)).setImageBitmap(bitmap);
                        }
                    });

            sixStorageReference3.getFile(localFile3)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile3.getAbsolutePath());
                            ((ImageView) findViewById(R.id.sixView3)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        sixCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Mysore+Palace/@12.305163,76.6551749,15z/data=!4m5!3m4!1s0x0:0xc37fbae2a124da0d!8m2!3d12.305163!4d76.6551749"));
                startActivity(intent);
            }
        });

        sixCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/St.+Philomena's+Cathedral/@12.3210417,76.6582635,15z/data=!4m5!3m4!1s0x0:0x8e900b9cb740e32d!8m2!3d12.3208715!4d76.6582409"));
                startActivity(intent);
            }
        });

        sixCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Karanji+Lake/@12.3027772,76.6561014,14z/data=!3m1!4b1!4m5!3m4!1s0x3baf70252f9ae3a3:0x687454ab78a369e1!8m2!3d12.302778!4d76.673611"));
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