package com.android.artic.repository.remote.response

data class NotificationResponse (
    val articles: List<ArticleResponse>,
    val isRead: Boolean,
    val notification_type: String,
    val date: String
)