<?php

function create_message($dividends, $divisor) {
  return '<?xml version="1.0"?>
  <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <soap:Body>
          <dividends>" . $dividends . "</dividends>
          <divisor>" . $divisor . "</divisor>
      </soap:Body>
  </soap:Envelope>';
}

function send_soap_request($url, $soap_body) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $soap_body);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array(
        'Content-Type: text/xml',
        'Content-Length: ' . strlen($soap_body)
    ));
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    echo "Enviando mensaje: Hola desde PHP\n";
    $response = curl_exec($ch);

    if (curl_error($ch)) {
        echo "Error: " . curl_error($ch) . "\n";
    } else {
        echo "Respuesta del servidor:\n" . $response . "\n";
    }

    curl_close($ch);
}

send_soap_request("http://localhost:8080", create_message("[1, 5, 23, 25, 35, 78, 30, 96]", 5));
send_soap_request("http://localhost:8080", create_message("[3, 20, 15]", 3));

?>