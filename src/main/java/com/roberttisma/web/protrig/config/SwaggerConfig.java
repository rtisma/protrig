/*
 * Copyright (c) 2018. Ontario Institute for Cancer Research
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.roberttisma.web.protrig.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  @Value("${server.version}")
  private String serverVersion;

  @Value("${swagger.alternateUrl:/swagger}")
  @Getter
  private String alternateSwaggerUrl;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(basePackage("com.roberttisma.web.protrig.controller"))
        .paths(any())
        .build()
        .pathMapping("/");
  }

  @Bean
  UiConfiguration uiConfig() {
    return new UiConfiguration(
        "validatorUrl",
        "none",
        "alpha",
        "schema",
        UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
        false,
        true,
        60000L);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("PROTRIG API")
        .description("Protrig is a simple rest controller that allows users to asynchronously sync the id-server database")
        .version(serverVersion)
        .build();
  }

}
