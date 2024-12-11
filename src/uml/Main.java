package uml;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Base64;
import java.util.zip.Deflater;

public class Main {
    public static void main(String[] args) {
        renderPlantUML();
    }

    public static void renderPlantUML() {
        try {
            // Get the current directory
            Path currentDirectory = Paths.get("C:\\Users\\mohammed almassri\\IdeaProjects\\library\\src\\uml");

            // Find .puml files
            DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory, "*.puml");
            for (Path pumlFile : stream) {
                // Read the content of the .puml file
                String content = Files.readString(pumlFile);

                // Encode the content for PlantUML URL
                String encodedContent = encodePlantUML(content);

                // PlantUML server URL
                String plantUMLServer = "https://www.plantuml.com/plantuml/png/" + encodedContent;
                System.out.println("url: " + plantUMLServer);
                // Send request to PlantUML server and download the image
                URL url = new URL(plantUMLServer);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Check response code
                if (connection.getResponseCode() == 200) {
                    // Read image from response
                    InputStream inputStream = connection.getInputStream();

                    // Save the image with the same name as the .puml file but with .png extension
                    Path imagePath = currentDirectory.resolve(pumlFile.getFileName().toString().replace(".puml", ".png"));
                    Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);

                    inputStream.close();
                    System.out.println("Generated: " + imagePath);
                } else {
                    System.err.println("Failed to generate image for: " + pumlFile.getFileName());
                }

                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String encodePlantUML(String source) throws IOException {
        // Compress the PlantUML source
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
        deflater.setInput(source.getBytes("UTF-8"));
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        deflater.end();

        // Encode the compressed data to PlantUML-compatible Base64
        return base64Encode(outputStream.toByteArray());
    }

    private static String base64Encode(byte[] data) {
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
        StringBuilder encoded = new StringBuilder();
        int value = 0;
        int bits = 0;

        for (byte b : data) {
            value = (value << 8) | (b & 0xFF);
            bits += 8;
            while (bits >= 6) {
                encoded.append(alphabet.charAt((value >> (bits - 6)) & 0x3F));
                bits -= 6;
            }
        }

        if (bits > 0) {
            encoded.append(alphabet.charAt((value << (6 - bits)) & 0x3F));
        }

        return encoded.toString();
    }

}
