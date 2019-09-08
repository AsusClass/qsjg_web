package utils;

import Dao.DaoImg;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作类
 * 只看public方法即可，private方法不用看
 * 例如:
 * FileUtil.storeUploadFile(xx,yy)
 */
public class FileUtil {

    private static FileForm fileForm;
    private static final String POST_FLAG = "\r\n-----";

    private FileUtil() {
    }

    /**
     * 根据输入文件名获取文件拓展名
     *
     * @param fileName 输入的文件
     * @return 如果没有拓展名返回 ""
     */
    public static String getExt(String fileName) {
        if (fileName == null) {
            return "";
        }
        String[] split = fileName.split("\\.");
        return split.length == 0 ? "" : split[split.length - 1];
    }

    /**
     * 保存http输入流中文件至本地
     *
     * @param inputStream http输入流，http中content-type必须是multipart/form-data
     * @param folder      文本待保存的目录
     * @return 保存至本地的File对象
     */
    public static synchronized File storeUploadFile(InputStream inputStream, String folder) {



        File file = null;
        byte[] readBuffer = new byte[1024];
        int readLength ;
        FileOutputStream fileOStream = null; //文件写入对象
        boolean isNeedCheckFileBoundary = true; //是否需要检查报文分隔符 默认为true
        try {
            while ((readLength = inputStream.read(readBuffer)) != -1) { //输入流读一部分数据到byte数组  返回的length不为-1说明有读取到数据
                if (isNeedCheckFileBoundary) {  //第一次读取的数据分隔符需要处理
                    isNeedCheckFileBoundary = false;
                    int startReadIndex = getFileBoundaryStartIndex(readBuffer); //获取有用数据开始下标 下标前均为报文信息
                    if (startReadIndex==-1){
                        return null;
                    }
                    if (hasFileName()) {  //判断
                        int maxId = Integer.parseInt(DaoImg.queryLateImg().get("id").toString());  //数据库最大id+1
                        file = createNewFile(folder.concat(Integer.toString(maxId+1).concat("_").concat(fileForm.fileName))); // 创建文件  这样就不会重复
                        fileOStream = new FileOutputStream(file);  //文件写入对象

                        byte[] realFileData = subBytes(readBuffer,
                                startReadIndex, readBuffer.length-1);  //截取有用数据
                        String firstLineData = new String(realFileData); //有用数据转为字符串

                        if (isSmallFile(firstLineData)) {  //判断是不是小文件 说明一次就读完了
                            realFileData = subBytes(realFileData,
                                    0, Math.max(firstLineData.indexOf(POST_FLAG) - 1, 0));
                            fileOStream.write(realFileData);
                            fileOStream.close();
                            break;
                        }

                        fileOStream.write(readBuffer, startReadIndex, readBuffer.length - startReadIndex);
                        continue;
                    }
                }
                if (fileOStream != null) {
                    fileOStream.write(readBuffer, 0, readLength);
                }
            }
        } catch (Exception e) {
            Log.e(e);
        } finally {
            DataUtil.close(fileOStream);
        }
        return file;
    }

    /**
     * 根据文件路径生成新的文件
     *
     * @param path 待生成文件的路径
     * @return 文件对象
     */
    private static File createNewFile(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                //文件不存在，创建文件
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e(e);
        }
        return file;
    }

    /**
     * 判断是否有文件名称
     */
    private static boolean hasFileName() {
        return fileForm != null && fileForm.fileName != null;
    }

    /**
     * 判断是否是小文件  (单个文件上传 末尾有\r\n----+分隔符)
     *
     * @param firstLine 输入流第一行数据
     * @return true 表示是小文件
     */
    private static boolean isSmallFile(String firstLine) {
        return firstLine.contains(POST_FLAG);
    }


    /**
     *获取有用数据开始位置下标
     * @param buffer
     * @return
     */
    private static int getFileBoundaryStartIndex(byte[] buffer) {
        String headerss = new String(buffer);
        System.out.println(headerss);
        Boolean islegal = false;
        for (int i = 0; i < buffer.length; i++) {   //每4行读
            int startIndex = getLineIndex(buffer, (i << 2)+1); //  向左移两位 相当于X4   i=0时  获取第0行结束后的起始位置 也就是0
            int endIndex = getLineIndex(buffer, (i + 1) << 2); //i+1=1, 获取第4行结束后起始位置
            byte[] fourLines = subBytes(buffer, startIndex, endIndex);  //截取byte数组一部分  总共4行
            System.out.print("开始");
            System.out.print(new String(fourLines));
            System.out.print("结束");

            String  lineStr = new String(fourLines);
//            if (lineStr.contains("token")){ //首先检查有没有token
//                Map tokenAndValue = getToken(lineStr);  //提取token信息
//
//                 islegal = TokenProccessor.checkRequsetToken(tokenAndValue.get("token").toString());  //验证token合不合法
//            }
            islegal = true;

            if (i!=0) {
                if (islegal && isFileBoundary(fourLines))  // 4行包含分隔符
                    return endIndex+1;  //有用数据为4行结束后的起始位置开始
                else return -1;  //token不合法或者数据有问题
            }
        }
        return 0;
    }


    /**
     * 判断是否包含分隔符串
     * @param buffer
     * @return
     */
    private static boolean isFileBoundary(byte[] buffer) {
        if (buffer == null) {
            return false;
        }
        String lineStr = new String(buffer);


        if (lineStr.contains("filename")) {  //因为是4行一读   有filename字段 说明是前4行
            fillFileForm(lineStr); //是前面4行头部信息  实例文件表单对象
            return true;
        }
        return false;
    }


    /**
     * 提取token
     * @param lineStr
     * @return
     */
    private static Map getToken(String lineStr){
        HashMap<String,String> re = new HashMap<>();
        String[] split = lineStr.split("\r\n");
        String key = split[1].substring(split[1].indexOf("token"),split[1].indexOf("token")+5);
        String value = split[3].replace("\r\n","");
        re.put(key,value);

        return re;
    }




    /**
     * 填充获取的表单数据
     *
     * @param lineStr 待获取的数据
     */
    private static void fillFileForm(String lineStr) {
        try {
            //存在严重的性能问题，可改写成时间复杂度o(1)
            String[] split = lineStr.substring(lineStr.indexOf("filename"), lineStr.lastIndexOf("\r\n"))
                    .replace("\"", "")
                    .replaceAll("\r\n|:", "=")
                    .split("=");
            if (split.length >= 4) {
                if (fileForm == null) fileForm = new FileForm();
                fileForm.boundary = lineStr.substring(0, lineStr.indexOf("\n") - 1);
                fileForm.fileName = split[1];
                fileForm.countentType = split[3];
            }
        } catch (Exception e) {
            Log.e(e);
        }
    }

    /**
     * 获取第1行开始字符位置
     * 和第4行结束时位置
     * @param src
     * @param lineNum
     * @return
     */
    private static int getLineIndex(byte[] src, int lineNum) {
        if (lineNum <= 1) return 0;
        int lineCnt = 0;  //行号统计



        int pre_line_index=0; // 上一行行尾处下标
        int index=0 ; // 行尾处下标
        for (int k = 0; k < src.length; k++) {

            if (src[k] == 13 || src[k+1] == 10) {  //13是\r  10是\n表示换行 \r\n
                lineCnt++;

                if (lineCnt == (lineNum-1))
                    pre_line_index = k+1;

                if (lineCnt == lineNum) {
                    index = k+1;
                    break;
                }
            }
        }




        if (lineNum%4==0){  //取行未字符位置
                return index;
        }else { //取行始字符位置
                return pre_line_index+1;
        }
    }

    /**
     * 截取byte数组的一部分
     * @param source
     * @param beginIndex 开始下标
     * @param endIndex 结束下标
     * @return
     */
    private static byte[] subBytes(byte[] source, int beginIndex, int endIndex) {
        if (source == null || source.length <= 0 || endIndex - beginIndex <= 0) return null;  //输入不合法
        int byteLength = endIndex - beginIndex + 1;
        byte[] temp = new byte[byteLength];
        System.arraycopy(source, beginIndex, temp, 0, byteLength);
        return temp;
    }

    /**
     * 文件表单实体
     */
    public static class FileForm {
        String fileName;
        String boundary;
        String countentType;
    }
}