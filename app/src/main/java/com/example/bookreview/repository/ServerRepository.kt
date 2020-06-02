package com.example.bookreview.repository

import com.example.bookreview.book.Book
import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.dto.testClass
import com.example.bookreview.ui.review.Review
import io.reactivex.Single

interface ServerRepository {
    fun requestResponse() : Single<testClass>
    fun getWithdrawalResponse(userId: String) : Single<ServerResponse>

    fun getAllReviewResponse() : Single<ServerResponse>
    fun getMyReviewResponse(userId: String) : Single<ServerResponse>
    fun getMyReviewResponse() : Single<ServerResponse>
    fun postMyReviewResponse(review: Review) : Single<ServerResponse>


    fun addHistory(bookTitle:String?, bookAuthor:String?, userID:String?) : Single<ServerResponse>

}