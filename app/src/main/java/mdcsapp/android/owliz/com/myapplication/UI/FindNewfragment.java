package mdcsapp.android.owliz.com.myapplication.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mdcsapp.android.owliz.com.myapplication.R;

/**
 * Created by owliz on 2017/5/8.
 */

public class FindNewfragment extends Fragment {
    View vNewFellowUnit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vNewFellowUnit = inflater.inflate(R.layout.fragment_findnew, container, false);
        return vNewFellowUnit;
    }
}
