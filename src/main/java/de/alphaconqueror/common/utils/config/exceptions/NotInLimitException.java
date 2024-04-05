/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.exceptions;

import de.alphaconqueror.common.utils.config.key.ConfigKey;

public class NotInLimitException extends ConfigException {

    private final Object value;

    public NotInLimitException(final Object value) {this.value = value;}

    @Override
    public String getMessage(final ConfigKey<?> key) {
        return "Value '" + this.getValue(key).toString() + "' is not in " + key.possibilities()
                .toString() + '.';
    }

    @Override
    public Object getValue(final ConfigKey<?> key) {
        return this.value;
    }
}
