package io.github.kacper99.qbittorrentj;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@WireMockTest
class TransferInfoCallsTest {

    private static TransferInfo transferInfo;

    @BeforeAll
    static void beforeAll(final WireMockRuntimeInfo wireMockRuntimeInfo) {
        transferInfo = new TransferInfo("http://localhost:" + wireMockRuntimeInfo.getHttpPort());
    }

    @Test
    void settingUploadLimit() throws IOException {
        transferInfo.setUploadSpeedLimit(30);

        verify(postRequestedFor(urlEqualTo("/api/v2/transfer/setUploadLimit"))
                .withRequestBody(matching("limit=30720")));
    }

    @Test
    void settingDownloadLimit() throws IOException {
        transferInfo.setDownloadSpeedLimit(20);

        verify(postRequestedFor(urlEqualTo("/api/v2/transfer/setDownloadLimit"))
                .withRequestBody(matching("limit=20480")));
    }
}
