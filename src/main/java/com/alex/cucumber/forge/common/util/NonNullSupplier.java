package com.alex.cucumber.forge.common.util;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface NonNullSupplier<T>
{
    @NotNull T get();
}
