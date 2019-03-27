package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import in.bharatrohan.bharatrohan.R;

public class Feedback extends AppCompatActivity {

    private MaterialSpinner typeSpinner;
    private EditText editMessage;
    private Button btnSubmit;
    private String msgType = "-SELECT-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        init();

        typeSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            typeSpinner.setError(null);
            if (position == 1) {
                msgType = "Feedback";
            } else if (position == 2) {
                msgType = "Complaint";
            }
        });


        btnSubmit.setOnClickListener(v -> {
            if (!validate()) {
                String message = editMessage.getText().toString().trim();
                Intent send = new Intent(Intent.ACTION_SENDTO);
                send.putExtra(Intent.EXTRA_TEXT, message);
                send.putExtra(Intent.EXTRA_SUBJECT, msgType);
                String uriText = "mailto:" + Uri.encode("support@bharatrohan.in");
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            } else {
                validate();
            }
        });
    }


    private void init() {
        typeSpinner = (MaterialSpinner) findViewById(R.id.typeSpinner);
        editMessage = findViewById(R.id.editMessage);
        btnSubmit = findViewById(R.id.btnSubmit);


        String title = "-" + "SELECT" + "-";

        ArrayList<String> helpList = new ArrayList<>();
        helpList.add(title);
        helpList.add("Feedback");
        helpList.add("Complaint");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, helpList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter1);
    }

    private boolean validate() {
        String message = editMessage.getText().toString().trim();
        if (msgType.equals("-SELECT-")) {
            typeSpinner.setError("Select type of message");
            typeSpinner.requestFocus();
            Toast.makeText(this, "Please Select type of message", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (message.isEmpty()) {
            editMessage.setError("Enter some message");
            editMessage.requestFocus();
            return true;
        }

        return false;
    }
}
