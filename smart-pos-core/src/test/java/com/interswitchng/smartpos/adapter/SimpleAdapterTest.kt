package com.interswitchng.smartpos.adapter

import com.igweze.ebi.simplecalladapter.Simple
import com.igweze.ebi.simplecalladapter.SimpleCallAdapter
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test
import org.koin.test.KoinTest
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class SimpleAdapterTest: KoinTest {


    @Test
    fun `should return response when successful`() {
        val msg = "some response"

        val response: Response<String> = mock {
            on(mock.body()) doReturn msg
        }

        val call: Call<String> = mock {
            on(mock.execute()) doReturn response
        }

        val simpleAdapter = SimpleCallAdapter<String>(String::class.java)
        val simpleResponse = simpleAdapter.adapt(call) as Simple<String>

        simpleResponse.run { s, throwable ->
            assertNull("error is not null", throwable)
            assertNotNull("response is null", s)
            assertSame("error message is different", msg, s)
        }

    }

    @Test
    fun `should return error when exception happens`() {
        val msg = "some error occurred"
        val error: IOException = mock {
            on(mock.message) doReturn msg
            on(mock.localizedMessage) doReturn msg
        }

        val call: Call<String> = mock {
            on(mock.execute()) doThrow error
        }

        val simpleAdapter = SimpleCallAdapter<String>(String::class.java)
        val simpleResponse = simpleAdapter.adapt(call) as Simple<String>

        simpleResponse.run { s, throwable ->
            assertNotNull("error is null", throwable)
            assertSame("error message is different", msg, throwable?.message)
            assertNull("response is not null", s)
        }

    }
}