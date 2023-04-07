package com.ipn.esiatecamachalco.library.pdf

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.library.zoomimage.ZoomImageView

internal class PdfViewAdapter(private val renderer: com.ipn.esiatecamachalco.library.pdf.PdfRendererCore) :
    RecyclerView.Adapter<PdfViewAdapter.PdfPageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfPageViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_pdf_page, parent, false)
        return PdfPageViewHolder(v)
    }

    override fun getItemCount(): Int {
        return renderer.getPageCount()
    }

    override fun onBindViewHolder(holder: PdfPageViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PdfPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            with(itemView) {
                val pageView = findViewById<ZoomImageView>(R.id.pageView)
                //pageView.setImageBitmap(null)
                renderer.renderPage(position) { bitmap: Bitmap?, pageNo: Int ->
                    android.util.Log.d("PDF", "DEBUG: El pageNo es $pageNo y la posicion es $position -> ${pageNo != position}")
                    //pageView.setImageBitmap(bitmap)
                    Glide.with(itemView).load(bitmap).into(pageView)
                    /*if (pageNo != position) return@renderPage
                    bitmap?.let {
                        pageView.layoutParams = pageView.layoutParams.apply {
                            height =
                                (pageView.width.toFloat() / ((bitmap.width.toFloat() / bitmap.height.toFloat()))).toInt()
                        }
                        pageView.setImageBitmap(bitmap)
                        pageView.animation = AlphaAnimation(0F, 1F).apply {
                            interpolator = LinearInterpolator()
                            duration = 300
                        }
                    }*/
                }
            }
        }
    }
}