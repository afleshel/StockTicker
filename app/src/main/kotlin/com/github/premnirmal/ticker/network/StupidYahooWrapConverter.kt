package com.github.premnirmal.ticker.network

import android.util.Log
import com.github.premnirmal.ticker.network.data.Suggestions
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by premnirmal on 3/3/16.
 */
internal class StupidYahooWrapConverter(gson: Gson) : BaseConverter<Suggestions>(gson) {

  override fun convert(value: ResponseBody?): Suggestions? {
    try {
      val bodyString = getString(value!!.byteStream())
      val m = PATTERN_RESPONSE.matcher(bodyString)
      if (m.find()) {
        val suggestions = gson.fromJson(m.group(1), Suggestions::class.java)
        val resultSet = suggestions.ResultSet
        val query = resultSet?.Query
        val result = resultSet?.Result
        Log.d("RESULT", "RESULT")
        return suggestions
      }
      throw error("Invalid response")
    } catch (e: IOException) {
      e.printStackTrace()
      return null
    }
  }

  companion object {

    private val PATTERN_RESPONSE = Pattern.compile(
        "YAHOO\\.Finance\\.SymbolSuggest\\.ssCallback\\((\\{.*?\\})\\)")
  }
}