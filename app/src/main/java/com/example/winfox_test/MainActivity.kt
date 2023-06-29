package com.example.winfox_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.winfox_test.password.PasswordFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PasswordFragment>(R.id.fragment_container)
        }
    }

    override fun onStop() {
        super.onStop()

        Session.lastSessionTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()

        val hasPreviousSession =  Session.lastSessionTime != Session.EMPTY_SESSION_TIME
        val needLogout = System.currentTimeMillis() - Session.lastSessionTime > LOGOUT_TIMEOUT

        if (hasPreviousSession && needLogout) {
            finish()
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Session.lastSessionTime = Session.EMPTY_SESSION_TIME
    }

    fun replaceFragment(fragment: Fragment, allowBackNavigation: Boolean = true) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            if (allowBackNavigation) {
                replace(R.id.fragment_container, fragment).addToBackStack(null)
            } else {
                replace(R.id.fragment_container, fragment)
            }
        }
    }

    companion object {
        private const val LOGOUT_TIMEOUT = 60_000L // 60 sec
    }
}
