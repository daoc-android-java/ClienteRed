package com.example.clientered;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SpringActivity extends Activity {
    TextView tv;
    RestTemplate rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);

        tv = findViewById(R.id.tv);
        rest = new RestTemplate();
    }

    public void doGet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double rnd = Math.random();
                String url = "http://10.0.2.2:8080/algo?num=" + rnd;
                final String resp = rest.getForObject(url, String.class);
                SpringActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(resp.toString());
                    }
                });
            }
        }).start();
    }

    public void doPost(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double rnd = Math.random();
                String url = "http://10.0.2.2:8080/body";
                final String resp = rest.postForObject(url, "Este es el cuerpo de mi post", String.class);
                SpringActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(resp.toString());
                    }
                });
            }
        }).start();
    }

}