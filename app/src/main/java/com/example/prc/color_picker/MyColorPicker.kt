package com.example.prc.color_picker

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.prc.R
import com.example.prc.toBmp
import com.example.prc.visible

@SuppressLint("ClickableViewAccessibility")
class MyColorPicker(val fl: FrameLayout, val ivPicker: ImageView, selectedcolor: (colorInt: Int, colorSt: String) -> Unit, onEnd: () -> Unit) {

    init {
        //for inner border
        ((ivPicker.drawable as LayerDrawable).findDrawableByLayerId(R.id.innerRing) as GradientDrawable).setStroke(42, Color.BLACK)
        setThumbColor(Color.WHITE)
        ivPicker.visible()

        ivPicker.parent?.let { (it as ViewGroup).removeView(ivPicker) }
        fl.addView(ivPicker)

        val ivBitmap = fl.toBmp()

        val od = OnBoundedDragTouchListener(ivPicker)
        od.setOnDragActionListener(object : OnBoundedDragTouchListener.OnDragActionListener {
            override fun onDragStart(view: View) {
                //don't use roundToInt here, I repeat don't!
                val cX: Int = (view.x + view.width * 1f / 2).toInt()
                val cY: Int = ((view.y + view.height * 1f / 2).toInt())

                val pixel = ivBitmap.getPixel(
                        if (cX >= ivBitmap.width) ivBitmap.width - 1 else cX,
                        if (cY >= ivBitmap.height) ivBitmap.height - 1 else cY)

                val color = Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel))

                selectedcolor(color, "#${Integer.toHexString(color)}")

                setThumbColor(color)
            }

            override fun onDragEnd(view: View) {
                onEnd()
            }

        })
        ivPicker.setOnTouchListener(od)

        fl.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    ivPicker.animate()
                            .x(event.x - ivPicker.width / 2)
                            .y(event.y - ivPicker.height / 2)
                            .setDuration(0).start()

                    ivPicker.dispatchTouchEvent(event)
                }

                MotionEvent.ACTION_MOVE -> ivPicker.dispatchTouchEvent(event)
            }

            true
        }
    }

    private fun setThumbColor(selectedColor: Int) {
        ((ivPicker.drawable as LayerDrawable).findDrawableByLayerId(R.id.ring) as GradientDrawable).setStroke(40, selectedColor)
    }
}