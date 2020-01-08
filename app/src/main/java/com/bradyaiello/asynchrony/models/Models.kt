package com.bradyaiello.asynchrony.models

data class CatFact (
    val used: Boolean,
    val source: String,
    val type: String,
    val deleted: Boolean,
    val _id: String,
    val user: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val status: CatFactStatus
)

data class CatFactStatus (
    val verified: Boolean,
    val sentCount: Int
)