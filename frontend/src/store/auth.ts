const TOKEN_KEY = 'auth_token'
const USER_ID_KEY = 'user_id'
const USERNAME_KEY = 'username'

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}

export function getUserId(): number | null {
  const id = localStorage.getItem(USER_ID_KEY)
  return id ? Number(id) : null
}

export function getUsername(): string | null {
  return localStorage.getItem(USERNAME_KEY)
}

export function setAuth(userId: number, username: string) {
  localStorage.setItem(TOKEN_KEY, `user-${userId}`)
  localStorage.setItem(USER_ID_KEY, String(userId))
  localStorage.setItem(USERNAME_KEY, username)
}

export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_ID_KEY)
  localStorage.removeItem(USERNAME_KEY)
}

export function isAuthenticated(): boolean {
  return !!getToken()
}
