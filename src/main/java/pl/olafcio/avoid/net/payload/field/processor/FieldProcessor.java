package pl.olafcio.avoid.net.payload.field.processor;

import org.jetbrains.annotations.ApiStatus;

public enum FieldProcessor {
    @ApiStatus.Internal
    __AUTO__,

    @Type(int.class)   VAR_INT,
    @Type(int[].class) VAR_INT_ARRAY,
    @Type(long.class)  VAR_LONG,
    @Type(int.class)   MEDIUM,
    @Type(int.class)   MEDIUM_LE,
    @Type(long.class)  UNSIGNED_INT,
    @Type(long.class)  UNSIGNED_INT_LE,
    @Type(int.class)   UNSIGNED_MEDIUM,
    @Type(int.class)   UNSIGNED_MEDIUM_LE,
    @Type(int.class)   UNSIGNED_SHORT,
    @Type(int.class)   UNSIGNED_SHORT_LE,
    @Type(short.class) UNSIGNED_BYTE
}
