package org.allex.apptodo.apptodo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText edtName, edtDesc;
    Switch swtState;
    Button btnCrear, btnVerListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDes);
        swtState = findViewById(R.id.swtEstado);
        btnCrear = findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(this);
        btnVerListado = findViewById(R.id.btnVer);
        btnVerListado.setOnClickListener(this);
        createNotificationChannel();
        Intent intent = new Intent(this, ListadoTareas.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    this, "local")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("test")
                    .setContentText("Demo notification activity")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(R.id.btnVer, builder.build());
        }else{
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("test")
                    .setContentText("Demo notification activity")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(R.id.btnVer, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "local";
            String description = "local_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("local", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCrear:
                CDBManager cdbManager = new CDBManager(
                        getApplicationContext(), "ugb", "localhost",
                        "4545", "admin", "1234");
                cdbManager.startDB();
                String name = edtName.getText().toString();
                String desc = edtDesc.getText().toString();
                boolean state = swtState.isChecked();
                TareasDAO dao = new TareasDAO(cdbManager.database);
                Tareas t = new Tareas();
                t.setActive(state);
                t.setDescription(desc);
                t.setName(name);
                boolean inserted = dao.insert(t);
                if(inserted){
                    Toast.makeText(MainActivity.this, "Registro guardado",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error al insertar",
                            Toast.LENGTH_LONG).show();
                }
                edtDesc.setText("");
                edtName.setText("");
                swtState.setChecked(false);
                break;

            case R.id.btnVer:
                Intent i = new Intent(MainActivity.this, ListadoTareas.class);
                startActivity(i);
                break;
        }
    }
}
