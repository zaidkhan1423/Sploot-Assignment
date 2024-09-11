package com.zaid.splootassignment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zaid.splootassignment.data.model.response.Source

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val url: String?,
    val urlToImage: String?
)

fun com.zaid.splootassignment.data.model.response.Article.toLocal(): Article {
    return Article(
        title = this.title,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun List<com.zaid.splootassignment.data.model.response.Article?>?.toLocalList(): List<Article> {
    return this?.mapNotNull { it?.toLocal() } ?: emptyList()
}
fun Article.toResponse(): com.zaid.splootassignment.data.model.response.Article {
    return com.zaid.splootassignment.data.model.response.Article(
        title = this.title,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source?.let { Source(it.id, it.name) },
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun List<Article>.toResponseList(): List<com.zaid.splootassignment.data.model.response.Article> {
    return this.map { it.toResponse() }
}
