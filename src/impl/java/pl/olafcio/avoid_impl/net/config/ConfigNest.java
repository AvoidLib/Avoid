package pl.olafcio.avoid_impl.net.config;

import pl.olafcio.avoid_common.Either;
import pl.olafcio.avoid_impl.net.config.controller.ConfigController;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.ArrayList;

public record ConfigNest(
        String key,
        ArrayList<Either<ConfigController<?, ?, ?>, ConfigNest>> controllers,
        Field field,
        VarHandle varhandle
) {}
