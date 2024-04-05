/*
 * This file is part of 'Java Common Utils'.
 *
 * Copyright (c) 2024 Marc Beckhaeuser (AlphaConqueror) <marcbeckhaeuser@gmail.com>
 *
 * ALL RIGHTS RESERVED.
 */

package de.alphaconqueror.common.utils.logging;

/**
 * Represents a logger instance.
 * <p>
 * Functions use '{}' as a placeholder for arguments.
 */
public interface Logger {

    void info(String s, Object... args);

    void warn(String s, Object... args);

    void severe(String s, Throwable t);
}
