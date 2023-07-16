package io.github.kacper99.qbittorrentj.okhttp;

import okhttp3.logging.HttpLoggingInterceptor;

public class Logger {

    public static HttpLoggingInterceptor logger() {
        final var logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logger;
    }
}
