import React, { useEffect, useState } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import {
  AppBar,
  Box,
  Button,
  IconButton,
  Toolbar,
  Typography,
} from "@mui/material";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { eliminarToken, esTokenValido } from "../../utility/common";

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [usurioIniciadoSesion, setUsurioIniciadoSesion] = useState(false);

  const manejarCerrarSesion = () => {
    navigate("/login");
    eliminarToken();
  };

  useEffect(() => {
    const estaLogueado = esTokenValido();
    setUsurioIniciadoSesion(estaLogueado);
  }, [location]);

  useEffect(() => {
    const intervalo = setInterval(() => {
      if (!esTokenValido()) {
        setUsurioIniciadoSesion(false);
        manejarCerrarSesion();
      }
    }, 1800000);
    return () => clearInterval(intervalo);
  }, []);
  return (
    <>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              App Votos
            </Typography>
            {usurioIniciadoSesion ? (
              <>
                <Button component={Link} to="/dashboard" color="inherit">
                  Dashboard
                </Button>
                <Button component={Link} to="/encuesta/crear" color="inherit">
                  Publicar encuesta
                </Button>
                <Button component={Link} to="/mis-encuestas" color="inherit">
                  Miss Encuestas
                </Button>
                <Button
                  color="inherit"
                  onClick={manejarCerrarSesion}
                >
                  Cerrar Sesión
                </Button>
              </>
            ) : (
              <>
                <Button component={Link} to="/login" color="inherit">
                  Login
                </Button>
                <Button component={Link} to="/registro" color="inherit">
                  Registro
                </Button>
              </>
            )}
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
};

export default Header;
