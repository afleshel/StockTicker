package com.github.premnirmal.ticker.settings

import android.os.AsyncTask
import android.text.TextUtils
import com.github.premnirmal.ticker.Analytics
import com.github.premnirmal.ticker.CrashLogger
import com.github.premnirmal.ticker.model.IStocksProvider
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.Arrays

/**
 * Created by premnirmal on 2/27/16.
 */
internal open class FileImportTask(
    private val stocksProvider: IStocksProvider) : AsyncTask<String, Void, Boolean>() {

  override fun doInBackground(vararg params: String?): Boolean? {
    if (params.size == 0 || params[0] == null) {
      return false
    }
    val uri: URI
    try {
      uri = URI(params[0])
    } catch (e: URISyntaxException) {
      CrashLogger.logException(e)
      return false
    }

    if (uri.path == null || !uri.path.endsWith(".txt")) {
      return false
    }

    val tickersFile = File(params[0])
    var result = false

    if (!tickersFile.exists()) {
      return false
    }
    val text = StringBuilder()
    try {
      val br = BufferedReader(FileReader(tickersFile))
      var line: String? = br.readLine()
      while (line != null) {
        text.append(line)
        line = br.readLine()
      }
      val tickers = text.toString()
          .replace(" ".toRegex(), "")
          .split(",".toRegex())
          .dropLastWhile(String::isEmpty)
          .toTypedArray()
      stocksProvider.addStocks(Arrays.asList(*tickers))
      result = true
      Analytics.trackSettingsChange("IMPORT", TextUtils.join(",", tickers))
    } catch (e: IOException) {
      CrashLogger.logException(e)
      result = false
    }

    return result
  }
}