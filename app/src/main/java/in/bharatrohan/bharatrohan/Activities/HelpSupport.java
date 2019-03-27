package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.bharatrohan.bharatrohan.R;

public class HelpSupport extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        findViewById(R.id.constFeed).setOnClickListener(v -> startActivity(new Intent(this, Feedback.class)));

        findViewById(R.id.constImp).setOnClickListener(v -> startActivity(new Intent(this, ImpContacts.class)));
    }
}
