/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
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

    Map<String, String> getStringMap(String path,
            Map<String, String> def) throws KeyNotFoundException;
}
