package mdcsapp.android.owliz.com.myapplication.UI;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import mdcsapp.android.owliz.com.myapplication.Logic.DatePickerFragment;
import mdcsapp.android.owliz.com.myapplication.Logic.Student;
import mdcsapp.android.owliz.com.myapplication.Logic.VerifyLogin;
import mdcsapp.android.owliz.com.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.DBStructure.DBManager;


/**
 * Created by Owliz on 2017/4/28.
 */

// implement interface[DatePickerFragment.OnDateSetListener] declared in fragment
public class RegisterActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener
        , AdapterView.OnItemSelectedListener {

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
    private Button mbtn_signup;

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
        // Button element
        mbtn_signup = (Button) findViewById(R.id.btn_signup);

        // Spinner click listener
        msp_course.setOnItemSelectedListener(this);
        msp_nation.setOnItemSelectedListener(this);
        msp_nativeLanguage.setOnItemSelectedListener(this);
        msp_favoriteSport.setOnItemSelectedListener(this);
        msp_favoriteMovieType.setOnItemSelectedListener(this);

        // Loading spinner data from database
        loadNationSpinnerData();
        loadNativeLanguageSpinnerData();
        //loadNationSpinnerData();

        // Loading spinner data from stringArry
        loadCourseSpinnerData();
        loadFavoriteSportSpinnerData();
        loadFavoriteMovieTypeSpinnerData();

        mbtn_signup.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
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
                                                   return;
                                               }
                                               if (myPswd.isEmpty()) {
                                                   met_pswd.setError("password is required!");
                                                   return;
                                               }
                                               if (rePswd.isEmpty()) {
                                                   met_repswd.setError("re-password is required!");
                                                   return;
                                               }
                                               if (firstNm.isEmpty()) {
                                                   met_firstNm.setError("firstNm is required!");
                                                   return;
                                               }
                                               if (surNm.isEmpty()) {
                                                   met_surNm.setError("surtNm is required!");
                                                   return;
                                               }
                                               if (!myPswd.equals(rePswd)) {
                                                   met_repswd.setError("Two different password input!");
                                                   return;
                                               }
                                               if (doB.equals("click to selectT00:00:00+08:00")) {
                                                   mtv_showDoB.setError("please input your birth!");
                                                   return;
                                               }


//create an anonymous AsyncTask
                                               new AsyncTask<String, Void, Integer>() {
                                                   @Override
                                                   protected Integer doInBackground(String... params) {
                                                       if (!(VerifyLogin.getId(myId)).equals("[]")) {
                                                           return 1;
                                                       } else {
                                                           Student stu = new Student(myId, myPswd, firstNm, surNm, course, doB, gender, studyMd, currentJb,
                                                                   nativeLanguage, nation, addr, suburb, favoriteUt, favoriteSpt, favoriteMt, favoriteMv, currentDate);
                                                           VerifyLogin.signup(stu);
                                                           return 0;
                                                       }
                                                   }

                                                   @Override
                                                   protected void onPostExecute(Integer response) {
                                                       if (response == 1) {
                                                           System.out.println("-------------1");
                                                           met_email.setError("Account already exists!");
                                                       } else if (response == 0) {
                                                           Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                           //start intent
                                                           startActivity(intent);
                                                           finish();
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
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                //start intent
                startActivity(intent);
                finish();
            }
        });

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    // 4. Implement method in interface
    public void onDateSet(int year, int month, int day) {
        mtv_showDoB.setText(year + "-" + (++month) + "-" + day);
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


    //    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

    }

    //    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
