package com.interswitchng.smartpos.adapter

import com.igweze.ebi.simplecalladapter.SimpleCallAdapterFactory
import com.interswitchng.smartpos.Utilities
import com.interswitchng.smartpos.old_shared.interfaces.retrofit.IHttpService
import com.nhaarman.mockitokotlin2.mock
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SampleMockWebServerTest: KoinTest{

    private val sampleResponse = Utilities.getJson("success-code-response.json")
    private val mockServer = MockWebServer()

    @Before
    fun setup() {
        // configure mock server and response
        val url = mockServer.url("/")
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(SimpleCallAdapterFactory.create())
                .baseUrl(url)
                .build()


        val retrofitModule = module {
            single { retrofit.create(IHttpService::class.java) }
        }

        StandAloneContext.loadKoinModules(retrofitModule)
    }


    @Test
    fun `should extract response from body`() {

        mockServer.enqueue(MockResponse().setBody(sampleResponse))

        // check http service
        val httpService: IHttpService = get()

        // issue request and check results
        httpService.getQrCode(mock()).run { codeResponse, throwable ->
            Assert.assertNull("an error occured", throwable)
            Assert.assertNotNull("response is null", codeResponse)
        }
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}