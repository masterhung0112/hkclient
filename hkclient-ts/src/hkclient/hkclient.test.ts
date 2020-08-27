import { ClientError } from 'hkclient/hkclient'
import assert from 'assert'

describe('HkClient', () => {
    beforeAll(() => {

    })

    afterAll(() => {

    })

    describe('doFetchWithResponse', () => {

    })
})

describe('ClientError', () => {
    it('standard fields should be enumerable', () => {
        const error = new ClientError('https://example.com', {
            message: 'This is a message',
            intl: {
                id: 'test.error',
                defaultMessage: 'This is a message with a translation',
            },
            server_error_id: 'test.app_error',
            status_code: 418,
            url: 'https://example.com/api/v4/error',
        })

        const copy = {...error};
        assert.strictEqual(copy.message, error.message)
        assert.strictEqual(copy.intl, error.intl)
        assert.strictEqual(copy.server_error_id, error.server_error_id)
        assert.strictEqual(copy.status_code, error.status_code)
        assert.strictEqual(copy.url, error.url)
    })
})