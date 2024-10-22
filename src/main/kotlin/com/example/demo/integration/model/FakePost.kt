package com.example.demo.integration.model

data class FakePost(
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?
) {
    constructor() : this (null, null, null, null)
}
