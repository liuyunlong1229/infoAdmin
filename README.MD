 1、生成的二维码图片在磁盘的固定跟目录下，为了在网站上可以访问需要配置目录映射，修改tomcat的server.xml，在host标签下面加入
<Context path="/images/qrcode"      docBase="E:/qrCode" debug="0" reloadable="true"/>



No qualifying bean of type [org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping] found for dependency

I was having the same problem and am using the XML configuration.
I removed the @Configuration attribute from my SwaggerConfig and it worked fine.