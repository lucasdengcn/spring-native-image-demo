package com.example.demo.integration.client

import com.example.demo.integration.model.FakePost
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping


@FeignClient("fakePost")
interface FakePostClient {

    @GetMapping("/posts")
    fun getPosts(): List<FakePost>

    @CircuitBreaker(name = "fakePost")
    @Retry(name = "fakePost")
    @GetMapping("/posts/{id}")
    fun getPostDetail(@PathVariable("id") id: Int): FakePost

    @PostMapping("/posts")
    fun createPost(post: FakePost): FakePost

}