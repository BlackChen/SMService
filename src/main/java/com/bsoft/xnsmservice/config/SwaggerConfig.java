package com.bsoft.xnsmservice.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * Created by blackchen on 2020/9/29 10:16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
//				.host("localhost:8081")// 不配的话，默认当前项目端口
				//apiInfo指定测试文档基本信息，这部分将在页面展示
				.apiInfo(apiInfo())
				.select()
				//apis() 控制哪些接口暴露给swagger，
				// RequestHandlerSelectors.any() 所有都暴露
				// RequestHandlerSelectors.basePackage("com.info.*")  指定包位置
				.apis(RequestHandlerSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
				.paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
				.paths(PathSelectors.any())
				.build();
	}

	//基本信息，页面展示
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("短信服务接口")
				.description("短信服务:天柱/")
				//联系人实体类
				.contact(
						new Contact("BlackChen",
								"",
								"chenq1@bsoft.com.cn")
				)
				//版本号
				.version("1.0.0-SNAPSHOT")
				.build();
	}

}
