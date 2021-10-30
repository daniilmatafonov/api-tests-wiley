package tests.httpbin;

import com.wiley.api.search.api.HttpBinApi;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.wiley.config.TestConfig.httpBinApiClient;
import static com.wiley.mapping.ResponseSpecBuilders.shouldBeCode;
import static com.wiley.mapping.ResponseSpecBuilders.validatedWith;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Tag("http_bin")
public class HttpBinTest {

    @BeforeAll
    static void beforeAll() {
        Awaitility.setDefaultTimeout(Duration.ONE_MINUTE);
    }

    private volatile boolean completed = false;
    private static final int DELAY = 10;

    /**
     * test checks delay
     */
    @Test
    public void checkDelayEqualTo10Seconds() {
        final HttpBinApi.DelayOperation delayOperation = httpBinApiClient.httpBinApi().delay().delay(DELAY);
        step("verify delay equalTo " + DELAY + " seconds", () -> {
            await().ignoreExceptions().atLeast(DELAY, SECONDS).until(() -> sendDelay(delayOperation), equalTo(completed));
        });
    }

    /**
     * test get image
     */
    @Test
    public void checkGetImage() {
        final HttpBinApi.GetImageOperation imageOperation = httpBinApiClient.httpBinApi().getImage();
        final Response response = imageOperation.execute(validatedWith(shouldBeCode(HttpStatus.SC_OK)));
        step("verify response content-type is image/png", () -> {
            assertThat(response.getContentType(), equalTo("image/png"));
        });
    }

    private boolean sendDelay(HttpBinApi.DelayOperation delayOperation) {
        if (delayOperation.execute(validatedWith(shouldBeCode(HttpStatus.SC_OK))).getStatusCode() == HttpStatus.SC_OK) {
            completed = true;
        }
        return false;
    }
}
