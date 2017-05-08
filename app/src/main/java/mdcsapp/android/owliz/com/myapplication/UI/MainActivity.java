package mdcsapp.android.owliz.com.myapplication.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import mdcsapp.android.owliz.com.myapplication.Logic.VerifyLogin;
import mdcsapp.android.owliz.com.myapplication.R;

/**
 * Created by owliz on 2017/5/8.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_ID = (EditText) findViewById(R.id.et_ID);
        final EditText et_pswd = (EditText) findViewById(R.id.et_pswd);
        // CircularProgressButton element
        final CircularProgressButton btn_login = (CircularProgressButton) findViewById(R.id.btn_login);
        TextView tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);

        // turn on indeterminate progress
        btn_login.setIndeterminateProgressMode(true);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setProgress(20);
                final String myId = et_ID.getText().toString();
                final String myPswd = et_pswd.getText().toString();

// Validate user input
                if (myId.isEmpty()) {
                    et_ID.setError("email address is required!");
                    btn_login.setProgress(0);
                    return;
                }
                if (myPswd.isEmpty()) {
                    et_pswd.setError("password is required!");
                    btn_login.setProgress(0);
                    return;
                }

//create an anonymous AsyncTask
                new AsyncTask<String, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(String... params) {

                        if ((VerifyLogin.verifyLogin(myId, myPswd)).equals("[]")) {
                            if (!(VerifyLogin.getId(myId)).equals("[]")) {
                                return 2;
                            }
                            return 1;
                        } else {
                            return 0;
                        }
                    }

                    @Override
                    protected void onPostExecute(Integer response) {
                        if (response == 1) {
                            et_ID.setError("Incorrect username or password.");
                            btn_login.setProgress(0);
                        } else if (response == 2) {
                            et_pswd.setError("Incorrect password.");
                            btn_login.setProgress(0);
                        }
                        else if (response == 0) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "welcome back!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    //start intent
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000);


                        }

                    }
                }.execute();
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                //start intent
                startActivity(intent);
                finish();
            }
        });
    }
}