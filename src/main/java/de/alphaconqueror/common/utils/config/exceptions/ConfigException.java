/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.exceptions;

import de.alphaconqueror.common.utils.config.key.ConfigKey;

public abstract class ConfigException extends RuntimeException {

    public abstract String getMessage(ConfigKey<?> key);

    public Object getValue(final ConfigKey<?> key) {
        return key.def();
    }
}
