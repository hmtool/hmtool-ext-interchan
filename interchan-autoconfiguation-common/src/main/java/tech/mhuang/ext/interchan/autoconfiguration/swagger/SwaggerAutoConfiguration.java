package tech.mhuang.ext.interchan.autoconfiguration.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tech.mhuang.core.util.CollectionUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger自动注册
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "mhuang.interchan.swagger", name = "enable", havingValue = "true")
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableSwagger2
public class SwaggerAutoConfiguration {

    private final SwaggerProperties properties;

    public SwaggerAutoConfiguration(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Docket docket() {

        ApiInfo apiInfo = createApiInfo();
        List<ResponseMessage> responseMessageList = createRespMsg();
        List<Parameter> parameterList = createReqParam();

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
                .apis(RequestHandlerSelectors.basePackage(this.properties.getBasePackage()))
                .paths(PathSelectors.any()).build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalOperationParameters(parameterList);
    }

    private List<Parameter> createReqParam() {
        if (CollectionUtil.isEmpty(this.properties.getReqParamList())) {
            this.properties.addReqParam("Authorization", "Bearer 开头加上登录的时候令牌,填写时表示带用户凭证进行访问", "string", "header", false);
            this.properties.addReqParam("AuthType", "令牌类型", "string", "header", false);
            this.properties.addReqParam("source", "用户来源", "string", "header", false);
        }
        return this.properties.getReqParamList().stream().map(request -> {
            ParameterBuilder builder = new ParameterBuilder();
            return builder.name(request.getName()).description(request.getDescription())
                    .modelRef(new ModelRef(request.getType())).parameterType(request.getType())
                    .required(request.isRequired()).build();
        }).collect(Collectors.toList());
    }

    /**
     * create response message
     *
     * @return
     *
     */
    private List<ResponseMessage> createRespMsg() {
        if (CollectionUtil.isEmpty(this.properties.getGlobalRespMsg())) {
            this.properties.addResp(200, "请求成功");
            this.properties.addResp(403, "无权限访问");
            this.properties.addResp(404, "无路径响应");
            this.properties.addResp(500, "系统内部错误");
        }
        return this.properties.getGlobalRespMsg().stream().map(response -> {
            ResponseMessageBuilder builder = new ResponseMessageBuilder();
            return builder.code(response.getCode()).message(response.getMsg()).build();
        }).collect(Collectors.toList());
    }

    /**
     * create api info
     *
     * @return
     */
    private ApiInfo createApiInfo() {
        return new ApiInfoBuilder().title(this.properties.getTitle())
                .description(this.properties.getDescription())
                .version(this.properties.getVersion())
                .license(this.properties.getLicense())
                .licenseUrl(this.properties.getLicenseUrl())
                .termsOfServiceUrl(this.properties.getTermsOfServiceUrl())
                .contact(new Contact(this.properties.getContact().getName(),
                        this.properties.getContact().getUrl(),
                        this.properties.getContact().getEmail())).build();
    }
}
