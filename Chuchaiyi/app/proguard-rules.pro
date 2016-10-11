# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\sortware\android\adt-bundle-windows-x86-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
  #指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#优化  不优化输入的类文件
-dontoptimize

#预校验
-dontpreverify
-dontshrink
-dontnote
-renamesourcefileattribute ProGuard
-keepattributes SourceFile,LineNumberTable
-dontwarn java.nio.file.**

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
@butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
@butterknife.* <methods>;
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-keepclassmembers class ** {
    public void onEvent*(**);
}
# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep public class com.ccy.chuchaiyi.R$*{
public static final int *;
}

-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }

-keepattributes Signature

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn android.webkit.WebView
-dontwarn android.webkit.WebViewClient
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#okhttputils
-dontwarn com.lzy.okhttputils.**
-keep class com.lzy.okhttputils.**{*;}

#okhttputils
-dontwarn com.lzy.okhttpserver.**
-keep class com.lzy.okhttpserver.**{*;}


#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}

 #混淆前后的映射
-printmapping mapping.txt

-dontwarn android.support.**

-keep public class * extends android.support.v4.app.Fragment

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
        native <methods>;
}

-keep class de.greenrobot.dao.** {*;}
#保持greenDao的方法不被混淆
#用来保持生成的表名不被混淆
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
  public static java.lang.String TABLENAME; }
-keep class **$Properties

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}