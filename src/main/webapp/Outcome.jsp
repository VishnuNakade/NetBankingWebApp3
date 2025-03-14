<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Outcome</title>
    <style>
        /* General Styling */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }

        /* Header */
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        /* Logo */
        .logo {
            display: flex;
            align-items: center;
        }

        .logo svg {
            width: 50px;
            height: 50px;
            margin-right: 10px;
            fill: #f1c40f;
        }

        /* Navigation */
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }

        nav ul li {
            margin-right: 20px;
        }

        nav ul li a {
            color: #fff;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s ease;
        }

        nav ul li a:hover {
            color: #f1c40f;
        }

        /* Main Content */
        main {
            padding: 50px 20px;
            text-align: center;
        }

        .balance-container {
            background-color: white;
            padding: 20px;
            width: 300px;
            height: 100px; /* Placeholder for balance data */
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* Footer */
        footer {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px 0;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>

    <!-- Header Section -->
    <header>
        <div class="logo">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path d="M12 2a10 10 0 00-7 17.1V22H8v-2a8 8 0 018-8h2v-2.9A10 10 0 0012 2zm0 16a6 6 0 01-6-6v-4h4v2h2v-2h4v4a6 6 0 01-6 6z"/>
            </svg>
            <h1>Banker</h1>
        </div>
        <nav>
            <ul>
                <li><a href="index.html">Home</a></li>
                <li><a href="FinalLogout.jsp">LogOut</a></li>
            </ul>
        </nav>
    </header>

    <!-- Main Content -->
    <main>
        <h2>Banker</h2>


        <div class="balance-container" id="balance-box">
    <%
        String message = (String) session.getAttribute("message");
        if (message != null) {
    %>
            <p style="color: green; font-weight: bold;"><%= message %></p>
    <%
            session.removeAttribute("message"); // Clear message after displaying
        } else {
    %>
            <p>No message available</p>
    <%
        }
    %>
</div>

    </main>

    <!-- Footer -->
    <footer>
        <p>&copy; 2025 Banker. All rights reserved.</p>
    </footer>

</body>
</html>
