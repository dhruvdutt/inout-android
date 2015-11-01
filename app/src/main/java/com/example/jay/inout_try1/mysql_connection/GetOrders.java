package com.example.jay.inout_try1.mysql_connection;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.inout_try1.R;
import com.example.jay.inout_try1.UserActivity;
import com.example.jay.inout_try1.adapter.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jay on 01/11/2015.
 */
public class GetOrders extends AsyncTask<String,Void,String[][]> {

    Context context;
    private String username;
    View view;

    public static RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;

    public GetOrders(Context context,View view, RecyclerView mRecyclerView, RecyclerView.Adapter mAdapter){
        this.context=context;
        username= UserActivity.username;
        this.view=view;
        this.mRecyclerView=mRecyclerView;
        this.mAdapter=mAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[][] doInBackground(String... params) {
        String result="";
        InputStream inputStream=null;
        String orderUrl = null;

        String method=params[0];
        if(method.equalsIgnoreCase("past")){
            orderUrl="http://192.168.1.105/inout/getPastOrder.php";
        }else if(method.equalsIgnoreCase("current")){
            orderUrl="http://192.168.1.105/inout/getCurrentOrder.php";
        }

        try{
            URL url=new URL(orderUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data= URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //get info from server

            inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            result=stringBuilder.toString();

            if(result==null){
                return null;
            }
            return parseJsonData(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String[][] s) {

        if (!(s == null)) {
            mAdapter = new RecyclerAdapter(s,view);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            view= View.inflate(this.context, R.layout.no_records_layout, new ViewGroup(this.context) {
                @Override
                protected void onLayout(boolean changed, int l, int t, int r, int b) {

                }
            });
        }
    }

    public String[][] parseJsonData(String result){
        JSONArray jsonArray= null;
        String detailsArr[][]=null;
        try {
            jsonArray = new JSONArray(result);

            if(jsonArray==null){
                return null;
            }
            detailsArr = new String[jsonArray.length()][8];

            Log.i("LENGTH",String.valueOf(jsonArray.length()));
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                detailsArr[i][0]=jsonObject.getString("O_ID");
                Log.i("ARRAY_O_ID",detailsArr[i][0]);
                detailsArr[i][1]=jsonObject.getString("O_DATE");
                detailsArr[i][2]=jsonObject.getString("SKILL_NAME");
                detailsArr[i][3]=jsonObject.getString("A_FNAME");
                detailsArr[i][4]=jsonObject.getString("QUANTITY");
                detailsArr[i][5]=jsonObject.getString("COMP_NAME");
                detailsArr[i][6]=jsonObject.getString("NOTE");
                detailsArr[i][7]=jsonObject.getString("STATUS");

                Log.i("ARRAY",detailsArr[i][7]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detailsArr;
    }
}
