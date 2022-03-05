# JWT-token-based-Security-Example

- first goes to class SecurityConfiguration extends WebSecurityConfigurerAdapter for set auth.userDetailsService(userService)  from database
- goes to configure method for authorizeRequests().antMatchers("/authenticate").permitAll()
- then  authenticationManager.authenticate for authrentication username and password.
- take user userService.loadUserByUsername(jwtRequest.getUsername() 
- generate token return token.

JWT Working Flow Chart
![JWT_Working](https://user-images.githubusercontent.com/57706022/151702851-332535e3-970c-456d-a9cc-12a148ab9d63.png)

Post Request - 
![post](https://user-images.githubusercontent.com/57706022/151702767-eb253338-0128-40bc-91e6-aa04e1747878.png)
Get Request
![Get](https://user-images.githubusercontent.com/57706022/151702664-33fb9e35-3018-4290-a7c5-9c19dfe92f18.png)


![Json](https://user-images.githubusercontent.com/57706022/151702807-3ba60eee-ebbe-4cf6-933a-f5704692cc2f.png)



