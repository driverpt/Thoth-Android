-ignorewarnings
-dontoptimize
-dontobfuscate
-dontskipnonpubliclibraryclasses

-ignorewarnings

-renamesourcefileattribute SourceFile

-keepattributes SourceFile,LineNumberTable,*Annotation*

-printmapping map.txt
-printseeds seed.txt

-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View { public <init>(android.content.Context); public <init>(android.content.Context, android.util.AttributeSet); public <init>(android.content.Context, android.util.AttributeSet, int); public void set*(...); }

-keepclassmembers class * extends android.app.Activity { public void *(android.view.View); }
-keepclassmembers class android.support.v4.app.Fragment { *** getActivity(); public *** onCreate(); public *** onCreateOptionsMenu(...); }