# Spring-Security-Basic

study

## @EnableWebSecurity

### AuthenticationConfiguration

https://blog.naver.com/gngh0101/222056047335


## SecurityContextHolder

SecurityContext 제공, 기본적으로 ThreadLocal을 사용한다.

### SecurityContext
Authentication 제공 

### Authentication

Principal과 GrantAuthority 제공.

### Principal

“누구"에 해당하는 정보. UserDetailsService에서 리턴한 객체(UserDetails 타입)

### GrantAuthority

“ROLE_USER”, “ROLE_ADMIN”등 Principal이 가지고 있는 “권한”을 나타낸다.


## 원리

스프링 시큐리티에서 인증(Authentication)은 AuthenticationManager가 한다.

인자로 받은 Authentication
 - 사용자가 입력한 인증에 필요한 정보(username, password)로 만든 객체. (폼 인증인 경우)
 - Authentication
   - Principal: “hotire”
   - Credentials: “123”

유효한 인증인지 확인
 - 사용자가 입력한 password가 UserDetailsService를 통해 읽어온 UserDetails 객체에 들어있는 password와 일치하는지 확인
 - 해당 사용자 계정이 잠겨 있진 않은지, 비활성 계정은 아닌지 등 확인

Authentication 객체를 리턴
 - Authentication
  - Principal: UserDetailsService에서 리턴한 그 객체 (User)
  - Credentials: 
  - GrantedAuthorities


### ProviderManager 

ProviderManager는 AuthenticationManager의 구현체

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



AuthenticationManager가 인증을 마친 뒤 리턴 받은 Authentication 객체의 행방은??


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


### FilterChainProxy


스프링 시큐리티가 제공하는 필터들
1. WebAsyncManagerIntergrationFilter
2. SecurityContextPersistenceFilter
3. HeaderWriterFilter
4. CsrfFilter
5. LogoutFilter
6. UsernamePasswordAuthenticationFilter
7. DefaultLoginPageGeneratingFilter
8. DefaultLogoutPageGeneratingFilter
9. BasicAuthenticationFilter
10. RequestCacheAwareFtiler
11. SecurityContextHolderAwareReqeustFilter
12. AnonymouseAuthenticationFilter
13. SessionManagementFilter
14. ExeptionTranslationFilter
15. FilterSecurityInterceptor

이 모든 필터는 FilterChainProxy가 호출한다.


### DelegatingFilterProxy

- AbstractSecurityWebApplicationInitializer 을 사용해서 설정 (Spring Boot 사용시, SecurityAutoConfiguration) 

- FilterChainProxy 를 springSecurityFilterChain 이라는 이름의 빈으로 등록한다.



### AccessDecisionManager

Access Control(인가) 결정을 내리는 인터페이스로, 구현체 3가지를 기본으로 제공한다.

- AffirmativeBased : Voter 중 한명이라도 허용하면 허용 (기본전략)
- ConsensusBased : 다수결 
- UnanimousBased : 만장일치 


AccessDecisionVoter

- 해당 Authentication이 특정한 Object에 접근할 때 필요한 ConfigAttributes를 만족하는지 확인한다.

- WebExpressionVoter: 웹 시큐리티에서 사용하는 기본 구현체, ROLE_XXXX가 매치하는지 확인.

- RoleHierarchyVoter: 계층형 ROLE 지원. ADMIN > MANAGER > USER


기본적으로 ADMIN 권한이 있더라도, USER 권한을 얻지 못한다.

```
 public AccessDecisionManager accessDecisionManager() {
    final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    final DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);

    final WebExpressionVoter voter = new WebExpressionVoter();
    voter.setExpressionHandler(handler);

    return new AffirmativeBased(ImmutableList.of(voter));
  }
```

Custom AccessDecisionManager 을 만들어서 RoleHierarchyVoter 사용해보자. 



## Reactive Security 

### Release Notes

- 5.2.0 
    - AuthorityReactiveAuthorizationManager.hasAnyRole이 지원된다. (Spring Boot 2.2.0 이상)
    
     
    
## JWT OAuth

- JWT : https://jwt.io/     

- RSA Key : https://travistidwell.com/jsencrypt/demo/

### JWT

PAYLOAD
```javascript
{
  "id": "1234567890",
  "role": ["USER"],
  "iat": 1516239022
}
```

### Getting Started MVC with JWT

https://blog.naver.com/gngh0101/221910233192 

### Getting Started Reactive with JWT

https://blog.naver.com/gngh0101/221916575369