package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setLocale(new PrefManager(this).getUserLanguage());
        setContentView(R.layout.activity_splash_screen);

        if (new PrefManager(this).getlaunchCount() == 0) {
            Intent intent = new Intent(this, LanguageScreen.class);
            startActivity(intent);
            new PrefManager(this).savelaunchCount(1);
            finish();
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }


    }


    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
