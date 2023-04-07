package com.ipn.esiatecamachalco.library.zoomimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.AttrRes


/**
 * Uses [ZoomEngine] to allow zooming and pan events to the inner drawable.
 */
final class ZoomImageView private constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        val engine: ZoomEngine = ZoomEngine(context)
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr), ZoomApi by engine {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0)
            : this(context, attrs, defStyleAttr, ZoomEngine(context))

    private val mMatrix = Matrix()

    private val isInSharedElementTransition: Boolean
        get() = width != measuredWidth || height != measuredHeight

    init {
        val overScrollHorizontal = true
        val overScrollVertical = true
        val horizontalPanEnabled = true
        val verticalPanEnabled = true
        val overPinchable = true
        val zoomEnabled = true
        val flingEnabled = true
        val scrollEnabled = true
        val oneFingerScrollEnabled = true
        val twoFingersScrollEnabled = true
        val threeFingersScrollEnabled = true
        val allowFlingInOverscroll = true
        val minZoom = ZoomApi.MIN_ZOOM_DEFAULT
        val maxZoom = ZoomApi.MAX_ZOOM_DEFAULT
        @ZoomApi.ZoomType val minZoomMode = ZoomApi.MIN_ZOOM_DEFAULT_TYPE
        @ZoomApi.ZoomType val maxZoomMode = ZoomApi.MAX_ZOOM_DEFAULT_TYPE
        val transformation = ZoomApi.TRANSFORMATION_CENTER_INSIDE
        val transformationGravity = ZoomApi.TRANSFORMATION_GRAVITY_AUTO
        val alignment = ZoomApi.ALIGNMENT_DEFAULT
        val animationDuration = ZoomEngine.DEFAULT_ANIMATION_DURATION

        engine.setContainer(this)
        engine.addListener(object : ZoomEngine.Listener {
            override fun onIdle(engine: ZoomEngine) {}
            override fun onUpdate(engine: ZoomEngine, matrix: Matrix) {
                mMatrix.set(matrix)
                imageMatrix = mMatrix
                awakenScrollBars()
            }
        })

        setTransformation(transformation, transformationGravity)
        setAlignment(alignment)
        setOverScrollHorizontal(overScrollHorizontal)
        setOverScrollVertical(overScrollVertical)
        setHorizontalPanEnabled(horizontalPanEnabled)
        setVerticalPanEnabled(verticalPanEnabled)
        setOverPinchable(overPinchable)
        setZoomEnabled(zoomEnabled)
        setFlingEnabled(flingEnabled)
        setScrollEnabled(scrollEnabled)
        setOneFingerScrollEnabled(oneFingerScrollEnabled)
        setTwoFingersScrollEnabled(twoFingersScrollEnabled)
        setThreeFingersScrollEnabled(threeFingersScrollEnabled)
        setAllowFlingInOverscroll(allowFlingInOverscroll)
        setAnimationDuration(animationDuration)
        setMinZoom(minZoom, minZoomMode)
        setMaxZoom(maxZoom, maxZoomMode)

        imageMatrix = mMatrix
        scaleType = ScaleType.MATRIX
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable != null) {
            if (drawable.intrinsicWidth == -1 || drawable.intrinsicHeight == -1) {
                throw IllegalArgumentException("Drawables without intrinsic dimensions (such as a solid color) are not supported")
            }
            engine.setContentSize(drawable.intrinsicWidth.toFloat(),
                    drawable.intrinsicHeight.toFloat())
        }
        super.setImageDrawable(drawable)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Using | so click listeners work.
        return engine.onTouchEvent(ev) or super.onTouchEvent(ev)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        /* Log.e("ZoomEngineDEBUG", "View - dispatching container size" +
                " width: " + getWidth() + ", height:" + getHeight() +
                " - different?" + isInSharedElementTransition()); */
        engine.setContainerSize(width.toFloat(), height.toFloat(), true)
    }

    override fun onDraw(canvas: Canvas) {
        if (isInSharedElementTransition) {
            // The framework will often change our matrix between onUpdate and onDraw, leaving us with
            // a bad first frame that makes a noticeable flash. Replace the matrix values with our own.
            imageMatrix = mMatrix
        }
        super.onDraw(canvas)
    }

    override fun computeHorizontalScrollOffset(): Int = engine.computeHorizontalScrollOffset()

    override fun computeHorizontalScrollRange(): Int = engine.computeHorizontalScrollRange()

    override fun computeVerticalScrollOffset(): Int = engine.computeVerticalScrollOffset()

    override fun computeVerticalScrollRange(): Int = engine.computeVerticalScrollRange()
}