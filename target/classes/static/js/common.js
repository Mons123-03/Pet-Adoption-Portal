// common.js -- shared helpers, icons, and navbar used across all pages

// ---------- Small inline SVG icon library (no external image files needed) ----------
const ICONS = {
    pawLogo: '<svg width="28" height="28" viewBox="0 0 24 24" fill="#E3A23C"><circle cx="7" cy="7" r="2.3"/><circle cx="12.2" cy="4.8" r="2.1"/><circle cx="17.3" cy="7" r="2.3"/><circle cx="19" cy="12.5" r="2"/><path d="M12 12.2c-3 0-6.6 2.2-6.6 5.6 0 1.7 1.4 2.9 3 2.4 1.2-.4 2.2-.9 3.6-.9s2.4.5 3.6.9c1.6.5 3-.7 3-2.4 0-3.4-3.6-5.6-6.6-5.6z"/></svg>',
    users: '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#E3A23C" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>',
    paw: '<svg width="22" height="22" viewBox="0 0 24 24" fill="#E3A23C"><circle cx="7" cy="7" r="2.3"/><circle cx="12.2" cy="4.8" r="2.1"/><circle cx="17.3" cy="7" r="2.3"/><circle cx="19" cy="12.5" r="2"/><path d="M12 12.2c-3 0-6.6 2.2-6.6 5.6 0 1.7 1.4 2.9 3 2.4 1.2-.4 2.2-.9 3.6-.9s2.4.5 3.6.9c1.6.5 3-.7 3-2.4 0-3.4-3.6-5.6-6.6-5.6z"/></svg>',
    check: '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#E3A23C" stroke-width="2"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>',
    clock: '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#E3A23C" stroke-width="2"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>',
    home: '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#E3A23C" stroke-width="2"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>',
    empty: '<svg width="52" height="52" viewBox="0 0 24 24" fill="none" stroke="#9AA3AC" stroke-width="1.5"><circle cx="7" cy="7" r="2"/><circle cx="12" cy="5" r="1.8"/><circle cx="17" cy="7" r="2"/><circle cx="18.5" cy="12" r="1.7"/><path d="M12 12c-2.6 0-5.7 1.9-5.7 4.8 0 1.5 1.2 2.5 2.6 2.1 1-.4 1.9-.8 3.1-.8s2.1.4 3.1.8c1.4.4 2.6-.6 2.6-2.1 0-2.9-3.1-4.8-5.7-4.8z"/></svg>'
};

// A simple flat illustration: a person kneeling and petting a dog, with a cat sitting nearby.
// Pure SVG shapes, no external images, styled for the dark theme with the amber accent.
const HERO_ILLUSTRATION = `
<svg class="hero-illustration" viewBox="0 0 420 320" xmlns="http://www.w3.org/2000/svg">
  <ellipse cx="210" cy="290" rx="170" ry="18" fill="#000" opacity="0.25"/>

  <!-- Person kneeling -->
  <g>
    <!-- torso -->
    <path d="M120 150c0-28 20-46 44-46s42 18 42 44c0 18-10 30-10 30l-8 60h-58l-8-58s-2-14-2-30z" fill="#E3A23C"/>
    <!-- head -->
    <circle cx="163" cy="90" r="26" fill="#F0C892"/>
    <!-- hair -->
    <path d="M139 84c0-16 12-28 26-28s26 12 26 26c-8-6-16-8-26-8s-18 4-26 10z" fill="#3A3220"/>
    <!-- arm reaching to dog -->
    <path d="M188 150c18 4 32 14 44 22" stroke="#E3A23C" stroke-width="16" stroke-linecap="round" fill="none"/>
    <!-- kneeling leg -->
    <path d="M132 226l-6 46h34l4-46z" fill="#262B31"/>
    <path d="M170 226l10 46h32l-14-46z" fill="#262B31"/>
  </g>

  <!-- Dog -->
  <g>
    <ellipse cx="270" cy="250" rx="52" ry="30" fill="#C9891E"/>
    <circle cx="232" cy="222" r="26" fill="#D9A441"/>
    <path d="M214 202c-6-10-4-22 4-24 6-2 12 6 12 16z" fill="#C9891E"/>
    <path d="M250 202c6-10 4-22-4-24-6-2-12 6-12 16z" fill="#C9891E"/>
    <circle cx="224" cy="220" r="3.2" fill="#1D2126"/>
    <circle cx="240" cy="220" r="3.2" fill="#1D2126"/>
    <ellipse cx="232" cy="232" rx="5" ry="3.4" fill="#1D2126"/>
    <path d="M298 248c10 4 18 2 22-6" stroke="#C9891E" stroke-width="8" stroke-linecap="round" fill="none"/>
  </g>

  <!-- Cat sitting nearby -->
  <g>
    <ellipse cx="345" cy="262" rx="28" ry="26" fill="#9AA3AC" opacity="0.9"/>
    <circle cx="345" cy="224" r="18" fill="#9AA3AC" opacity="0.9"/>
    <path d="M332 212l-6-14 14 8z" fill="#9AA3AC" opacity="0.9"/>
    <path d="M358 212l6-14-14 8z" fill="#9AA3AC" opacity="0.9"/>
    <circle cx="339" cy="222" r="2.4" fill="#1D2126"/>
    <circle cx="351" cy="222" r="2.4" fill="#1D2126"/>
    <path d="M368 258c8 2 14-2 16-10" stroke="#9AA3AC" stroke-width="6" stroke-linecap="round" fill="none" opacity="0.9"/>
  </g>

  <!-- paw prints trailing -->
  <g opacity="0.5" fill="#E3A23C">
    <circle cx="60" cy="80" r="4"/><circle cx="72" cy="72" r="3"/><circle cx="50" cy="70" r="3"/>
    <circle cx="90" cy="110" r="4"/><circle cx="102" cy="102" r="3"/><circle cx="80" cy="100" r="3"/>
  </g>
</svg>`;

async function apiGet(url) {
    const res = await fetch(url, { credentials: 'same-origin' });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) throw new Error(data.error || 'Request failed');
    return data;
}

async function apiPostForm(url, paramsObj) {
    const body = new URLSearchParams(paramsObj).toString();
    const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        credentials: 'same-origin',
        body: body
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) throw new Error(data.error || 'Request failed');
    return data;
}

async function apiPostMultipart(url, formData) {
    const res = await fetch(url, {
        method: 'POST',
        credentials: 'same-origin',
        body: formData
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) throw new Error(data.error || 'Request failed');
    return data;
}

function showAlert(container, message, type) {
    type = type || 'danger';
    container.innerHTML = '<div class="alert alert-' + type + '">' + escapeHtml(message) + '</div>';
}

function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str == null ? '' : str;
    return div.innerHTML;
}

function statusBadge(status) {
    return '<span class="badge status-badge badge-' + status + '">' + status + '</span>';
}

function emptyState(message) {
    return '<div class="empty-state">' + ICONS.empty + '<p class="mb-0">' + escapeHtml(message) + '</p></div>';
}

function petPhotoOrPlaceholder(imageUrl) {
    if (imageUrl) {
        return '<img src="' + imageUrl + '" class="card-img-top">';
    }
    return '<div class="no-photo-placeholder">' + ICONS.paw + '<span class="ms-2">No photo yet</span></div>';
}

async function loadNavbar(activePage) {
    const nav = document.getElementById('navbar');
    let user = null;
    try {
        const data = await apiGet('/api/me');
        user = data.user;
    } catch (e) { /* not logged in */ }

    let links = '';
    if (!user) {
        links =
            '<li class="nav-item"><a class="nav-link' + (activePage === 'home' ? ' active' : '') + '" href="index.html">Browse Pets</a></li>' +
            '<li class="nav-item"><a class="btn btn-outline-light btn-sm ms-lg-2 mt-2 mt-lg-0" href="login.html">Log In</a></li>' +
            '<li class="nav-item"><a class="btn btn-amber btn-sm ms-lg-2 mt-2 mt-lg-0" href="register.html">Sign Up</a></li>';
    } else if (user.role === 'ADOPTER') {
        links =
            '<li class="nav-item"><a class="nav-link' + (activePage === 'home' ? ' active' : '') + '" href="index.html">Browse Pets</a></li>' +
            '<li class="nav-item"><a class="nav-link' + (activePage === 'requests' ? ' active' : '') + '" href="my-requests.html">My Requests</a></li>' +
            '<li class="nav-item"><span class="navbar-text me-lg-2">' + escapeHtml(user.full_name) + '</span></li>' +
            '<li class="nav-item"><a href="#" class="btn btn-outline-light btn-sm" onclick="logout(event)">Log Out</a></li>';
    } else if (user.role === 'OWNER') {
        links =
            '<li class="nav-item"><a class="nav-link' + (activePage === 'home' ? ' active' : '') + '" href="index.html">Browse Pets</a></li>' +
            '<li class="nav-item"><a class="nav-link' + (activePage === 'owner' ? ' active' : '') + '" href="owner-dashboard.html">My Listings</a></li>' +
            '<li class="nav-item"><a class="nav-link' + (activePage === 'owner-requests' ? ' active' : '') + '" href="owner-requests.html">Requests Received</a></li>' +
            '<li class="nav-item"><span class="navbar-text me-lg-2">' + escapeHtml(user.full_name) + '</span></li>' +
            '<li class="nav-item"><a href="#" class="btn btn-outline-light btn-sm" onclick="logout(event)">Log Out</a></li>';
    } else if (user.role === 'ADMIN') {
        links =
            '<li class="nav-item"><a class="nav-link' + (activePage === 'admin' ? ' active' : '') + '" href="admin-dashboard.html">Admin Dashboard</a></li>' +
            '<li class="nav-item"><span class="navbar-text me-lg-2">' + escapeHtml(user.full_name) + '</span></li>' +
            '<li class="nav-item"><a href="#" class="btn btn-outline-light btn-sm" onclick="logout(event)">Log Out</a></li>';
    }

    nav.innerHTML =
        '<div class="container">' +
        '<a class="navbar-brand" href="index.html">' + ICONS.pawLogo + ' Pet Adoption Portal</a>' +
        '<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navContent">' +
        '<span class="navbar-toggler-icon"></span></button>' +
        '<div class="collapse navbar-collapse" id="navContent">' +
        '<ul class="navbar-nav ms-auto align-items-lg-center gap-lg-1">' + links + '</ul>' +
        '</div></div>';

    return user;
}

async function logout(e) {
    e.preventDefault();
    await apiPostForm('/api/logout', {});
    window.location.href = 'index.html';
}
