package net.codjo.aspect;
/**
 *
 */
public interface AspectFilter {
    public boolean accept(JoinPoint joinPoint, String aspectId);

    AspectFilter ALL = new AspectFilter() {
        public boolean accept(JoinPoint joinPoint, String aspectId) {
            return true;
        }
    };
    AspectFilter NONE = new AspectFilter() {
        public boolean accept(JoinPoint joinPoint, String aspectId) {
            return false;
        }
    };
}
