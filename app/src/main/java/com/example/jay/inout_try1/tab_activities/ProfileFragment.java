package com.example.jay.inout_try1.tab_activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jay.inout_try1.R;
import com.example.jay.inout_try1.UserActivity;
import com.example.jay.inout_try1.mysql_connection.GetImage;

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
 * Created by jay on 31/10/2015.
 */
public class ProfileFragment extends Fragment {

    public static View view;
    TextView voterId,fname,mname,lname,dob,gender,address,phoneno;

    public String username;

    public ProfileFragment(){
        username=UserActivity.username;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);

        getDP(username);

        voterId=(TextView)view.findViewById(R.id.voter_id_tv);
        fname=(TextView)view.findViewById(R.id.fname_tv);
        mname=(TextView)view.findViewById(R.id.mname_tv);
        lname=(TextView)view.findViewById(R.id.lname_tv);
        dob=(TextView)view.findViewById(R.id.dob_tv);
        gender=(TextView)view.findViewById(R.id.gender_tv);
        address=(TextView)view.findViewById(R.id.address_tv);
        phoneno=(TextView)view.findViewById(R.id.phone_tv);

        GetUserDetails getUserDetails=new GetUserDetails(this.getContext());
        getUserDetails.execute();

        return view;
    }

    public void getDP(String uid){
        GetImage getImage=new GetImage(this.getContext(),view);
        getImage.execute(uid);
    }

    public class GetUserDetails extends AsyncTask<String,Void,String[]> {

        Context context;


        public GetUserDetails(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String result="";
            InputStream inputStream=null;
            String userUrl = "http://192.168.1.105/inout/getArtisanData.php";

            try{
                URL url=new URL(userUrl);
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
        protected void onPostExecute(String[] s) {
            voterId.setText(username);
            fname.setText(s[0]);
            Log.i("S[0]", s[0]);
            mname.setText(s[1]);
            Log.i("S[0]", s[2]);
            lname.setText(s[2]);
            Log.i("S[0]", s[2]);
            dob.setText(s[3]);
            Log.i("S[0]", s[3]);
            gender.setText(s[4]);
            Log.i("S[0]", s[4]);
            address.setText(s[5]);
            Log.i("S[0]", s[5]);
            phoneno.setText(s[6]);
            Log.i("S[0]", s[6]);
        }

        public String[] parseJsonData(String result){
            //JSONArray jsonArray= null;
            String detailsArr[]=null;
            try {
                //jsonArray = new JSONArray(result);

                JSONObject jsonObject=new JSONObject(result);
              //  if(jsonArray==null){
              //      return null;
              //  }
                detailsArr = new String[jsonObject.length()];

                Log.i("LENGTH", String.valueOf(jsonObject.length()));

               // JSONObject jsonObject=jsonArray.getJSONObject(0);
                detailsArr[0]=jsonObject.getString("1");
                Log.i("ARRAY_O_ID",detailsArr[0]);
                detailsArr[1]=jsonObject.getString("2");
                detailsArr[2]=jsonObject.getString("3");
                detailsArr[3]=jsonObject.getString("4");
                detailsArr[4]=jsonObject.getString("5");
                detailsArr[5]=jsonObject.getString("6");
                detailsArr[6]=jsonObject.getString("7");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return detailsArr;
        }
    }
}
