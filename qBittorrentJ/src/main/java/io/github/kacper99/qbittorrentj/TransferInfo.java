package io.github.kacper99.qbittorrentj;

import io.github.kacper99.qbittorrentj.okhttp.SimpleCookieJar;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * The constraints for the download speeds in qBittorrent are:
 * - Must be an integer
 * - Minimum value 1
 * - Maximum value 10000
 *
 * The values are sent as bytes per second, but qBittorrent seems to round it to the nearest kilobyte.
 */
public class TransferInfo {

    private final OkHttpClient httpClient =
            new OkHttpClient.Builder().cookieJar(SimpleCookieJar.getInstance()).build();
    private final String qBittorrentWebUiUrl;

    /**
     * Protected as we only want to allow this to be constructed from {@link QBittorrent}
     */
    protected TransferInfo(String qBittorrentWebUiUrl) {
        this.qBittorrentWebUiUrl = qBittorrentWebUiUrl;
    }

    /**
     * @return download speed in KiB/s
     */
    public int getDownloadSpeedLimit() throws IOException {
        return getSpeedRequest(TransferInfoSpeedRequestType.DOWNLOAD);
    }

    /**
     * @param limit download speed limit in KiB/s
     * @return whether the request was successful
     */
    public boolean setDownloadSpeedLimit(final int limit) throws IOException {
        return setSpeedRequest(TransferInfoSpeedRequestType.DOWNLOAD, limit);
    }

    /**
     * @return upload speed in KiB/s
     */
    public int getUploadSpeedLimit() throws IOException {
        return getSpeedRequest(TransferInfoSpeedRequestType.UPLOAD);
    }

    /**
     * @param limit upload speed limit in KiB/s
     * @return whether the request was successful
     */
    public boolean setUploadSpeedLimit(final int limit) throws IOException {
        return setSpeedRequest(TransferInfoSpeedRequestType.UPLOAD, limit);
    }

    private int getSpeedRequest(final TransferInfoSpeedRequestType requestType) throws IOException {
        final var speedRequest = transferRequest(requestType.getPath).get().build();

        try (final var response = httpClient.newCall(speedRequest).execute();
                final var body = response.body()) {
            return Integer.parseInt(body.string()) / 1024;
        }
    }

    private boolean setSpeedRequest(final TransferInfoSpeedRequestType requestType, final int limit)
            throws IOException {
        final var request = transferRequest(requestType.setPath)
                .post(new FormBody.Builder()
                        .add("limit", String.valueOf(toBytesPerSecond(limit)))
                        .build())
                .build();

        try (final var response = httpClient.newCall(request).execute()) {
            return response.code() == 200;
        }
    }

    private static int toBytesPerSecond(final int kb) {
        return kb * 1024;
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
