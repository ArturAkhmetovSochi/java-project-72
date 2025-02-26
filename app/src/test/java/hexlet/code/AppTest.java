package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.model.UrlCheck;

public class AppTest {

    private static MockWebServer mockServer;
    private Javalin app;

    @BeforeAll
    public static void startMockWebServer() throws IOException {
        mockServer = new MockWebServer();
        Path mocktesthtml = Paths.get("src/test/resources/mocktest.html").toAbsolutePath().normalize();
        MockResponse mockResponse = new MockResponse()
                .setBody(Files.readString(mocktesthtml))
                .setResponseCode(200);
        mockServer.enqueue(mockResponse);
        mockServer.start();
    }

    @AfterAll
    public static void stopMockWenServer() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testBasePage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://www.google.com");
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.google.com");
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void mockTest() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            String mockUrl =  mockServer.url("/").toString();
            Url url = new Url(mockUrl);
            UrlRepository.save(url);
            var response = client.post(NamedRoutes.checksPath(String.valueOf(url.getId())));
            assertThat(response.code()).isEqualTo(200);

            UrlCheck urlCheck = UrlCheckRepository.findByUrlId(url.getId()).getFirst();
            assertThat(urlCheck.getId()).isEqualTo(1);
            assertThat(urlCheck.getUrlId()).isEqualTo(1);
            assertThat(urlCheck.getStatusCode()).isEqualTo(200);
            assertThat(urlCheck.getTitle()).contains("Title test");
            assertThat(urlCheck.getH1()).contains("H1 test");
            assertThat(urlCheck.getDescription()).contains("description test");
        });
    }


}
