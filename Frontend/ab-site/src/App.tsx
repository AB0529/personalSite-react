import "bootstrap/dist/css/bootstrap.css";
import "./css/main.scss";

import { useEffect } from "react";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Pages
import { Login } from "./pages/auth/login";
import { NotFound } from "./pages/not-found/not-found";
import { Register } from "./pages/auth/register";
import { Profile } from "./pages/profile/profile";
import { AdminHome } from "./pages/admin/adminHome";
import { AdminUsers } from "./pages/admin/adminUsers";

import { setDarkTheme, setLightTheme } from "./state/setTheme";
import HomePage from "./pages/home/home";
import { LoggedInRoute } from "./pages/auth/components/auth/LoggedInRoute";
import { AdminRoute } from "./pages/auth/components/auth/AdminRoute";

const queryClient = new QueryClient();

const App = () => {
  useEffect(() => {
    const theme = localStorage.getItem("theme");

    // Theme is not set, set dark as default
    if (!theme) localStorage.setItem("theme", "dark");

    switch (theme) {
      case "dark":
        setDarkTheme();
        break;
      case "light":
        setLightTheme();
        break;
    }
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          {/* Home page */}
          <Route path="/" element={<HomePage />} />
          {/* Login page */}
          <Route path="/login" element={<Login />} />
          {/* Login register */}
          <Route path="/register" element={<Register />} />
          {/* User profile LOGGED IN ONLY */}
          <Route path="/profile" element={<LoggedInRoute />}>
            <Route path="/profile" element={<Profile />} />
          </Route>
          {/* Admin pages */}
          <Route path="/admin" element={<AdminRoute />}>
            {/* Admin home */}
            <Route path="/admin" element={<AdminHome />} />
            {/* Admin users */}
            <Route path="/admin/users" element={<AdminUsers />} />{" "}
          </Route>
          {/* 404 Page */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
};

export default App;
