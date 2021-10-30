package tests.wiley;

import com.wiley.api.search.api.WileyApi;
import com.wiley.api.search.model.SearchResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.wiley.config.TestConfig.wileyApiClient;
import static com.wiley.mapping.ResponseSpecBuilders.shouldBeCode;
import static com.wiley.mapping.ResponseSpecBuilders.validatedWith;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Tag("wiley")
public class WileySearchTest {

    private static final String TERM = "Java";
    private static final String WILEY = "Wiley";
    private String expectedSuggestion = "<span class=\"search-highlight\">" + StringUtils.toRootLowerCase(TERM) + "</span>";
    private static final long EXPECTED_MATCHES_COUNT = 4;

    /**
     * test checks term suggestions by java and pages which includes Wiley
     */
    @Test
    public void checkRespContainsExact4TermSuggestionsAndPages() {
        final WileyApi.SearchOperation searchOperation = wileyApiClient.wileyApi().search().term(TERM);
        final SearchResp searchResp = searchOperation.executeAs(validatedWith(shouldBeCode(HttpStatus.SC_OK)));
        step("verify response is not empty", () -> {
            assertThat(searchResp, Matchers.notNullValue());
        });
        step("verify response contains exact " + EXPECTED_MATCHES_COUNT + " terms suggestions with java", () -> {
            long suggestionsCount = searchResp.getSuggestions().stream().filter(item -> item.getTerm().contains(expectedSuggestion)).count();
            assertThat(suggestionsCount, equalTo(EXPECTED_MATCHES_COUNT));
        });
        step("verify response contains exact " + EXPECTED_MATCHES_COUNT + " pages with title includes " + WILEY, () -> {
            long pagesCount = searchResp.getPages().stream().filter(item -> item.getTitle().contains(WILEY)).count();
            assertThat(pagesCount, equalTo(EXPECTED_MATCHES_COUNT));
        });
    }
}
