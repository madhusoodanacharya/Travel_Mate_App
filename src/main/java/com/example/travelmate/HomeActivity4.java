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

public class HomeActivity4 extends AppCompatActivity {

    private CardView fouCardView, fouCardView2, fouCardView3;

    private StorageReference fouStorageReference, fouStorageReference2, fouStorageReference3;

    private TextView fouTextView2;

    DecimalFormat df = new DecimalFormat("#.##");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home4);

        fouCardView = findViewById(R.id.fouCardView);
        fouCardView2 = findViewById(R.id.fouCardView2);
        fouCardView3 = findViewById(R.id.fouCardView3);
        fouTextView2 = findViewById(R.id.fouTextView2);

        String tempUrl = "http://api.openweathermap.org/data/2.5/weather?q=Dehli,India&APPID=9fc3e491b7e306d955956d37b0daee0e";

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
                            fouTextView2.setText(output);
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

        fouStorageReference = FirebaseStorage.getInstance().getReference().child("dehli/red.jpg");
        fouStorageReference2 = FirebaseStorage.getInstance().getReference().child("dehli/lotus.jpg");
        fouStorageReference3 = FirebaseStorage.getInstance().getReference().child("dehli/gate.jpg");

        try {
            final File localFile = File.createTempFile("red", "jpg");
            final File localFile2 = File.createTempFile("lotus", "jpg");
            final File localFile3 = File.createTempFile("gate", "jpg");

            fouStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fouView)).setImageBitmap(bitmap);
                        }
                    });

            fouStorageReference2.getFile(localFile2)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fouView2)).setImageBitmap(bitmap);
                        }
                    });

            fouStorageReference3.getFile(localFile3)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile3.getAbsolutePath());
                            ((ImageView) findViewById(R.id.fouView3)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        fouCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Red+Fort/@28.6561592,77.2410203,15z/data=!4m5!3m4!1s0x0:0x441e32f4fa5002fb!8m2!3d28.6561592!4d77.2410203"));
                startActivity(intent);
            }
        });

        fouCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Lotus+Temple/@28.553492,77.2588264,15z/data=!4m5!3m4!1s0x0:0x653beb1ee85ec67a!8m2!3d28.553492!4d77.2588264"));
                startActivity(intent);
            }
        });

        fouCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/India+Gate/@28.612912,77.2295097,15z/data=!4m5!3m4!1s0x0:0x717971125923e5d!8m2!3d28.612912!4d77.2295097"));
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