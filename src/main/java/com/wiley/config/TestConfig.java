package com.wiley.config;

import com.wiley.http.client.ApiClient;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;

/**
 * Class initiate HTTP RestAssured apiClient and read test data from test.properties
 */
public class TestConfig {

    public static ApiClient wileyApiClient;
    public static ApiClient httpBinApiClient;
    public static String WILEY_API_URL = "https://www.wiley.com/en-us";
    public static String HTTPBIN_API_URL = "https://httpbin.org";

    static {
        initWileyApi();
        initHttpBinApi();
    }


    /**
     * Creates apiClient for wiley API
     */
    private static void initWileyApi() {
        wileyApiClient = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(() ->
                new RequestSpecBuilder().setConfig(RestAssuredConfig.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE)).objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON))).setBaseUri(WILEY_API_URL).setContentType(ContentType.JSON)));
    }

    /**
     * Creates apiClient for httpbin API
     */
    private static void initHttpBinApi() {
        httpBinApiClient = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(() ->
                new RequestSpecBuilder().setConfig(RestAssuredConfig.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE)).objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON))).setBaseUri(HTTPBIN_API_URL).setContentType(ContentType.JSON)));
    }

}
