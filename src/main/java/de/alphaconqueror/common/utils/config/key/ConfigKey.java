/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.key;

import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import de.alphaconqueror.common.utils.config.exceptions.KeyNotFoundException;
import java.util.Collection;

/**
 * Represents a key in the configuration.
 *
 * @param <T> the value type
 */
public interface ConfigKey<T> {

    /**
     * Gets the config key factory.
     *
     * @return the config key factory
     */
    ConfigKeyFactory<T> factory();

    /**
     * Gets the path.
     *
     * @return the path
     */
    String path();

    /**
     * Gets the default value.
     *
     * @return the default value
     */
    T def();

    /**
     * Gets the position of this key within the keys enum.
     *
     * @return the position
     */
    int ordinal();

    /**
     * Gets if the config key can be reloaded.
     *
     * @return if the key can be reloaded
     */
    boolean reloadable();

    /**
     * Gets the possible values.
     *
     * @return the possible values
     */
    Collection<T> possibilities();

    /**
     * Resolves and returns the value mapped to this key using the given config instance.
     *
     * @param adapter the config adapter instance
     * @return the value mapped to this key
     * @throws KeyNotFoundException if key cannot be found
     */
    T get(ConfigurationAdapter adapter) throws KeyNotFoundException;
}
