package com.ipn.esiatecamachalco.library.pdf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ipn.esiatecamachalco.databinding.ActivityPdfViewerBinding
import java.io.File

class PdfViewerActivity : AppCompatActivity() {

    private var menuItem: MenuItem? = null
    private var fileUrl: String? = null

    companion object {
        const val FILE_URL = "pdf_file_url"
        private const val FILE_DIRECTORY = "pdf_file_directory"
        const val FILE_TITLE = "pdf_file_title"

        fun launchPdfFromPath(
            context: Context?,
            path: String?,
            pdfTitle: String?,
        ): Intent {
            val intent = Intent(context, PdfViewerActivity::class.java)
            intent.putExtra(FILE_URL, path)
            intent.putExtra(FILE_TITLE, pdfTitle)
            return intent
        }

    }

    private lateinit var binding: ActivityPdfViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar(intent.extras!!.getString(FILE_TITLE,"PDF"))

        init()
    }

    private fun init() {
        if (intent.extras!!.containsKey(FILE_URL)) {
            fileUrl = intent.extras!!.getString(FILE_URL)
            initPdfViewerWithPath(this.fileUrl)
        }
    }

    private fun setUpToolbar(toolbarTitle: String) {
        setSupportActionBar(binding.includedView.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            binding.includedView.tvAppBarTitle.text = toolbarTitle
            setDisplayShowTitleEnabled(false)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPdfViewerWithPath(filePath: String?) {
        try {
            val file = File(filePath!!)
            binding.pdfView.initWithFile(
                file,
                com.ipn.esiatecamachalco.library.pdf.PdfQuality.NORMAL
            )
        } catch (e: Exception) {
            onPdfError()
        }
    }

    private fun onPdfError() {
        Toast.makeText(this, "El pdf se ha dañado", Toast.LENGTH_SHORT).show()
        true.showProgressBar()
        finish()
    }

    private fun Boolean.showProgressBar() {
        binding.progressBar.visibility = if (this) View.VISIBLE else GONE
    }

    override fun onDestroy() {
        binding.pdfView.closePdfRender()
        super.onDestroy()
    }

}