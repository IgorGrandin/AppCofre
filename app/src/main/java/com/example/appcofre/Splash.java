package com.example.appcofre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    Animation rotateProg;
    Animation reverseRotateProg;
    ImageView cofre1Prog;
    ImageView cofre2Prog;
    ImageView cofre3Prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cofre1Prog = (ImageView) findViewById(R.id.imgCofre1);
        cofre2Prog = (ImageView) findViewById(R.id.imgCofre2);
        cofre3Prog = (ImageView) findViewById(R.id.imgCofre3);
        rotateProg = AnimationUtils.loadAnimation(this, R.anim.rotate);
        reverseRotateProg = AnimationUtils.loadAnimation(this, R.anim.reverserotate);

        cofre2Prog.setAnimation(rotateProg);
        cofre3Prog.setAnimation(reverseRotateProg);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 5000);
    }

    private void mostrarMainActivity() {
        Intent It = new Intent(Splash.this, MainActivity.class);
        startActivity(It);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}