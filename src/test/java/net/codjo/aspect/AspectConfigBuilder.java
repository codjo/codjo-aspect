package net.codjo.aspect;
/**
 *
 */
class AspectConfigBuilder {
    private AspectConfig config;


    AspectConfigBuilder(String aspectId, String aspectClassname) {
        config = new AspectConfig(aspectId, aspectClassname);
    }

    AspectConfigBuilder addJoinPoint(int call, String point, String argument) {
        config.addJoinPoint(new JoinPoint(call, point, argument));
        return this;
    }

    AspectConfigBuilder addJoinPoint(int call, String point, String argument, boolean isFork) {
        config.addJoinPoint(new JoinPoint(call, point, argument, isFork));
        return this;
    }

    AspectConfig get() {
        return config;
    }
}
