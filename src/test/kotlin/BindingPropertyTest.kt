package glue

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BindingPropertyTest {

    @Mock
    lateinit var bindingManager: BaseBindingManager

    lateinit var bindingData: Data

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        bindingData = Data(bindingManager)
    }

    @Test
    fun bindingProperty_shouldGetInitialValue() {
        val testValue = "Test"

        val bindingProperty = BindingProperty<BindingData, String>(bindingManager, testValue)

        val value = bindingProperty.getValue(bindingData, Data::property)

        assertEquals(testValue, value)
    }

    @Test
    fun bindingProperty_shouldSetAndGetValue() {

        val bindingProperty = BindingProperty<BindingData, String>(bindingManager, "")

        val testValue = "Test"

        bindingProperty.setValue(bindingData, Data::property, testValue)

        val value = bindingProperty.getValue(bindingData, Data::property)

        assertEquals(testValue, value)
    }

    @Test
    fun bindingProperty_shouldNotify() {
        val bindingProperty = BindingProperty<BindingData, String>(bindingManager, "")

        val testValue = "Test"

        bindingProperty.setValue(bindingData, Data::property, testValue)

        verify(bindingManager).notifyPropertyChanged(Data::property)
    }

    @Test
    fun bindingProperty_shouldNotNotify() {
        val testValue = "Test"

        val bindingProperty = BindingProperty<BindingData, String>(bindingManager, testValue)

        bindingProperty.setValue(bindingData, Data::property, testValue)

        verify(bindingManager, Mockito.never()).notifyPropertyChanged(Data::property)
    }

    class Data(override val bindingManager: BaseBindingManager) : BindingData {
        var property = ""
    }
}