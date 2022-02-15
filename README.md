# demo-city

Get Token: http://localhost:8080/api/login Method Type: POST, Request Body: {"username":"user@example.com", "password":"user"} or {"username":"user2@example.com", "password":"user2"} 

User has permission to edit the city, user2 has not permission.

After obtained token, in each request to endpoints you have to add the token into the header. If token is expired you can utilize from refresh token (/api/token/refresh) endpoint.
