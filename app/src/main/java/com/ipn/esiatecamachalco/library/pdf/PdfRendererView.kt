package com.ipn.esiatecamachalco.library.pdf

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.ipn.esiatecamachalco.R
import java.io.File

@SuppressLint("SetTextI18n")
class PdfRendererView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    enum class PdfEngine(val value: Int) {
        INTERNAL(100),
        GOOGLE(200)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var pdfRendererCore: com.ipn.esiatecamachalco.library.pdf.PdfRendererCore
    private lateinit var pdfViewAdapter: PdfViewAdapter
    private lateinit var pageNo: TextView
    private lateinit var webView: WebView
    private var quality = com.ipn.esiatecamachalco.library.pdf.PdfQuality.NORMAL
    private var engine = PdfEngine.INTERNAL
    private var showDivider = true
    private var divider: Drawable? = null
    private var runnable = Runnable {}
    private var pdfRendererCoreInitialised = false

    var statusListener: StatusCallBack? = null
    val totalPageCount: Int
        get() {
            return pdfRendererCore.getPageCount()
        }

    interface StatusCallBack {
        fun onPageChanged(currentPage: Int, totalPage: Int) {}
    }

    fun initWithFile(file: File, pdfQuality: com.ipn.esiatecamachalco.library.pdf.PdfQuality = this.quality) {
        init(file, pdfQuality)
    }

    private fun init(file: File, pdfQuality: com.ipn.esiatecamachalco.library.pdf.PdfQuality) {
        pdfRendererCore =
            com.ipn.esiatecamachalco.library.pdf.PdfRendererCore(context, file, pdfQuality)
        pdfRendererCoreInitialised = true
        pdfViewAdapter = PdfViewAdapter(pdfRendererCore)
        val v = LayoutInflater.from(context).inflate(R.layout.pdf_rendererview, this, false)
        addView(v)
        pageNo = v.findViewById(R.id.pageNo)
        webView = v.findViewById(R.id.webView)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            adapter = pdfViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            if (showDivider) {
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                    divider?.let { setDrawable(it) }
                }.let { addItemDecoration(it) }
            }
            addOnScrollListener(scrollListener)
        }

        runnable = Runnable {
            pageNo.visibility = View.GONE
        }

    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            (recyclerView.layoutManager as LinearLayoutManager).run {
                var foundPosition = findFirstVisibleItemPosition()

                /*android.util.Log.d("PDF", "DEBUG: findFirstCompletelyVisibleItemPosition ${findFirstCompletelyVisibleItemPosition()}");
                android.util.Log.d("PDF", "DEBUG: findFirstVisibleItemPosition ${findFirstVisibleItemPosition()}");
                android.util.Log.d("PDF", "DEBUG: findLastVisibleItemPosition ${findLastVisibleItemPosition()}");
                android.util.Log.d("PDF", "DEBUG: findLastCompletelyVisibleItemPosition ${findLastCompletelyVisibleItemPosition()}");
                android.util.Log.d("PDF", "DEBUG: -----------------------");*/

                pageNo.apply {
                    if (foundPosition != NO_POSITION) {
                        text = "${(foundPosition + 1)} de $totalPageCount"
                    }
                    //else { android.util.Log.d("PDF", "DEBUG: No se ha actualizado el valor $foundPosition"); }
                    pageNo.visibility = View.VISIBLE
                }

                if (foundPosition == 0)
                    pageNo.postDelayed({
                        pageNo.visibility = GONE
                    }, 2000)

                if (foundPosition != NO_POSITION) {
                    statusListener?.onPageChanged(foundPosition, totalPageCount)
                    return@run
                }
                foundPosition = findFirstVisibleItemPosition()
                if (foundPosition != NO_POSITION) {
                    statusListener?.onPageChanged(foundPosition, totalPageCount)
                    return@run
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                pageNo.postDelayed(runnable, 3000)
            } else {
                pageNo.removeCallbacks(runnable)
            }
        }

    }

    init {
        getAttrs(attrs, defStyleAttr)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PdfRendererView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val ratio =
            typedArray.getInt(R.styleable.PdfRendererView_pdfView_quality, com.ipn.esiatecamachalco.library.pdf.PdfQuality.NORMAL.ratio)
        quality = com.ipn.esiatecamachalco.library.pdf.PdfQuality.values().first { it.ratio == ratio }
        val engineValue =
            typedArray.getInt(R.styleable.PdfRendererView_pdfView_engine, PdfEngine.INTERNAL.value)
        engine = PdfEngine.values().first { it.value == engineValue }
        showDivider = typedArray.getBoolean(R.styleable.PdfRendererView_pdfView_showDivider, true)
        divider = typedArray.getDrawable(R.styleable.PdfRendererView_pdfView_divider)

        typedArray.recycle()
    }

    fun closePdfRender() {
        if (pdfRendererCoreInitialised)
            pdfRendererCore.closePdfRender()
    }

}