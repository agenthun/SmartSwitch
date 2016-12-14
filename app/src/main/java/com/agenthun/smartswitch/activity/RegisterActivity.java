package com.agenthun.smartswitch.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.helper.ApiLevelHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.username)
    AppCompatEditText etUsername;
    @Bind(R.id.password)
    AppCompatEditText etPassword;
    @Bind(R.id.sign_up_button)
    AppCompatButton btnSignUp;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.cv_register)
    CardView cardViewRegister;

    public static void start(Activity activity, ActivityOptionsCompat options) {
        Intent starter = new Intent(activity, RegisterActivity.class);
        ActivityCompat.startActivity(activity, starter, options.toBundle());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            showEnterAnimation();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.signup_enter);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cardViewRegister.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @OnClick({R.id.sign_up_button, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_button:
                attemptSignUp();
                break;
            case R.id.fab:
                onBackPressed();
                break;
        }
    }

    private void attemptSignUp() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardViewRegister,
                cardViewRegister.getWidth() / 2,
                0,
                fab.getWidth() / 2,
                cardViewRegister.getHeight());
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                cardViewRegister.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardViewRegister,
                cardViewRegister.getWidth() / 2,
                0,
                cardViewRegister.getHeight(),
                fab.getWidth() / 2);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new FastOutLinearInInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cardViewRegister.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_add_black_24dp);
                RegisterActivity.super.onBackPressed();
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            animateRevealClose();
        } else {
            ActivityCompat.finishAfterTransition(RegisterActivity.this);
        }
    }
}
