<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ContactUs.css">
</head>
<body>
	<jsp:include page="Header.jsp" />
	<section class="contact-section">
            <div class="contact-form">
                <h2>Contact Us</h2>
                <form onsubmit="return showConfirmation();">
    				<label for="name">Name</label>
    				<input type="text" id="name" placeholder="Enter your name" required>

    				<label for="email">Email</label>
    				<input type="email" id="email" placeholder="Enter your email" required>

    				<label for="question">Question</label>
    				<textarea id="question" placeholder="Enter your question or feedback" required></textarea>

    				<button type="submit">Submit</button>
				</form>

            </div>
            <div class="map">
                <iframe
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d14130.723041715715!2d85.3166697723611!3d27.70833157985717!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb198e32a64939%3A0x94979dd415c63038!2sKathmandu%2044600%2C%20Nepal!5e0!3m2!1sen!2sus!4v1683718274973!5m2!1sen!2sus"
                    width="600"
                    height="450"
                    style="border:0;"
                    allowfullscreen=""
                    loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade">
                </iframe>
            </div>
        </section>
        <script>
    function showConfirmation() {
        alert("✅ Your message has been submitted successfully!");
        return false; // prevent page refresh (if you're not actually submitting data)
    }
</script>
        
        <jsp:include page="Footer.jsp" />
</body>
</html>