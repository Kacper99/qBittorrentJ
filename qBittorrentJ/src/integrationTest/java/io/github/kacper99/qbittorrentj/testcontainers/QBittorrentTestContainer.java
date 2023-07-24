package io.github.kacper99.qbittorrentj.testcontainers;

import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

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
