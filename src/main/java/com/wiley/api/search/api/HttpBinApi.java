package com.wiley.api.search.api;

import com.wiley.interfaces.IOperation;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.POST;

public class HttpBinApi {

    private Supplier<RequestSpecBuilder> reqSpecSupplier;

    private HttpBinApi(Supplier<RequestSpecBuilder> reqSpecSupplier) {
        this.reqSpecSupplier = reqSpecSupplier;
    }

    public static HttpBinApi httpBin(Supplier<RequestSpecBuilder> reqSpecSupplier) {
        return new HttpBinApi(reqSpecSupplier);
    }

    private RequestSpecBuilder createReqSpec() {
        RequestSpecBuilder reqSpec = reqSpecSupplier.get();
        return reqSpec;
    }

    public DelayOperation delay() {
        return new DelayOperation(createReqSpec());
    }

    public GetImageOperation getImage() {
        return new GetImageOperation(createReqSpec());
    }

    public static class GetImageOperation implements IOperation {

        public static final Method REQ_METHOD = GET;
        public static final String REQ_URI = "/image/png";

        private RequestSpecBuilder reqSpec;
        private ResponseSpecBuilder respSpec;

        public GetImageOperation(RequestSpecBuilder reqSpec) {
            this.reqSpec = reqSpec;
            reqSpec.setContentType("image/png");
            reqSpec.setAccept("image/png");
            this.respSpec = new ResponseSpecBuilder();
        }

        /**
         * GET /image/png
         *
         * @param handler handler
         * @param <T>     type
         * @return type
         */
        @Override
        public <T> T execute(Function<Response, T> handler) {
            return handler.apply(RestAssured.given().spec(reqSpec.build()).log().all().expect().spec(respSpec.build()).log().all().when().request(REQ_METHOD, REQ_URI));
        }

    }

    public static class DelayOperation implements IOperation {

        public static final Method REQ_METHOD = POST;
        public static final String REQ_URI = "/delay/{delay}";

        private RequestSpecBuilder reqSpec;
        private ResponseSpecBuilder respSpec;

        public DelayOperation(RequestSpecBuilder reqSpec) {
            this.reqSpec = reqSpec;
            reqSpec.setContentType("application/json");
            reqSpec.setAccept("application/json");
            this.respSpec = new ResponseSpecBuilder();
        }

        /**
         * POST /delay/{delay}
         *
         * @param handler handler
         * @param <T>     type
         * @return type
         */
        @Override
        public <T> T execute(Function<Response, T> handler) {
            return handler.apply(RestAssured.given().spec(reqSpec.build()).log().all().expect().spec(respSpec.build()).log().all().when().request(REQ_METHOD, REQ_URI));
        }

        private static final String DELAY_NAME_QUERY = "delay";

        /**
         * @param delay (int)  (required)
         * @return operation
         */
        public HttpBinApi.DelayOperation delay(int delay) {
            reqSpec.addPathParam(DELAY_NAME_QUERY, delay);
            return this;
        }
    }
}
