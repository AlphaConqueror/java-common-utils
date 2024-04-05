/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.exceptions;

import de.alphaconqueror.common.utils.config.key.ConfigKey;

public class NotInRangeException extends ConfigException {

    private final Object min;
    private final Object max;
    private final Object value;

    public NotInRangeException(final Object min, final Object max, final Object value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public String getMessage(final ConfigKey<?> key) {
        return "Value '" + this.getValue(key).toString() + "' not in range [" + this.min.toString()
                + ',' + this.max.toString() + "].";
    }

    @Override
    public Object getValue(final ConfigKey<?> key) {
        return this.value;
    }
}
