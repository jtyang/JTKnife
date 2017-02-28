package com.android.jtknife.core.common.imageloader.config;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.jtknife.core.utils.DeviceUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import okhttp3.OkHttpClient;

/**
 * Fresco 图片加载配置
 * AUTHOR: yangjiantong
 * DATE: 2017/2/28
 */
public class ImagePipelineConfigFactory {

    private static final int RUNTIME_MAX_MEMORY = ((int) Runtime.getRuntime().maxMemory());
    public static final int RUNTIME_MAX_MEMORY_8 = (RUNTIME_MAX_MEMORY / 8);
    public static final int RUNTIME_MAX_MEMORY_16 = (RUNTIME_MAX_MEMORY / 16);

    private static ImagePipelineConfig imagePipelineConfig;

    public static ImagePipelineConfig getImagePipelineConfig(Context context, MemoryTrimmableRegistry memoryTrimmableRegistry) {
        if (imagePipelineConfig == null) {
            ImagePipelineConfig.Builder builder = OkHttpImagePipelineConfigFactory.newBuilder(context, new OkHttpClient());
            try {
                initPipeline(builder, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            enableDownSample(builder);
            if (isLowMemory(context)) {
                builder.setBitmapsConfig(Bitmap.Config.ARGB_4444);
            }
            builder.setMemoryTrimmableRegistry(memoryTrimmableRegistry);
            imagePipelineConfig = builder.build();
        }
        return imagePipelineConfig;
    }

    private static void initPipeline(ImagePipelineConfig.Builder builder, Context context) {
        builder.setBitmapMemoryCacheParamsSupplier(
                new BitmapMemoryCacheParamsSupplier(new MemoryCacheParams(RUNTIME_MAX_MEMORY_8, Integer.MAX_VALUE, RUNTIME_MAX_MEMORY_16, Integer.MAX_VALUE, Integer.MAX_VALUE)))
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(context.getExternalCacheDir())
                        .setBaseDirectoryName("fresco_cache")
                        .setMaxCacheSize(50 * ByteConstants.MB)
                        .build())
                .setSmallImageDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(context.getExternalCacheDir())
                        .setBaseDirectoryName("fresco_cache_small")
                        .setMaxCacheSize(10 * ByteConstants.MB)
                        .build())
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig());
    }

    /**
     * Downsample：向下采样,它处理图片的速度比常规的裁剪scaling更快
     * 且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
     * @param builder
     */
    private static void enableDownSample(ImagePipelineConfig.Builder builder) {
        builder.setDownsampleEnabled(true);
//        builder.experiment().setWebpSupportEnabled(true);
    }

    private static class BitmapMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
        private MemoryCacheParams memoryCacheParams;

        public BitmapMemoryCacheParamsSupplier(MemoryCacheParams memoryCacheParams) {
            this.memoryCacheParams = memoryCacheParams;
        }

        @Override
        public MemoryCacheParams get() {
            return this.memoryCacheParams;
        }
    }


    private static boolean hasGetMemory = false;
    private static boolean isLowMemory = false;

    private static boolean isLowMemory(Context context) {
        if (!hasGetMemory) {
            long memory = DeviceUtils.getMemoryTotal(context);
            if (memory <= 0 || memory > 1024 * ByteConstants.MB) {
                isLowMemory = false;
            } else {
                isLowMemory = true;
            }
            hasGetMemory = false;
        }
        return isLowMemory;
    }

}
