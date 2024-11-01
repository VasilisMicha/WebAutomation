package api;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiRequest {
    private static final Logger logger = Logger.getLogger(ApiRequest.class.getName());
    private static final HttpClient client;

    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .followRedirects(Redirect.NEVER)
                .build();
    }

    public HttpResponse<String> createRequest(String server, String data) {
        try {
            String postUrl = "https://" + server + ".herozerogame.com/request.php";
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI(postUrl))
                    .header("User-Agent", "Chrome/126.0.6478.127")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            return client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during API request", e);
        }
        return null;
    }
}
