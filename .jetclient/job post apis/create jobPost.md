```toml
name = 'create jobPost'
method = 'POST'
url = 'http://localhost:8080/api/v1/job-posts/31'
sortWeight = 1000000
id = '468c231d-00f3-441b-983d-1962fddf0deb'

[body]
type = 'JSON'
raw = '''
{
  "recruiterId": 6,
  "companyId": 1,
  "jobTitle": " Backend Developer",
  "jobDescription": "Develop and maintain backend systems using Java and Spring Boot.",
  "location": {
    "countryId": "US",
    "city": "New York",
    "addressLine1": "123 Main Street",
    "addressLine2": "Apt 4B",
    "postalCode": "10001"
  },
  "workMode": "REMOTE",
  "jobType": "FULL_TIME",
  "applicationDeadline": "2025-02-24T14:30:00",
  "hourlyRate": 20,
  "experienceLevel": "PROFESSIONAL"
}'''
```
