package com.example.hotfix

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            val perm = Manifest.permission.WRITE_EXTERNAL_STORAGE
            if (checkSelfPermission(perm) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(perm), 200)
            }
        }

        tv_fix.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}
