package mdcsapp.android.owliz.com.myapplication.UI;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import database.DBStructure.DBManager;
import mdcsapp.android.owliz.com.myapplication.Logic.DatePickerFragment;
import mdcsapp.android.owliz.com.myapplication.Logic.Student;
import mdcsapp.android.owliz.com.myapplication.Logic.RestClient;
import mdcsapp.android.owliz.com.myapplication.R;

/**
 * Created by owliz on 2017/5/8.
 */

public class ProfileFragment extends Fragment {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private TextView mtv_showDoB;
    private TextView mtv_ID;

    private EditText met_email;
    private EditText met_pswd;
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
    private CircularProgressButton mbtn_update;

    protected DBManager dbManager;

    View vProfileUnit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vProfileUnit = inflater.inflate(R.layout.fragment_profile, container, false);


        // 初始化dbManager
        dbManager = new DBManager(getActivity().getApplicationContext());

        //持久化存储数据
        pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);
        //获取SharedPreferences.Editor对象
        editor = pref.edit();

        //获取SharedPreferences值

        final String monashEmail = pref.getString("monashEmail", "this is default value");
        Log.d("Profile", "获取到的monashEmail是：" + monashEmail);
        String password = pref.getString("password", "this is default value");
        final String firstName = pref.getString("firstName", "this is default value");
        final String surname = pref.getString("surname", "this is default value");
        String doB = pref.getString("doB", "this is default value");
        String gender = pref.getString("gender", "this is default value");
        final String course = pref.getString("course", "this is default value");
        final String studyMode = pref.getString("studyMode", "this is default value");
        final String address = pref.getString("address", "this is default value");
        String suburb = pref.getString("suburb", "this is default value");
        final String nationality = pref.getString("nationality", "this is default value");
        String nativeLanguage = pref.getString("nativeLanguage", "this is default value");
        final String favoriteSport = pref.getString("favoriteSport", "this is default value");
        final String favoriteMovieType = pref.getString("favoriteMovieType", "this is default value");
        final String favoriteMovie = pref.getString("favoriteMovie", "this is default value");
        final String favouriteUnit = pref.getString("favouriteUnit", "this is default value");
        final String currentJob = pref.getString("currentJob", "this is default value");
        final String subscriptionDate = pref.getString("subscriptionDate", "this is default value");


        //  TextView element
        mtv_showDoB = (TextView) vProfileUnit.findViewById(R.id.tv_showDoB);
        mtv_ID = (TextView) vProfileUnit.findViewById(R.id.tv_ID);
        //  EditText element
        met_email = (EditText) vProfileUnit.findViewById(R.id.et_newID);
        met_pswd = (EditText) vProfileUnit.findViewById(R.id.et_newPswd);
        met_firstNm = (EditText) vProfileUnit.findViewById(R.id.et_firstNm);
        met_surNm = (EditText) vProfileUnit.findViewById(R.id.et_surNm);
        met_addr = (EditText) vProfileUnit.findViewById(R.id.et_addr);
        met_suburb = (EditText) vProfileUnit.findViewById(R.id.et_suburb);
        met_favoriteMv = (EditText) vProfileUnit.findViewById(R.id.et_favoriteMovie);
        met_FavoriteUt = (EditText) vProfileUnit.findViewById(R.id.et_favoriteUnit);
        met_CurrentJb = (EditText) vProfileUnit.findViewById(R.id.et_currentJob);
        // RadioGroupButton
        mrdg_gender = (RadioGroup) vProfileUnit.findViewById(R.id.rg_gender);
        mrdg_studyMd = (RadioGroup) vProfileUnit.findViewById(R.id.rg_studyMode);
        // Spinner element
        msp_course = (Spinner) vProfileUnit.findViewById(R.id.sp_course);
        msp_nation = (Spinner) vProfileUnit.findViewById(R.id.sp_nationality);
        msp_nativeLanguage = (Spinner) vProfileUnit.findViewById(R.id.sp_nativeLanguage);
        msp_favoriteSport = (Spinner) vProfileUnit.findViewById(R.id.sp_favoriteSport);
        msp_favoriteMovieType = (Spinner) vProfileUnit.findViewById(R.id.sp_favoriteMovieType);
        // CircularProgressButton element
        mbtn_update = (CircularProgressButton) vProfileUnit.findViewById(R.id.btn_update);

        // Loading spinner data from database
        loadNationSpinnerData();
        loadNativeLanguageSpinnerData();

        // Loading spinner data from stringArry
        loadCourseSpinnerData();
        loadFavoriteSportSpinnerData();
        loadFavoriteMovieTypeSpinnerData();

        //initial form
        mtv_ID.setText(monashEmail);
        met_pswd.setText(password);
        met_firstNm.setText(firstName);
        met_surNm.setText(surname);
        doB = doB.substring(0, 10);
        mtv_showDoB.setText(doB);
        met_CurrentJb.setText(currentJob);
        met_addr.setText(address);
        met_suburb.setText(suburb);
        met_FavoriteUt.setText(favouriteUnit);
        met_favoriteMv.setText(favoriteMovie);
        //initial gender
        switch (gender) {
            case "female":
                ((RadioButton) mrdg_gender.getChildAt(0)).setChecked(true);
                break;
            case "male":
                ((RadioButton) mrdg_gender.getChildAt(1)).setChecked(true);
                break;
            default:
                ((RadioButton) mrdg_gender.getChildAt(2)).setChecked(true);
                break;
        }
        //initial studymode
        switch (studyMode) {
            case "full-time":
                ((RadioButton) mrdg_studyMd.getChildAt(0)).setChecked(true);
                break;
            case "part-time":
                ((RadioButton) mrdg_studyMd.getChildAt(1)).setChecked(true);
                break;
            default:
                break;
        }

//        Log.d("Profile", "获取到的course是：" + course);
        if (!course.equals("")) {
            setSpinnerItemSelectedByValue(msp_course, course);
        }
        if (!nativeLanguage.equals("")) {
            setSpinnerItemSelectedByValue(msp_nativeLanguage, nativeLanguage);
        }
        if (!nationality.equals("")) {
            setSpinnerItemSelectedByValue(msp_nation, nationality);
        }
        if (!favoriteSport.equals("")) {
            setSpinnerItemSelectedByValue(msp_favoriteSport, favoriteSport);
        }
        if (!favoriteMovieType.equals("")) {
            setSpinnerItemSelectedByValue(msp_favoriteMovieType, favoriteMovieType);
        }
        // Spinner click listener
        msp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nativeLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_favoriteSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_favoriteMovieType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mbtn_update.setIndeterminateProgressMode(true); // turn on indeterminate progress

        /*mbtn_signup.setProgress(1); // set progress > 0 & < 100 to display indeterminate progress
        mbtn_signup.setProgress(100); // set progress to 100 or -1 to indicate complete or error state*/


        mbtn_update.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               mbtn_update.setProgress(20);
                                               final String myPswd = met_pswd.getText().toString();
                                               final String firstNm = met_firstNm.getText().toString();
                                               final String surNm = met_surNm.getText().toString();
                                               final String addr = met_addr.getText().toString();
                                               final String suburb = met_suburb.getText().toString();
                                               final String favoriteMv = met_favoriteMv.getText().toString();
                                               final String favoriteUt = met_FavoriteUt.getText().toString();
                                               final String currentJb = met_CurrentJb.getText().toString();
                                               final String doB = mtv_showDoB.getText().toString() + "T00:00:00+08:00";

                                               RadioButton rb1 = (RadioButton) vProfileUnit.findViewById(mrdg_gender.getCheckedRadioButtonId());
                                               final String gender = rb1.getText().toString();
                                               RadioButton rb2 = (RadioButton) vProfileUnit.findViewById(mrdg_studyMd.getCheckedRadioButtonId());
                                               final String studyMd = rb2.getText().toString();
                                               final String course = msp_course.getSelectedItem().toString();
                                               final String nativeLanguage = msp_nativeLanguage.getSelectedItem().toString();
                                               final String nation = msp_nation.getSelectedItem().toString();
                                               final String favoriteMt = msp_favoriteMovieType.getSelectedItem().toString();
                                               final String favoriteSpt = msp_favoriteSport.getSelectedItem().toString();
                                               final String currentDate = formatCurrentDate();

// Validate user input
                                               if (myPswd.isEmpty()) {
                                                   met_pswd.setError("password is required!");
                                                   mbtn_update.setProgress(0);
                                                   return;
                                               }
                                               if (firstNm.isEmpty()) {
                                                   met_firstNm.setError("firstNm is required!");
                                                   mbtn_update.setProgress(0);
                                                   return;
                                               }
                                               if (surNm.isEmpty()) {
                                                   met_surNm.setError("surtNm is required!");
                                                   mbtn_update.setProgress(0);
                                                   return;
                                               }

                                               if (doB.equals("click to selectT00:00:00+08:00")) {
                                                   Toast.makeText(getActivity().getApplicationContext(), "please input your birth!", Toast.LENGTH_SHORT).show();
                                                   mbtn_update.setProgress(0);
                                                   return;
                                               }

//create an anonymous AsyncTask
                                               new AsyncTask<String, Void, Integer>() {
                                                   @Override
                                                   protected Integer doInBackground(String... params) {
                                                       Student stu = new Student(monashEmail, myPswd, firstNm, surNm, course, doB, gender, studyMd, currentJb,
                                                               nativeLanguage, nation, addr, suburb, favoriteUt, favoriteSpt, favoriteMt, favoriteMv, currentDate);
                                                       RestClient.updateProfile(monashEmail, stu);
                                                       return 1;
                                                   }

                                                   @Override
                                                   protected void onPostExecute(Integer info) {
                                                       mbtn_update.setProgress(100);
                                                       editor.clear();
                                                       editor.commit();
                                                       editor.putString("monashEmail", monashEmail);
                                                       editor.putString("password", myPswd);
                                                       editor.putString("firstName", firstNm);
                                                       editor.putString("surname", surNm);
                                                       editor.putString("doB", doB);
                                                       editor.putString("gender", gender);
                                                       editor.putString("course", course);
                                                       editor.putString("studyMode", studyMd);
                                                       editor.putString("address", addr);
                                                       editor.putString("suburb", suburb);
                                                       editor.putString("nationality", nation);
                                                       editor.putString("nativeLanguage", nativeLanguage);
                                                       editor.putString("favoriteSport", favoriteSpt);
                                                       editor.putString("favoriteMovieType", favoriteMt);
                                                       editor.putString("favoriteMovie", favoriteMv);
                                                       editor.putString("favouriteUnit", favoriteUt);
                                                       editor.putString("currentJob", currentJb);
                                                       editor.putString("subscriptionDate", currentDate);
                                                       editor.commit();
                                                       new Handler().postDelayed(new Runnable() {
                                                           public void run() {
                                                               mbtn_update.setProgress(0);
                                                           }
                                                       }, 1000);

                                                   }
                                               }.execute();
                                           }
                                       }
        );

        return vProfileUnit;
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.movieTypes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        msp_favoriteMovieType.setAdapter(adapter);
    }


    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据值, 设置spinner默认选中:
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
//            Log.d("Profile", "获取到的spinner_value是：" + value);
            //use regex to match entry
            if (apsAdapter.getItem(i).toString().matches(value + "+")) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }
}
