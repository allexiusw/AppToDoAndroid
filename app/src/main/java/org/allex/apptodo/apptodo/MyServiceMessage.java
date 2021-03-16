package org.allex.apptodo.apptodo;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import androidx.annotation.NonNull;

public class MyServiceMessage extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getId();
        Log.i("Token", "Refreshed token: " + refreshedToken);
    }
}

