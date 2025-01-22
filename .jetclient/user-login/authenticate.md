```toml
name = 'authenticate'
method = 'POST'
url = 'http://localhost:8080/api/v1/auth/login'
sortWeight = 2000000
id = 'c2708189-ec4b-410b-acaf-f347dd0bfbaa'

[auth]
type = 'NO_AUTH'

[body]
type = 'JSON'
raw = '''
{ 
  "email": "john.doe@yahoo2.com",
  "password": "securePassword",
}'''
```
