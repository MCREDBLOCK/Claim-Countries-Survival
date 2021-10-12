package com.redblock6.survival.mccore.utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class VariousUtils {
    public static boolean isDirEmpty(final Path directory) throws IOException {
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

    public static String reverseFormattedName(String s) {
        String translated = s.replaceAll(" ", "_").substring(4).toLowerCase();
        if (translated.contains(" ")) {
            String splits[] =  translated.split(" ");
            ArrayList<String> stringArrayList = new ArrayList<>();
            String finish = "";
            for (String need : splits) {
                capitalize(need);
                stringArrayList.add(need);
                finish = need + finish;
            }
            return finish;
        } else {
            return capitalize(translated);
        }
    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
