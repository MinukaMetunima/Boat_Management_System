<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Create Account | Sail Lanka Safari" />
<jsp:include page="../layouts/header.jsp" />

<div style="min-height: 80vh; display: flex; align-items: center; justify-content: center; padding: 120px 16px 60px 16px;">
    <div class="card" style="width: 100%; max-width: 500px; box-shadow: var(--shadow-lg);">
        <div class="card-header" style="text-align: center; display: block; padding: 28px 24px 16px 24px;">
            <div class="logo-symbol" style="margin: 0 auto 12px auto;">
                <i class="fa-solid fa-user-plus"></i>
            </div>
            <h2 style="font-size: 24px; color: var(--navy-primary);">Join Sail Lanka</h2>
            <p style="font-size: 13.5px; color: var(--text-muted); margin-top: 4px;">Create your guest account to manage ocean cruise bookings</p>
        </div>

        <div class="card-body">
            <form action="${pageContext.request.contextPath}/register" method="POST">
                <input type="hidden" name="csrf_token" value="${csrfToken}">

                <div class="form-group">
                    <label class="form-label"><i class="fa-solid fa-user"></i> Full Name</label>
                    <input type="text" name="fullName" class="form-control" required placeholder="e.g. David Miller">
                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fa-solid fa-envelope"></i> Email Address</label>
                    <input type="email" name="email" class="form-control" required placeholder="david.miller@gmail.com">
                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fa-solid fa-phone"></i> Phone Number</label>
                    <input type="text" name="phone" class="form-control" placeholder="+44 7911 123456">
                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fa-solid fa-globe"></i> Country of Origin</label>
                    <input type="text" name="country" class="form-control" value="Sri Lanka" placeholder="e.g. United Kingdom, Australia">
                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fa-solid fa-lock"></i> Password</label>
                    <input type="password" name="password" class="form-control" required placeholder="At least 6 characters">
                </div>

                <button type="submit" class="btn btn-gold" style="width: 100%; margin-top: 10px;">
                    <i class="fa-solid fa-circle-check"></i> Register Account
                </button>
            </form>
        </div>

        <div class="card-footer" style="text-align: center; font-size: 13.5px;">
            Already registered? <a href="${pageContext.request.contextPath}/login" style="font-weight: 600;">Sign In</a>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
