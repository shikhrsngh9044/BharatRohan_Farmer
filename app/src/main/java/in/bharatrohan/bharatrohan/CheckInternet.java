package in.bharatrohan.bharatrohan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckInternet {

    Context context;

    public CheckInternet(Context context) {
        this.context = context;
    }

    public void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            } else {
                Toast.makeText(context, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
