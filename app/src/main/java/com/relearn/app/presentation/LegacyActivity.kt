package com.relearn.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.relearn.app.R
import com.relearn.app.feature.offline.data.OfflineHabitsFragment

class LegacyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legacy)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OfflineHabitsFragment())
            .commit()
    }
}
