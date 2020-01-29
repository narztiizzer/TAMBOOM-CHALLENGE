package com.tiizzer.narz.tamboom.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiizzer.narz.tamboom.challenge.view.DonationFragment
import com.tiizzer.narz.tamboom.challenge.view.TamboonFragment

class DonationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DonationFragment())
            .commit()
    }
}
