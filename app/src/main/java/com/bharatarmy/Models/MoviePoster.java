package com.bharatarmy.Models;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class MoviePoster {
    @NotNull
    private String name;
    @NotNull
    private String imageUrl;
    private int width;
    private int height;

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.name = var1;
    }

    @NotNull
    public final String getImageUrl() {
        return this.imageUrl;
    }

    public final void setImageUrl(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.imageUrl = var1;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setWidth(int var1) {
        this.width = var1;
    }

    public final int getHeight() {
        return this.height;
    }

    public final void setHeight(int var1) {
        this.height = var1;
    }

    public MoviePoster(@NotNull String name, @NotNull String imageUrl, int width, int height) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(imageUrl, "imageUrl");
        this.name = name;
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
    }
}
