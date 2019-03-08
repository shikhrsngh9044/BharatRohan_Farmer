package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class UnSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_splash);

        findViewById(R.id.imgCall).setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + new PrefManager(this).getFeContact()));
            startActivity(dialIntent);
        });

        findViewById(R.id.tvContact).setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + new PrefManager(this).getFeContact()));
            startActivity(dialIntent);
        });

        findViewById(R.id.btnExit).setOnClickListener(v -> {

            new PrefManager(this).saveLoginDetails("", "");
            new PrefManager(this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
            new PrefManager(this).saveAvatar("");
            new PrefManager(this).saveToken("");
            new PrefManager(this).saveFarmerId("");

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        });

    }
}
