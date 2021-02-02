package com.rf.lib.gradle.plugin.dimenstool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Roongf on 2/1/21
 */
public class DimensWriter {
    public static final String contentMathHeader = "  <!-- dimens create header -->  ";
    /**
     * small width dimens value 定义
     */
    private static final String SW_DIMEN_DEFINE_CONTENT = "  <dimen name=\"px_%d\">%.2fdp</dimen>\r\n";
    private static final String SW_DIMEN_DEFINE_CONTENT_TEXT_SIZE = "  <dimen name=\"text_px_%d\">%.2fdp</dimen>\r\n";
    private static final String SW_DIMEN_DEFINE_CONTENT_NEGATIVE = "  <dimen name=\"n_px_%d\">-%.2fdp</dimen>\r\n";

    /**
     * screen size dimens value 定义
     */
    private static final String SCREEN_SIZE_DIMEN_DEFINE_CONTENT = "  <dimen name=\"px_%d\">%.2fpx</dimen>\r\n";
    private static final String SCREEN_SIZE_DIMEN_DEFINE_CONTENT_TEXT_SIZE = "  <dimen name=\"text_px_%d\">%.2fpx</dimen>\r\n";
    private static final String SCREEN_SIZE_DIMEN_DEFINE_CONTENT_NEGATIVE = "  <dimen name=\"n_px_%d\">-%.2fpx</dimen>\r\n";

    private final String mDirectory;
    private final DimensConfigBean dimensConfigBean;

    public DimensWriter(String directory, DimensConfigBean dimensConfigBean) {
        this.mDirectory = directory;
        this.dimensConfigBean = dimensConfigBean;
    }

    public void execute() throws IOException {
        if (mDirectory == null || mDirectory.length() == 0) {
            throw new NullPointerException("your configFile is empty, please fix it");
        }

        String fileDir = "values" + File.separator + "dimens.xml";
        createSwdpDimens(mDirectory, fileDir, dimensConfigBean.getSwDpStandard());

        for (Integer swDpValue : dimensConfigBean.getSwDpValues()) {
            String fileDir1 = "values-sw" + swDpValue + "dp" + File.separator + "dimens.xml";
            createSwdpDimens(mDirectory, fileDir1, swDpValue);
        }
        for (DimensConfigBean.ScreenPixel swSizeValue : dimensConfigBean.getSwSizeValues()) {
            createScreenSizeDimens(mDirectory, swSizeValue);
        }
    }


    private String buildDimensHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(contentMathHeader);
        sb.append("\n");
        sb.append("  <!-- create time: ");
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        sb.append("     ");
        sb.append("create by user:");
        sb.append(System.getProperty("user.name"));
        sb.append(" --> \n");
        return sb.toString();
    }

    /**
     * xml中原来的内容
     *
     * @return
     * @throws IOException
     */
    private String buildOriginContent(File file) throws IOException {
        BufferedReader brFile = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = brFile.readLine()) != null) {
            if (!line.contains(contentMathHeader)) {
                sb.append(line);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    private DecimalFormat df = new DecimalFormat("0.00");


    /**
     * 计算 small width 对应的dp值
     *
     * @param baseValue
     * @param smallWidthValue
     * @return
     */
    private float calculatePx2DpValue(float baseValue, int smallWidthValue) {
        float dpValue = (baseValue / (float) dimensConfigBean.getSwDpStandard()) * smallWidthValue;
        return new BigDecimal(dpValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 计算具体 屏幕尺寸 的对应的px值
     *
     * @param basePxValue
     * @param screenSize
     * @return
     */
    private float calculateDefineScreenSizePxValue(float basePxValue, int screenSize) {
        float pxValue = (float) basePxValue * screenSize / dimensConfigBean.getSwScreenSizeStandard();
        return new BigDecimal(pxValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * smallWidth文件
     *
     * @throws IOException
     */
    private void createSwdpDimens(String dir, String fileDir, int swDpValue) throws IOException {
        System.out.println("createSwdpDimens： dir -> " + dir + ", dpValue -> " + swDpValue);
        File file = new File(dir, fileDir);
        if (!file.exists()) {
            File parentDir = new File(file.getParent());
            parentDir.mkdirs();
            file.createNewFile();
        }
        String originalContent = buildOriginContent(file);
        FileWriter fileWriter = new FileWriter(file);
        if (originalContent != null && !originalContent.contains("<resources>")) {
            fileWriter.write("<resources>");
            fileWriter.write("\r\n");
        }
        fileWriter.write(originalContent);
        fileWriter.write("\r\n");

        fileWriter.write(buildDimensHeader());
        fileWriter.write("\r\n");

        String line;
        // 打印文本(textSize)尺寸 1- 50标准尺寸
        for (int i = 1; i <= 100; i++) {
            fileWriter.write(String.format(SW_DIMEN_DEFINE_CONTENT_TEXT_SIZE, i, calculatePx2DpValue(i, swDpValue)));
        }
        fileWriter.write("\r\n");

        // 打印长度尺寸 0 到 1500标准尺寸
        for (int i = 0; i <= 1500; i++) {
            fileWriter.write(String.format(SW_DIMEN_DEFINE_CONTENT, i, calculatePx2DpValue(i, swDpValue)));
        }
        fileWriter.write("\r\n");

        // 打印长度 -1 到 -200标准尺寸
        for (int i = -1; i >= -200; i--) {
            fileWriter.write(String.format(SW_DIMEN_DEFINE_CONTENT_NEGATIVE, Math.abs(i), calculatePx2DpValue(Math.abs(i), swDpValue)));
        }
        fileWriter.write("\r\n");
        fileWriter.write("\r\n");
        fileWriter.write("</resources>");
        fileWriter.flush();
        fileWriter.close();

        System.out.println("------------- create small width dp value end -----------------");
    }

    private void createScreenSizeDimens(String dir, DimensConfigBean.ScreenPixel screenPixel) throws IOException {
        System.out.println("createScreenSizeDimens： dir -> " + dir + ",  screenPixel -> " + screenPixel.toString());
        File file = new File(dir, "values-" + screenPixel.getWidth() + "x" + screenPixel.getHeight() + File.separator + "dimens.xml");
        if (!file.exists()) {
            File parentDir = new File(file.getParent());
            parentDir.mkdirs();
            file.createNewFile();
        }
        String originalContent = buildOriginContent(file);
        FileWriter fileWriter = new FileWriter(file);
        if (originalContent != null && !originalContent.contains("<resources>")) {
            fileWriter.write("<resources>");
            fileWriter.write("\r\n");
        }
        fileWriter.write(originalContent);
        fileWriter.write("\r\n");
        fileWriter.write(buildDimensHeader());
        fileWriter.write("\r\n");

        String line;
        // 打印文本尺寸 1- 50标准尺寸
        for (int i = 1; i <= 100; i++) {
            fileWriter.write(String.format(SCREEN_SIZE_DIMEN_DEFINE_CONTENT_TEXT_SIZE, i, calculateDefineScreenSizePxValue(i, screenPixel.getWidth())));
        }
        fileWriter.write("\r\n");

        // 打印长度尺寸 0 - 1500标准尺寸
        for (int i = 0; i <= 1500; i++) {
            fileWriter.write(String.format(SCREEN_SIZE_DIMEN_DEFINE_CONTENT, i, calculateDefineScreenSizePxValue(i, screenPixel.getWidth())));
        }
        fileWriter.write("\r\n");

        // 打印长度尺寸 -1 - (-200)标准尺寸
        for (int i = -1; i >= -200; i--) {
            fileWriter.write(String.format(SCREEN_SIZE_DIMEN_DEFINE_CONTENT_NEGATIVE, Math.abs(i), calculateDefineScreenSizePxValue(Math.abs(i), screenPixel.getWidth())));
        }

        fileWriter.write("\r\n");
        fileWriter.write("\r\n");
        fileWriter.write("</resources>");
        fileWriter.flush();
        fileWriter.close();

        System.out.println("------------- create screen size px value end -----------------");
    }
}
