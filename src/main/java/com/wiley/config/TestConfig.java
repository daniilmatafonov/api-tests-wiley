package com.wiley.config;

import com.wiley.filters.HttpRequestFilter;
import com.wiley.http.client.ApiClient;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;

/**
 * Class initiate HTTP RestAssured apiClient and read test data from test.properties
 */
public class TestConfig {

    public static ApiClient wileyApiClient;
    public static ApiClient httpBinApiClient;
    public static String WILEY_API_URL = "https://www.wiley.com/en-us";
    public static String HTTP_BIN_API_URL = "https://httpbin.org";
    private static List<Filter> filters = new ArrayList<>();

    static {
        initFilters();
        initWileyApi();
        initHttpBinApi();
    }


    /**
     * Creates apiClient for wiley API
     */
    private static void initWileyApi() {
        wileyApiClient = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(() ->
                new RequestSpecBuilder().addFilters(filters).setConfig(RestAssuredConfig.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE)).objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON))).setBaseUri(WILEY_API_URL).setContentType(ContentType.JSON)));
    }

    /**
     * Creates apiClient for httpbin API
     */
    private static void initHttpBinApi() {
        httpBinApiClient = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(() ->
                new RequestSpecBuilder().addFilters(filters).setConfig(RestAssuredConfig.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE)).objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON))).setBaseUri(HTTP_BIN_API_URL).setContentType(ContentType.JSON)));
    }

    private static void initFilters(){
        filters.add(new ErrorLoggingFilter());
        filters.add(new HttpRequestFilter());
    }

}
