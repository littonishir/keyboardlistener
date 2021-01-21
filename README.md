# KeyboardListener

**Android监听键盘是否弹出，以及获取键盘高度，屏幕宽度，屏幕高度。**

## 使用

1. ```groovy
   implementation 'com.github.littonishir:keyboardlistener:1.0.2'
   ```

2. Activity实现OnKeyboardChangedListener接口

3. 初始化的时候添加全局监听

4. 复写onKeyboardChanged方法获取键盘相关的信息

```kotlin
class MainActivity : AppCompatActivity(), OnKeyboardChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mCon = findViewById<ConstraintLayout>(R.id.mCon)
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
```

下载项目体验一下！
