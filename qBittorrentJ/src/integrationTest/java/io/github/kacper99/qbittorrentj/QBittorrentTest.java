package io.github.kacper99.qbittorrentj;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * For these tests to run you currently need to spin up the qbittorrent container in the docker compose file.
 */
class QBittorrentTest {

    // TODO: Currently the test container keeps giving unathorized, believe its to do with the mapped port being
    // different to the exposed one.
    //    @Container
    //    GenericContainer qbittorentContainer = new GenericContainer(
    //                    DockerImageName.parse("lscr.io/linuxserver/qbittorrent:latest"))
    //            .withExposedPorts(8080)
    //            .withClasspathResourceMapping("/qbittorrent", "/config", BindMode.READ_ONLY)
    //            .withEnv(Map.of("TZ", "Etc/UTC", "WEBUI_PORT", "8080"));

    @Test
    void foo() throws IOException {
        final var qBittorrentWebUiUrl = "http://localhost:8080";
        final var qBittorrent = new QBittorrent(qBittorrentWebUiUrl);

        final var loggedIn = qBittorrent.login("admin", "adminadmin");

        assertThat(loggedIn).isTrue();
        assertThat(qBittorrent.transferInfo().getDownloadSpeedLimit()).isNotNegative();
        assertThat(qBittorrent.transferInfo().getUploadSpeedLimit()).isNotNegative();
    }
}
