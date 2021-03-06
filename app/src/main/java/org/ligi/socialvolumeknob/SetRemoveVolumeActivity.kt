package org.ligi.socialvolumeknob

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class SetRemoveVolumeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder().build()

        val token = intent.data.getQueryParameter("token")
        croller.label ="Ligi"

        croller.progress = croller.max
        croller.labelSize = resources.getDimension(R.dimen.croller_label_size).toInt()
        croller.setOnProgressChangedListener {
            val asDouble = it.toDouble() / croller.max

            Thread(Runnable {
                val string = okHttpClient.newCall(
                        Request.Builder().url(" https://fcm.googleapis.com/fcm/send")

                                .header("Authorization", "key=AAAA8AjvQIc:APA91bExsE3Qn1ULeLZ8COAYDbzLX4p1rc08x6q8ooRTYPKraFy0vEPflavesGT6cbWaKOxucvKCMk9SoK6Yjv--kRJLMEGq3lSoAQ9Ce9alrHpPzEmjLk9aMfkoZlxLjWhs_v4ej-xp")
                                .method("POST", RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                        "{\"registration_ids\":[\"$token\"],\"data\":{\"volume\":\"$asDouble\"}}"))
                                .build()
                ).execute().body().string()

                Log.i("SocialVolumeKnob",string)
            }).start()

        }

    }

}
