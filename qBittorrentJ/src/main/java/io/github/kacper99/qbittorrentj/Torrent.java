package io.github.kacper99.qbittorrentj;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Torrent {

    private final String qBittorrentWebUiUrl;

    private final OkHttpClient httpClient = new OkHttpClient();

    public Torrent(final String qBittorrentWebUiUrl) {
        this.qBittorrentWebUiUrl = qBittorrentWebUiUrl;
    }

    private void login(final String username, final String password) {
        final var loginRequest = new Request.Builder()
                .url("%s/api/v2/auth/login".formatted(qBittorrentWebUiUrl))
                .get();
    }
}
