package com.team4.bookreview.ui.book

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.team4.bookreview.R
import com.team4.bookreview.ui.review.ReviewActivity
import com.team4.bookreview.utils.LoadingIndicator
import com.team4.bookreview.viewModel.BookInformationViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookInformationActivity : AppCompatActivity() {

    private val viewModel by viewModel<BookInformationViewModel>()
    private var mLoadingIndicator: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book)
        loadingIndicatorObserving()

        //status bar 투명하게 처리
        this.window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }

        val uid = intent.extras?.getString("uid")
        val link = intent.extras?.getString("link")
        val bid = intent.extras?.getString("bid")

        if(bid != null) viewModel.loadHtml(bid)

        viewModel.isParsingFinished.observe(this, Observer {
            if(viewModel.title != null) book_information_title.text = viewModel.title
            if(viewModel.author != null) book_information_author.text = viewModel.author
            if(viewModel.price != null) book_information_price.text = viewModel.price
            if(viewModel.imageSrc != null) Picasso.get().load(viewModel.imageSrc).into(book_information_cover)
            if(viewModel.desc != null) book_information_about.text = viewModel.desc
            if(viewModel.star != null){
                book_information_star_number.text = viewModel.star
                book_information_star.rating = (viewModel.star!!.toFloat()/2.0).toFloat()
            }
            if(viewModel.reviewNUm != null) book_information_review_number.text = viewModel.reviewNUm
        })
        book_information_back_button.setOnClickListener {
            finish()
        }

        book_information_review_button.setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java)
                .putExtra("id",uid)
                .putExtra("bookId", bid)
                .putExtra("title", viewModel.title)
            )
        }

        book_information_share_button.setOnClickListener {
            val text = "책 제목: ${viewModel.title} \n저자: ${viewModel.author} \n링크: $link"
            text.replace("<b>", "")
            text.replace("</b>", "")
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }
    private fun loadingIndicatorObserving() {
        viewModel.startLoadingIndicatorEvent.observe(this, Observer {
            startLoadingIndicator()
        })
        viewModel.stopLoadingIndicatorEvent.observe(this, Observer {
            stopLoadingIndicator()
        })
    }

    private fun stopLoadingIndicator() {
        mLoadingIndicator?.let {
            if (it.isShowing) it.cancel()
        }
    }

    private fun startLoadingIndicator() {
        stopLoadingIndicator()
        if (!isFinishing) {
            mLoadingIndicator = LoadingIndicator(this)
            mLoadingIndicator?.show()
        }
    }
}