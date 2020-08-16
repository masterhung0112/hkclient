import com.hungknow.HkClient
import com.hungknow.models.UserProfile
import com.hungknow.utils.Helpers
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlinx.serialization.json.Json

class TestHelper {
    lateinit var basicClient: HkClient

    constructor() {
//        this.basicClient = null;

//        this.basicUser = null;
//        this.basicTeam = null;
//        this.basicTeamMember = null;
//        this.basicChannel = null;
//        this.basicChannelMember = null;
//        this.basicPost = null;
//        this.basicRoles = null;
//        this.basicScheme = null;
    }

    fun createClient(): HkClient {
        var mockEngineConfig = MockEngineConfig()
        mockEngineConfig.addHandler { request ->
            when (request.url.fullPath) {
                "/api/v1/users" -> {
                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    respond(Json.stringify(UserProfile.serializer(), UserProfile()), headers = responseHeaders)
                }
                else -> error("Error for else %s".format(request.url.fullPath))
            }
        }
        val client = HkClient(MockEngine(mockEngineConfig))

        client.setUrl(DEFAULT_SERVER);

        return client;
    }

    fun initBasic(client: HkClient = createClient()) {
        client.setUrl(DEFAULT_SERVER);
        this.basicClient = client

//        this.initMockEntities()
//        this.activateMocking();
    }

    companion object {
        val PASSWORD = "password1"
        val DEFAULT_SERVER = "http://localhost:8065"

        fun generateId() = Helpers.generateId()

        fun fakeEmail(): String =
                "success${this.generateId()}@simulator.amazonses.com"

        fun fakeUser(): UserProfile {
            return UserProfile(
                    email = this.fakeEmail(),
//            allow_marketing = true,
                    password = PASSWORD,
//            locale = DEFAULT_LOCALE,
                    username = this.generateId(),
                    first_name = this.generateId(),
                    last_name = this.generateId(),
//            create_at = Date.now(),
                    delete_at = 0,
                    roles = "system_user"
            )
        }
    }
}