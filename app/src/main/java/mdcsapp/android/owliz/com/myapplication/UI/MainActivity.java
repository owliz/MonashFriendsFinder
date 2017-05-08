package mdcsapp.android.owliz.com.myapplication.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        Button btn_login = (Button) findViewById(R.id.btn_login);
        TextView tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                final String myId = et_ID.getText().toString();
                final String myPswd = et_pswd.getText().toString();

// Validate user input
                if (myId.isEmpty()) {
                    et_ID.setError("email address is required!");
                    return;
                }
                if (myPswd.isEmpty()) {
                    et_pswd.setError("password is required!");
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
                            System.out.println("-------------1");
                            et_ID.setError("Incorrect username or password.");
                        } else if (response == 2) {
                            et_pswd.setError("Incorrect password.");
                        }
                        else if (response == 0) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            //start intent
                            startActivity(intent);
                            finish();

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
            }
        });
    }
}