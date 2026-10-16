package pl.olafcio.avoid_impl.net.payload;

import pl.olafcio.avoid_impl.net.payload.field_controller.NetworkController;

public record FieldInstance<Avoid, Native>(
        int order,
        NetworkController<Avoid, Native> controller
) {}
