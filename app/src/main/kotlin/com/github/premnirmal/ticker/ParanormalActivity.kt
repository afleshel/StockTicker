package com.github.premnirmal.ticker

import android.app.Activity
import android.app.AlertDialog
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import com.github.premnirmal.tickerwidget.BuildConfig
import com.github.premnirmal.tickerwidget.R
import javax.inject.Inject

/**
 * Created by premnirmal on 2/25/16.
 */
class ParanormalActivity : BaseActivity() {

    @Inject
    lateinit internal var preferences: SharedPreferences

    private var dialogShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.getAppComponent().inject(this)
        val extras: Bundle? = intent.extras
        val widgetId: Int
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        } else {
            widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
        }
        if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            val result: Intent = Intent()
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(Activity.RESULT_OK, result)
        }
        setContentView(R.layout.activity_paranormal)
        if (preferences.getBoolean(Tools.WHATS_NEW, false)) {
            preferences.edit().putBoolean(Tools.WHATS_NEW, false).apply()
            val stringBuilder = StringBuilder()
            val whatsNew = resources.getStringArray(R.array.whats_new)
            for (i in whatsNew.indices) {
                stringBuilder.append("\t")
                stringBuilder.append(whatsNew[i])
                if (i != whatsNew.size - 1) {
                    stringBuilder.append("\n\n")
                }
            }
            AlertDialog.Builder(this).setTitle("What\'s new in Version " + BuildConfig.VERSION_NAME)
                    .setMessage(stringBuilder.toString())
                    .setNeutralButton("OK") { dialog, which -> dialog.dismiss() }
                    .show()
        }
    }

    override fun onResume() {
        super.onResume()
        supportInvalidateOptionsMenu()
        maybeAskToRate()
    }

    protected fun maybeAskToRate() {
        if (!dialogShown && Tools.shouldPromptRate()) {
            AlertDialog.Builder(this).setTitle(R.string.like_our_app)
                    .setMessage(R.string.please_rate)
                    .setPositiveButton(R.string.yes) {
                        dialog, which ->
                        sendToPlayStore()
                        Analytics.trackRateYes()
                        Tools.userDidRate()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.no) {
                        dialog, which ->
                        Analytics.trackRateNo()
                        dialog.dismiss()
                    }
                    .create().show()
            dialogShown = true
        }
    }

    private fun sendToPlayStore() {
        val marketUri: Uri = Uri.parse("market://details?id=" + packageName)
        val marketIntent: Intent = Intent(Intent.ACTION_VIEW, marketUri)
        startActivity(marketIntent)
    }
}