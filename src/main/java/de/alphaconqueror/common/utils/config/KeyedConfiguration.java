/*
 * MIT License
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.alphaconqueror.common.utils.config;

import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import de.alphaconqueror.common.utils.config.exceptions.ConfigException;
import de.alphaconqueror.common.utils.config.key.ConfigKey;
import de.alphaconqueror.common.utils.config.key.SimpleConfigKey;
import de.alphaconqueror.common.utils.logging.Logger;
import de.alphaconqueror.common.utils.util.ImmutableCollectors;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class KeyedConfiguration {

    private final Logger logger;
    private final ConfigurationAdapter adapter;
    private final List<? extends ConfigKey<?>> keys;
    private final ValuesMap values;

    public KeyedConfiguration(final Logger logger, final ConfigurationAdapter adapter,
            final List<? extends ConfigKey<?>> keys) {
        this.logger = logger;
        this.adapter = adapter;
        this.keys = keys;
        this.values = new ValuesMap(keys.size());
    }

    /**
     * Initialises the given pseudo-enum keys class.
     *
     * @param keysClass the keys class
     * @return the list of keys defined by the class with their ordinal values set
     */
    public static List<SimpleConfigKey<?>> initialise(final Class<?> keysClass) {
        // get a list of all keys
        final List<SimpleConfigKey<?>> keys = Arrays.stream(keysClass.getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> ConfigKey.class.equals(f.getType()))
                .map(f -> {
                    try {
                        return (SimpleConfigKey<?>) f.get(null);
                    } catch (final IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(ImmutableCollectors.toList());

        // set ordinal values
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).setOrdinal(i);
        }

        return keys;
    }

    /**
     * Gets the value of a given context key.
     *
     * @param key the key
     * @param <T> the key return type
     * @return the value mapped to the given key. May be null.
     */
    public <T> T get(final ConfigKey<T> key) {
        return this.values.get(key);
    }

    /**
     * Reloads the configuration.
     */
    public void reload() {
        this.adapter.reload();
        this.load(false);
    }

    protected void init() {
        this.load(true);
    }

    protected void load(final boolean initial) {
        for (final ConfigKey<?> key : this.keys) {
            if (initial || key.reloadable()) {
                Object value = null;

                try {
                    value = key.get(this.adapter);
                } catch (final ConfigException e) {
                    if (key instanceof SimpleConfigKey) {
                        final SimpleConfigKey<?> simpleKey = (SimpleConfigKey<?>) key;

                        value = e.getValue(simpleKey);
                        this.logger.warn(e.getMessage(simpleKey)
                                + " The value of '{}' will be used instead.", value);
                    } else {
                        this.logger.severe("Caught exception during loading of config.", e);
                    }
                }

                this.values.put(key, value);
            }
        }
    }

    public static class ValuesMap {

        private final Object[] values;

        public ValuesMap(final int size) {
            this.values = new Object[size];
        }

        @SuppressWarnings("unchecked")
        public <T> T get(final ConfigKey<T> key) {
            return (T) this.values[key.ordinal()];
        }

        public void put(final ConfigKey<?> key, final Object value) {
            this.values[key.ordinal()] = value;
        }
    }
}
