package com.blinking.teke.android.blinking

import android.animation.*
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.animation.LinearInterpolator
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val anim:ValueAnimator= ValueAnimator()
    val mainLayout:ConstraintLayout by lazy { context }
    val run by lazy { runbutton }
    val durEdit:EditText by lazy { duration }
    val blinkEdit by lazy { blink_time }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        anim.init()
        run.setOnClickListener{
            if (!anim.isRunning){
                val duration= durEdit.text?.toString()?.toFloatOrNull() ?: 10000f;
                val blink= blinkEdit.text?.toString()?.toFloatOrNull() ?: 500f;
                if(duration<blink)
                    return@setOnClickListener
                val steps = (duration/blink).toInt()
                val values = IntArray(Math.min(steps,10000)).mapIndexed { index, i -> index }.toIntArray()
                anim.setIntValues(*values)
                anim.duration=duration.toLong()
                anim.interpolator=LinearInterpolator()
                run.setText(R.string.stop)
                anim.start()
            }
            else{
                anim.cancel()
                run.setText(R.string.start)
            }
        }
    }

    fun ValueAnimator.init(){
        ValueAnimator@this.addUpdateListener {
            val p = it?.getAnimatedValue() as Int
            val color= if(p%2==0) Color.BLACK else Color.WHITE
            mainLayout.setBackgroundColor(color)
        }
        ValueAnimator@this.addListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                onAnimationCancel(p0)
            }

            override fun onAnimationCancel(p0: Animator?) {
                run.setText(R.string.start)
                mainLayout.setBackgroundColor(Color.WHITE)
            }

            override fun onAnimationStart(p0: Animator?) {}

        })
    }
}
