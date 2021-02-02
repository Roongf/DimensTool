package com.rf.lib.gradle.plugin.dimenstool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Roongf on 1/27/21
 */
public class CreateDimensTask {
    public static final String defaultConfigFilePath = ".";
    public static final String configFileName = "dimensConfig.json";
    private static final int swDpStandardDPI = 360;
    private static final int swScreenSizeStandard = 1280;
    private static final ArrayList<Integer> swDpValues = new ArrayList<Integer>() {
        {
            add(320);
            add(360);
            add(410);
            add(450);
            add(500);
            add(540);
            add(600);
            add(720);
            add(768);
            add(800);
        }
    };
    private static final ArrayList<DimensConfigBean.ScreenPixel> swSizeValues = new ArrayList<DimensConfigBean.ScreenPixel>() {
        {
            add(new DimensConfigBean.ScreenPixel(720, 1280));
            add(new DimensConfigBean.ScreenPixel(1080, 1920));
            add(new DimensConfigBean.ScreenPixel(1440, 2560));
            add(new DimensConfigBean.ScreenPixel(1536, 2048));
        }
    };

    public static void execute(String configFilePath) throws IOException, JSONException {
        if (configFilePath == null || configFilePath.trim().length() == 0) {
            throw new IllegalArgumentException("your config file path is empty, check your file:" + configFilePath);
        }
        checkConfigFileExist(configFilePath);
        File file = new File(configFilePath, configFileName);

        System.out.println("file---》" + file.exists());
        BufferedReader brFile = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        System.out.println("file---》" + file.exists());

        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = brFile.readLine()) != null) {
            sb.append(line);
        }

        if (sb.length() == 0) {
            throw new IllegalArgumentException("配置文件路劲中的文件为空：path=" + file.getAbsolutePath());
        }

        System.out.println("sb:" + sb.toString());

        DimensConfigBean configBean = new GsonBuilder().setLenient().create().fromJson(sb.toString(), DimensConfigBean.class);
        System.out.println("configFilePath:" + configFilePath + ",    configBean: " + configBean.toString());
        createDimens(configBean);
    }

    private static void createDimens(DimensConfigBean configBean) throws IOException {
        System.out.println("------------- start Create Dimens files -----------------");
        String fileDir = new File("").getAbsolutePath();
        File folder = new File(fileDir, "src/main/res/");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        fileDir = folder.getAbsolutePath();
        new DimensWriter(fileDir, configBean).execute();
    }

    private static void checkConfigFileExist(String configFilePath) throws IOException, JSONException {
        File file = new File(configFilePath, configFileName);
        if (!file.exists()) {
            boolean mkdirsResult = file.createNewFile();
            String dirPath = file.getPath();

            FileWriter fileWriter;

            fileWriter = new FileWriter(file);

            DimensConfigBean configBean = new DimensConfigBean();
            configBean.setConfigFilePath(configFilePath);
            configBean.setSwDpStandard(swDpStandardDPI);
            configBean.setSwDpValues(swDpValues);
            configBean.setSwSizeValues(swSizeValues);
            configBean.setSwScreenSizeStandard(swScreenSizeStandard);

            fileWriter.write(new JSONObject(new Gson().toJson(configBean)).toString(1));
            fileWriter.flush();
        }
    }
}
