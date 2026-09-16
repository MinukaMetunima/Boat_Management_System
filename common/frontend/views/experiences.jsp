<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Safari Experiences & Tour Packages | Sail Lanka" />
<jsp:include page="../layouts/header.jsp" />

<div style="background: var(--navy-dark); padding: 130px 0 50px 0; color: #FFFFFF; text-align: center;">
    <div class="container">
        <div style="color: var(--champagne-gold); font-weight: 700; text-transform: uppercase; font-size: 13px; letter-spacing: 2px; margin-bottom: 8px;">
            Ocean Adventures & Private Charters
        </div>
        <h1 style="font-size: 42px; color: #FFFFFF; margin-bottom: 12px;">Safari Experiences</h1>
        <p style="max-width: 600px; margin: 0 auto; color: #94A3B8;">
            Select from our luxury blue whale safaris, scenic coastal sunset sails, or private dining cruises along Sri Lanka's turquoise waters.
        </p>
    </div>
</div>

<section class="section">
    <div class="container">
        <div class="tour-grid">
            <c:forEach var="tour" items="${tours}">
                <div class="tour-card">
                    <div class="tour-card-img" style="background-image: url('https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=800');">
                        <span class="tour-card-badge">${tour.tourType.title}</span>
                    </div>
                    <div class="tour-card-body">
                        <div class="tour-meta">
                            <span><i class="fa-regular fa-clock"></i> ${tour.durationHours} Hours</span>
                            <span><i class="fa-solid fa-users"></i> Max ${tour.maxPassengers} Pax</span>
                        </div>
                        <h3 class="tour-card-title">${tour.title}</h3>
                        <p class="tour-card-desc">${tour.description}</p>
                        
                        <c:if test="${not empty tour.inclusions}">
                            <div style="margin-bottom: 16px; font-size: 12.5px; color: var(--text-muted); background: #F8FAFC; padding: 10px; border-radius: 6px;">
                                <strong style="color: var(--navy-primary);"><i class="fa-solid fa-check"></i> Inclusions:</strong> ${tour.inclusions}
                            </div>
                        </c:if>

                        <div class="tour-card-footer">
                            <div class="tour-price-box">
                                <span class="tour-price-label">Price per person</span>
                                <span class="tour-price-val">${tour.basePrice.formatted}</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/booking?tourId=${tour.id}" class="btn btn-gold btn-sm">
                                Book Now <i class="fa-solid fa-calendar-check"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp" />
