/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.ScrapTelnet.handler;

import com.leapfrog.ScrapTelnet.util.Grabber;
import static com.leapfrog.ScrapTelnet.util.Grabber.Grab;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author milan
 */
public class ClientListener extends Thread {

    private PrintStream writer;
    private BufferedReader reader;

    private Socket client;

    public ClientListener(Socket Client) throws IOException {
        this.client = Client;
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintStream(client.getOutputStream());

    }

    @Override
    public void run() {
        try {
            writer.println("****Welcome to the Scrap Server****");
            boolean login = false;
            while (!login) {
                writer.println("Enter Your UserName: ");
                String userName = reader.readLine();
                writer.println("Enter your Password");
                String password = reader.readLine();
                if (password.equals("admin123")) {
                    writer.println("Welcome " + userName + " to Srcap Server");
                    System.out.println(userName + " > Connected.");
                    login = true;
                      while(true){
                    writer.println("1. Scrap from JobsNepal.com ");
                    writer.println("2. Scrap from MeroJob.com");
                    writer.println("3. Scrap images from SantaBanta.com ");
                    writer.println("Enter Your Choice[1-3]?");
                    Scanner input = new Scanner(client.getInputStream());
                    int choice = input.nextInt();
                    switch (choice) {

                        case 1:
                            writer.println("Scrapping jobs from JobsNepal.com ");
                            jobsnepalgrabber();
                            break;
                        case 2:
                            writer.println("Scapping jobs from Merojob.com ");
                            
                            
                            merojobgrabber();
                            break;
                        case 3:
                            writer.println("Scrapping images from Santabanta.com ");
                            
                            
                            santagrabber();
                            break;

                    }
                      }
                } else {
                    writer.println("Invalid Password");
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void jobsnepalgrabber() {
        try {

            String content = Grab("http://www.jobsnepal.com");
            String regPattern = "<a class=\"job-item\"(.*?)href=(.*?)\\s>(.*?)</a>";
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String jobName = matcher.group(3);
                writer.println(jobName);
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void merojobgrabber() {
         try {
             //http://www.merojob.com/search-new/index.php?SeaOpt=basic

            String content = Grab("http://www.merojob.com/search-new/index.php?SeaOpt=basic");
            String regPattern = "<h4 class='title changefont'>(.*?)<(.*?) alt=\"TOP JOB\" title=\"TOP JOB\" /></h4>";
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String jobName = matcher.group(1);
                writer.println(jobName);
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

    public void santagrabber() {
        try {

            String content = Grab("http://www.santabanta.com/wallpapers/");
            String regPattern = "<div class=\"wallpapers-box-300x180-2-img\"><a title=(.*?) href=\"(.*?)\">";
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String modelName = matcher.group(1);
                String imageLink = matcher.group(2);
                writer.println("Crawling:\t" + modelName);
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
                    FileOutputStream os = new FileOutputStream("f:/new/" + tokens[tokens.length - 1]);
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
