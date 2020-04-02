package com.example.aguacuida;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.bluetoothjhr.BluetoothJhr;

import static java.lang.Thread.*;

public class ConectarActivity extends AppCompatActivity {
    ListView ListaDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar);
        ListaDispositivos = (ListView)findViewById(R.id.ListaDispositivos);
        final BluetoothJhr bluetoothJhr = new BluetoothJhr(this, ListaDispositivos);
        bluetoothJhr.EncenderBluetooth();

        ListaDispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothJhr.Disp_Seleccionado(view,position,MainActivity.class);
            }
        });




    }



}
