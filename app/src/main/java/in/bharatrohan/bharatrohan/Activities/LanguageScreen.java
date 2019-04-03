package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Locale;

import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class LanguageScreen extends AppCompatActivity {

    private Button eng_lang, hin_lang, accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_screen);

        eng_lang = findViewById(R.id.english_lang);
        hin_lang = findViewById(R.id.hindi_lang);
        accept = findViewById(R.id.accept);

        eng_lang.setOnClickListener(v -> {
            eng_lang.setBackground(getDrawable(R.drawable.rounded_button_dark));
            hin_lang.setBackground(getDrawable(R.drawable.rounded_button_light));
            new PrefManager(LanguageScreen.this).saveUserLanguage("en");
        });

        hin_lang.setOnClickListener(v -> {
            hin_lang.setBackground(getDrawable(R.drawable.rounded_button_dark));
            eng_lang.setBackground(getDrawable(R.drawable.rounded_button_light));
            new PrefManager(LanguageScreen.this).saveUserLanguage("hi");
        });

        accept.setOnClickListener(v -> {
            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("main")) {
                    setLocale(new PrefManager(LanguageScreen.this).getUserLanguage());
                    startActivity(new Intent(LanguageScreen.this, MainActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(LanguageScreen.this, Login.class));
                finish();
            }
        });

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

}
