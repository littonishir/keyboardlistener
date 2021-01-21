package com.litton.example

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.lib.listener.GlobalLayoutListener
import com.lib.listener.OnKeyboardChangedListener

class MainActivity : AppCompatActivity(), OnKeyboardChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mCon = findViewById<ConstraintLayout>(R.id.mCon)
        mCon.setOnClickListener {
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken, 0)
        }
        mCon.viewTreeObserver.addOnGlobalLayoutListener(GlobalLayoutListener(mCon, this))
    }

    override fun onKeyboardChanged(isShow: Boolean, keyboardHeight: Int, screenWidth: Int, screenHeight: Int) {
        val sb = StringBuilder()
        sb.append("键盘是否展开: ").append(isShow).append("\n")
                .append("键盘高度(px): ").append(keyboardHeight).append("\n")
                .append("屏幕宽度(px): ").append(screenWidth).append("\n")
                .append("屏幕可用高度(px): ").append(screenHeight).append("\n")
        val mTv = findViewById<TextView>(R.id.mTv)
        mTv.text = sb.toString()
    }
}