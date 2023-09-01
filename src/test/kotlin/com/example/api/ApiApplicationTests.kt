package com.example.api

import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.JSONCompareMode.STRICT
import org.skyscreamer.jsonassert.comparator.CustomComparator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

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
	fun getById_1Test() {

		var id = 1

		while( id < 7){
			val response = mockMvc.perform(
				get("/api/books/$id")
					.contentType(MediaType.APPLICATION_JSON)
			).andReturn().response
			val actualStatus = response.status
			val actualResponseBody = response.contentAsString

			val expectedStatus = HttpStatus.OK.value()

			val expectedResponseBody = when(id){
				1 -> """
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
				2 ->  """
					{
                     "status": 1,
						"data": 
							{
								"id": 2,
								"title": "title_2",
								"author": "author_2"
							}
					}
                """.trimIndent()
				3 ->  """
					{
                     "status": 1,
						"data": 
							{
								"id": 3,
								"title": "title_3",
								"author": "author_3"
							}
					}
                """.trimIndent()
				4 ->  """
					{
                     "status": 1,
						"data": 
							{
								"id": 4,
								"title": "title_4",
								"author": "author_4"
							}
					}
                """.trimIndent()
				5 ->  """
					{
                     "status": 1,
						"data": 
							{
								"id": 5,
								"title": "title_5",
								"author": "author_5"
							}
					}
                """.trimIndent()

				else -> """
					{
                     "status": 0,
					 "message":"DATA_NOT_FOUND"
					}
                """.trimIndent()
			}

			Assertions.assertThat(actualStatus).isEqualTo(expectedStatus)
			JSONAssert.assertEquals(
				expectedResponseBody,
				actualResponseBody,
				JSONCompareMode.STRICT
			)

			id++
		}
	}

	@Test
	@DataSet("datasets/common.yml")
	@ExpectedDataSet(
		value = ["datasets/insert_success.yml"],
		orderBy = ["id"],
		ignoreCols = ["id"]
	)
	fun insertTest(){
		val rawRequestBody = """
			{
				"title": "title_6",
				"author": "author_6"
			}
		""".trimIndent()

		val response = mockMvc.perform(
			post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(rawRequestBody)
		).andReturn().response

		val actualStatus = response.status
		val actualResponseBody = response.contentAsString

		val expectedStatus =  HttpStatus.OK.value()
		val expectedResponseBody =
			"""
				{
					"status": 1,
					"data":{
						"id" : 6,
						"title" : "title_6",
						"author" : "author_6"
					}
				}
			""".trimIndent()

		Assertions.assertThat(actualStatus).isEqualTo(expectedStatus)
		JSONAssert.assertEquals(
			expectedResponseBody,
			actualResponseBody,
			CustomComparator(
				STRICT,
				Customization("books.id"){_,_ -> true}
			)
		)
	}
}
