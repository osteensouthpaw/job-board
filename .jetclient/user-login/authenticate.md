```toml
name = 'authenticate'
method = 'GET'
url = 'http://localhost:8080/api/v1/auth/me'
sortWeight = 2000000
id = 'c2708189-ec4b-410b-acaf-f347dd0bfbaa'

[auth]
type = 'NO_AUTH'

[body]
type = 'JSON'
raw = '''
{
  "email": "john.doe@hello.com",
  "password": "securePassword",
}'''
```
