package io.github.kacper99.qbittorrentj.testcontainers;

import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Spins up a test container containing LinuxServer.io qBittorrent container.
 * <p>
 * As the test container uses a random port on the host machine, a config is provided in integration test resources
 * which disables host header validation which is then mounted to the test container
 *
 * @see <a href="https://java.testcontainers.org/">Testcontainers</a>
 * @see <a href="https://hub.docker.com/r/linuxserver/qbittorrent">qBittorrent container</a>
 */
public class QBittorrentTestContainer extends GenericContainer<QBittorrentTestContainer> {

    private static final DockerImageName IMAGE_NAME = DockerImageName.parse("lscr.io/linuxserver/qbittorrent:latest");

    public QBittorrentTestContainer() {
        super(IMAGE_NAME);
    }

    @Override
    public void start() {
        this.withClasspathResourceMapping(
                "/testcontainers/qBittorrent/qBittorrent.conf",
                "/config/qBittorrent/qBittorrent.conf",
                BindMode.READ_ONLY);
        this.withExposedPorts(8080);
        super.start();
    }

    public String webUiUrl() {
        return "http://%s:%s".formatted(getHost(), getMappedPort(8080));
    }
}
