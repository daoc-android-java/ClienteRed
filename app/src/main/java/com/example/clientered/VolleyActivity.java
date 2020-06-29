package com.example.clientered;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends Activity {
    RequestQueue queue;
    TextView tv;
    Response.Listener respOk;
    Response.ErrorListener respErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        tv = findViewById(R.id.tv);

        queue = Volley.newRequestQueue(this);

        respOk = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText("OK: " + response);
            }
        };

        respErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("ERROR: " + error.getMessage());
            }
        };
    }

    public void doGet(View view) { //DELETE es igual
        double rnd = Math.random();
        String url = "http://10.0.2.2:8080/algo?num=" + rnd;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, respOk, respErr);

        queue.add(stringRequest);
    }

    public void doPost(View view) { //PUT es igual
        String url = "http://10.0.2.2:8080/body";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, respOk, respErr) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return "Este es el cuerpo del POST".getBytes();
            }
        };

        queue.add(stringRequest);
    }
}