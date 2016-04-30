/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author milan
 */
public class Grabber {
    
    public static String Grab(String link) throws MalformedURLException, IOException{
         URL url = new URL(link);
            URLConnection con = url.openConnection();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line="";
            StringBuilder builder = new StringBuilder();
            while((line=reader.readLine())!=null){
                builder.append(line);
                
                
                
                //<div class="wallpapers-box-300x180-2-img"><a title=(.*?) href=(.*?)>
            }
            reader.close();
            return builder.toString();
    }
    
}
