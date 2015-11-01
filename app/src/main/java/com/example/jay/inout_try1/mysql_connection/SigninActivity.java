package com.example.jay.inout_try1.mysql_connection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jay.inout_try1.UserActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by jay on 31/10/2015.
 */
public class SigninActivity extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context context;
    String method;

    public static final String USER_NAME = "username";
    String username;

    public SigninActivity(Context context){
        this.context=context;
        Log.i("LOGIN", "Signin Activity start");
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Information");
        Log.i("LOGIN", "Signin Activity pre execute");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("LOGIN", "Signin Activity bg start");
        String join_url;
        method=params[0];
        if(method.equalsIgnoreCase("join")){
            username=params[1];
            String password=params[2];
            join_url="http://192.168.1.105/inout/appLogin.php";
            try {
                URL url=new URL(join_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get info from server
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equalsIgnoreCase("forgot")){
            username=params[1];
            Log.i("FORGOT",username);
            join_url="http://192.168.1.105/inout/ForgotPasswordApp.php";
            try {
                URL url=new URL(join_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.i("FORGOT", method+"1");

                OutputStream outputStream=httpURLConnection.getOutputStream();
                Log.i("FORGOT",method+"2");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                Log.i("FORGOT",method+"3");
                String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                Log.i("DATA",data);
                bufferedWriter.write(data);
                Log.i("FORGOT", method + "5");
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get info from server
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("RESPONSE",response);
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("LOGIN", "Signin Activity post execute");
        if(method.equalsIgnoreCase("join")){
            if(result.equalsIgnoreCase("Registration Successfull")){
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, UserActivity.class);
                intent.putExtra(USER_NAME,username);
                context.startActivity(intent);
            }else {
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }
    }
}
