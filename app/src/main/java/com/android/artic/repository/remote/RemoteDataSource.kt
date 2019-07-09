package com.android.artic.repository.remote

import com.android.artic.repository.remote.response.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Path

interface RemoteDataSource {
    fun getNewArticleList(): Call<BaseResponse<List<ArticleResponse>>>
    fun getArticle(contentType: String, token: String, articleIdx: Int): Call<BaseResponse<ArticleResponse>>
    fun getNewArchiveList(): Call<BaseResponse<List<ArchiveResponse>>>
    fun getArticPickList(): Call<BaseResponse<List<ArticleResponse>>>
    fun getCategoryList(): Call<BaseResponse<List<CategoryResponse>>>
    fun getMyPageInfo(contentType:String, token: String) : Call<BaseResponse<MyPageResponse>>
    fun getCategoryArchiveList(contentType:String, token: String, categoryIdx : Int) : Call<BaseResponse<List<ArchiveResponse>>>
    fun getArchiveListGivenCategory(categoryIdx: Int): Call<BaseResponse<List<ArchiveResponse>>>
    fun getReadingHistoryArticle(contentType: String, token: String): Call<BaseResponse<List<ArticleResponse>>>
    fun getMyArchiveList(contentType: String, token: String): Call<BaseResponse<List<ArchiveResponse>>>
    fun getScrapArchiveList(contentType: String, token:String): Call<BaseResponse<List<ArchiveResponse>>>
    fun postRegisterArchive(contentType: String, token: String, body: JsonObject): Call<BaseResponse<Int>>
    fun putMyPageInfo(contentType: String, token:String, body: JsonObject) : Call<BaseResponse<List<MyPageResponse>>>
    fun getArticleListGivenArchiveId(archiveId: Int, contentType: String, token: String): Call<BaseResponse<List<ArticleResponse>>>
    fun postArticleLike(contentType: String, token: String, articleIdx: Int) : Call<BaseResponse<Int>>
    fun postArticleRead(contentType: String, token : String, articleIdx: Int) : Call<BaseResponse<Int>>

    fun getSearchArticleList(
        contentType: String,
        token: String,
        keyword: String
    ): Call<BaseResponse<List<ArticleResponse>>>

    fun getSearchArchiveList(
        contentType: String,
        token: String,
        keyword: String
    ): Call<BaseResponse<List<ArchiveResponse>>>

    // @수민) 아티클 담기
    fun postCollectArticleInArchive(
        contentType: String,
        token: String,
        archiveIdx: Int,
        articleIdx: Int
    ): Call<BaseResponse<Int>>

    // @수민) 아카이브 스크랩
    fun postArchiveScrap(
        contentType: String,
        token: String,
        archiveIdx: Int
    ) : Call<BaseResponse<Any>>
}