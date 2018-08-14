package com.sgcc.pda.sdk.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Created by TL on 17/7/31
 */
public class FileUtils {

    public static final String PATH_IMAGE_OBJECT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sitework/object";
    public static final String TAG = "FileUtils";
    public static final String voiceFile = Environment.getExternalStorageDirectory() + File.separator + "NaviOne.SDK.pda" + File.separator + "commdata.ndz";
    public static final String voiceFileName = "commdata.ndz";
    public static final String assertFileName = "commdata.ndz";
    public static final String assertName = "assets/";
    public static final String mapDirPath = Environment.getExternalStorageDirectory() + File.separator + "NaviOne.SDK.pda";

    /**
     * @param fromPath 被复制的文件路径
     * @param toPath   复制的目录文件路径
     * @param rewrite  是否重新创建文件
     *                 <p/>
     *                 <p>文件的复制操作方法
     */
    public static void copyfile(String fromPath, String toPath, Boolean rewrite) {

        File fromFile = new File(fromPath);
        File toFile = new File(toPath);

        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public synchronized static boolean checkEnvironment(String path) {
        if (null != path && Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param sPath 文件路径
     * @return
     */
    public static boolean fileIsExists(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            if (file.isFile()) {
                flag = true;
            } else {
                return flag;
            }
        }
        return flag;
    }

    public static void isExist(String path) {
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static boolean ifExist(String path) {
        File file = new File(path);
        //判断文件是否存在
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static boolean mkdir(File file) {
        while (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        return file.mkdir();
    }

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    public static void deteleObjectFile(String gdh) {
        File file = new File(PATH_IMAGE_OBJECT, gdh + ".obj");
        deleteFolder(file.getPath());
        //删除待上传工单
        File fileWaitOrder = new File(PATH_IMAGE_OBJECT, gdh + ".waitorder");
        deleteFolder(fileWaitOrder.getPath());
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
//                file.createNewFile();
                LogUtil.e(TAG, "文件不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * @param myContext
     * @param ASSETS_NAME 要复制的文件名
     * @param savePath    要保存的路径
     * @param saveName    复制后的文件名
     */
    public static void copyAssertFile(Context myContext, String ASSETS_NAME, String savePath, String saveName) {
        String filename = savePath + "/" + saveName;
        File dir = new File(savePath);
        File file = new File(filename);
        int i = 0 ;
        // 如果目录不中存在，创建这个目录
        if (!dir.exists())
            dir.mkdir();
        try {
            if (!file.exists()) {
                //创建输入流、输出流
//                InputStream is = myContext.getResources().getAssets().open(ASSETS_NAME);
                InputStream is = myContext.getClass().getClassLoader().getResourceAsStream(assertName + ASSETS_NAME);
//                FileOutputStream fos = new FileOutputStream(filename);
                FileOutputStream fos = new FileOutputStream(file);

                //计算并格式化 文件的大小
                byte[] buffer = new byte[1024 * 20];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    i++;
                    fos.write(buffer, 0, count);
                }
                LogUtil.d(TAG,"i = "+ i);
                fos.flush();
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //写文件
    private static boolean writeFile(String fileName, InputStream in) throws IOException {
        boolean bRet = true;
        try {
            OutputStream os = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }
            in.close();
            in = null;
            os.flush();
            os.close();
            os = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    private static void checkFolderExists(String path) {
        File file = new File(path);
        if ((file.exists() && !file.isDirectory()) || !file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean isExist(String directoryPath, String fileName) {
        return new File(directoryPath, fileName).exists();
    }

}

