package com.wiley.api.search.api;

import com.google.gson.reflect.TypeToken;
import com.wiley.api.search.model.SearchResp;
import com.wiley.interfaces.IOperation;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.restassured.http.Method.GET;

public class WileyApi {

    private Supplier<RequestSpecBuilder> reqSpecSupplier;

    private WileyApi(Supplier<RequestSpecBuilder> reqSpecSupplier) {
        this.reqSpecSupplier = reqSpecSupplier;
    }

    public static WileyApi search(Supplier<RequestSpecBuilder> reqSpecSupplier) {
        return new WileyApi(reqSpecSupplier);
    }

    private RequestSpecBuilder createReqSpec() {
        RequestSpecBuilder reqSpec = reqSpecSupplier.get();
        return reqSpec;
    }

    public SearchOperation search() {
        return new SearchOperation(createReqSpec());
    }

    public static class SearchOperation implements IOperation {

        public static final Method REQ_METHOD = GET;
        public static final String REQ_URI = "/search/autocomplete/comp_00001H9J";

        private RequestSpecBuilder reqSpec;
        private ResponseSpecBuilder respSpec;

        public SearchOperation(RequestSpecBuilder reqSpec) {
            this.reqSpec = reqSpec;
            reqSpec.setContentType("application/json");
            reqSpec.setAccept("application/json");
            this.respSpec = new ResponseSpecBuilder();
        }

        /**
         * GET /search/autocomplete/comp_00001H9J
         *
         * @param handler handler
         * @param <T>     type
         * @return type
         */
        @Override
        public <T> T execute(Function<Response, T> handler) {
            return handler.apply(RestAssured.given().spec(reqSpec.build()).log().all().expect().spec(respSpec.build()).log().all().when().request(REQ_METHOD, REQ_URI));
        }

        /**
         * GET /search/autocomplete/comp_00001H9J
         *
         * @param handler handler
         * @return SearchResp
         */
        public SearchResp executeAs(Function<Response, Response> handler) {
            Type type = new TypeToken<SearchResp>() {
            }.getType();
            return execute(handler).as(type);
        }

        private static final String TERM_NAME_QUERY = "term";

        /**
         * @param term (String)  (required)
         * @return operation
         */
        public WileyApi.SearchOperation term(String term) {
            reqSpec.addQueryParam(TERM_NAME_QUERY, term);
            return this;
        }
    }
}
