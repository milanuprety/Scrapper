/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog;

import com.leapfrog.util.Grabber;
import static com.leapfrog.util.Grabber.Grab;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author milan
 * Scrap wallpapeprs : santabanta.com
 */
public class mainp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        String baseURL="http://www.santabanta.com/wallpapers/global-celebrities(m)/7/?order=popular";
        try {

            String content = Grab(baseURL);
            String regPattern = "<div class=\"wallpapers-box-300x180-2-img\"><a title=(.*?) href=\"(.*?)\">";
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String modelName = matcher.group(1);
                String imageLink = matcher.group(2);
                System.out.println("Crawling:\t" + modelName);
                String url1 = "http://www.santabanta.com" + imageLink;

                String innerContent = Grab(url1);
                String regPattern1 = "<div class=\"wallpaper-big-1-downloads\"><a href=\"(.*?)\"";
                Pattern pattern1 = Pattern.compile(regPattern1);
                Matcher matcher1 = pattern1.matcher(innerContent);
                while (matcher1.find()) {
                    String imglink = matcher1.group(1);

                    URL imageURL = new URL(imglink);
                    URLConnection imgCon = imageURL.openConnection();
                    InputStream is = imgCon.getInputStream();
                    String[] tokens = imglink.split("/");
                    FileOutputStream os = new FileOutputStream("f:/new/"+tokens[tokens.length-1]);
                    byte[] buff = new byte[1024 * 10];
                    int data = 0;
                    while ((data = is.read(buff)) != -1) {
                        os.write(buff, 0, data);

                    }
                    is.close();
                    os.close();
                }

            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

}
