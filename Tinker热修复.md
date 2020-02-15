**手机**

ClassLoader: 子类BaseDexClassLoader
TODO 
兼容Androoid不同版本设计到反射部分的代码
more detail see the
https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java



**源码PathList:**

dexElements[classes,dex]



apk安装后，赋值一份apk到私有目录 data/app/xx--包名/base.apk



> PathClassLoader系统类文件下载



- Tinker的热修复为什么要冷启动才能生效?

  因为类是无法被卸载的.只有App冷启动的时候才会作走ClassLoader中的findLoadedClass的代码.

  ![1581773368134](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581773368134.png)![1581773369175](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581773369175.png)



核心思路

1. 创建BaseDexClassLoader子类DexClassLoader
2. 加载修复好的classes2.dex(服务器下载)
3. 将自己的dex和系统的dexElements进行合并
4. 反射技术，赋值给系统的pathList

Elements数组:



![1581773977267](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581773977267.png)

双亲委派的代码.



Elements中存放的是class.dex

getDir 创建的是私有目录

-1 结尾如果读到最后一个没有数据的时候就数组越界异常，-1 就是不在数组

的索引值的范围内，所以就将-1作为read完成后的返回值.



###  Tinker热修复.xmind

- 怎么打热修复的Dex文件.

![1581778181065](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581778181065.png)

 多个.dex文件.找到要修复的.dex文件





![关于dexElements数组的找寻](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581782077277.png)





> 源码查看在线网站 <https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java>

- dex文件存放在"odex"目录下面.

- 点击fix相当于从网络下载patch包，然后合并到宿主的apk.



![1581783215338](C:\Users\MyPC\AppData\Roaming\Typora\typora-user-images\1581783215338.png)

- 关于DexClassLoader中所需要的几个参数，dexPath用dex.getAbsPath即可.


