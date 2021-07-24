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

public class HomeActivity3 extends AppCompatActivity {

    private CardView thiCardView, thiCardView2, thiCardView3;

    private StorageReference thiStorageReference, thiStorageReference2, thiStorageReference3;

    private TextView thiTextView2;

    DecimalFormat df = new DecimalFormat("#.##");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);

        thiCardView = findViewById(R.id.thiCardView);
        thiCardView2 = findViewById(R.id.thiCardView2);
        thiCardView3 = findViewById(R.id.thiCardView3);
        thiTextView2 = findViewById(R.id.thiTextView2);

        String tempUrl = "http://api.openweathermap.org/data/2.5/weather?q=Chennai,India&APPID=9fc3e491b7e306d955956d37b0daee0e";

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
                            thiTextView2.setText(output);
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

        thiStorageReference = FirebaseStorage.getInstance().getReference().child("chennai/marina.jpg");
        thiStorageReference2 = FirebaseStorage.getInstance().getReference().child("chennai/george.jpg");
        thiStorageReference3 = FirebaseStorage.getInstance().getReference().child("chennai/anna.jpg");

        try {
            final File localFile = File.createTempFile("marina", "jpg");
            final File localFile2 = File.createTempFile("george", "jpg");
            final File localFile3 = File.createTempFile("anna", "jpg");

            thiStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView) findViewById(R.id.thiView)).setImageBitmap(bitmap);
                        }
                    });

            thiStorageReference2.getFile(localFile2)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                            ((ImageView) findViewById(R.id.thiView2)).setImageBitmap(bitmap);
                        }
                    });

            thiStorageReference3.getFile(localFile3)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile3.getAbsolutePath());
                            ((ImageView) findViewById(R.id.thiView3)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        thiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Marina+Beach/@13.0437224,80.2663439,14z/data=!3m1!4b1!4m5!3m4!1s0x3a52689bb60132bb:0x549cf8edf62b60a!8m2!3d13.0499526!4d80.2824026"));
                startActivity(intent);
            }
        });

        thiCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Fort+St+George,+Chennai,+Tamil+Nadu/@13.080028,80.2806821,16z/data=!3m1!4b1!4m5!3m4!1s0x3a5268acffd2d779:0xb5d9bf27190ed0e!8m2!3d13.0813203!4d80.2864862"));
                startActivity(intent);
            }
        });

        thiCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Arignar+Anna+Zoological+Park/@12.8793318,80.0797229,17z/data=!3m1!4b1!4m5!3m4!1s0x3a52f604b07fd4cd:0xb2f00bc9eb6c9c86!8m2!3d12.8793266!4d80.0819116"));
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