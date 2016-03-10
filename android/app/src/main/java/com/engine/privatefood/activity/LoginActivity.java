package com.engine.privatefood.activity;

import android.os.Bundle;
import com.engine.privatefood.fragment.LoginFragment;

/**
 * Created by engine on 16/3/10.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginFragment fragment = new LoginFragment();
        addFragment(fragment);
    }
}
