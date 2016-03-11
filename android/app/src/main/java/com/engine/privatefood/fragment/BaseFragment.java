package com.engine.privatefood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import com.engine.privatefood.BusProvider;
import com.engine.privatefood.R;
import com.engine.privatefood.activity.BaseActivity;


/**
 * Created by engine on 15/7/27.
 */
public class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void dismissKeyPad(View view) {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(getBaseActivty().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyPad(final View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "-----show Input ---");
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 500);
    }

    protected BaseActivity getBaseActivty() {

        return ((BaseActivity) getActivity());
    }
    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void back() {
        ((BaseActivity) getActivity()).appBack();
    }

    public void backtwo() {
        ((BaseActivity) getActivity()).appBackFragment();
    }

//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        Log.e(TAG, "transit=" + transit + ",enter=" + enter + ",nextAnim=" + nextAnim);
//        if (enter) {
//            return AnimationUtils.loadAnimation(getActivity(), R.anim.in_from_right);
//        } else {
//            return AnimationUtils.loadAnimation(getActivity(), R.anim.out_to_right);
//        }
//    }
}
