package com.sunlight.common.utils;

import java.io.*;

/**
 * @author gaoguang
 * @description 文件操作工具类
 */
public class FileUtils {

    /**
     * 通过完整路径创建目录
     *
     * @param path 路径
     */
    public static boolean createDirWithFullPath(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 创建文件
     *
     * @param filePath 文件 路径
     */
    public static boolean createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.createNewFile();
        }
        return false;
    }

    /**
     * 复制文件（包括复制文件夹）
     *
     * @param srcFilePath  复制文件的源路径
     * @param destFilePath 要复制的到的路径
     */
    public static void copyDirAndFiles(String srcFilePath, String destFilePath) {

        // 源文件（文件夹）
        File srcFile = new File(srcFilePath);

        // 如果是文件夹
        if (srcFile.isDirectory()) {
            // 列出该目录下所有文件
            File[] files = srcFile.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    // 如果是目录
                    String name = f.getName();
                    destFilePath = destFilePath + File.separator + name;
                    File destFile = new File(destFilePath);
                    // 创建文件夹
                    if (!destFile.exists()) {
                        destFile.mkdirs();
                    }
                    copyDirAndFiles(f.getPath(), destFilePath);
                } else {
                    // 如果是文件
                    copyFile(f.getPath(), destFilePath);
                }
            }
        } else {
            // 如果是文件
            copyFile(srcFilePath, destFilePath);
        }
    }

    /**
     * 文件复制
     *
     * @param srcPath
     * @param destPath
     */

    public static void copyFile(String srcPath, String destPath) {

        File srcFile = new File(srcPath);
        File destFile = new File(destPath, srcFile.getName());
        FileInputStream inputStream = null;
        // 输入流
        FileOutputStream outStream = null;
        // 输出流
        try {
            // 读取文件
            inputStream = new FileInputStream(srcFile);
            outStream = new FileOutputStream(destFile);
            // 创建文件
            destFile.createNewFile();
            byte[] byteArray = new byte[1024];

            while (-1 != inputStream.read(byteArray)) {

                outStream.write(byteArray);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @title deleteAllFilesOfDir
     * @description 删除文件夹及文件
     * @author fanshaocong
     * @date 2017年12月5日 上午9:17:23
     */
    public static void deleteAllFilesOfDir(File path) {

        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

}
