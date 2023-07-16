package io.github.kacper99.qbittorrentj;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * For these tests to run you currently need to spin up the qbittorrent container in the docker compose file.
 */
class TransferInfoTest {

    // TODO: Currently the test container keeps giving unathorized, believe its to do with the mapped port being
    // different to the exposed one.
    //    @Container
    //    GenericContainer qbittorentContainer = new GenericContainer(
    //                    DockerImageName.parse("lscr.io/linuxserver/qbittorrent:latest"))
    //            .withExposedPorts(8080)
    //            .withClasspathResourceMapping("/qbittorrent", "/config", BindMode.READ_ONLY)
    //            .withEnv(Map.of("TZ", "Etc/UTC", "WEBUI_PORT", "8080"));

    private static final QBittorrent qBittorrent = new QBittorrent("http://localhost:8080");
    final TransferInfo transferInfo = qBittorrent.transferInfo();

    @BeforeAll
    public static void beforeAll() {
        final var loggedIn = qBittorrent.login("admin", "adminadmin");
        assertThat(loggedIn).isTrue();
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
