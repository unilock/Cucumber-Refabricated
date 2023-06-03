package com.alex.cucumber.forge.common.util;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface NonNullPredicate<T>
{
    boolean test(@NotNull T t);
}
