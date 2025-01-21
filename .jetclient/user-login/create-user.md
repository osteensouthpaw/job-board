```toml
name = 'create-user'
method = 'POST'
url = 'http://localhost:8080/api/v1/auth/register'
sortWeight = 1000000
id = '994da0e1-5426-4d92-96b0-8be9f573182e'

[auth]
type = 'NO_AUTH'

[body]
type = 'JSON'
raw = '''
{
  "firstName": "John",
  "lastName": "Doe",
  "userType": "RECRUITER",
  "email": "john.doe@hello.com",
  "password": "securePassword",
  "gender": "MALE",
  "imageUrl": "https://example.com/images/john.jpg"
}

'''
```
