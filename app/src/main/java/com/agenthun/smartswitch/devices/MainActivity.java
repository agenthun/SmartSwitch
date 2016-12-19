package com.agenthun.smartswitch.devices;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.activity.LoginActivity;
import com.agenthun.smartswitch.data.User;
import com.agenthun.smartswitch.databinding.ActivityMainBinding;
import com.agenthun.smartswitch.helper.PreferencesHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String EXTRA_USER = "user";

    @Bind(R.id.progress)
    ProgressBar progressBar;

    public static void start(Context context, User user) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra(EXTRA_USER, user);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "getUserId() returned: " + PreferencesHelper.getUserId(this));
        Log.d(TAG, "getSID() returned: " + PreferencesHelper.getSID(this));

        User user = getIntent().getParcelableExtra(EXTRA_USER);
        if (!PreferencesHelper.isSignedIn(this)) {
            if (user == null) {
                user = PreferencesHelper.getUser(this);
            } else {
                PreferencesHelper.writeUserInfoToPreferences(this, user);
            }
        }
        binding.setUser(user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState != null) {
            setProgressBarVisibility(View.GONE);
        } else {
            attachDeviceFragment();
        }
        supportPostponeEnterTransition();
    }

    private void attachDeviceFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.content_main);
        if (!(fragment instanceof DeviceFragment)) {
            fragment = DeviceFragment.newInstance();
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
        setProgressBarVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            signOut(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut(boolean isSave) {
        PreferencesHelper.signOut(this, isSave);
        LoginActivity.start(this, isSave);
        ActivityCompat.finishAfterTransition(this);
    }

    private void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
