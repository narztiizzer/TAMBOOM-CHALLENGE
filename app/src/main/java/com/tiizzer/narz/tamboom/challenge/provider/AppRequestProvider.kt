package com.tiizzer.narz.tamboom.challenge.provider

import android.content.Context
import cc.narztiizzer.brief.network.KHttp
import cc.narztiizzer.brief.network.responses.Response
import com.google.gson.Gson
import com.tiizzer.narz.tamboom.challenge.Config
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.extension.gson.stringify
import com.tiizzer.narz.tamboom.challenge.model.Donation


object AppRequestProvider {
    fun getCharitiesRequest(context: Context): Response {
        val urlResId = if(Config.USE_SAMPLE_DATA) R.string.request_charities_mock_url else R.string.request_charities_url
        val url = context.getString(urlResId)
        return KHttp.get(url)
    }

    fun getDonationRequest(context: Context, data: Donation): Response {
        val urlResId = if(Config.USE_SAMPLE_DATA) R.string.request_donations_mock_url else R.string.request_donations_url
        val url = context.getString(urlResId)
        val headers = mapOf("Content-Type" to "application/json")
        return KHttp.post(
            url = url,
            headers = headers,
            data = Gson().stringify(data)
        )
    }
}
