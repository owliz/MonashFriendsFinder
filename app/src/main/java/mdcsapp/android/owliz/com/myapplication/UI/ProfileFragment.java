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

public class ProfileFragment extends Fragment {
    View vProfileUnit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vProfileUnit = inflater.inflate(R.layout.fragment_profile, container, false);
        return vProfileUnit;
    }
}
