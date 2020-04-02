package com.example.aguacuida;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aguacuida.ui.dashboard.DashboardFragment;
import com.example.aguacuida.ui.home.HomeFragment;
import com.example.aguacuida.ui.notifications.NotificationsFragment;
import com.example.hp.bluetoothjhr.BluetoothJhr;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //Se agregar el listener para interactuar con el nav y así poder cambiar de fragmentos
        navView.setOnNavigationItemSelectedListener(navListener);
        //Agregamos el fragmento principal para cuando inicie la app
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, fragment).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            //Se crea un objeto de tipo fragment donde validamos con un switch case el id del botón del navigation y después instanciamos
            //al fragmento que se accederá
            switch (item.getItemId()){
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new DashboardFragment();
                    break;

                case R.id.navigation_weather:
                    fragment = new tiempo();
                    break;

            }
            //Y aqui se reemplaza el fragment por que el que se haya seleccionado previamente
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();


            return true;
        }
    };





}
