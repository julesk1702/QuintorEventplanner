import React from "react";
import { useLocation } from "react-router-dom";

export default function ErrorPage() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const code = queryParams.get("code");

  const errorMessages = {
    0: "Network Error",
    400: "Bad Request",
    401: "Unauthorized",
    403: "Forbidden",
    404: "Page Not Found",
    500: "Internal Server Error",
    503: "Service Unavailable",
  };

  const errorExplanations = {
    0: "The server is unreachable. Please check your internet connection.",
    400: "The server could not understand your request. Please try again.",
    401: "You are not authorized to access this page. Please log in.",
    403: "You are not allowed to access this page. Please contact an administrator.",
    404: "Looks like you've stumbled upon a digital black hole. Maybe you meant to search for an existing page? ;)",
    500: "The server encountered an unexpected error. Please try again later.",
    503: "The server is currently unavailable. Please try again later.",
  };

  return (
    <div className="text-center h-100 bg-transparent">
      <h1 className="mt-3">{code || '404'} - {errorMessages[code] || errorMessages[404]}</h1>
      <p>{errorExplanations[code] || errorExplanations[404]}</p>
    </div>
  )
}
