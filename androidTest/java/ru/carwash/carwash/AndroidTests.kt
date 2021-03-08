package ru.carwash.carwash

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.carwash.controllers.DataProcessor

@RunWith(AndroidJUnit4::class)
class AndroidTests {

    @Test
    fun savingData() {
        var context = InstrumentationRegistry.getInstrumentation().context
        DataProcessor.saveData(context,"test",3)
        Assert.assertEquals(DataProcessor.getInt(context,"test",0), 3)
    }
}