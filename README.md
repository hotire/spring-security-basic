# Spring-Security-Basic

study


## SecurityContextHolder

### SecurityContext

### Authentication


## 원리

### ProviderManager

```
public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
	....
	....	
  for (AuthenticationProvider provider : getProviders()) {
    ....
    result = provider.authenticate(authentication);
    ....
    	if (result == null && parent != null) {
    	....
    	  result = parentResult = parent.authenticate(authentication);
    	
    	....
    	}		
  }
}

```

### AbstractUserDetailsAuthenticationProvider

```
public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
			
}			
```

### DaoAuthenticationProvider

```
protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
  ....
  ....
  UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
  ....
  ....		
}			
			
```


### SecurityContextPersistenceFilter

- SecurityContext 를 HTTP Session에 저장하여 사용하는 Filter
- SecurityContextRepository 인터페이스 구현체를 교체하면 HTTP Session이 아닌 다른 곳에도 저장이 가능하다.
(기본으로 HttpSessionSecurityContextRepository 사용한다)

```
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
  ....
  ....
  HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request,
  				response);
  SecurityContext contextBeforeChainExecution = repo.loadContext(holder);
  
  try {
  	SecurityContextHolder.setContext(contextBeforeChainExecution);
  
  	chain.doFilter(holder.getRequest(), holder.getResponse());
  
  }
  finally {
  	SecurityContext contextAfterChainExecution = SecurityContextHolder
  					.getContext();
  	// Crucial removal of SecurityContextHolder contents - do this before anything
  	// else.
  	SecurityContextHolder.clearContext();
  	repo.saveContext(contextAfterChainExecution, holder.getRequest(),
  					holder.getResponse());
  ....					
}

```

### HttpSessionSecurityContextRepository


```
public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

  ....
  ....
  SecurityContext context = readSecurityContextFromSession(httpSession);
  ....
  ....
}
```


### UsernamePasswordAuthenticationFilter

- Form 인증을 처리하는 Filter
- 인증된 Authentication 객체를 SecurityContextHolder 에 넣어준다. 


```
public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
  ....
  ....
  return this.getAuthenticationManager().authenticate(authRequest);			
}
```

### AbstractAuthenticationProcessingFilter

UsernamePasswordAuthenticationFilter 의 부모 클래스로, 

attemptAuthentication 메서드를 템플릿 메서드 패턴으로 호출함.

```

public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
  ....
  ....
  authResult = attemptAuthentication(request, response);
  ....
  successfulAuthentication(request, response, chain, authResult); 
}

protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
	....
	....
	SecurityContextHolder.getContext().setAuthentication(authResult);
  ...
}
```

### DelegatingFilterProxy

- AbstractSecurityWebApplicationInitializer 을 사용해서 설정

- Spring Boot 사용시, 