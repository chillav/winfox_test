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

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment).addToBackStack(null)
        }
    }
}
