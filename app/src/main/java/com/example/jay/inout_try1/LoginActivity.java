package com.example.jay.inout_try1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jay.inout_try1.mysql_connection.SigninActivity;

public class LoginActivity extends AppCompatActivity{

    EditText uname_et;
    EditText pwd_et;
    Button join;
    TextView forgotPwd;

    String username,password;

    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("LOGIN", "Activity started");
        uname_et=(EditText) findViewById(R.id.voter_id);
        pwd_et=(EditText) findViewById(R.id.password);

        join=(Button) findViewById(R.id.sign_in_button);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=uname_et.getText().toString();
                password=pwd_et.getText().toString();

                String method="join";
                SigninActivity signinActivity=new SigninActivity(LoginActivity.this);
                signinActivity.execute(method,username,password);
            }
        });

        forgotPwd=(TextView)findViewById(R.id.forgotPwd_tv);
        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=uname_et.getText().toString();

                String method="forgot";
                SigninActivity signinActivity=new SigninActivity(LoginActivity.this);
                signinActivity.execute(method,username);
            }
        });

        Log.i("LOGIN", "Activity end");
    }

}