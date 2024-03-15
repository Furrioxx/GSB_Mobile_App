package com.furrioxx.connexiongsb.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class NetworkUtils {
    private static final String BASE_URL = "https://tom-pelud.fr/gsb/api/";
    private static final String TAG = "NetWorkUtils";
    public static String login(String mail, String password){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJSONString = null;

        try{
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("login")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Create the POST data
            String postData = "mail=" + Uri.encode(mail) +
                    "&password=" + Uri.encode(password);

            // Write the data to the connection
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            responseJSONString = builder.toString();

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseJSONString;
    }

    public static String getCostSheet(String mail, String token, String idUser, String isTreated){
        Log.d(TAG, "Data envoyé : " + mail + " " +  token + " " +   idUser + " " +   isTreated);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJSONString = null;

        try{
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("getCostSheet")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Create the POST data
            String postData = "mail=" + Uri.encode(mail) +
                    "&id=" + Uri.encode(idUser) + "&isTraite=" + Uri.encode(isTreated) + "&token=" + Uri.encode(token);

            // Write the data to the connection
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            responseJSONString = builder.toString();

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseJSONString;
    }

    public static String getCost(String mail, String token, String idCostSheet){
        Log.d(TAG, "Data envoyé : " + mail + " " +  token + " " +   idCostSheet);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJSONString = null;

        try{
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("getCost")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Create the POST data
            String postData = "mail=" + Uri.encode(mail)
                    + "&token=" + Uri.encode(token)
                    + "&idFicheFrais=" + Uri.encode(idCostSheet);

            // Write the data to the connection
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            responseJSONString = builder.toString();

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseJSONString;
    }

    public static String deleteCostSheet(String mail, String token, String idCostSheet){
        Log.d(TAG, "Data envoyé : " + mail + " " +  token + " " +   idCostSheet);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJSONString = null;

        try{
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("deleteCostSheet")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Create the POST data
            String postData = "mail=" + Uri.encode(mail)
                    + "&token=" + Uri.encode(token)
                    + "&idFicheFrais=" + Uri.encode(idCostSheet);

            // Write the data to the connection
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            responseJSONString = builder.toString();

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseJSONString;
    }

    public static String addCostSheet(String mail, String token, String imageTransportPath) {
        Log.d(TAG, imageTransportPath);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJSONString = null;

        try {
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendPath("addCostSheet")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + UUID.randomUUID().toString());

            // Create the multipart request
            OutputStream outputStream = urlConnection.getOutputStream();
            String boundary = UUID.randomUUID().toString();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            writer.write("--" + boundary + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"mail\"\r\n\r\n" + mail + "\r\n");
            writer.write("--" + boundary + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"token\"\r\n\r\n" + token + "\r\n");

            // Add files
            addFilePart(outputStream, boundary, "transportFile", imageTransportPath);
            //addFilePart(outputStream, boundary, "fileOther", imageAutreURI);

            writer.write("--" + boundary + "--\r\n");
            writer.flush();
            writer.close();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            responseJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseJSONString;
    }


    private static void addFilePart(OutputStream outputStream, String boundary, String fieldName, String filePath) throws IOException {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                outputStream.write(("--" + boundary + "\r\n").getBytes());
                outputStream.write(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
                outputStream.write(("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + "\r\n\r\n").getBytes());
                outputStream.flush();

                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.write("\r\n".getBytes());
                outputStream.flush();
                inputStream.close();
            } else {
                System.err.println("File does not exist at: " + filePath);
            }
        }
    }
}
