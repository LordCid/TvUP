package com.example.omapp.common.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

class GlideImplementation : ImagesLoader {

    override fun <T> loadImage(image: T, view: View, params: ImagesLoader.Params?) {
        val request = getRequest(image, view, params)
        when (view) {
            is ImageView -> request.into(view)
            else -> request.into(getTarget(view))
        }
    }

    private fun getTarget(view: View): SimpleTarget<Bitmap> {
        return object: SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                view.background = BitmapDrawable(view.resources, resource)
            }
        }
    }

    private fun <T> getRequest(
        image: T?, view: View, params: ImagesLoader.Params?
    ): RequestBuilder<Bitmap> {
        return Glide.with(view.context)
            .setUpConfig()
            .retrieveScaleType(params?.scaleType)
            .retrieveTransformation(params?.transformation)
            .load(image)
            .addListener(params?.onImageLoaded)
            .addThumbnail(view, params)
            .addTransition(params?.transition)
            .apply {
                if (params?.cache?.not() == true) {
                    dontStoreCache()
                }
            }
    }

    private fun RequestManager.setUpConfig(): RequestBuilder<Bitmap> {
        return asBitmap()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    }

    private fun <ModelType> RequestBuilder<ModelType>.dontStoreCache(): RequestBuilder<ModelType> {
        return diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
    }

    private fun <ModelType> RequestBuilder<ModelType>.retrieveScaleType(
        scales: ImagesLoader.ScaleTypes?
    ): RequestBuilder<ModelType> {
        return when (scales) {
            ImagesLoader.ScaleTypes.CENTER_CROP -> centerCrop()
            ImagesLoader.ScaleTypes.FIT_CENTER -> fitCenter()
            else -> this
        }
    }

    private fun <ModelType> RequestBuilder<ModelType>.retrieveTransformation(
        transformation: ImagesLoader.TransformationType?
    ): RequestBuilder<ModelType> {
        return when (transformation) {
            is ImagesLoader.TransformationType.Rounded ->
                transform(RoundedCorners(transformation.radius))
            else -> this
        }
    }

    private fun <TranscodeType> RequestBuilder<TranscodeType>.addListener(
        onImageLoaded: ((Boolean) -> Unit)?
    ): RequestBuilder<TranscodeType> {
        if (onImageLoaded == null) return this
        return this.listener(
            object : RequestListener<TranscodeType> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<TranscodeType>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageLoaded(false)
                    return false
                }

                override fun onResourceReady(
                    resource: TranscodeType,
                    model: Any?,
                    target: Target<TranscodeType>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageLoaded(true)
                    return false
                }
            }
        )
    }

    private fun RequestBuilder<Bitmap>.addThumbnail(
        view: View, params: ImagesLoader.Params?
    ): RequestBuilder<Bitmap> {
        if (params?.previousLoad == null) return this
        return this.thumbnail(
            getRequest(
                params.previousLoad,
                view,
                params.copy(onImageLoaded = null, previousLoad = null, transition = null)
            )
        )
    }

    private fun RequestBuilder<Bitmap>.addTransition(
        transition: ImagesLoader.TransitionType?
    ): RequestBuilder<Bitmap> {
        return when (transition) {
            is ImagesLoader.TransitionType.CrossFade -> transition(BitmapTransitionOptions.withCrossFade(transition.duration))
            else -> this
        }
    }
}
