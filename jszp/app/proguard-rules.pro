-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合  包明不混合大小写
-dontskipnonpubliclibraryclasses    #不去忽略非公共的库类
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontoptimize         #优化  不优化输入的类文件
-ignorewarnings        #忽略警告信息
-renamesourcefileattribute SourceFile       #保持代码行数
-keepattributes *Annotation*        #保护注解

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep class com.sgcc.pda.table.** { *; }  # 保持哪些类不被混淆
-keep class com.sgcc.pda.sdk.utils.http.SerializableOkHttpCookies { *; } # 保持哪些类不被混淆
-keep class Android.support.design.widget.TabLayout{*;} # 利用反射设置下划线长度
-keep class com.sgcc.pda.hardware.resam.beans.LoginUid{*;}

#----------------event bus---------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class com.sgcc.pda.events.** { *; }

#---------------Gson----------
-keepattributes Signature

-keep class sun.misc.Unsafe { *; }   # 保持哪些类不被混淆
-keep class com.google.gson.stream.** { *; }  # 保持哪些类不被混淆
-keep class com.google.gson.examples.android.model.** { *; }  # 保持哪些类不被混淆
-keep class com.google.gson.** { *;}  # 保持哪些类不被混淆

-keep class com.sgcc.pda.gsonbean.** { *; }  # 保持哪些类不被混淆

#----------------忽略日志输出--------------------
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

#----------------greenDao混淆--------------------
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
 }
 -keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**

#-keep class com.hzwq.mmp.greendao.** { *; }

#---------------glide---------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#-----------------butterknife--------------------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

