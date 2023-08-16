# Dentalstack
There are 2 api endpoints in the project
1. generate-otp
2. login
generate-otp
It is a post api where user requests for the otp the otp is generated in the backend and returned to user
then user moves to the login part.
login api
Here user provides the phone number and otp which was recieved by calling generate-otp and in backend first phone number is verified that user is
registerd or not and then cached otp is validated if validation is successful a jwt token is generated uskig sha256 Algorithm.
 first end point : localhost:8081/auth/generate-otp 
   request body raw Json
   {"phoneNumber": "1234567890"}
second end point : localhost:8081/auth/login
  request body raw Json
  {"phoneNumber" : "1234567890","otp":"{generate-otp api's response}"}
  response
  if validation Successful
     Logged in successfully! JWT: a jwt token 
  else
     if user not found
        User not found
     if otp is not correct
        Invalid otp
   
