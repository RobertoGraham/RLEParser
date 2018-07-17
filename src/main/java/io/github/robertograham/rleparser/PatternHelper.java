package io.github.robertograham.rleparser;

import org.intellij.lang.annotations.Language;

import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatternHelper {

    public static List<String> getMatchList(String text, @Language("RegExp") String regex) {
        Scanner scanner = new Scanner(text);
        Pattern pattern = Pattern.compile(regex);

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<MatchResult>(1000, Spliterator.ORDERED | Spliterator.NONNULL) {

            public boolean tryAdvance(Consumer<? super MatchResult> action) {
                if (scanner.findWithinHorizon(pattern, 0) != null) {
                    action.accept(scanner.match());
                    return true;
                } else
                    return false;
            }
        }, false)
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
