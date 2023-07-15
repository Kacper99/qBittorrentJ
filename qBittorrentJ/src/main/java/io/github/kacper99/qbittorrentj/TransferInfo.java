package io.github.kacper99.qbittorrentj;

import io.github.kacper99.qbittorrentj.okhttp.SimpleCookieJar;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class TransferInfo {

    private final OkHttpClient httpClient =
            new OkHttpClient.Builder().cookieJar(SimpleCookieJar.getInstance()).addInterceptor(new HttpLoggingInterceptor()).build();
    private final String qBittorrentWebUiUrl;

    /**
     * Protected as we only want to allow this to be constructed from {@link QBittorrent}
     */
    protected TransferInfo(String qBittorrentWebUiUrl) {
        this.qBittorrentWebUiUrl = qBittorrentWebUiUrl;
    }

    public int getDownloadSpeedLimit() throws IOException {
        return getSpeedRequest(TransferInfoSpeedRequestType.DOWNLOAD);
    }

    public void setDownloadSpeedLimit(final int limit) throws IOException {
        setSpeedRequest(TransferInfoSpeedRequestType.DOWNLOAD, limit);
    }

    public int getUploadSpeedLimit() throws IOException {
        return getSpeedRequest(TransferInfoSpeedRequestType.UPLOAD);
    }

    private int getSpeedRequest(final TransferInfoSpeedRequestType requestType) throws IOException {
        final var speedRequest = transferRequest(requestType.getPath).get().build();

        try (final var responseBody = httpClient.newCall(speedRequest).execute().body()) {
            return Integer.parseInt(responseBody.string());
        }
    }

    private void setSpeedRequest(final TransferInfoSpeedRequestType requestType, final int limit) throws IOException {
        final var request = transferRequest(requestType.setPath)
                .post(new FormBody.Builder().add("limit", String.valueOf(limit)).build())
                .build();

        httpClient.newCall(request).execute().code();
    }

    private enum TransferInfoSpeedRequestType {
        UPLOAD("/uploadLimit", "/setUploadLimit"),
        DOWNLOAD("/downloadLimit", "/setDownloadLimit");

        private final String getPath;
        private final String setPath;

        TransferInfoSpeedRequestType(final String getPath, String setPath) {
            this.getPath = getPath;
            this.setPath = setPath;
        }
    }

    private Request.Builder transferRequest(final String methodPath) {
        return new Request.Builder().url(qBittorrentWebUiUrl + "/api/v2/transfer" + methodPath);
    }
}
