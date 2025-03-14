<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QR Code Payment</title>

    <script src="https://unpkg.com/html5-qrcode"></script> <!-- QR Scanner Library -->

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
            text-align: center;
        }

        .container {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
            text-align: left;
        }

        input, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        #reader {
            margin: 20px auto;
            text-align: center;
        }

        button {
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            width: 100%;
            margin-top: 10px;
        }

        button:hover {
            background-color: #218838;
        }

        .scan-message {
            color: green;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <h1>Scan QR & Pay</h1>

    <div class="container">
        <h2>Scan QR Code</h2>
        <div id="reader"></div> <!-- QR Code Scanner -->
        
        <hr>

        <form action="BillsServlet" method="post">
            <label for="your-account">Your Account Number:</label>
            <input type="text" name="your-account" id="your-account" required>

            <label for="amount">Amount:</label>
            <input type="number" name="amount" id="amount" required>

            <input type="hidden" name="pageid" value="QRPayment">

            <!-- Display QR Scan Result -->
            <label for="scan-result">Scanned QR Code Result:</label>
            <textarea id="scan-result" readonly rows="3" name="beneficiary-account"></textarea> <!-- Read-only QR Code Data -->

            <button type="submit">Proceed with Payment</button> <!-- Button inside the form -->
        </form>
    </div>

    <script>
        function onScanSuccess(decodedText) {
            console.log("Scanned QR Code: " + decodedText);

            // Display full QR scan result in the text area
            document.getElementById("scan-result").value = decodedText;

            // Show a success message
            document.getElementById("scan-result").style.color = "green";
        }

        // Initialize QR Scanner
        var html5QrcodeScanner = new Html5QrcodeScanner("reader", { fps: 10, qrbox: 250 });
        html5QrcodeScanner.render(onScanSuccess);
    </script>

</body>
</html>
