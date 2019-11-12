# HalloWinneR API

Strava API : How to get access Token ?
This step will be done by the front.

0. Create Application in Strava

https://www.strava.com/settings/api

1. Get code

GET https://www.strava.com/oauth/mobile/authorize?client_id=[YOUR_CLIENT_ID]&response_type=code&approval_prompt=auto&redirect_uri=http://localhost:8080/ty-hallowinner&scope=activity:write,activity:read

2. Get Access Token and Refresh Token

POST https://www.strava.com/oauth/token
```json
{
"client_id":"[YOUR_CLIENT_ID]",
"client_secret":"[YOUR_CLIENT_SECRET]",
"code":"[PREVIOUSLY RETRIEVED CODE]",
"grant_type":"authorization_code"
}
```

## Available Scripts

In the project directory, you can run:

### `mvn clean install`

Creates and install the package.

### `./aws_deploy.sh`

The script use Elastic Beanstalk cli to setup environment variable and deploy the app build from maven command.

Requirements : 

a. Install aws cli && eb cli 
 
b. Run `eb init ty-hallowinner-api --region eu-west-3`

c. Add the following lines at the end of .elasticbeanstalk/config.yml :
```yaml
deploy:
  artifact: target/ty-hallowinner-api.jar
```
