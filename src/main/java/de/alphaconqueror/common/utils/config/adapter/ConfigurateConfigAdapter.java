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

import com.google.common.base.Splitter;
import de.alphaconqueror.common.utils.config.exceptions.KeyNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public abstract class ConfigurateConfigAdapter implements ConfigurationAdapter {

    private final Path path;
    private ConfigurationNode root;

    public ConfigurateConfigAdapter(final Path path) {
        this.path = path;
        this.reload();
    }

    protected abstract ConfigurationLoader<? extends ConfigurationNode> createLoader(Path path);

    @Override
    public void reload() {
        final ConfigurationLoader<? extends ConfigurationNode> loader = this.createLoader(
                this.path);

        try {
            this.root = loader.load();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(final String path, final String def) throws KeyNotFoundException {
        return this.resolvePath(path, ConfigurationNode::isVirtual).getString(def);
    }

    @Override
    public int getInteger(final String path, final int def) throws KeyNotFoundException {
        return this.resolvePath(path, ConfigurationNode::isVirtual).getInt(def);
    }

    @Override
    public long getLong(final String path, final long def) throws KeyNotFoundException {
        return this.resolvePath(path, ConfigurationNode::isVirtual).getLong(def);
    }

    @Override
    public double getDouble(final String path, final double def) throws KeyNotFoundException {
        return this.resolvePath(path, ConfigurationNode::isVirtual).getDouble(def);
    }

    @Override
    public boolean getBoolean(final String path, final boolean def) throws KeyNotFoundException {
        return this.resolvePath(path, ConfigurationNode::isVirtual).getBoolean(def);
    }

    @Override
    public List<String> getStringList(final String path,
            final List<String> def) throws KeyNotFoundException {
        return this.resolvePath(path, node -> node.isVirtual() || !node.isList())
                .getList(Object::toString);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getStringMap(final String path,
            final Map<String, String> def) throws KeyNotFoundException {
        final Map<String, Object> m = (Map<String, Object>) this.resolvePath(path,
                node -> node.isVirtual() || !node.isMap()).getValue(Collections.emptyMap());

        return m.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().toString()));
    }

    private ConfigurationNode resolvePath(final String path,
            final Predicate<ConfigurationNode> predicate) {
        if (this.root == null) {
            throw new RuntimeException("Config is not loaded.");
        }

        final ConfigurationNode node = this.root.getNode(
                Splitter.on('.').splitToList(path).toArray());

        if (predicate.test(node)) {
            throw new KeyNotFoundException();
        }

        return node;
    }
}
