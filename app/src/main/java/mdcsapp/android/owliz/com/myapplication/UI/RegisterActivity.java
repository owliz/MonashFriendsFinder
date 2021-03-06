package mdcsapp.android.owliz.com.myapplication.UI;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import mdcsapp.android.owliz.com.myapplication.Logic.DatePickerFragment;
import mdcsapp.android.owliz.com.myapplication.Logic.Student;
import mdcsapp.android.owliz.com.myapplication.Logic.RestClient;
import mdcsapp.android.owliz.com.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.DBStructure.DBManager;


/**
 * Created by Owliz on 2017/4/28.
 */

// implement interface[DatePickerFragment.OnDateSetListener] declared in fragment
public class RegisterActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private TextView mtv_showDoB;
    private TextView mtv_login;

    private EditText met_email;
    private EditText met_pswd;
    private EditText met_repswd;
    private EditText met_firstNm;
    private EditText met_surNm;
    private EditText met_addr;
    private EditText met_suburb;
    private EditText met_favoriteMv;
    private EditText met_FavoriteUt;
    private EditText met_CurrentJb;


    private RadioGroup mrdg_gender;
    private RadioGroup mrdg_studyMd;

    private Spinner msp_course;
    private Spinner msp_nation;
    private Spinner msp_nativeLanguage;
    private Spinner msp_favoriteSport;
    private Spinner msp_favoriteMovieType;
    private CircularProgressButton mbtn_signUp;

    protected DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbManager = new DBManager(this);

        //  TextView element
        mtv_showDoB = (TextView) findViewById(R.id.tv_showDoB);
        mtv_login = (TextView) findViewById(R.id.tv_login);
        //  EditText element
        met_email = (EditText) findViewById(R.id.et_newID);
        met_pswd = (EditText) findViewById(R.id.et_newPswd);
        met_repswd = (EditText) findViewById(R.id.et_rePswd);
        met_firstNm = (EditText) findViewById(R.id.et_firstNm);
        met_surNm = (EditText) findViewById(R.id.et_surNm);
        met_addr = (EditText) findViewById(R.id.et_addr);
        met_suburb = (EditText) findViewById(R.id.et_suburb);
        met_favoriteMv = (EditText) findViewById(R.id.et_favoriteMovie);
        met_FavoriteUt = (EditText) findViewById(R.id.et_favoriteUnit);
        met_CurrentJb = (EditText) findViewById(R.id.et_currentJob);
        // RadioGroupButton
        mrdg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        mrdg_studyMd = (RadioGroup) findViewById(R.id.rg_studyMode);
        // Spinner element
        msp_course = (Spinner) findViewById(R.id.sp_course);
        msp_nation = (Spinner) findViewById(R.id.sp_nationality);
        msp_nativeLanguage = (Spinner) findViewById(R.id.sp_nativeLanguage);
        msp_favoriteSport = (Spinner) findViewById(R.id.sp_favoriteSport);
        msp_favoriteMovieType = (Spinner) findViewById(R.id.sp_favoriteMovieType);
        // CircularProgressButton element
        mbtn_signUp = (CircularProgressButton) findViewById(R.id.btn_signup);

        // Spinner click listener
        final boolean[] isSpinnerFirst = {true,true,true,true,true};
        // Spinner click listener
        msp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (isSpinnerFirst[0]) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst[0] = false ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (isSpinnerFirst[1]) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst[1] = false ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nativeLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (isSpinnerFirst[2]) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst[2] = false ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_favoriteSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (isSpinnerFirst[3]) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst[3] = false ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_favoriteMovieType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (isSpinnerFirst[4]) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst[4] = false ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Loading spinner data from database
        loadNationSpinnerData();
        loadNativeLanguageSpinnerData();


        // Loading spinner data from stringArry
        loadCourseSpinnerData();
        loadFavoriteSportSpinnerData();
        loadFavoriteMovieTypeSpinnerData();

        mbtn_signUp.setIndeterminateProgressMode(true); // turn on indeterminate progress

        /*mbtn_signup.setProgress(1); // set progress > 0 & < 100 to display indeterminate progress
        mbtn_signup.setProgress(100); // set progress to 100 or -1 to indicate complete or error state*/


        mbtn_signUp.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               mbtn_signUp.setProgress(20);
                                               final String myId = met_email.getText().toString();
                                               final String myPswd = met_pswd.getText().toString();
                                               final String rePswd = met_repswd.getText().toString();
                                               final String firstNm = met_firstNm.getText().toString();
                                               final String surNm = met_surNm.getText().toString();
                                               final String addr = met_addr.getText().toString();
                                               final String suburb = met_suburb.getText().toString();
                                               final String favoriteMv = met_favoriteMv.getText().toString();
                                               final String favoriteUt = met_FavoriteUt.getText().toString();
                                               final String currentJb = met_CurrentJb.getText().toString();
                                               final String doB = mtv_showDoB.getText().toString() + "T00:00:00+08:00";

                                               RadioButton rb1 = (RadioButton) findViewById(mrdg_gender.getCheckedRadioButtonId());
                                               final String gender = rb1.getText().toString();
                                               RadioButton rb2 = (RadioButton) findViewById(mrdg_studyMd.getCheckedRadioButtonId());
                                               final String studyMd = rb2.getText().toString();
                                               final String course = msp_course.getSelectedItem().toString();
                                               final String nativeLanguage = msp_nativeLanguage.getSelectedItem().toString();
                                               final String nation = msp_nation.getSelectedItem().toString();
                                               final String favoriteMt = msp_favoriteMovieType.getSelectedItem().toString();
                                               final String favoriteSpt = msp_favoriteSport.getSelectedItem().toString();
                                               final String currentDate = formatCurrentDate();

// Validate user input
                                               if (myId.isEmpty()) {
                                                   met_email.setError("email address is required!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (myPswd.isEmpty()) {
                                                   met_pswd.setError("password is required!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (rePswd.isEmpty()) {
                                                   met_repswd.setError("re-password is required!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (firstNm.isEmpty()) {
                                                   met_firstNm.setError("firstNm is required!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (surNm.isEmpty()) {
                                                   met_surNm.setError("surtNm is required!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (!myPswd.equals(rePswd)) {
                                                   met_repswd.setError("Two different password input!");
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }
                                               if (doB.equals("click to selectT00:00:00+08:00")) {
                                                   Toast.makeText(getApplicationContext(), "please input your birth!", Toast.LENGTH_SHORT).show();
                                                   mbtn_signUp.setProgress(0);
                                                   return;
                                               }

//create an anonymous AsyncTask
                                               new AsyncTask<String, Void, Integer>() {
                                                   @Override
                                                   protected Integer doInBackground(String... params) {
                                                       if (!(RestClient.getId(myId)).equals("[]")) {
                                                           return 1;
                                                       } else {
                                                           Student stu = new Student(myId, myPswd, firstNm, surNm, course, doB, gender, studyMd, currentJb,
                                                                   nativeLanguage, nation, addr, suburb, favoriteUt, favoriteSpt, favoriteMt, favoriteMv, currentDate);
                                                           RestClient.signUp(stu);
                                                           return 0;
                                                       }
                                                   }

                                                   @Override
                                                   protected void onPostExecute(Integer response) {
                                                       if (response == 1) {
                                                           mbtn_signUp.setProgress(0);
                                                           Toast.makeText(getApplicationContext(), "Account already exists!", Toast.LENGTH_SHORT).show();
                                                           met_email.setError("Account already exists!");
                                                       } else if (response == 0) {
                                                           mbtn_signUp.setProgress(100);
                                                           new Handler().postDelayed(new Runnable() {
                                                               public void run() {
                                                                   Toast.makeText(getApplicationContext(), "please return to login", Toast.LENGTH_SHORT).show();
                                                                   Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                   //start intent
                                                                   startActivity(intent);
                                                                   finish();//execute the task
                                                               }
                                                           }, 2000);

                                                       }

                                                   }
                                               }.execute();
                                           }
                                       }
        );

        // jump to login UI
        mtv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        int keyCode=0;
        if (keyCode == KeyEvent.KEYCODE_BACK) {}

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    // 4. Implement method in interface
    public void onDateSet(int year, int month, int day) {
        String s_year;
        String s_month;
        String s_day;
        if (year < 100) {
            s_year = Integer.toString(year);
            s_year = "00" + s_year;
        } else if (year < 1000) {
            s_year = Integer.toString(year);
            s_year = "0" + s_year;
        } else {
            s_year = Integer.toString(year);
        }

        if (month < 9) {
            s_month = Integer.toString(++month);
            s_month = '0' + s_month;
        } else {
            s_month = Integer.toString(++month);
        }

        if (day < 10) {
            s_day = Integer.toString(day);
            s_day = '0' + s_day;
        } else {
            s_day = Integer.toString(day);
        }

        mtv_showDoB.setText(s_year + "-" + s_month + "-" + s_day);
    }

    /**
     * Function to load the Nationality spinner data from SQLite database
     */
    public void loadNationSpinnerData() {

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Spinner Drop down elements
        List<String> lables = dbManager.getAllNation();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        msp_nation.setAdapter(dataAdapter);

        dbManager.close();
    }

    public void loadNativeLanguageSpinnerData() {

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Spinner Drop down elements
        List<String> lables = dbManager.getAllNativeLanguage();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        msp_nativeLanguage.setAdapter(dataAdapter);

        dbManager.close();
    }

    /**
     * Function to load the Course spinner data from SQLite database
     */
    public void loadCourseSpinnerData() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courses_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        msp_course.setAdapter(adapter);
    }

    /**
     * Function to load the Course spinner data from SQLite database
     */
    public void loadFavoriteSportSpinnerData() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sports_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        msp_favoriteSport.setAdapter(adapter);
    }

    /**
     * Function to load the Course spinner data from SQLite database
     */
    public void loadFavoriteMovieTypeSpinnerData() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movieTypes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        msp_favoriteMovieType.setAdapter(adapter);
    }


//    //    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position,
//                               long id) {
//        // On selecting a spinner item
////        String label = parent.getItemAtPosition(position).toString();
//    }
//
//    //    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//    }

    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
