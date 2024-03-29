package club.bluezenith.util.render.scrollable;

@FunctionalInterface
public interface TriPredicate<A, B, C> {
    boolean test(A a, B b, C c);
}
