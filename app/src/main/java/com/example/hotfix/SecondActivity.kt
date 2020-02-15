package com.example.hotfix

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Environment
import android.view.View
import com.example.hotfix.utils.FixDexUtils
import java.io.File
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<View>(R.id.tv_fix).setOnClickListener { fixBug() }
        findViewById<View>(R.id.tv_calculate).setOnClickListener { calculate() }
    }

    private fun fixBug() {
        fixBugTest()
    }

    private fun calculate() {
        val calculator = Calculator()
        calculator.calculate(this)
    }

    private fun fixBugTest() {
        val sourceFile = File(
            Environment.getExternalStorageDirectory(),
            "classes2.dex")
        //找寻到app私有目录 将dex补丁包放置到私有目录下
        val targetFile = File(getDir("odex",Context.MODE_PRIVATE).absolutePath+File.separator+"classes2.dex")
        if (targetFile.exists()) targetFile.delete()
        //私有目录复制完毕

        sourceFile.copyTo(targetFile)
        FixDexUtils.loadFixedDex(this)
    }

}
