import com.hungknow.HkClientApp
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Users {
    //    var store: Store
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    companion object {
        lateinit var testHelper: TestHelper
        lateinit var hkClientApp: HkClientApp

        @BeforeClass
        @JvmStatic
        fun beforeAlL() {
            testHelper = TestHelper()
            testHelper.initBasic()
        }

        @AfterClass
        @JvmStatic
        fun AfterAll() {
//            testHelper.Te?
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun testCreateUserClientSuccess() = runBlockingTest {
        val userToCreate = TestHelper.fakeUser()

//        val user = testHelper.basicClient.createUser(userToCreate)
//        assertNotNull(user)

        var outputUser = testHelper.hkClientApp.Users().createUser(userToCreate)
        assertNotNull(outputUser)
        assertEquals(outputUser.id, "idfromme")
    }
}