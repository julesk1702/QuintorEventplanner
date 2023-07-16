import UserRepository from "../repositories/UsersRepository";

/**
 * Validates whether the user is logged in or not
 * @returns {boolean} true if user is logged in, false otherwise
 */
export function validateIsUserLoggedIn() {
  // TODO: This is a temporary solution. We need to implement a better way to validate user login
  const authenticated = localStorage.getItem("userId");
  return authenticated !== null;
}

/**
 * Validates whether the user is an admin or not
 * @returns {boolean} true if user is admin, false otherwise
 */
export async function validateIsUserAdmin() {
  // Extremely insecure way of checking if user is admin, but with no database calls
  // return localStorage.getItem("isAdmin");

  // More secure way of checking if user is admin, but with database calls everytime the page is loaded
  const userId = localStorage.getItem("userId");
  if (userId === null) { return false; }
  const userInfo = await UserRepository.getUserById(userId);
  if (userInfo === null || userInfo === undefined) { return false; }
  else if (userInfo.data.role.toUpperCase() === "ADMIN") { return true; }
  else { return false; }
}
