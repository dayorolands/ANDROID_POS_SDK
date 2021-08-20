package com.interswitchng.smartpos.activities

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(HomeActivityTests::class, UssdActivityTests::class)
class ActivityTestSuite