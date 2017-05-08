package mdcsapp.android.owliz.com.myapplication.Logic;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owliz on 2017/5/8.
 */

public class Student {
    private String firstName;
    private String surname;
    private String doB;
    private String gender;
    private String course;
    private String studyMode;
    private String address;
    private String suburb;
    private String nationality;
    private String nativeLanguage;
    private String favoriteSport;
    private String favoriteMovieType;
    private String favoriteMovie;
    private String favouriteUnit;
    private String currentJob;
    private String monashEmail;
    private String password;
    private String subscriptionDate;

    public Student(String myId, String myPswd, String firstNm, String surNm, String course, String doB, String gender, String studyMd, String currentJb,
                   String nativeLanguage, String nation, String addr, String suburb, String favoriteUt, String favoriteSpt, String favoriteMt, String favoriteMv, String currentDate) {
        this.monashEmail = myId;
        this.password = myPswd;
        this.firstName = firstNm;
        this.surname = surNm;
        this.course = spliteCourse(course);
        this.doB = doB;
        this.gender = gender;
        this.studyMode = studyMd;
        this.currentJob = currentJb;
        this.nativeLanguage = nativeLanguage;
        this.nationality = nation;
        this.address = addr;
        this.suburb = suburb;
        this.favouriteUnit = favoriteUt;
        this.favoriteSport = favoriteSpt;
        this.favoriteMovieType = favoriteMt;
        this.favoriteMovie = favoriteMv;
        this.subscriptionDate = currentDate;
    }


    public String spliteCourse(String str) {
        String tmp = str;
        String splitStr;
        int j = tmp.indexOf("("); // 找分隔符的位置
        splitStr = tmp.substring(0, j); // 找到分隔符，截取子字符串
        return splitStr;
    }
}