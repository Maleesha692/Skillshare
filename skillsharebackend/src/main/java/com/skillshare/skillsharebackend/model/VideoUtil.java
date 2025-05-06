package com.skillshare.skillsharebackend.model;

import com.coremedia.iso.IsoFile;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoUtil {

    public static boolean isDurationValid(InputStream videoStream, long maxSeconds) throws IOException {
        // Save input stream to temp file (because IsoFile needs a file)
        File tempFile = File.createTempFile("video", ".mp4");
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = videoStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        try (IsoFile isoFile = new IsoFile(tempFile.getAbsolutePath())) {
            double lengthInSeconds = (double) isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                    isoFile.getMovieBox().getMovieHeaderBox().getTimescale();

            return lengthInSeconds <= maxSeconds;
        } finally {
            tempFile.delete(); // Clean up temp file
        }
    }
}
