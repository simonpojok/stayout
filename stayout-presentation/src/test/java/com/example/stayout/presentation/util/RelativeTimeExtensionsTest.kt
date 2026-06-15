package com.example.stayout.presentation.util

import android.text.format.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RelativeTimeExtensionsTest {
    @Test
    fun `toRelativeTimeString formats a past timestamp as relative time`() {
        val twoHoursAgo = System.currentTimeMillis() - (2 * DateUtils.HOUR_IN_MILLIS)

        val result = twoHoursAgo.toRelativeTimeString()

        val expected =
            DateUtils.getRelativeTimeSpanString(twoHoursAgo, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        assertEquals(expected.toString(), result.toString())
    }
}
