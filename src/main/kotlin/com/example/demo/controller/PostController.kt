package com.example.demo.controller

import com.example.demo.integration.client.FakePostClient
import com.example.demo.integration.model.FakePost
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Post APIs")
@RestController
@RequestMapping("/posts")
@Validated
class PostController (val fakePostClient: FakePostClient) {

    @Operation(description = "get a Post detail")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/{id}")
    fun getOrder(
        @PathVariable id : Int,
    ): ResponseEntity<FakePost> {
        val order = fakePostClient.getPostDetail(id);
        return ResponseEntity.ok(order)
    }

    @Operation(description = "get pageable posts ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/{page}/{size}")
    fun getOrders(
        @PathVariable page : Int,
        @PathVariable size : Int
    ): ResponseEntity<List<FakePost>> {
        val fakePostList = fakePostClient.getPosts();
        return ResponseEntity.ok(fakePostList)
    }

}