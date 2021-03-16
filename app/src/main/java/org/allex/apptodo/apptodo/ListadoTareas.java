package org.allex.apptodo.apptodo;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ListadoTareas extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tareas);

        recyclerView = findViewById(R.id.recyclerTareas);
        CDBManager cdbManager = new CDBManager(this, "ugb",
                "localhost", "4545", "admin", "1234");
        cdbManager.startDB();
        TareasDAO dao = new TareasDAO(cdbManager.database);
        ArrayList<Tareas> tareas = dao.getAll();

        TareasAdapter adapter = new TareasAdapter(tareas);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        CDBManager cdbManager = new CDBManager(this, "ugb",
                "localhost", "4545", "admin", "1234");
        cdbManager.startDB();
        TareasDAO dao = new TareasDAO(cdbManager.database);
        ArrayList<Tareas> tareas = dao.getAll();

        TareasAdapter adapter = new TareasAdapter(tareas);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
