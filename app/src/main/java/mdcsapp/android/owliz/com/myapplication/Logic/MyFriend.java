package mdcsapp.android.owliz.com.myapplication.Logic;

import java.util.UUID;

/**
 * Created by owliz on 2017/5/10.
 */

public class MyFriend {
    private UUID mId;
    // full name as title
    private String mTitle;
    private String mFullName;
    private String mEmail;
    private String mGender;
    private String mCourse;
    private String mFavoriteMovie;

    public MyFriend() {
        //generate unique identifier
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getEmail() {
        return mEmail;
    }
    public String getGender() {
        return mGender;
    }
    public String getCourse() {
        return mCourse;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public void setGender(String mGender) {
        this.mGender = mGender;
    }
    public void setCourse(String mCourse) {
        this.mCourse = mCourse;
    }

    public String getFavoriteMovie() {
        return mFavoriteMovie;
    }

    public void setFavoriteMovie(String mFavoriteMovie) {
        this.mFavoriteMovie = mFavoriteMovie;
    }
}
