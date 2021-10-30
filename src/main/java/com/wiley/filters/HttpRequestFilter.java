package com.wiley.filters;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.UUID;

/**
 * Processes http requests for Allure Report
 */
public class HttpRequestFilter extends AllureRestAssured {

    /**
     *
     * @param requestSpec A request specification that also supports getting and changing the defined values. Intended for Filters.
     * @param responseSpec A response specification that also supports getting the defined values. Intended for Filters.
     * @param filterContext Provides the functionality to set properties, sending requests and continue the filter chain.
     * @return The response of a request made by REST Assured.
     */
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.startStep(UUID.randomUUID().toString(), (new StepResult()).setStatus(Status.PASSED).setName(String.format("%s: %s", requestSpec.getMethod(), requestSpec.getURI())));

        Response response;
        try {
            response = super.filter(requestSpec, responseSpec, filterContext);
        } finally {
            lifecycle.stopStep();
        }

        return response;
    }
}
