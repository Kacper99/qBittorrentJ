package io.github.kacper99.qbittorrentj.okhttp;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

public class SimpleCookieJar implements CookieJar {
    private static SimpleCookieJar simpleCookieJarInstance;

    public static SimpleCookieJar getInstance() {
        if (simpleCookieJarInstance == null) {
            simpleCookieJarInstance = new SimpleCookieJar();
        }
        return simpleCookieJarInstance;
    }

    final List<Cookie> cookies;

    private SimpleCookieJar() {
        this.cookies = new ArrayList<>();
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        cookies.addAll(list);
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookies;
    }
}
