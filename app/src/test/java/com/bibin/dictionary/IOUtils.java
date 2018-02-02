package com.bibin.dictionary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.BufferedSource;
import okio.Okio;

public class IOUtils {

    public static String readStringFromResourcePath(String path) throws IOException {
        InputStream in = IOUtils.class.getClassLoader().getResourceAsStream(path);
        BufferedSource bufferedSource = Okio.buffer(Okio.source(in));
        return bufferedSource.readString(Charset.defaultCharset());
    }
}
