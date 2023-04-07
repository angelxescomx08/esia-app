package com.ipn.esiatecamachalco.library.foldingtabbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

class SelectedMenuItem : androidx.appcompat.widget.AppCompatImageView {

    private var mCirclePaint: Paint
    private var radius: Float = 0f

    constructor(context: Context, @ColorRes color: Int) : this(context, null, color)

    constructor(context: Context, attrs: AttributeSet?, @ColorRes color: Int) : this(context, attrs, 0, color)

    constructor(context: Context, attrs: AttributeSet?, defStyleRes: Int, @ColorRes color: Int)
    : super(context, attrs, defStyleRes) {
        mCirclePaint = Paint(ANTI_ALIAS_FLAG).apply {
            this.color = ResourcesCompat.getColor(resources, color, null)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isActivated) {
            drawCircleIcon(canvas)
        }
    }

    /**
     * Here we are making scale drawing of selection
     * */
    private fun drawCircleIcon(canvas: Canvas) {
        canvas.drawCircle(canvas.width / 2.0f, canvas.height - paddingBottom / 1.5f, radius, mCirclePaint)
        if (radius <= canvas.width / 20.0f) {
            radius++
            invalidate()
        }
    }
}