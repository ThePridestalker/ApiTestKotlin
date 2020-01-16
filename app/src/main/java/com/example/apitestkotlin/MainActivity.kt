package com.example.apitestkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var apiURL: String =
        "https://gist.githubusercontent.com/ThePridestalker/2cc51b6e682f2db5e3a76284b4504428/raw/cf3f9d4ec6759f35f1c7c3d55c54dc8aafbce0e5/Frutas.json"

    private var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonAPI.setOnClickListener {
            callAPI();
        }
    }

    private fun callAPI() {

        doAsync {
            val apiResponse = URL(apiURL).readText()
            val arrayFrutas = JSONArray(apiResponse)
            for (i in 0 until arrayFrutas.length()){
                val fruta = arrayFrutas.getJSONObject(i)
                val map = HashMap<String,String>()
                map["name"] = fruta.getString("name")
                dataList.add(map)
            }
            uiThread {
            listView.adapter = CustomAdapter(this@MainActivity, dataList)
            }
        }
    }
}
