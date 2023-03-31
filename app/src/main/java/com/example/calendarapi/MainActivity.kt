package com.example.calendarapi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var name = ""
    var date = ""
    var description = ""
    private var month = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.Holiday_Button)
        val nameText = findViewById<TextView>(R.id.holiday_name)
        val dateText = findViewById<TextView>(R.id.holiday_date)
        val descriptionText = findViewById<TextView>(R.id.holiday_description)
        button.setOnClickListener{
            getHolidayData()
            nameText.text = name
            dateText.text = date
            descriptionText.text = description
            month = Random.nextInt(1,12)
        }

    }


    private fun getHolidayData() {
        val client = AsyncHttpClient()
        client["https://calendarific.com/api/v2/holidays?&api_key=7f6a92fd901021be055c2ab6e0081b75e268b395&country=US&year=2023&month=$month", object : JsonHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    Log.d("HolidayNameURL", "Name URL set")
                    name = json.jsonObject.getJSONObject("response").getJSONArray("holidays").getJSONObject(0).getString("name")
                    date = json.jsonObject.getJSONObject("response").getJSONArray("holidays").getJSONObject(2).getJSONObject("date").getString("iso")
                    description = json.jsonObject.getJSONObject("response").getJSONArray("holidays").getJSONObject(1).getString("description")
                Log.d("Holiday", "response successful$json")
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    Log.d("Holiday Data Error", "error with getHolidayData")
                }
            }]


        }


    }
