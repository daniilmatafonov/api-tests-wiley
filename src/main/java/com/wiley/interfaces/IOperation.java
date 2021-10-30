

package com.wiley.interfaces;

import io.restassured.response.Response;

import java.util.function.Function;

public interface IOperation {

    <T> T execute(Function<Response, T> handler);

}
