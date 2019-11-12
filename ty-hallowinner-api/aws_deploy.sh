# Generates ./elasticbeanstalk/config.yml
# eb init ty-hallowinner-api --region eu-west-3

eb use ty-hallowinner-api-sandbox

eb setenv SPRING_PROFILES_ACTIVE=aws,h2
eb setenv SERVER_PORT=5000

mvn clean package spring-boot:repackage
eb deploy
eb status