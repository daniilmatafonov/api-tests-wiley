package tests;

import com.wiley.api.search.api.WileyApi;
import com.wiley.api.search.model.SearchResp;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.wiley.config.TestConfig.wileyApiClient;
import static com.wiley.mapping.ResponseSpecBuilders.shouldBeCode;
import static com.wiley.mapping.ResponseSpecBuilders.validatedWith;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("wiley")
public class WileySearchTest {

    @Test
    public void checkRespContainsExact4TermSuggestions() {
        final WileyApi.SearchOperation searchOperation = wileyApiClient.search().search().term("Java");
        final SearchResp searchResp = searchOperation.executeAs(validatedWith(shouldBeCode(HttpStatus.SC_OK)));
        step("verify response is not empty", () -> {
            assertThat(searchResp, Matchers.notNullValue());
        });
    }
}
