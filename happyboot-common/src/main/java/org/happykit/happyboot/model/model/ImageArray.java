package org.happykit.happyboot.model.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 图片数组自定义类型
 * ["1281062239738019841","1281062240287473666"]
 *
 * @author chen.xudong
 * @date 2020/7/9
 */
public class ImageArray extends ArrayList<String> {
    private static final long serialVersionUID = 8683452581122892189L;

    public ImageArray() {
    }

    public ImageArray(ImageArray imageArray) {
        this.addAll(imageArray);
    }

    /**
     * 图片字符串 => 数组
     *
     * @param images 1281062239738019841","1281062240287473666"
     * @return ["1281062239738019841","1281062240287473666"]
     */
    public static ImageArray split(String images) {
        ImageArray imageArray = new ImageArray();
        if (null == images) {
            return imageArray;
        }
        String[] imageArr = images.split(",");
        imageArray.addAll(Arrays.asList(imageArr));
        return imageArray;
    }

    /**
     * 图片数组 => 字符串
     *
     * @return 1281062239738019841","1281062240287473666"
     */
    public String join() {
        if (this.size() == 0) {
            return null;
        }
        Iterator<String> iter = this.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(iter.next()).append(",");
        }
        String text = sb.toString();
        return sb.substring(0, text.length() - 1);
    }
}
