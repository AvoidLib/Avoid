package pl.olafcio.avoid_platform.applevel;

import net.minecraftforge.fml.ModLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import pl.olafcio.reflectstorm.ReflectStorm;

public enum ActualInjection {
    ;

    public static void withdepends(Runnable inject) {
        var logger = ReflectStorm.builder()
                                 .klass(ModLoader.class)
                                 .field("LOGGER", org.apache.logging.log4j.Logger.class)
                                 .<Logger>get(null);

        // ((Logger) ModLoader.LOGGER)==org.apache.logging.log4j.core.Logger
        // ((Logger) ModLoader.LOGGER).privateConfig==Logger$PrivateConfig
        // ((Logger) ModLoader.LOGGER).privateConfig.loggerConfig==LoggerConfig

        logger.get().addFilter(new AbstractFilter() {
            @Override
            public Result filter(LogEvent event) {
                if (event.getLevel() == Level.FATAL) {
                    try                 { inject.run(); }
                    catch (Exception e) {
                        //Ultrafail
                    }
                }

                return super.filter(event);
            }
        });
    }
}
