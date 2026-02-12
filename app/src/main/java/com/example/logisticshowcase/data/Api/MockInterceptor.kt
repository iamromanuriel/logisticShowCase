package com.example.logisticshowcase.data.Api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class MockInterceptor(
  val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()


        return chain.proceed(chain.request())
    }


    private fun Context.readJsonFromAssets(fileName: String): String {
        return try {
            this.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            ""
        }
    }
}