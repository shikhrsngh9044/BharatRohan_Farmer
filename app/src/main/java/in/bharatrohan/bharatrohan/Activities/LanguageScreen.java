package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        eng_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eng_lang.setBackground(getDrawable(R.drawable.rounded_button_dark));
                hin_lang.setBackground(getDrawable(R.drawable.rounded_button_light));
            }
        });

        hin_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hin_lang.setBackground(getDrawable(R.drawable.rounded_button_dark));
                eng_lang.setBackground(getDrawable(R.drawable.rounded_button_light));

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LanguageScreen.this, Login.class));
            }
        });

    }
}
