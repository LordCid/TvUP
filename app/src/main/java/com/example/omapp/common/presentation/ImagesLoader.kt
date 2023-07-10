package com.example.omapp.common.presentation

import android.view.View

interface ImagesLoader {

    fun <T> loadImage(image: T, view: View, params: Params? = null)

    enum class ScaleTypes {
        CENTER_CROP,
        FIT_CENTER,
    }

    sealed class TransformationType {
        object Normal : TransformationType()
        data class Rounded(val radius: Int) : TransformationType()
    }

    sealed class TransitionType {
        data class CrossFade(val duration: Int) : TransitionType()
    }

    data class Params constructor(
        val onImageLoaded: ((Boolean) -> Unit)? = null,
        val cache: Boolean = true,
        val scaleType: ScaleTypes? = null,
        val transformation: TransformationType? = null,
        val transition: TransitionType? = null,
        val previousLoad: String? = null
    )

    @Deprecated("Use only in Java files, the good files")
    class ParamsBuilder {
        var onImageLoaded: ((Boolean) -> Unit)? = null
        var cache: Boolean = true
        var scaleType: ScaleTypes? = null
        var transformation: TransformationType? = null
        var transition: TransitionType? = null
        var previousLoad: String? = null

        fun build() = Params(
            onImageLoaded,
            cache,
            scaleType,
            transformation,
            transition,
            previousLoad
        )
    }
}