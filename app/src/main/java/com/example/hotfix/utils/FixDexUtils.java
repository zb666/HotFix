package com.example.hotfix.utils;

import android.content.Context;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @ClassName: FixxDexUtils
 * @Description:
 * @CreateDate: 2020/2/15
 */
public class FixDexUtils {

    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    public static void loadFixedDex(Context context) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        if (context == null)
            return;
        File fileDir = context.getDir("odex",Context.MODE_PRIVATE);
        File[] files = fileDir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".dex")&&!file.getName().endsWith("classes.dex")){
                loadedDex.add(file);
            }
        }
        //创建类加载器
        createDexClassLoader(context,fileDir);
    }

    /**
     * 创建File将.dex文件放在目录下
     * @param context
     * @param fileDir
     */
    private static void createDexClassLoader(Context context, File fileDir) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        String absolutePath = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        File fOpt = new File(absolutePath);
        if (!fOpt.exists()) {
            fOpt.mkdirs();
        }
        DexClassLoader dexClassLoader;
        //为每个DexClassLoader创建类加载器
        for (File dex : loadedDex) {
            dexClassLoader = new DexClassLoader(dex.getAbsolutePath(),absolutePath,
                    null,
                    context.getClassLoader());
            hotFix(dexClassLoader,context);
        }
    }

    private static void hotFix(DexClassLoader dexClassLoader, Context context) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        try {
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            //获取补丁包的DexElements
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(dexClassLoader));
            //获取系统的DexElements数组对象
            //在BaseDexClassLoader->DexPathList->dexElements下
            Object sysDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));
            //合并dexElements
            Object dexElements = ArrayUtils.combineArray(myDexElements, sysDexElements);
            //hook系统的PathList完成反射注入
            //new array->assignment value to -> system's classloader
            //获取系统的ClassLoader
            Object sysPathList = ReflectUtils.getPathList(pathClassLoader);
            ReflectUtils.setField(sysPathList, sysPathList.getClass(), dexElements);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
