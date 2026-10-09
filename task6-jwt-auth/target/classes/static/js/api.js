// ---------------------------------------------------------------------
// apiFetch() - this project doesn't use React/Axios (the rest of the
// internship frontend is plain HTML/JS so it runs with zero build step),
// but this function does exactly what the task's Axios interceptor spec
// asks for:
//   - reads the JWT from localStorage
//   - attaches it as "Authorization: Bearer <token>" on every request
//   - if the server ever responds 401 (token missing/expired), it clears
//     the stored token and redirects to the login page automatically
// Use apiFetch(url, options) anywhere you would normally use fetch().
// ---------------------------------------------------------------------
async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');

  const headers = Object.assign({}, options.headers || {});
  if (token) {
    headers['Authorization'] = 'Bearer ' + token;
  }

  const response = await fetch(url, Object.assign({}, options, { headers }));

  if (response.status === 401) {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    window.location.href = '/login.html';
    throw new Error('Not authenticated');
  }

  return response;
}

// Route guard - call this at the top of any page that requires login.
// Redirects to /login.html if there's no token in localStorage.
function requireAuth() {
  if (!localStorage.getItem('jwt_token')) {
    window.location.href = '/login.html';
  }
}

function currentRole() {
  return localStorage.getItem('role');
}

function logout() {
  localStorage.removeItem('jwt_token');
  localStorage.removeItem('username');
  localStorage.removeItem('role');
  window.location.href = '/login.html';
}
