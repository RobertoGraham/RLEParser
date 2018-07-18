package io.github.robertograham.rleparser;

public class RleLinePredicates {

    private static final String COMMENT_STARTER = "#";

    public static boolean isComment(String rleLine) {
        return rleLine.startsWith(COMMENT_STARTER);
    }
}
