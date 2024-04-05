/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.key;

import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import de.alphaconqueror.common.utils.config.exceptions.NotInLimitException;
import java.util.Collection;
import java.util.Set;

/**
 * Basic {@link ConfigKey} implementation.
 *
 * @param <T> the value type
 */
public class SimpleConfigKey<T> implements ConfigKey<T> {

    private final ConfigKeyFactory<T> factory;
    private final String path;
    private final T def;
    private int ordinal = -1;
    private boolean reloadable = true;
    private Set<T> possibilities;

    SimpleConfigKey(final ConfigKeyFactory<T> factory, final String path, final T def) {
        this.factory = factory;
        this.path = path;
        this.def = def;
    }

    @Override
    public T get(final ConfigurationAdapter adapter) {
        final T result = this.factory.getValue(adapter, this.path, this.def);

        if (this.possibilities == null || this.possibilities.contains(result)) {
            return result;
        }

        throw new NotInLimitException(result);
    }

    @Override
    public ConfigKeyFactory<T> factory() {
        return this.factory;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public T def() {
        return this.def;
    }

    @Override
    public int ordinal() {
        return this.ordinal;
    }

    @Override
    public boolean reloadable() {
        return this.reloadable;
    }

    public void setOrdinal(final int ordinal) {
        this.ordinal = ordinal;
    }

    public void setReloadable(final boolean reloadable) {
        this.reloadable = reloadable;
    }

    @Override
    public Collection<T> possibilities() {
        return this.possibilities;
    }

    public void setPossibilities(final Set<T> possibilities) {
        this.possibilities = possibilities;
    }
}
