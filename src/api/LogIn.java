package api;
import com.google.gson.*;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.nio.charset.StandardCharsets;

public class LogIn {
    JsonObject checkJson;

    public LogIn(){checkJson = null;}

    public void logingIn(String email, String password, String server){
        try {

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            HttpClient client = HttpClient.newBuilder()
                    .cookieHandler(cookieManager)
                    .followRedirects(Redirect.NEVER)
                    .build();

            HttpRequest initialRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://" + server + ".herozerogame.com/"))
                    .header("User-Agent", "Chrome/126.0.6478.127")
                    .build();

            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
            String initialResponseBody = initialResponse.body();

            // Extract app version and client ID
            String appVersion = extractValue(initialResponseBody, "localeVersion", 48, 51);
            String clientId = extractValue(initialResponseBody, "uniqueId", 11, 25);

            //b67c0311ba923f64edc58bf3d7957b60
            String requestData = String.format("email=%s&password=%s&platform=&platform_user_id=&client_id=%s&app_version=%s&videce_info=%%7B%%22language%%22%%3A%%22xu%%22%%2C%%22pixelAspectRatio%%22%%3A1%%2C%%22screenDPI%%22%%3A72%%2C%%22screenResolutionX%%22%%3A1536%%2C%%22screenResolutionY%%22%%3A864%%2C%%22touchscreenType%%22%%3A0%%2C%%22os%%22%%3A%%22HTML5%%22%%2C%%22version%%22%%3A%%22WEB%%208%%2C9%%2C7%%2C0%%22%%7D&device_id=web&action=loginUser&user_id=0&user_session_id=0&client_version=html5_%s&auth=1&rct=1&keep_active=true&device_type=web",
                    encode(email), encode(password), clientId, appVersion, appVersion);

            ApiRequest loginRequest = new ApiRequest();
            HttpResponse<String> check = loginRequest.createRequest(server, requestData);
            this.checkJson = JsonParser.parseString(check.body()).getAsJsonObject();
            System.out.println(checkJson);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractValue(String responseBody, String key, int offsetStart, int offsetEnd) {
        int indexOfKey = responseBody.indexOf(key);
        return responseBody.substring(indexOfKey + offsetStart, indexOfKey + offsetStart + offsetEnd);
    }

    public JsonObject getDataJson() {
        return checkJson.getAsJsonObject("data");
    }

    private static String encode(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
