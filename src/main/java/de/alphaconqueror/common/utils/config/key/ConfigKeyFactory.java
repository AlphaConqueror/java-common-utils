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

package de.alphaconqueror.common.utils.config.key;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ConfigKeyFactory<T> {

    ConfigKeyFactory<Boolean> BOOLEAN = ConfigurationAdapter::getBoolean;
    ConfigKeyFactory<String> STRING = ConfigurationAdapter::getString;
    ConfigKeyFactory<Integer> INT = ConfigurationAdapter::getInteger;
    ConfigKeyFactory<Long> LONG = ConfigurationAdapter::getLong;
    ConfigKeyFactory<Double> DOUBLE = ConfigurationAdapter::getDouble;
    ConfigKeyFactory<String> LOWERCASE_STRING = (adapter, path, def) -> adapter.getString(path, def)
            .toLowerCase(Locale.ROOT);
    ConfigKeyFactory<List<String>> STRING_LIST = (config, path, def) -> ImmutableList.copyOf(
            config.getStringList(path, ImmutableList.of()));

    ConfigKeyFactory<List<Integer>> INT_LIST = (config, path, def) -> ImmutableList.copyOf(
            config.getIntList(path, ImmutableList.of()));

    ConfigKeyFactory<List<Long>> LONG_LIST = (config, path, def) -> ImmutableList.copyOf(
            config.getLongList(path, ImmutableList.of()));

    ConfigKeyFactory<List<Double>> DOUBLE_LIST = (config, path, def) -> ImmutableList.copyOf(
            config.getDoubleList(path, ImmutableList.of()));
    ConfigKeyFactory<Map<String, String>> STRING_MAP = (config, path, def) -> ImmutableMap.copyOf(
            config.getStringMap(path, ImmutableMap.of()));

    static <T> SimpleConfigKey<T> key(final ConfigKeyFactory<T> factory, final String path,
            final T def) {
        return new SimpleConfigKey<>(factory, path, def);
    }

    static <T extends Comparable<T>> ComparableConfigKey<T> comparableKey(
            final ConfigKeyFactory<T> factory, final String path, final T def) {
        return new ComparableConfigKey<>(factory, path, def);
    }

    static <T> SimpleConfigKey<T> notReloadable(final SimpleConfigKey<T> key) {
        key.setReloadable(false);
        return key;
    }

    static <T extends Comparable<T>> ComparableConfigKey<T> range(final ComparableConfigKey<T> key,
            final T min, final T max) {
        key.setRange(min, max);
        return key;
    }

    @SafeVarargs
    static <T> SimpleConfigKey<T> limit(final SimpleConfigKey<T> key, final T... possibilities) {
        key.setPossibilities(Sets.newHashSet(possibilities));
        return key;
    }

    static ComparableConfigKey<Boolean> booleanKey(final String path, final boolean def) {
        return comparableKey(BOOLEAN, path, def);
    }

    static ComparableConfigKey<String> stringKey(final String path, final String def) {
        return comparableKey(STRING, path, def);
    }

    static ComparableConfigKey<String> lowercaseStringKey(final String path, final String def) {
        return comparableKey(LOWERCASE_STRING, path, def);
    }

    static ComparableConfigKey<Integer> intKey(final String path, final int def) {
        return comparableKey(INT, path, def);
    }

    static ComparableConfigKey<Long> longKey(final String path, final long def) {
        return comparableKey(LONG, path, def);
    }

    static ComparableConfigKey<Double> doubleKey(final String path, final double def) {
        return comparableKey(DOUBLE, path, def);
    }

    static SimpleConfigKey<List<String>> stringListKey(final String path, final String... def) {
        return key(STRING_LIST, path, Arrays.asList(def));
    }

    static SimpleConfigKey<List<Integer>> intListKey(final String path, final Integer... def) {
        return key(INT_LIST, path, Arrays.asList(def));
    }

    static SimpleConfigKey<List<Long>> longListKey(final String path, final Long... def) {
        return key(LONG_LIST, path, Arrays.asList(def));
    }

    static SimpleConfigKey<List<Double>> doubleListKey(final String path, final Double... def) {
        return key(DOUBLE_LIST, path, Arrays.asList(def));
    }

    static SimpleConfigKey<Map<String, String>> mapKey(final String path) {
        return key(STRING_MAP, path, null);
    }

    /**
     * Extracts the value from the config.
     *
     * @param config the config
     * @param path   the path where the value is
     * @param def    the default value
     * @return the value
     */
    T getValue(ConfigurationAdapter config, String path, T def);
}
