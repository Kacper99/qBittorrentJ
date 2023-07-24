package io.github.kacper99.qbittorrentj;

import static org.assertj.core.api.Assertions.*;

import io.github.kacper99.qbittorrentj.testcontainers.QBittorrentTestContainer;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class TransferInfoTest {
    @Container
    private static final QBittorrentTestContainer qBittorrentContainer = new QBittorrentTestContainer();

    private static TransferInfo transferInfo;

    @BeforeAll
    static void beforeAll() {
        final var qBittorrent = new QBittorrent(qBittorrentContainer.webUiUrl());
        final var loggedIn = qBittorrent.login("admin", "adminadmin");

        assertThat(loggedIn).isTrue();
        transferInfo = qBittorrent.transferInfo();
    }

    @Test
    void settingAndGettingDownloadLimit() throws IOException {
        final var requestSuccessful = transferInfo.setDownloadSpeedLimit(2);

        assertThat(requestSuccessful).isTrue();
        assertThat(transferInfo.getDownloadSpeedLimit()).isEqualTo(2);
    }

    @Test
    void settingAndGettingUploadLimit() throws IOException {
        final var requestSuccessful = transferInfo.setUploadSpeedLimit(40);

        assertThat(requestSuccessful).isTrue();
        assertThat(transferInfo.getUploadSpeedLimit()).isEqualTo(40);
    }
}
