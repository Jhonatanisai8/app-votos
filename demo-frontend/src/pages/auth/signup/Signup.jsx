import React, { useState } from "react";
import { Link as MuiLink } from "@mui/material";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import {
  Avatar,
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
  createTheme,
  CssBaseline,
  Grid,
  TextField,
  ThemeProvider,
  Typography,
} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useSnackbar } from "notistack";
import { signup } from "../../../services/auth/auth";
import { guardarToken } from "../../../utility/common";

const defaultTheme = createTheme();
const Signup = () => {
  const { enqueueSnackbar } = useSnackbar();
  const [formularioDatos, setFormularioDatos] = useState({
    email: "",
    password: "",
    nombres: "",
    apellidos: "",
  });
  const [cargando, setCargando] = useState(false);
  const navigate = useNavigate();
  const manejarCambioDeEntrada = async (event) => {
    const { name, value } = event.target;
    setFormularioDatos({
      ...formularioDatos,
      [name]: value,
    });
  };
  const manejarEnvio = async (event) => {
    event.preventDefault();
    setCargando(true);
    try {
      const response = await signup(formularioDatos);
      if (response.status === 200) {
        const responseData = response.data;
        guardarToken(responseData.data);
        navigate("/dashboard");
        enqueueSnackbar("Registro exitoso", {
          variant: "success",
          autoHideDuration: 5000,
        });
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        enqueueSnackbar("El usuario ya existe", {
          variant: "error",
          autoHideDuration: 5000,
        });
      } else {
        enqueueSnackbar("Error al registrar usuario", {
          variant: "error",
          autoHideDuration: 5000,
        });
      }
    } finally {
      setCargando(false);
    }
  };
  return (
    <>
      <ThemeProvider theme={defaultTheme}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              marginTop: 8,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Avatar
              sx={{
                m: 1,
                bgcolor: "primary.main",
              }}
            >
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Registrarse
            </Typography>
          </Box>
          <Box
            component="form"
            onSubmit={manejarEnvio}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              value={formularioDatos.email}
              onChange={manejarCambioDeEntrada}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              id="password"
              label="Password"
              name="password"
              autoComplete="password"
              autoFocus
              value={formularioDatos.password}
              onChange={manejarCambioDeEntrada}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              id="nombres"
              label="Nombres"
              name="nombres"
              autoComplete="nombres"
              autoFocus
              value={formularioDatos.nombres}
              onChange={manejarCambioDeEntrada}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              id="apellidos"
              label="Apellidos"
              name="apellidos"
              autoComplete="apellidos"
              autoFocus
              value={formularioDatos.apellidos}
              onChange={manejarCambioDeEntrada}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={!formularioDatos.email || !formularioDatos.password}
            >
              {cargando ? (
                <CircularProgress size={24} color="inherit" sx={{ mr: 1 }} />
              ) : (
                "Registrarse"
              )}
            </Button>
            <Grid container>
              <Grid item>
                <MuiLink
                  component={RouterLink}
                  to="/login"
                  variant="body2"
                  sx={{ cursor: "pointer", textDecoration: "none" }}
                >
                  {"¿Ya tienes una cuenta? Inicia Sesión"}
                </MuiLink>
              </Grid>
            </Grid>
          </Box>
        </Container>
      </ThemeProvider>
      <Backdrop
        sx={(theme) => ({ color: "#fff", zIndex: theme.zIndex.drawer + 1 })}
        open={cargando}
      >
        <CircularProgress color="success" />
      </Backdrop>
    </>
  );
};

export default Signup;
