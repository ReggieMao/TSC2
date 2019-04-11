-keep public class com.google.android.gms.* { public *; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keep class com.ebei.** { *; }
-keep class com.google.android.exoplayer2.** { *; }
-keep class com.coremedia.** { *; }
-keep class com.googlecode.mp4parser.** { *; }
-dontwarn com.coremedia.**
-dontwarn com.ebei.**
-dontwarn com.google.android.exoplayer2.**
-dontwarn com.google.android.gms.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.primitives.**
-dontwarn com.googlecode.mp4parser.**

-dontoptimize
-dontobfuscate

-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn lombok.**
-dontwarn butterknife.**
-dontwarn com.tbruyelle.rxpermissions2.**
-dontwarn cn.bingoogolapple.qrcode.zxing.**
-dontwarn androidx.media.**
-dontwarn android.support.v7.widget.**
-dontwarn com.fota.option.**

-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class [com.ebei.tsc].R$*{
    public static final int *;
}