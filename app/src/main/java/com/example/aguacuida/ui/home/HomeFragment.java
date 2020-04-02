package com.example.aguacuida.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aguacuida.ConectarActivity;
import com.example.aguacuida.R;
import com.example.hp.bluetoothjhr.BluetoothJhr;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private BluetoothJhr bluetoothJhr2;
    private HomeViewModel homeViewModel;
    private Button btnEnviar;
    private TextView consola, textView;
    private EditText textoEnviar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(root);
        bluetoothJhr2 = new BluetoothJhr(ConectarActivity.class, getActivity());
        btnEnviar.setOnClickListener(this);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void initComponents(View root){
        btnEnviar = (Button)root.findViewById(R.id.Enviar);
        consola = (TextView)root.findViewById(R.id.Consola);
        textoEnviar =(EditText)root.findViewById(R.id.TextoEnviar);
        textView = root.findViewById(R.id.text_home);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Enviar:
                String Mensaje = textoEnviar.getText().toString();
                bluetoothJhr2.Tx(Mensaje);

                break;
        }
    }
    @Override
    public  void  onResume(){
        super.onResume();
        bluetoothJhr2.ConectaBluetooth();
    }





}
