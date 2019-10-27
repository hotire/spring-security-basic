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

### UsernamePasswordAuthenticationFilter

```
public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
  ....
  ....
  return this.getAuthenticationManager().authenticate(authRequest);			
}
```

### AbstractAuthenticationProcessingFilter

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