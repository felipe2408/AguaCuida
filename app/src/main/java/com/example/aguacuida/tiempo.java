package com.example.aguacuida;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import com.androdocs.httprequest.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class tiempo extends Fragment {

    private TiempoViewModel mViewModel;
    String CITY = "madrid,co";
    String API = "a008fc996b3ad0b7980913d8b17cf757";

    public static tiempo newInstance() {
        return new tiempo();
    }

    public class ViewHolder{
        TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
                sunsetTxt, windTxt, pressureTxt, humidityTxt, errorTxt;
        ProgressBar progressBar;
        RelativeLayout mainContainer;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tiempo_fragment, container, false);
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.addressTxt = (TextView)view.findViewById(R.id.address);
        viewHolder.addressTxt = (TextView)view.findViewById(R.id.address);
        viewHolder.updated_atTxt = (TextView)view.findViewById(R.id.updated_at);
        viewHolder.statusTxt = (TextView)view.findViewById(R.id.status);
        viewHolder.tempTxt = (TextView)view.findViewById(R.id.temp);
        viewHolder.temp_minTxt = (TextView)view.findViewById(R.id.temp_min);
        viewHolder.temp_maxTxt = (TextView)view.findViewById(R.id.temp_max);
        viewHolder.sunriseTxt = (TextView)view.findViewById(R.id.sunrise);
        viewHolder.sunsetTxt = (TextView)view.findViewById(R.id.sunset);
        viewHolder.windTxt = (TextView)view.findViewById(R.id.wind);
        viewHolder.pressureTxt = (TextView)view.findViewById(R.id.pressure);
        viewHolder.humidityTxt = (TextView)view.findViewById(R.id.humidity);

        new weatherTask(viewHolder, view).execute();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TiempoViewModel.class);
        // TODO: Use the ViewModel
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        ViewHolder viewHolder;
        View view;
        public weatherTask(ViewHolder viewHolder, View view){
            this.viewHolder = viewHolder;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            viewHolder.progressBar = (ProgressBar)view.findViewById(R.id.loader);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.mainContainer = (RelativeLayout)view.findViewById(R.id.mainContainer);
            viewHolder.mainContainer.setVisibility(View.GONE);


            viewHolder.errorTxt = (TextView)view.findViewById(R.id.errorText);
            viewHolder.errorTxt.setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                this.viewHolder.addressTxt.setText(address);
                this.viewHolder.updated_atTxt.setText(updatedAtText);
                this.viewHolder.statusTxt.setText(weatherDescription.toUpperCase());
                this.viewHolder.tempTxt.setText(temp);
                this.viewHolder.temp_minTxt.setText(tempMin);
                this.viewHolder.temp_maxTxt.setText(tempMax);
                this.viewHolder.sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                this.viewHolder.sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                this.viewHolder.windTxt.setText(windSpeed);
                this.viewHolder.pressureTxt.setText(pressure);
                this.viewHolder.humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.mainContainer.setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.errorTxt.setVisibility(View.VISIBLE);
            }

        }
    }

}
