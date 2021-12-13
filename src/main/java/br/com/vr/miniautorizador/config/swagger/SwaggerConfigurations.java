package br.com.vr.miniautorizador.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.vr.miniautorizador.modelo.Cartao;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket miniAutorizadorApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.vr.miniautorizador"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Cartao.class);
	}
}
