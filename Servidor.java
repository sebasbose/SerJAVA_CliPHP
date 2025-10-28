import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Servidor {

    public static int[] stringToArray(String str) {
        // Elimina corchetes y espacios innecesarios
        str = str.trim().replaceAll("[\\[\\]]", "");

        // Divide por comas
        String[] parts = str.split(",");

        // Convierte a enteros
        int[] numbers = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i].trim());
        }

        return numbers;
    }

    public static int[] getDivisibleNumbers(int[] dividends, int divider) {
        // Primero contamos cuántos son divisibles
        int count = 0;
        for (int num : dividends) {
            if (num % divider == 0) {
                count++;
            }
        }

        // Creamos el nuevo arreglo del tamaño exacto
        int[] result = new int[count];
        int index = 0;

        // Llenamos el nuevo arreglo
        for (int num : dividends) {
            if (num % divider == 0) {
                result[index++] = num;
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Servidor SOAP iniciado en puerto 8080");
        
        while (true) {
            Socket client = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();
            
            // Leer toda la solicitud
            String line;
            StringBuilder headers = new StringBuilder();
            int contentLength = 0;
            
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                headers.append(line).append("\n");
                if (line.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            
            // Leer el cuerpo si hay contenido
            String body = "";
            if (contentLength > 0) {
                char[] buffer = new char[contentLength];
                in.read(buffer, 0, contentLength);
                body = new String(buffer);
            }

            // Procesar mensaje SOAP
            if (body.contains("<dividends>") && body.contains("</divider>")) {
                // Decodificar los valores de dividends
                int start = body.indexOf("<dividends>") + 11;
                int end = body.indexOf("</dividends>");
                int[] dividends = stringToArray(body.substring(start, end));

                // Decodificar el valor de divider
                start = body.indexOf("<divider>") + 9;
                end = body.indexOf("</divider>");
                int divider = Integer.parseInt(body.substring(start, end));

                // Create output array
                int[] output = getDivisibleNumbers(dividends, divider);

                String responseXml = "<?xml version=\"1.0\"?>" +
                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                            "<output>Servidor recibió: " + Arrays.toString(output) + "</output>" +
                        "</soap:Body>" +
                    "</soap:Envelope>";
                
                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/xml\r\n" +
                    "Content-Length: " + responseXml.length() + "\r\n" +
                    "\r\n" +
                    responseXml;
                
                out.write(httpResponse.getBytes());
                out.flush();
            }
            
            client.close();
        }
    }
}