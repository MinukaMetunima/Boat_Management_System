/**
 * Common JavaScript for Boat Safari Management System
 * Inspired by Sail Lanka Charter (sail-lanka-charter.com)
 */

document.addEventListener('DOMContentLoaded', () => {
    initNavbarScroll();
    initFlashAlerts();
    initLiveEmergencyTicker();
});

/**
 * Adds background glassmorphism effect on scroll
 */
function initNavbarScroll() {
    const header = document.querySelector('.site-header');
    if (!header) return;

    window.addEventListener('scroll', () => {
        if (window.scrollY > 40) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
}

/**
 * Auto-dismisses flash alert toasts after 5 seconds
 */
function initFlashAlerts() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });
}

/**
 * Queries /api/emergency/feed to dynamically show the live emergency ribbon
 */
function initLiveEmergencyTicker() {
    const ribbon = document.getElementById('emergencyRibbon');
    const ribbonText = document.getElementById('emergencyRibbonText');
    if (!ribbon || !ribbonText) return;

    fetch('/api/emergency/feed')
        .then(res => res.json())
        .then(data => {
            if (data && data.count > 0) {
                const latest = data.alerts[0];
                ribbonText.textContent = `[${latest.severity}] ${latest.title} — ${latest.message}`;
                ribbon.className = `emergency-ribbon active ${latest.bannerClass || ''}`;
            } else {
                ribbon.classList.remove('active');
            }
        })
        .catch(() => {
            // Silently ignore if network/endpoint unavailable
        });
}

/**
 * Validates promo codes asynchronously in the booking form
 */
function validatePromoCode(code, totalAmount, callback) {
    if (!code || !code.trim()) return;

    const url = `/api/promo/validate?code=${encodeURIComponent(code.trim())}&total=${encodeURIComponent(totalAmount)}`;
    fetch(url)
        .then(res => res.json())
        .then(data => {
            if (callback) callback(data);
        })
        .catch(err => {
            if (callback) callback({ valid: false, message: 'Could not verify promo code.' });
        });
}
