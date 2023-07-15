package io.github.kacper99.qbittorrentj.groups;

import io.github.kacper99.qbittorrentj.okhttp.SimpleCookieJar;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TransferInfo {

    private final OkHttpClient httpClient =
            new OkHttpClient.Builder().cookieJar(SimpleCookieJar.getInstance()).build();
    private final String qBittorrentWebUiUrl;

    public TransferInfo(String qBittorrentWebUiUrl) {
        this.qBittorrentWebUiUrl = qBittorrentWebUiUrl;
    }

    public int getDownloadSpeedLimit() throws IOException {
        return speedRequest(TransferInfoSpeedRequestType.DOWNLOAD);
    }

    public int getUploadSpeedLimit() throws IOException {
        return speedRequest(TransferInfoSpeedRequestType.UPLOAD);
    }

    private int speedRequest(final TransferInfoSpeedRequestType requestType) throws IOException {
        final var sppedRequest =
                request("/api/v2/transfer/%s".formatted(requestType.path)).get().build();

        try (final var responseBody = httpClient.newCall(sppedRequest).execute().body()) {
            return Integer.parseInt(responseBody.string());
        }
    }

    private enum TransferInfoSpeedRequestType {
        UPLOAD("uploadLimit"),
        DOWNLOAD("downloadLimit");

        private final String path;

        TransferInfoSpeedRequestType(final String path) {
            this.path = path;
        }
    }

    private Request.Builder request(final String path) {
        return new Request.Builder().url(qBittorrentWebUiUrl + path);
    }
}
