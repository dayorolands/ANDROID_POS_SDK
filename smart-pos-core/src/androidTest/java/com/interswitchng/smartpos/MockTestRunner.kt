package com.interswitchng.smartpos

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import com.interswitchng.smartpos.base.MockApplication

class MockTestRunner: AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, MockApplication::class.java.name, context)
    }

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        arguments?.apply {
            val mocks = getString("shouldMock") ?: "true"
            MockApplication.shouldMock = mocks.toBoolean()
        }
    }
}