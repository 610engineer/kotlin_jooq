package com.example.api

import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.JSONCompareMode.STRICT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
class ApiApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Test
	@DataSet("datasets/common.yml")
	@ExpectedDataSet(
		value = ["datasets/common.yml"],
		orderBy = ["id"]
	)
	fun getAllTest() {

		val id = 1

		val response = mockMvc.perform(
			get("/api/books/$id")
			.contentType(MediaType.APPLICATION_JSON)
		).andReturn().response
		val actualStatus = response.status
		val actualResponseBody = response.contentAsString

		val expectedStatus = HttpStatus.OK.value()
		val expectedResponseBody =
			"""
					{
                     "status": 1,
						"data": 
							{
								"id": 1,
								"title": "title_1",
								"author": "author_1"
							}
					}
                """.trimIndent()

		Assertions.assertThat(actualStatus).isEqualTo(expectedStatus)
		JSONAssert.assertEquals(
			expectedResponseBody,
			actualResponseBody,
			JSONCompareMode.STRICT
		)
	}

}
