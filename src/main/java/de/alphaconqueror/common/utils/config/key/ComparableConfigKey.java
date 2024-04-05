/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.config.key;

import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import de.alphaconqueror.common.utils.config.exceptions.NotInRangeException;

public class ComparableConfigKey<T extends Comparable<T>> extends SimpleConfigKey<T> {

    private T min;
    private T max;

    public ComparableConfigKey(final ConfigKeyFactory<T> factory, final String path, final T def) {
        super(factory, path, def);
    }

    @Override
    public T get(final ConfigurationAdapter adapter) {
        final T value = super.get(adapter);

        if (this.min != null && this.min.compareTo(value) >= 0) {
            throw new NotInRangeException(this.min, this.max, this.min);
        } else if (this.max != null && this.max.compareTo(value) <= 0) {
            throw new NotInRangeException(this.min, this.max, this.max);
        }

        return value;
    }

    public void setRange(final T min, final T max) {
        this.min = min;
        this.max = max;
    }
}
