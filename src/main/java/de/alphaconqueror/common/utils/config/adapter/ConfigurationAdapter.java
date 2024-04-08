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

package de.alphaconqueror.common.utils.config.adapter;

import de.alphaconqueror.common.utils.config.exceptions.KeyNotFoundException;
import java.util.List;
import java.util.Map;

public interface ConfigurationAdapter {

    void reload();

    String getString(String path, String def) throws KeyNotFoundException;

    int getInteger(String path, int def) throws KeyNotFoundException;

    long getLong(String path, long def) throws KeyNotFoundException;

    double getDouble(String path, double def) throws KeyNotFoundException;

    boolean getBoolean(String path, boolean def) throws KeyNotFoundException;

    List<String> getStringList(String path, List<String> def) throws KeyNotFoundException;

    List<Integer> getIntList(String path, List<Integer> def) throws KeyNotFoundException;

    List<Long> getLongList(String path, List<Long> def) throws KeyNotFoundException;

    List<Double> getDoubleList(String path, List<Double> def) throws KeyNotFoundException;

    Map<String, String> getStringMap(String path,
            Map<String, String> def) throws KeyNotFoundException;
}
