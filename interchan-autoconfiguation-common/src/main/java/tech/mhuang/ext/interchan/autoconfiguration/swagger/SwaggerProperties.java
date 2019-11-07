package tech.mhuang.ext.interchan.autoconfiguration.swagger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger properties
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.swagger")
public class SwaggerProperties {

    /**
     * open swagger2
     */
    private boolean enable = false;

    /**
     * import packages use swagger
     */
    private String basePackage;

    /**
     * swagger title
     */
    private String title;

    /**
     * swagger description
     */
    private String description;

    /**
     * swagger version
     */
    private String version;

    /**
     * license
     */
    private String license;

    /**
     * license url
     */
    private String licenseUrl;

    /**
     * terms of service url
     */
    private String termsOfServiceUrl = "";

    /**
     * swagger req alias.
     */
    private List<Request> reqParamList = new ArrayList<>();

    /**
     * swagger response alias.
     */
    private List<Response> globalRespMsg = new ArrayList<>();

    private Contact contact = new Contact();

    public boolean addResp(int code, String message) {
        return globalRespMsg.add(new Response(code, message));
    }

    public boolean addReqParam(String name, String description, String ref, String type, boolean required) {
        return reqParamList.add(new Request(name, description, ref, type, required));
    }

    /**
     * contact properties
     */
    @Data
    static class Contact {

        /**
         * contact person
         */
        String name;

        /**
         * contact url
         */
        String url;
        /**
         * contact email
         */
        String email;
    }

    @Data
    @AllArgsConstructor
    static class Request {
        /**
         * name
         */
        String name;
        /**
         * description
         */
        String description;

        /**
         * modelRef
         */
        String ref;
        /**
         * type
         */
        String type;

        /**
         * required.
         * default is false
         */
        boolean required;
    }

    /**
     * ask
     */
    @Data
    @AllArgsConstructor
    static class Response {
        /**
         * ask code
         */
        int code;
        /**
         * ask msg
         */
        String msg;
    }
}
