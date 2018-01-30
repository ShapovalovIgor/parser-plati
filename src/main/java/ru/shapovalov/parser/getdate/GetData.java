package ru.shapovalov.parser.getdate;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class GetData {

    public String getNewData(URL url, String request) throws IOException {
        HttpURLConnection connection = getConnect(url);
        sendQuery(connection, request);
        if (connection != null) {
            connection.disconnect();
        }
        return getDate(connection);
    }

    private HttpURLConnection getConnect(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/xml");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private void sendQuery(HttpURLConnection connection, String sendData) throws IOException {
        WritableByteChannel channelSendData = Channels.newChannel(connection.getOutputStream());
        byte[] sendDataBytes = sendData.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(sendDataBytes);
        channelSendData.write(byteBuffer);
        byteBuffer.clear();
        channelSendData.close();
    }

    private String getDate(HttpURLConnection connection) throws IOException {
//NIO
        //        ReadableByteChannel channelGetData = Channels.newChannel(connection.getInputStream());
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
//        channelGetData.read(byteBuffer);
//        String data = new String(byteBuffer.array());
//        byteBuffer.clear();
//        channelGetData.close();
//        System.out.println(data);
//        return data;
        //IO
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        return response.toString();
    }
}

