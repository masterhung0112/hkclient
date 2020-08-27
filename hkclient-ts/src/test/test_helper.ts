import assert from 'assert'
import nock from 'nock'
import HkClient from 'hkclient/hkclient'

export const DEFAULT_SERVER = 'http://localhost:8065'

class TestHelper {
    basicClient = null
    constructor() {
    }

    createClient = () => {
        const client = new HkClient()

        client.url = DEFAULT_SERVER

        return client;
    }
}

export default new TestHelper()