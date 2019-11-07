package tech.mhuang.ext.interchan.autoconfiguration.swagger;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger reactive
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(PropertiesRouteDefinitionLocator.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GatewaySwaggerAutoConfiguration {

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider(PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator) {
        return new SwaggerProvider(propertiesRouteDefinitionLocator);
    }

    @Bean
    @ConditionalOnBean(SwaggerResourcesProvider.class)
    public SwaggerCustomResponse swaggerCustomResponse(SwaggerResourcesProvider swaggerResourcesProvider) {
        return new SwaggerCustomResponse(swaggerResourcesProvider);
    }

    @Bean
    @ConditionalOnBean(SwaggerCustomResponse.class)
    public RouterFunction routerFunction(SwaggerCustomResponse customResponse) {
        return RouterFunctions.route(RequestPredicates.GET("/swagger-resources")
                .and(RequestPredicates.accept(MediaType.ALL)), req -> customResponse.getResource(req))
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
                        .and(RequestPredicates.accept(MediaType.ALL)), req -> customResponse.getUi(req))
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                        .and(RequestPredicates.accept(MediaType.ALL)), req -> customResponse.getSecurity(req));
    }

    @AllArgsConstructor
    public class SwaggerCustomResponse {

        public SwaggerResourcesProvider swaggerResourcesProvider;

        public Mono<ServerResponse> getResource(ServerRequest serverRequest) {
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(swaggerResourcesProvider.get()));
        }

        public Mono<ServerResponse> getSecurity(ServerRequest request) {
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(SecurityConfigurationBuilder.builder().build()));
        }

        public Mono<ServerResponse> getUi(ServerRequest request) {
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(UiConfigurationBuilder.builder().build()));
        }
    }

    public class SwaggerProvider implements SwaggerResourcesProvider {

        public static final String API_URI = "/v2/api-docs";

        private PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator;

        public  SwaggerProvider(PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator){
            this.propertiesRouteDefinitionLocator = propertiesRouteDefinitionLocator;
        }
        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resources = new ArrayList<>();
            propertiesRouteDefinitionLocator.getRouteDefinitions().subscribe(route -> {
                String host = route.getUri().getHost();
                resources.add(swaggerResource(host, String.format("/%s%s", host, API_URI)));
            });
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion("2.0");
            return swaggerResource;
        }
    }
}
