<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List" %>
<%
    // Data prepared (and HTML-escaped) by ProfileServlet
    List<String> hobbies = (List<String>) request.getAttribute("hobbies");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${name} | My Profile</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="page">
    <section class="profile card">
        <div class="cover"></div>
        <div class="avatar">${initial}</div>

        <h1 class="p-name">${name}</h1>
        <p class="p-program">🎓 ${program}</p>

        <div class="info-grid">
            <div class="info">
                <span class="info-label">🆔 Student ID</span>
                <span class="info-value">${studentId}</span>
            </div>
            <div class="info">
                <span class="info-label">📧 Email</span>
                <span class="info-value"><a href="mailto:${email}">${email}</a></span>
            </div>
        </div>

        <h2>🎯 Hobbies</h2>
        <div class="chips static">
            <% if (hobbies != null && !hobbies.isEmpty()) {
                   for (String h : hobbies) { %>
                <span class="chip-out"><%= h %></span>
            <%     }
               } else { %>
                <span class="muted">No hobbies selected.</span>
            <% } %>
        </div>

        <h2>✍️ About Me</h2>
        <blockquote class="intro">${intro}</blockquote>

        <div class="actions center">
            <a href="index.html" class="btn btn-primary">← Edit / New Profile</a>
            <button type="button" class="btn btn-ghost" onclick="window.print()">🖨️ Print</button>
        </div>
    </section>
    <footer>CSC584 &middot; Individual Assignment 1</footer>
</div>
</body>
</html>
