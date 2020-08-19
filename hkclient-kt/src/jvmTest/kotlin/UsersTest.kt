import com.hungknow.HkClientApp
import com.hungknow.models.UserProfile
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.*
import org.junit.Test
import kotlin.test.*

class UsersTest {
    //    var store: Store
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    companion object {
        lateinit var testHelper: TestHelper
        lateinit var mockEngineConfig: MockEngineConfig

        @BeforeClass
        @JvmStatic
        fun beforeAlL() {
            testHelper = TestHelper()
        }

        @AfterClass
        @JvmStatic
        fun AfterAll() {
//            testHelper.Te?
        }
    }

    @Before
    fun setUp() {
        mockEngineConfig = MockEngineConfig()
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
        mockEngineConfig.addHandler { request ->
            when (request.url.fullPath) {
                "/api/v1/users" -> {
                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    respond(Json.stringify(UserProfile.serializer(), userToCreate), headers = responseHeaders)
                }
                else -> error("Error for else %s".format(request.url.fullPath))
            }
        }
        testHelper.initBasic(mockEngineConfig)


        // Setup Http mock

//        val user = testHelper.basicClient.createUser(userToCreate)
//        assertNotNull(user)

        testHelper.hkClientApp.Users().createUser(userToCreate)
        val state = testHelper.hkClientApp.globalStore.getState()
        assertSame(userToCreate.id, state.entities.users.currentUserId)
        assertFalse { state.entities.users.profiles.isNullOrEmpty() }
        assertTrue { state.entities.users.profiles.containsKey(state.entities.users.currentUserId) }
        assertTrue { userToCreate.equals(state.entities.users.profiles[state.entities.users.currentUserId]) }
//        assertEquals(outputUser.id, "idfromme")
    }
}