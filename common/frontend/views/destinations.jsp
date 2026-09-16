<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Destinations & Harbours | Sail Lanka" />
<jsp:include page="../layouts/header.jsp" />

<div style="background: var(--navy-dark); padding: 130px 0 50px 0; color: #FFFFFF; text-align: center;">
    <div class="container">
        <div style="color: var(--champagne-gold); font-weight: 700; text-transform: uppercase; font-size: 13px; letter-spacing: 2px; margin-bottom: 8px;">
            Sri Lankan Coastal Sanctuaries
        </div>
        <h1 style="font-size: 42px; color: #FFFFFF; margin-bottom: 12px;">Ports & Sailing Destinations</h1>
        <p style="max-width: 600px; margin: 0 auto; color: #94A3B8;">
            From the continental shelf of Mirissa to the historic ramparts of Galle and the coral gardens of Pigeon Island.
        </p>
    </div>
</div>

<section class="section">
    <div class="container">
        <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(340px, 1fr)); gap: 30px;">
            <c:forEach var="dest" items="${destinations}">
                <div class="card">
                    <div style="height: 180px; background: linear-gradient(135deg, var(--navy-secondary), var(--marine-blue)); display: flex; align-items: center; justify-content: center; color: #FFFFFF; font-size: 44px;">
                        <i class="fa-solid fa-anchor"></i>
                    </div>
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
                            <span class="badge badge-gold">${dest.region.displayName}</span>
                            <span style="font-size: 12px; color: var(--text-light);"><i class="fa-solid fa-compass"></i> Port</span>
                        </div>
                        <h3 style="font-size: 20px; color: var(--navy-primary); margin-bottom: 6px;">${dest.name}</h3>
                        <div style="font-size: 13.5px; font-weight: 600; color: var(--marine-blue); margin-bottom: 12px;">
                            <i class="fa-solid fa-location-dot"></i> ${dest.harborName}
                        </div>
                        <p style="font-size: 14px; color: var(--text-muted); margin-bottom: 20px;">
                            ${dest.description}
                        </p>
                        <a href="${pageContext.request.contextPath}/experiences" class="btn btn-outline-navy btn-sm" style="width: 100%;">
                            View Departing Tours
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp" />
