<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Sail Lanka | Luxury Catamaran & Ocean Safari Experiences" />
<jsp:include page="../layouts/header.jsp" />

<style>
/* Hero Section */
.hero-section {
    min-height: 90vh;
    background: linear-gradient(135deg, rgba(7, 24, 44, 0.88), rgba(11, 37, 69, 0.72)),
                url('https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=1600') center/cover no-repeat;
    display: flex;
    align-items: center;
    position: relative;
    padding-top: 100px;
    color: #FFFFFF;
}

.hero-content {
    max-width: 780px;
}

.hero-badge {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 6px 16px;
    background: rgba(212, 175, 55, 0.2);
    border: 1px solid var(--champagne-gold);
    border-radius: var(--radius-full);
    color: var(--gold-light);
    font-size: 13px;
    font-weight: 600;
    letter-spacing: 1px;
    text-transform: uppercase;
    margin-bottom: 24px;
}

.hero-title {
    font-family: var(--font-serif);
    font-size: 54px;
    font-weight: 700;
    line-height: 1.15;
    color: #FFFFFF;
    margin-bottom: 20px;
    letter-spacing: -0.5px;
}

.hero-subtitle {
    font-size: 18px;
    color: #E2E8F0;
    line-height: 1.7;
    margin-bottom: 36px;
    font-weight: 300;
}

.hero-cta {
    display: flex;
    gap: 16px;
    flex-wrap: wrap;
}

/* Quick Search Floating Box */
.search-banner {
    margin-top: -50px;
    position: relative;
    z-index: 100;
}

.search-card {
    background: #FFFFFF;
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-lg);
    padding: 24px 32px;
    border: 1px solid rgba(212, 175, 55, 0.25);
}

.search-form {
    display: grid;
    grid-template-columns: 2fr 1.5fr 1fr 1.2fr;
    gap: 20px;
    align-items: flex-end;
}

/* Section Common */
.section {
    padding: 90px 0;
}

.section-title-wrap {
    text-align: center;
    max-width: 680px;
    margin: 0 auto 55px auto;
}

.section-eyebrow {
    font-size: 13px;
    letter-spacing: 2px;
    text-transform: uppercase;
    color: var(--champagne-gold);
    font-weight: 700;
    margin-bottom: 10px;
}

.section-title {
    font-size: 36px;
    color: var(--navy-primary);
    font-weight: 800;
    margin-bottom: 14px;
}

.section-desc {
    color: var(--text-muted);
    font-size: 16px;
}

/* Tour Cards Grid */
.tour-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
    gap: 32px;
}

.tour-card {
    background: #FFFFFF;
    border-radius: var(--radius-md);
    overflow: hidden;
    box-shadow: var(--shadow-sm);
    border: 1px solid var(--border-color);
    transition: var(--transition);
    display: flex;
    flex-direction: column;
}

.tour-card:hover {
    transform: translateY(-6px);
    box-shadow: var(--shadow-lg);
    border-color: rgba(212, 175, 55, 0.4);
}

.tour-card-img {
    height: 230px;
    position: relative;
    background-size: cover;
    background-position: center;
}

.tour-card-badge {
    position: absolute;
    top: 16px;
    left: 16px;
    background: rgba(7, 24, 44, 0.85);
    backdrop-filter: blur(8px);
    color: var(--champagne-gold);
    padding: 4px 12px;
    border-radius: var(--radius-full);
    font-size: 12px;
    font-weight: 700;
    border: 1px solid var(--border-gold);
}

.tour-card-body {
    padding: 24px;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
}

.tour-card-title {
    font-size: 20px;
    font-weight: 700;
    color: var(--navy-primary);
    margin-bottom: 10px;
    line-height: 1.35;
}

.tour-meta {
    display: flex;
    gap: 16px;
    margin-bottom: 14px;
    font-size: 13px;
    color: var(--text-muted);
}

.tour-meta span {
    display: flex;
    align-items: center;
    gap: 6px;
}

.tour-card-desc {
    font-size: 14px;
    color: var(--text-muted);
    line-height: 1.6;
    margin-bottom: 20px;
    flex-grow: 1;
}

.tour-card-footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 18px;
    border-top: 1px solid var(--border-color);
}

.tour-price-box {
    display: flex;
    flex-direction: column;
}

.tour-price-label {
    font-size: 11px;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    color: var(--text-light);
}

.tour-price-val {
    font-size: 22px;
    font-weight: 800;
    color: var(--navy-primary);
}

/* Fleet Showcase */
.fleet-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 26px;
}

.vessel-card {
    background: #FFFFFF;
    border-radius: var(--radius-md);
    border: 1px solid var(--border-color);
    padding: 24px;
    text-align: center;
    box-shadow: var(--shadow-sm);
    transition: var(--transition);
}

.vessel-card:hover {
    transform: translateY(-5px);
    box-shadow: var(--shadow-md);
    border-color: var(--champagne-gold);
}

.vessel-icon {
    width: 60px;
    height: 60px;
    background: rgba(11, 37, 69, 0.06);
    border-radius: 50%;
    margin: 0 auto 16px auto;
    display: flex;
    align-items: center;
    justify-content: center;
    color: var(--marine-blue);
    font-size: 26px;
}

/* Feature Highlights */
.feature-box {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;
}

.feature-item {
    background: #FFFFFF;
    padding: 32px 26px;
    border-radius: var(--radius-md);
    border-top: 3px solid var(--champagne-gold);
    box-shadow: var(--shadow-sm);
}

.feature-item h3 {
    font-size: 18px;
    margin: 14px 0 10px 0;
}

@media (max-width: 992px) {
    .search-form { grid-template-columns: 1fr 1fr; }
    .hero-title { font-size: 40px; }
    .feature-box { grid-template-columns: 1fr; }
}

@media (max-width: 576px) {
    .search-form { grid-template-columns: 1fr; }
}
</style>

<!-- Hero Section -->
<section class="hero-section">
    <div class="container hero-content">
        <div class="hero-badge">
            <i class="fa-solid fa-crown"></i> Premier Luxury Ocean Charters in Sri Lanka
        </div>
        <h1 class="hero-title">Bespoke Catamaran Safaris & Pelagic Ocean Voyages</h1>
        <p class="hero-subtitle">
            Sail into the pristine azure waters of the Indian Ocean. Experience blue whale watching in Mirissa, sunset cruises by Galle Fort, and coral reef expeditions in Trincomalee with luxury, comfort, and safety.
        </p>
        <div class="hero-cta">
            <a href="${pageContext.request.contextPath}/experiences" class="btn btn-gold btn-lg">
                <i class="fa-solid fa-compass"></i> Discover Cruises
            </a>
            <a href="${pageContext.request.contextPath}/schedules" class="btn btn-outline-gold btn-lg">
                <i class="fa-regular fa-calendar-check"></i> Live Departures
            </a>
        </div>
    </div>
</section>

<!-- Floating Search / Quick Filter Bar -->
<div class="container search-banner">
    <div class="search-card">
        <form action="${pageContext.request.contextPath}/experiences" method="GET" class="search-form">
            <div class="form-group" style="margin: 0;">
                <label class="form-label"><i class="fa-solid fa-water"></i> Safari Experience</label>
                <select name="type" class="form-control">
                    <option value="">All Safari Types</option>
                    <option value="WHALE_WATCHING">Blue Whale Watching</option>
                    <option value="SUNSET_SAIL">Sunset Champagne Sail</option>
                    <option value="SNORKELING_SAFARI">Coral Reef Snorkeling</option>
                    <option value="DINE_AT_SEA">Starlight Dine-at-Sea</option>
                </select>
            </div>
            <div class="form-group" style="margin: 0;">
                <label class="form-label"><i class="fa-solid fa-location-dot"></i> Coastal Region</label>
                <select name="region" class="form-control">
                    <option value="">All Ports</option>
                    <option value="SOUTH_COAST">Southern Coast (Mirissa / Galle)</option>
                    <option value="EAST_COAST">Eastern Coast (Trincomalee / Passikudah)</option>
                    <option value="WEST_COAST">Western Coast (Bentota / Colombo)</option>
                </select>
            </div>
            <div class="form-group" style="margin: 0;">
                <label class="form-label"><i class="fa-solid fa-user-group"></i> Guests</label>
                <input type="number" name="guests" value="2" min="1" max="25" class="form-control">
            </div>
            <div>
                <button type="submit" class="btn btn-primary" style="width: 100%; height: 46px;">
                    <i class="fa-solid fa-magnifying-glass"></i> Find Tours
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Featured Safari Experiences -->
<section class="section">
    <div class="container">
        <div class="section-title-wrap">
            <div class="section-eyebrow">Luxury Ocean Expeditions</div>
            <h2 class="section-title">Signature Safari Cruises</h2>
            <p class="section-desc">Handcrafted voyages along Sri Lanka's most celebrated coastal sanctuaries aboard purpose-built luxury catamarans.</p>
        </div>

        <div class="tour-grid">
            <c:forEach var="tour" items="${featuredTours}">
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
                        <div class="tour-card-footer">
                            <div class="tour-price-box">
                                <span class="tour-price-label">Starting from</span>
                                <span class="tour-price-val">${tour.basePrice.formatted}</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/booking?tourId=${tour.id}" class="btn btn-gold btn-sm">
                                Book Cruise <i class="fa-solid fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<!-- Luxury Fleet Showcase -->
<section class="section" style="background: #FFFFFF;">
    <div class="container">
        <div class="section-title-wrap">
            <div class="section-eyebrow">Our Catamarans & Yachts</div>
            <h2 class="section-title">The Maritime Fleet</h2>
            <p class="section-desc">Maintained to rigorous SOLAS standards with dual-hull stability, sun trampolines, and shaded panoramic salons.</p>
        </div>

        <div class="fleet-grid">
            <c:forEach var="vessel" items="${fleet}">
                <div class="vessel-card">
                    <div class="vessel-icon">
                        <i class="fa-solid fa-sailboat"></i>
                    </div>
                    <h3 style="font-size: 18px; color: var(--navy-primary); margin-bottom: 6px;">${vessel.name}</h3>
                    <div style="font-size: 12px; color: var(--text-light); margin-bottom: 12px; font-weight: 600;">${vessel.registrationNo}</div>
                    <p style="font-size: 13px; color: var(--text-muted); margin-bottom: 16px;">
                        ${vessel.vesselArchitecture}
                    </p>
                    <div style="display: flex; justify-content: center; gap: 8px; margin-bottom: 16px;">
                        <span class="badge badge-primary"><i class="fa-solid fa-users"></i> ${vessel.capacity.maxPassengers} Guests</span>
                        <span class="badge ${vessel.statusBadgeClass}">${vessel.statusLabel}</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/booking" class="btn btn-outline-navy btn-sm" style="width: 100%;">
                        Charter Vessel
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<!-- Why Sail Lanka Features -->
<section class="section" style="background: var(--bg-main);">
    <div class="container">
        <div class="section-title-wrap">
            <div class="section-eyebrow">The Sail Lanka Distinction</div>
            <h2 class="section-title">Why Sail With Us</h2>
        </div>

        <div class="feature-box">
            <div class="feature-item">
                <i class="fa-solid fa-shield-halved" style="font-size: 32px; color: var(--marine-blue);"></i>
                <h3>Uncompromising Safety</h3>
                <p style="font-size: 14px; color: var(--text-muted);">
                    All catamarans equipped with certified SOLAS life rafts, satellite EPIRBs, life jackets, and captained by Master Mariners with decades of ocean experience.
                </p>
            </div>
            <div class="feature-item">
                <i class="fa-solid fa-utensils" style="font-size: 32px; color: var(--champagne-gold);"></i>
                <h3>Gourmet Dining at Sea</h3>
                <p style="font-size: 14px; color: var(--text-muted);">
                    Enjoy fresh tropical fruit platters, Ceylon tea, artisanal canapés, and fresh seafood catches prepared on board with premium beverages.
                </p>
            </div>
            <div class="feature-item">
                <i class="fa-solid fa-binoculars" style="font-size: 32px; color: var(--ocean-teal);"></i>
                <h3>Marine Naturalist Guides</h3>
                <p style="font-size: 14px; color: var(--text-muted);">
                    Learn from resident marine biologists who provide ethical whale watching commentary, identify individual blue whales, and ensure zero harassment to ocean life.
                </p>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp" />
