package com.eland.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.io.File;
import java.io.IOException;

/**
 * google的Guava
 */

public class Guava {
    public  String getMD5Guava(File file) throws IOException {

        HashCode md5Hash = com.google.common.io.Files.hash(file, Hashing.md5());
        return md5Hash.toString();
    }

    public  String getSha1Guava(File file) throws IOException {

        HashCode sha1Hash = com.google.common.io.Files.hash(file, Hashing.sha1());
        return sha1Hash.toString();
    }

    public  String getSha256Guava(File file) throws IOException {

        HashCode sha256Hash = com.google.common.io.Files.hash(file, Hashing.sha256());
        return sha256Hash.toString();
    }

    public  String getCrc32Guava(File file) throws IOException {

        HashCode Crc32Hash = com.google.common.io.Files.hash(file, Hashing.crc32());
        return Crc32Hash.toString();
    }
}
