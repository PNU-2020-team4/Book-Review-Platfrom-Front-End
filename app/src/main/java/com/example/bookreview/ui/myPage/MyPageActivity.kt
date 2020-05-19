package com.example.bookreview.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bookreview.R
import com.example.bookreview.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_page.*
import kotlinx.android.synthetic.main.search.*

class MyPageActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        viewModel.userProfileImage.observe(this, Observer {
            Log.e("프로필 이미지 리소스", it)
            Picasso.get().load(it).into(user_profile_img)
        })

        my_page_back_button.setOnClickListener {
            finish()
        }

        my_review_button.setOnClickListener {
            startActivity(Intent(this, MyReviewActivity::class.java))
        }

        edit_profile_button.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        my_comment_button.setOnClickListener {
            startActivity(Intent(this, MyCommentActivity::class.java))
        }

        scrap_button.setOnClickListener {
            startActivity(Intent(this, ScrapActivity::class.java))
        }


    }
}