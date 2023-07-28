package spring.angular.social.util;

import java.nio.file.Paths;

public class SystemPathUtil {

    public static String getCurrentSystemPath() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
