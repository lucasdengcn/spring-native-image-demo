package com.example.demo.integration.client

import com.example.demo.exception.APIIntegrationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class FakePostClientTests (@Autowired val fakePostClient: FakePostClient) {

    @Test
    fun `get posts`() {
        val fakePostList = fakePostClient.getPosts()
        assertTrue(fakePostList.isNotEmpty(), message = "posts should not be empty")
    }

    @Test
    fun `get post detail successfully`() {
        val fakePost = fakePostClient.getPostDetail(1)
        assertNotNull(fakePost, message = "posts should not be empty")
    }

    @Test
    fun `get post detail not exit will throw 404`() {
        assertThrows<APIIntegrationException> {
            fakePostClient.getPostDetail(101)
        }
    }

}