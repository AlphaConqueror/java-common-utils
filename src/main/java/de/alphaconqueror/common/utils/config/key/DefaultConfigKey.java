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

import de.alphaconqueror.common.utils.config.adapter.ConfigurationAdapter;
import java.util.function.Function;

public class DefaultConfigKey<T> implements ConfigKey<T> {

    private final Function<? super ConfigurationAdapter, ? extends T> function;

    private int ordinal = -1;
    private boolean reloadable = true;

    DefaultConfigKey(final Function<? super ConfigurationAdapter, ? extends T> function) {
        this.function = function;
    }

    @Override
    public T get(final ConfigurationAdapter adapter) {
        return this.function.apply(adapter);
    }

    @Override
    public int ordinal() {
        return this.ordinal;
    }

    @Override
    public boolean reloadable() {
        return this.reloadable;
    }

    @Override
    public void setOrdinal(final int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public void setReloadable(final boolean reloadable) {
        this.reloadable = reloadable;
    }
}
