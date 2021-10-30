
package com.wiley.http.client;

import com.wiley.api.search.api.WileyApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import java.util.function.Supplier;

import static com.wiley.mapping.GsonObjectMapper.gson;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;


public class ApiClient {

    private final Config config;

    private ApiClient(Config config) {
        this.config = config;
    }

    public static ApiClient api(Config config) {
        return new ApiClient(config);
    }

    /**
     * Get WileyApi
     * @return WileyApi
     */
    public WileyApi search() {
        return WileyApi.search(config.reqSpecSupplier);
    }

    /**
     * Config for RestAssured
     */
    public static class Config {
        private Supplier<RequestSpecBuilder> reqSpecSupplier = () -> new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson())));

        /**
         * Use common specification for all operations
         *
         * @param supplier supplier
         * @return configuration
         */
        public Config reqSpecSupplier(Supplier<RequestSpecBuilder> supplier) {
            this.reqSpecSupplier = supplier;
            return this;
        }

        public static Config apiConfig() {
            return new Config();
        }
    }
}
