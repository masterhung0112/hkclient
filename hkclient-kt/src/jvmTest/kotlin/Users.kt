import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull

class Users {
    //    var store: Store
    lateinit var testHelper: TestHelper

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

//    @BeforeClass
//    fun beforeAlL() {
//        testHelper = TestHelper()
//        testHelper.initBasic()
//    }

    @Test
    fun testCreateUserClientSuccess() = runBlockingTest {
        val userToCreate = TestHelper.fakeUser();
        val testHelper = TestHelper()
        testHelper.initBasic()

        val user = testHelper.basicClient.createUser(userToCreate)
        assertNotNull(user)
    }
}