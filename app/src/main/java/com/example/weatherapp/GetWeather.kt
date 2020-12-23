package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.network.NetworkInterface
import com.example.weatherapp.network.PresenterClass
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class GetWeather : AppCompatActivity(), NetworkInterface.PresenterToView {

    lateinit var textView: TextView
    private var presenter: NetworkInterface.PresenterToModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_weather)

        findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
        findViewById<TextView>(R.id.errorText).visibility = View.GONE

        presenter = PresenterClass(this)

        val intent: Intent = getIntent();
        val cityName: String = intent.getStringExtra("city").toString()
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=4d11bca739359c0804812b65c2fbe0c5"

        presenter?.loadData(this, url)

    }

    override fun onSuccess(response: JSONObject) {
        val jsonObj = JSONObject(response.toString())
        val main = jsonObj.getJSONObject("main")
        val sys = jsonObj.getJSONObject("sys")
        val wind = jsonObj.getJSONObject("wind")
        val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
        val updatedAt: Long = jsonObj.getLong("dt")
        val updatedAtText =
            "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                Date(updatedAt * 1000)
            )
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val temperature = main.getString("temp")
        val tempToDouble: Double = temperature.toDouble() - 273.15
        val t = df.format(tempToDouble)
        val temp = "$t°C"

        val temperature_min = main.getString("temp_min")
        val temp_min = temperature_min.toDouble() - 273.15
        val temperatureMin = df.format(temp_min)
        val tempMin = "Min Temp: $temperatureMin°C"

        val temperature_max = main.getString("temp_max")
        val tempmax = temperature_max.toDouble() - 273.15
        val temperatureMax = df.format(tempmax)
        val tempMax = "Max Temp: $temperatureMax°C"

        val pressure = main.getString("pressure")
        val humidity = main.getString("humidity")
        val sunrise: Long = sys.getLong("sunrise")
        val sunset: Long = sys.getLong("sunset")
        val windSpeed = wind.getString("speed")
        val weatherDescription = weather.getString("description")
        val address = jsonObj.getString("name") + ", " + sys.getString("country")

        findViewById<TextView>(R.id.address).text = address
        findViewById<TextView>(R.id.updated_at).text = updatedAtText
        findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
        findViewById<TextView>(R.id.temp).text = temp
        findViewById<TextView>(R.id.temp_min).text = tempMin
        findViewById<TextView>(R.id.temp_max).text = tempMax
        findViewById<TextView>(R.id.sunrise).text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
        findViewById<TextView>(R.id.sunset).text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
        findViewById<TextView>(R.id.wind).text = windSpeed
        findViewById<TextView>(R.id.pressure).text = pressure
        findViewById<TextView>(R.id.humidity).text = humidity

        findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
        findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
    }

    override fun onFailed() {
        Toast.makeText(this, "Check connection or type the city name correctly", Toast.LENGTH_LONG)
            .show()
    }


}