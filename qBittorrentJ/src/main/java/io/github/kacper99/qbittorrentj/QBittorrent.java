package io.github.kacper99.qbittorrentj;

import io.github.kacper99.qbittorrentj.okhttp.SimpleCookieJar;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;
import okhttp3.*;

public class QBittorrent {
    private final OkHttpClient httpClient =
            new OkHttpClient.Builder().cookieJar(SimpleCookieJar.getInstance()).build();
    private final String qBittorrentWebUiUrl;

    public QBittorrent(final String qBittorrentWebUiUrl) {
        this.qBittorrentWebUiUrl = qBittorrentWebUiUrl;
    }

    public boolean login(final String username, final String password) {
        final var loginRequest = request("/api/v2/auth/login")
                .post(new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build())
                .build();

        return makeCall(loginRequest, ignoredResponse -> checkIfAuthenticated());
    }

    private boolean checkIfAuthenticated() {
        final var applicationVersionRequest =
                request("/api/v2/app/version").get().build();

        return makeCall(applicationVersionRequest, response -> response.code() == 200);
    }

    public TransferInfo transferInfo() {
        return new TransferInfo(qBittorrentWebUiUrl);
    }

    private Request.Builder request(final String path) {
        return new Request.Builder().url(qBittorrentWebUiUrl + path);
    }

    private <T> T makeCall(final Request request, final Function<Response, T> function) {
        try (final var response = httpClient.newCall(request).execute()) {
            return function.apply(response);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
