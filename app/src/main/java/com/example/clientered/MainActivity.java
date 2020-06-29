package com.example.clientered;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


//http://developer.android.com/training/basics/network-ops/connecting.html
//http://developer.android.com/reference/android/os/AsyncTask.html
public class MainActivity extends Activity {
	private TextView tv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView) findViewById(R.id.tv);
    }
    
    
    // Este método bota un error: NetworkOnMainThreadException
    public void getSync(View view) { 	
    	tv.setText("......");
    	
    	if(!estaConectado()) {
    		Toast.makeText(this, "No está conectado", Toast.LENGTH_SHORT).show();
    		return;
    	}
		//en el emulador, 10.0.2.2 (10.0.3.2 Genymotion) equivale a localhost (su PC)
		tv.setText( download("http://10.0.2.2:8080/") );
    }

    
    // Este es el método recomendado
    public void getAsync(View view) {
    	tv.setText("......");
    	
    	if(!estaConectado()) {
    		Toast.makeText(this, "No está conectado", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	//en el emulador, 10.0.2.2 (10.0.3.2 Genymotion) equivale a localhost (su PC)
    	new ConnectAsync().execute("http://10.0.2.2:8080/");
    }    

    private boolean estaConectado() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getActiveNetworkInfo().isConnected();
    }    
    
    
    private String download(String urlstr) {
		try {
			URL url = new URL(urlstr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true); 		
	        conn.connect();
	        
	        int buffersize = conn.getContentLength();
	        byte[] buffer = new byte[buffersize];
	        
	        InputStream is = conn.getInputStream();
	        is.read(buffer);
	        is.close();
	        conn.disconnect();
	        
	        return new String(buffer);
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR de conexión";
		}    	
    }    
    
    
    private class ConnectAsync extends AsyncTask<String, Void, String> {   	
		@Override
		protected String doInBackground(String... urls) {			
			return download(urls[0]);	
		}
		
        @Override
        protected void onPostExecute(String result) {
        	tv.setText(result);
        }	
    }
    
}
