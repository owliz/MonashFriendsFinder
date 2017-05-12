package mdcsapp.android.owliz.com.myapplication.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mdcsapp.android.owliz.com.myapplication.Logic.RestClient;
import mdcsapp.android.owliz.com.myapplication.R;

/**
 * Created by owliz on 2017/5/8.
 */

public class MainActivity extends AppCompatActivity {

    //使用SharedPreferences进行读取
    private SharedPreferences pref;
    //使用SharedPreferences.Editor进行存储
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_ID = (EditText) findViewById(R.id.et_ID);
        final EditText et_pswd = (EditText) findViewById(R.id.et_pswd);
        // CircularProgressButton element
        final CircularProgressButton btn_login = (CircularProgressButton) findViewById(R.id.btn_login);
        TextView tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);

        //第一个参数：文件名，没有则新建。第二个参数：写入模式-覆盖
        pref = getSharedPreferences("admin", MODE_PRIVATE);
        //获取SharedPreferences.Editor对象
        editor = pref.edit();

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
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String info;

//                        Log.d("xxx", "你的信息1" + RestClient.verifyLogin(myId, myPswd));
                        if ((info = RestClient.verifyLogin(myId, myPswd)).equals("[]")) {
//                            Log.d("xxx", "你的信息2" + RestClient.getId(myId));
                            if (!(RestClient.getId(myId)).equals("[]")) {
                                return "2";
                            }
                            return "1";
                        } else {
                            return info;
                        }
                    }

                    @Override
                    protected void onPostExecute(String info) {
                        if (info.equals("1")) {
                            et_ID.setError("Incorrect username or password.");
                            btn_login.setProgress(0);
                        } else if (info.equals("2")) {
                            et_pswd.setError("Incorrect password.");
                            btn_login.setProgress(0);
                        } else {

                            try {
                                JSONArray jsonArray = null;
                                jsonArray = new JSONArray(info);
                                JSONObject jsonObj = jsonArray.getJSONObject(0);
                                String firstName = (String) jsonObj.get("firstName");
                                final String surname = (String) jsonObj.get("surname");

                                String doB = (String) jsonObj.get("doB");
                                String gender = (String) jsonObj.get("gender");
                                String course = (String) jsonObj.get("course");
                                String studyMode = (String) jsonObj.get("studyMode");
                                String address = (String) jsonObj.get("address");
                                String suburb = (String) jsonObj.get("suburb");
                                String nationality = (String) jsonObj.get("nationality");
                                String nativeLanguage = (String) jsonObj.get("nativeLanguage");
                                String favoriteSport = (String) jsonObj.get("favoriteSport");
                                String favoriteMovieType = (String) jsonObj.get("favoriteMovieType");
                                String favoriteMovie = (String) jsonObj.get("favoriteMovie");
                                String favouriteUnit = (String) jsonObj.get("favouriteUnit");
                                String currentJob = (String) jsonObj.get("currentJob");
                                String subscriptionDate = (String) jsonObj.get("firstName");
//                                清空旧的cookie
                                editor.clear();
                                editor.commit();
                                editor.putString("monashEmail", myId);
//                                Log.d("mainActivity", "获取到的info是：" + info);
//                                Log.d("mainActivity", "获取到的address是：" + address);
                                editor.putString("password", myPswd);
                                editor.putString("firstName", firstName);
                                editor.putString("surname", surname);
                                editor.putString("doB", doB);
                                editor.putString("gender", gender);
                                editor.putString("course", course);
                                editor.putString("studyMode", studyMode);
                                editor.putString("address", address);
                                editor.putString("suburb", suburb);
                                editor.putString("nationality", nationality);
                                editor.putString("nativeLanguage", nativeLanguage);
                                editor.putString("favoriteSport", favoriteSport);
                                editor.putString("favoriteMovieType", favoriteMovieType);
                                editor.putString("favoriteMovie", favoriteMovie);
                                editor.putString("favouriteUnit", favouriteUnit);
                                editor.putString("currentJob", currentJob);
                                editor.putString("subscriptionDate", subscriptionDate);
                                editor.commit();

                            } catch (JSONException mE) {
                                mE.printStackTrace();
                            }
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "welcome back!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    //start intent
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);


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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("SYSTEM HINT");
            // 设置对话框消息
            isExit.setMessage("Do you want to quit?");
            // 添加选择按钮并注册监听
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", listener);
            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "YES", listener);
            // 显示对话框
            isExit.show();

        }
        return false;
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}