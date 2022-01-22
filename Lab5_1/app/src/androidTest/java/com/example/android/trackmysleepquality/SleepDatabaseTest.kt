/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import android.os.Handler
import android.os.Looper
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    // Disable parallel execution before running these
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        //Setup
        val night = SleepNight()
        night.sleepQuality = 12345

        //Test
        sleepDao.insert(night)
        val newNight = sleepDao.getTonight()
        assertEquals(12345, newNight?.sleepQuality)

        //Cleanup
        sleepDao.clear()
    }

    @Test
    @Throws(Exception::class)
    fun updateNight() {
        //Setup
        var night = SleepNight()
        sleepDao.insert(night)

        //Test
        night = sleepDao.getTonight()!!
        assertEquals(-1, night.sleepQuality)
        night.sleepQuality = 10
        sleepDao.update(night)
        val updatedNight = sleepDao.getTonight()
        assertEquals(10, updatedNight?.sleepQuality)

        //Cleanup
        sleepDao.clear()
    }

    @Test
    @Throws(Exception::class)
    fun getAllNights() {
        //Setup
        val night = SleepNight()
        sleepDao.insert(night)
        val night2 = SleepNight()
        sleepDao.insert(night2)

        //Test
        val nights = getValueSync(sleepDao.getAllNights())
        assertEquals(2, nights.count())

        //Cleanup
        sleepDao.clear()
    }

    @Test
    @Throws(Exception::class)
    fun getTonight() {
        //Setup
        // this should get the most recent night so insert
        // two with current time and check if it returns the last one
        val night = SleepNight()
        sleepDao.insert(night)
        val night2 = SleepNight()
        night2.sleepQuality = 222
        sleepDao.insert(night2)

        //Test
        val tonight = sleepDao.getTonight()
        assertEquals(222, tonight?.sleepQuality)

        //Cleanup
        sleepDao.clear()
    }

    @Test
    @Throws(Exception::class)
    fun clearDatabase() {
        //Setup
        val night = SleepNight()
        sleepDao.insert(night)
        val night2 = SleepNight()
        sleepDao.insert(night2)

        //Test
        sleepDao.clear()
        val nights = getValueSync(sleepDao.getAllNights())
        assertEquals(0, nights.count())

        //Cleanup
        // not required
    }

    private fun <T> getValueSync(lData : LiveData<T>): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                lData.removeObserver(this)
            }
        }

        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable {
            lData.observeForever(observer)
        })

        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw TimeoutException()
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}