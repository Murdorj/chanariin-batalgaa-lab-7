package edu.sc.csce747.MeetingPlanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Small helper that lets us flip specific mutants on/off at runtime using
 * the {@code -Dmutation.id=} JVM property. Keeping the toggle centralized
 * means we can keep the default (no mutation) behavior in place while still
 * running the full suite against each mutant individually.
 */
public final class MutationConfig {

    public static final String PROPERTY = "mutation.id";
    private static final Set<String> ACTIVE_MUTATIONS = parseActiveMutations();

    private MutationConfig() {}

    public static boolean isActive(String mutationId) {
        if (mutationId == null || mutationId.isEmpty()) {
            return false;
        }
        return ACTIVE_MUTATIONS.contains(mutationId);
    }

    private static Set<String> parseActiveMutations() {
        String configured = System.getProperty(PROPERTY, "").trim();
        if (configured.isEmpty()) {
            return Collections.emptySet();
        }
        String[] values = configured.split(",");
        HashSet<String> normalized = new HashSet<>();
        Arrays.stream(values)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .forEach(normalized::add);
        return Collections.unmodifiableSet(normalized);
    }
}
