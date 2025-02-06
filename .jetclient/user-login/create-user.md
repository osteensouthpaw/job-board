```toml
name = 'create-user'
method = 'GET'
url = 'http://localhost:8080/api/v1/users/verify-email?token=722079'
sortWeight = 1000000
id = '994da0e1-5426-4d92-96b0-8be9f573182e'

[[queryParams]]
key = 'token'
value = '722079'

[auth]
type = 'NO_AUTH'

[body]
type = 'JSON'
raw = '''
{
  "firstName": "John",
  "lastName": "Doe",
  "userType": "JOB_SEEKER",
  "email": "osteenomega27@gmail.com",
  "password": "securePassword",
  "gender": "MALE",
  "imageUrl": "https://example.com/images/john.jpg"
}

'''
```
