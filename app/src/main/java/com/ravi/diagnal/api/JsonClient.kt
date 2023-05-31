package com.ravi.diagnal.api

import android.content.Context
import com.google.gson.Gson
import com.ravi.libapi.response.DiagnalResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

// Function to load a JSON file asynchronously
suspend fun loadJsonFile(context: Context, fileName: String): DiagnalResponse? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = context.assets.open(fileName) // Assuming the JSON files are stored in the "assets" folder
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonString.append(line)
            }
            reader.close()
            val gson = Gson()
            val myDataList = gson.fromJson(jsonString.toString(), DiagnalResponse::class.java)
            myDataList
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }
}
