package com.agenthun.smartswitch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.data.User;
import com.agenthun.smartswitch.helper.ApiLevelHelper;
import com.agenthun.smartswitch.helper.PreferencesHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String EXTRA_SAVE_PREFERENCES = "SAVE";

    @Bind(R.id.username)
    AppCompatEditText etUsername;
    @Bind(R.id.password)
    AppCompatEditText etPassword;
    @Bind(R.id.sign_in_button)
    AppCompatButton btnSignIn;
    @Bind(R.id.forget_password)
    AppCompatTextView btnForgetPassword;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private User mUser;

    public static void start(Activity activity, Boolean isSavePreferences) {
        Intent starter = new Intent(activity, LoginActivity.class);
        starter.putExtra(EXTRA_SAVE_PREFERENCES, isSavePreferences);
        ActivityCompat.startActivity(activity,
                starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            fab.getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            fab.getViewTreeObserver().removeOnPreDrawListener(this);
                            fab.setScaleX(0f);
                            fab.setScaleY(0f);
                            fab.setAlpha(0f);
                            return true;
                        }
                    });
            ViewCompat.animate(fab)
                    .setStartDelay(200)
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .start();
        }

        assureUserInit();
        if (mUser == null || isInSaveMode()) {
            initContents();
        } else {
            attemptLogin(this, mUser);
        }
    }

    @OnClick({R.id.sign_in_button, R.id.forget_password, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                if (isInputDataValid()) {
                    saveUser(this);
                    attemptLogin(this, mUser);
                } else {
                    Snackbar.make(view, getString(R.string.error_invalid_user), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.forget_password:
                break;
            case R.id.fab:
                atteptRegister();
                break;
        }
    }

    private void attemptLogin(Activity activity, User user) {
        MainActivity.start(activity, user);
        ActivityCompat.finishAfterTransition(activity);
    }

    private void atteptRegister() {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            RegisterActivity.start(this, optionsCompat);
        } else {
            RegisterActivity.start(this);
            ViewCompat.animate(fab)
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .start();
        }
    }

    private void initContents() {
        assureUserInit();
        if (mUser != null) {
            etUsername.setText(mUser.getUsername());
            etUsername.setSelection(mUser.getUsername().length());
            etPassword.setText(mUser.getPassword());
        }
    }

    private void assureUserInit() {
        if (mUser == null) {
            mUser = PreferencesHelper.getUser(this);
        }
    }

    private void saveUser(Activity activity) {
        PreferencesHelper.clearUser(this);
        mUser = new User(etUsername.getText().toString(), etPassword.getText().toString());
        PreferencesHelper.writeToPreferences(activity, mUser);
    }

    private boolean isInputDataValid() {
        return PreferencesHelper.isInputDataValid(etUsername.getText(), etPassword.getText());
    }

    private boolean isInSaveMode() {
        final Intent intent = getIntent();
        boolean save = false;
        if (null != intent) {
            save = intent.getBooleanExtra(EXTRA_SAVE_PREFERENCES, false);
        }
        return save;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
