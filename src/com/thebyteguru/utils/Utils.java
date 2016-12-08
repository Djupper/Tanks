package com.thebyteguru.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prog on 08.12.2016.
 */
public class Utils {
    public static BufferedImage resize(BufferedImage image, int width, int height) {

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return newImage;
    }

    public static Integer[][] levelParser(String filePufth) {
        Integer[][] result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePufth)))) {
            String line = null;
            List<Integer[]> lvlLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {

                lvlLines.add(str2int_arrays(line.split(" ")));
            }
            result = new Integer[lvlLines.size()][lvlLines.get(0).length];
            for (int    i   = 0; i<lvlLines.size(); i++){
                result[i] = lvlLines.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static final Integer[] str2int_arrays(String[] sArr) {
        Integer[] result = new Integer[sArr.length];

        for (int i = 0; i < sArr.length; i++) {
            result[i] = Integer.parseInt(sArr[i]);
        }

        return result;
    }
}
