package mdcsapp.android.owliz.com.myapplication.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mdcsapp.android.owliz.com.myapplication.R;

/**
 * Created by owliz on 2017/5/9.
 */

public class SearchResultActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchResultListFragment();
    }
}
