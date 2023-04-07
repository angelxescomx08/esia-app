package com.ipn.esiatecamachalco.library.alert.clases

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.ipn.esiatecamachalco.R
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.math.hypot

class KUtils {
    companion object {

        fun expand(v: View) {
            val matchParentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
            val wrapContentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
            val targetHeight = v.measuredHeight

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    v.layoutParams.height =
                        if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Expansion speed of 1dp/ms
            a.duration =
                (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight = v.measuredHeight
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Collapse speed of 1dp/ms
            a.duration =
                (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }

        fun fromHtml(html: String): Spanned {
            val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }

        fun isNetworkEnable(context: Context): Boolean {

            val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var isMobileDataEnabled = false // Assume disabled

            try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val cmClass = Class.forName(cm.javaClass.name)
                val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
                method.isAccessible = true
                isMobileDataEnabled = method.invoke(cm) as Boolean
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

            //Log.i(TAG, "Proceso 'WIFI_MOBILE_ENABLE', DEBUG: wifi.isWifiEnabled = ${wifi.isWifiEnabled}")
            //Log.i(TAG, "Proceso 'WIFI_MOBILE_ENABLE', DEBUG: ismobileDataEnabled = $ismobileDataEnabled")

            return wifi.isWifiEnabled || isMobileDataEnabled
        }

        fun startGooglePing(ctx: Context): Boolean {
            var isSuccess = false
            try {
                if (isNetworkEnable(ctx)) {
                    try {
                        val url = URL("https://www.google.com:443/")
                        val urlc = url.openConnection() as HttpURLConnection
                        urlc.setRequestProperty(
                            "User-Agent",
                            "Android Application:" + ctx.getString(R.string.version)
                        )
                        urlc.setRequestProperty("Connection", "close")
                        urlc.connectTimeout = 5 * 1000
                        urlc.connect()
                        if (urlc.responseCode < 500 || urlc.responseCode > 504) {
                            isSuccess = true
                        }
                    }
                    catch (ex: java.net.SocketTimeoutException) {
                        ex.printStackTrace()
                    }
                    catch (ex2: java.io.IOException) {
                        ex2.printStackTrace()
                    }
                }
            }
            catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return isSuccess
        }

        fun hideKeyboard(act: Activity?) {
            act?.let { activity ->
                activity.currentFocus?.let { view ->
                    val inputManager =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(
                        view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                    view.clearFocus()
                }
            }
        }

        fun blinkView(view: View, duration:Long = 800 ) {
            val anim: Animation = AlphaAnimation(0.0f, 1.0f)
            anim.duration = duration
            anim.startOffset = 20
            anim.repeatMode = Animation.REVERSE
            view.startAnimation(anim)
        }

        fun animateViewBottom(view: View) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View, left: Int, top: Int, right: Int,
                    bottom: Int, oldLeft: Int, oldTop: Int,
                    oldRight: Int, oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)
                    animateRevealColorFromCoordinates(view as ViewGroup, right / 2, bottom)
                }
            })
        }

        fun animateViewTop(view: View) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View, left: Int, top: Int, right: Int,
                    bottom: Int, oldLeft: Int, oldTop: Int,
                    oldRight: Int, oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)
                    animateRevealColorFromCoordinates(view as ViewGroup, right / 2, top)
                }
            })
        }

        private fun animateRevealColorFromCoordinates(viewRoot: ViewGroup, x: Int, y: Int): Animator? {
            val finalRadius =
                hypot(viewRoot.width.toDouble(), viewRoot.height.toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0f, finalRadius)
            anim.duration = 1000
            anim.interpolator = AccelerateDecelerateInterpolator()
            anim.start()
            return anim
        }
    }
}

fun EditText.manageTextWatcher(textWatcher: TextWatcher) {
    this.setOnFocusChangeListener { _, isFocus ->
        if (isFocus) this.addTextChangedListener(textWatcher)
        else this.removeTextChangedListener(textWatcher)
    }
}