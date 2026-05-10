package com.example.weatherappkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappkotlin.api.RetrofitInstance
import com.example.weatherappkotlin.databinding.ActivityMainBinding
import com.example.weatherappkotlin.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val apiKey = "use your key "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetWeather.setOnClickListener {

            val city = binding.editCity.text.toString()

            if (city.isNotEmpty()) {

                RetrofitInstance.api.getWeather(city, apiKey)
                    .enqueue(object : Callback<WeatherResponse> {

                        override fun onResponse(
                            call: Call<WeatherResponse>,
                            response: Response<WeatherResponse>
                        ) {

                            if (response.isSuccessful) {

                                val temp = response.body()?.main?.temp
                                binding.txtResult.text = "Temperature: $temp°C"

                            } else {

                                val errorBody = response.errorBody()?.string()
                                binding.txtResult.text = "Error: $errorBody"
                            }
                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                            Toast.makeText(
                                this@MainActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }
}