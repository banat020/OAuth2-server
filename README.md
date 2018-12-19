# OAuth2-server

基于Springboot与spring-security、spring-security-oauth2实现的（认证服务（security)、资源服务、授权服务）服务器。本例的token保存在内存中，也可以很容易修改为保存在数据库或者Redis中。<br>
认证服务（security)、资源服务、授权服务，合并在同一个工程中实现。现实项目中，一般是认证服务与授权服务在一个工程中，而资源服务在另一个工程中。<br>
<br>
# 一、获取token <br>
支持三种方式获得access_token，分别是：authorization_code,implicit,refresh_token<br>
<br>
在本地测试，将OAuth2-server服务发布在本机8080。<br>
获取token，authorization_code与implicit的方式类似。<br>
<br>
1、authorization_code方式取得access_token:<br>
<br>
1)get方式访问：http://localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://localhost:8090/ <br>
注意其中的参数<b>response_type=code</b>和<b>redirect_uri=http://localhost:8090/ </b>，表示支持authorization_code方式授权和设置回调uri。<br>

2)提交被拦截，服务被重定向（forward）向至登录界面。<br>
在登录界面上输入用户名与密码，提交，服务被重定向（forward）到授权（Approval）界面。<br>
进行授权（Approval）后，服务被重定向（forward）至http://localhost:8090/。在回调的URI中带上了code，可以看到如：http://localhost:8090/?code=FLWFen <br>

3)得到code后，通过postman工具，向OAuth服务器发起申请授权token（本例支持同时返回access_token与refresh_token）的post请求：<br>
请求的URL是：http://client:secret@localhost:8080/oauth/token， client是登记的客户端，secret是客户端的密文。<br>
<b>请求的content-Type是：</b><br>
application/x-www-form-urlencoded<br>
	或者<br>
application/form-data<br>
本例支持application/form-data。<br>
<b>请求body的参数：</b><br>
code：[之前获取的code]，注意，code只可以使用一次便失效，并且即使没使用也有时效性。<br>
grant_type：authorization_code <br>
redirect_uri：http://localhost:8090/， 与获取code的redirect_uri必须完全对应<br>
<br>

得到的结果如下所示：<br>
<br>{
<br>    "access_token": "17586593-06e8-43be-a0bb-41348af9ae88",
<br>    "token_type": "bearer",
<br>    "refresh_token": "c98996d8-2b88-4415-963a-d8d1aaca30c8",
<br>    "expires_in": 43199,
<br>    "scope": "app test"
<br>}
<br>

<b>2、implicit方式获取token:</b><br>
注意：仅可获取access_token，不能获取refresh_token。<br>
<br>
1)get方式访问：http://localhost:8080/oauth/authorize?client_id=client&response_type=token&redirect_uri=http://localhost:8090/ <br>
注意其中的参数<b>response_type=token</b>，与authorization_code方式的最大不同是response_type变为token了<br>

2)提交被拦截，服务被重定向（forward）向至登录界面。<br>
在登录界面上输入用户名与密码，提交，服务被重定向（forward）到授权（Approval）界面。<br>
进行授权（Approval）后，服务被重定向（forward）至http://localhost:8090/。<br>
在回调的URI中带上token，可以看到如：http://localhost:8090/#access_token=17586593-06e8-43be-a0bb-41348af9ae88&token_type=bearer&expires_in=42487&scope=app%20test <br>
其中的access_token是：17586593-06e8-43be-a0bb-41348af9ae88，由于access_token还没有过期，你可以看到这里获取的access_token与authorization_code方式取得的access_token是一样的，再细心点，会发觉，过期时间变短了。<br>
<br>

<b>3、refresh_token方式获取token：</b><br>
注意：会得到一个全新的access_token。另外，也可以通过修改AuthServerConfig的configure(AuthorizationServerEndpointsConfigurer endpoints)，设置AuthorizationServerEndpointsConfigurer的reuseRefreshTokens(false)得到一个全新refresh_token)。
<br>
<br>
1)post方式发送请求至：http://client:secret@localhost:8080/oauth/token <br>
content-Type是application/x-www-form-urlencoded或者application/form-data都可。<br>
设置参数：<br>
grant_type：refresh_token <br>
refresh_token:[refresh_token值] <br> 
scope：[多个值用空格分开] <br>

2)得到结果如所示：<br>
<br>{
<br>    "access_token": "ef73b228-ac8d-4c20-a916-d17189048698",
<br>    "token_type": "bearer",
<br>    "refresh_token": "c98996d8-2b88-4415-963a-d8d1aaca30c8",
<br>    "expires_in": 43199,
<br>    "scope": "app test"
<br>}
<br>

# 二、通过access_token访问受保护的资源 <br>
 <br>
<b>1、获取客户端信息 </b><br>
有两种方式: <br>
方式一，url中追加access_token参数，如： <br>
http://localhost:8080/user?access_token=ef73b228-ac8d-4c20-a916-d17189048698 <br>
2)在请求的header中设置参数：
Authorization参数，值是“[grant_type] [access_token]”，grant_type值与access_token值之间用空格分开。例如：bearer 65d6f4f6-70d3-4bb4-b36b-c6e570a3027b <br>
 <br>
<b>2、其它受保护的资源</b>  <br>
与获取客户端信息的操作类同。 <br>
<br>



