package in.bharatrohan.bharatrohan.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import in.bharatrohan.bharatrohan.R;

public class ImpContacts extends AppCompatActivity {
    private TextView ceoMail, fseMail, fseContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_contacts);

        ceoMail = findViewById(R.id.ceoMail);
        fseContact = findViewById(R.id.fseContact);
        fseMail = findViewById(R.id.fseMail);

        findViewById(R.id.imFacebook).setOnClickListener(v -> {
            newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/BharatRohan.in");
        });

        findViewById(R.id.imgTwitter).setOnClickListener(v -> {
            openTwitter("https://twitter.com/BharatRohan3");
        });

        findViewById(R.id.imgInsta).setOnClickListener(v -> {
            openInsta("https://www.instagram.com/bharatrohan_official/");
        });

        findViewById(R.id.imgMail).setOnClickListener(v -> {
            openLinkedIn("https://www.linkedin.com/company/bharatrohan-airborne-innovations-private-limited/");
        });


        ceoMail.setOnClickListener(v -> {
            String mail = ceoMail.getText().toString().trim();
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(mail);
            Uri uri = Uri.parse(uriText);
            send.setData(uri);
            startActivity(Intent.createChooser(send, "Send mail..."));
        });

        fseContact.setOnClickListener(v -> {
            String contact = fseContact.getText().toString().trim();
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + contact));
            startActivity(dialIntent);
        });

        fseMail.setOnClickListener(v -> {
            String mail = fseMail.getText().toString().trim();
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(mail);
            Uri uri = Uri.parse(uriText);
            send.setData(uri);
            startActivity(Intent.createChooser(send, "Send mail..."));
        });
    }


    private void newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);

        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            int versionCode = pm.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (applicationInfo.enabled) {
                if (versionCode >= 3002850) {
                    uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                    Intent fbIntent = new Intent(Intent.ACTION_VIEW, uri);
                    fbIntent.setPackage("com.facebook.katana");
                    startActivity(fbIntent);
                } else {
                    uri = Uri.parse("fb://page/BharatRohan.in/");
                    Intent fbIntent = new Intent(Intent.ACTION_VIEW, uri);
                    fbIntent.setPackage("com.facebook.katana");
                    startActivity(fbIntent);
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            openBrowser(url);
        }
    }

    private void openInsta(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, uri);
            instaIntent.setPackage("com.instagram.android");
            startActivity(instaIntent);
        } catch (ActivityNotFoundException ex) {
            openBrowser(url);
        }
    }

    private void openTwitter(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, uri);
            instaIntent.setPackage("com.twitter.android");
            startActivity(instaIntent);
        } catch (ActivityNotFoundException ex) {
            openBrowser(url);
        }
    }

    private void openLinkedIn(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, uri);
            instaIntent.setPackage("com.linkedin.android");
            startActivity(instaIntent);
        } catch (ActivityNotFoundException ex) {
            openBrowser(url);
        }
    }

    private void openBrowser(String url) {

        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(ImpContacts.this, "No application can handle this request", Toast.LENGTH_SHORT).show();
        }
    }
}
