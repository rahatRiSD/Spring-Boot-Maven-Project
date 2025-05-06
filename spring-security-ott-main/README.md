## 🔹 How Spring Security Validates the OTT?

Spring Security has a built-in filter and authentication provider to handle **One-Time Token (OTT)** validation.

### 🔗 Request to `/login/ott?token=XYZ`
1. The user clicks on the **magic link** or enters the **token manually**.  
2. The request is sent to `/login/ott` with the **token** as a query parameter.

### 🛡️ Spring Security Intercepts the Request
- `OneTimeTokenAuthenticationFilter` (part of Spring Security) **intercepts the request**.
- It **extracts** the token value from the request (`token=XYZ`).

### 🔄 Delegates to Authentication Manager
- Spring Security **delegates** the token to `OneTimeTokenAuthenticationProvider`.

### ✅ Token Validation Process
✔ **Checks if the token exists** in the database or token storage.  
✔ **Validates token expiry** (time-limited).  
✔ **Verifies token association** with the correct user.  

### 🔓 Authentication Success or Failure
✅ **If valid** → Spring Security **authenticates** the user and starts a session.  
❌ **If invalid/expired** → Returns an **authentication error (401 Unauthorized)**.  

---

This structure makes it **clear, professional, and easy to read** for GitHub users. Let me know if you need any changes! 🚀
