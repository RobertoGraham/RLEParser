package io.github.robertograham.rleparser.helper;

public class RleFileHelper {

    private static final String COMMENT_STARTER = "#";

    public static boolean isComment(String rleLine) {
        return rleLine.startsWith(COMMENT_STARTER);
    }
}
