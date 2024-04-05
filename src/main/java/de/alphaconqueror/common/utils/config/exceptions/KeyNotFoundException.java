/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.exceptions;

import de.alphaconqueror.common.utils.config.key.ConfigKey;

public class KeyNotFoundException extends ConfigException {

    @Override
    public String getMessage(final ConfigKey<?> key) {
        return "Could not find key '" + key.path() + "' in the config.";
    }
}
